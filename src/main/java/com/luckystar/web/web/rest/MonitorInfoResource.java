package com.luckystar.web.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.luckystar.web.domain.MonitorInfo;
import com.luckystar.web.domain.User;
import com.luckystar.web.repository.MonitorInfoRepository;
import com.luckystar.web.repository.UserRepository;
import com.luckystar.web.security.SecurityUtils;

/**
 * Created by coldvmoon on 09/12/2017.
 */
@RestController
@RequestMapping("/api")
public class MonitorInfoResource {
	
	private final Logger log = LoggerFactory.getLogger(MonitorInfoResource.class);

    @Autowired
    private MonitorInfoRepository monitorInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/monitor-infos")
    @Timed
    public ResponseEntity<List<MonitorInfo>> monitorInfos() throws URISyntaxException {
    	log.info("{} request {}", SecurityUtils.getCurrentUserLogin(), "monitorInfos");
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        List<MonitorInfo> workInfos=monitorInfoRepository.getMonitorInfo(user.get().getId());
        return ResponseEntity.ok().body(workInfos);
    }
}
