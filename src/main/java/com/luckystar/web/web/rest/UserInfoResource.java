package com.luckystar.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.luckystar.web.domain.LaborUnion;
import com.luckystar.web.domain.User;
import com.luckystar.web.domain.UserInfo;

import com.luckystar.web.repository.LaborUnionRepository;
import com.luckystar.web.repository.UserInfoRepository;
import com.luckystar.web.repository.UserRepository;
import com.luckystar.web.security.SecurityUtils;
import com.luckystar.web.utils.RSA;
import com.luckystar.web.utils.Tools;
import com.luckystar.web.web.rest.util.HeaderUtil;
import com.luckystar.web.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * REST controller for managing UserInfo.
 */
@RestController
@RequestMapping("/api")
public class UserInfoResource {

    private final Logger log = LoggerFactory.getLogger(UserInfoResource.class);

    private static final String ENTITY_NAME = "userInfo";

    private final UserInfoRepository userInfoRepository;

    @Autowired
    private LaborUnionRepository laborUnionRepository;

    @Autowired
    private UserRepository userRepository;

    public UserInfoResource(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    /**
     * POST  /user-infos : Create a new userInfo.
     *
     * @param userInfo the userInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userInfo, or with status 400 (Bad Request) if the userInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-infos")
    @Timed
    public ResponseEntity createUserInfo(@Valid @RequestBody UserInfo userInfo) throws URISyntaxException {
        log.debug("REST request to save UserInfo : {}", userInfo);
        LaborUnion laborUnion = laborUnionRepository.findLaborUnionById(userInfo.getLaborUnion().getId());
        Long total = userInfoRepository.countByLaborUnionId(userInfo.getLaborUnion().getId());
        if(total.compareTo(laborUnion.getMaxMember())>=0){
            return  ResponseEntity.badRequest().body(null);
        }
        if (userInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userInfo cannot already have an ID")).body(null);
        }
        userInfo.setBeanRate(1f);
        userInfo.setTimeRate(1f);
        userInfo.setPassword("*加密*"+RSA.encryptByPublic(userInfo.getPassword()));
        userInfo.setLastMaintain(LocalDate.now());
        UserInfo result = userInfoRepository.save(userInfo);
        return ResponseEntity.created(new URI("/api/user-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);

    }

    /**
     * PUT  /user-infos : Updates an existing userInfo.
     *
     * @param userInfo the userInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userInfo,
     * or with status 400 (Bad Request) if the userInfo is not valid,
     * or with status 500 (Internal Server Error) if the userInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-infos")
    @Timed
    public ResponseEntity<UserInfo> updateUserInfo(@Valid @RequestBody UserInfo userInfo) throws URISyntaxException {
        log.debug("REST request to update UserInfo : {}", userInfo);
        if (userInfo.getId() == null) {
            return createUserInfo(userInfo);
        }
        if (!userInfo.getPassword().startsWith("*加密*")) {
            userInfo.setPassword("*加密*" + RSA.encryptByPublic(userInfo.getPassword()));
        }
        userInfo.setLastMaintain(LocalDate.now());
        UserInfo result = userInfoRepository.save(userInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-infos : get all the userInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userInfos in body
     */
    @GetMapping("/user-infos")
    @Timed
    public ResponseEntity<List<UserInfo>> getAllUserInfos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UserInfos");

        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        Page<UserInfo> page = null;
        if (user.get().getLogin().equals("system")) {
            page = userInfoRepository.findAll(pageable);
        } else {
            Tools.humpToline(pageable);
            page = userInfoRepository.findByUserIsCurrentUser(user.get().getId(), pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-infos/:id : get the "id" userInfo.
     *
     * @param id the id of the userInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userInfo, or with status 404 (Not Found)
     */
    @GetMapping("/user-infos/{id}")
    @Timed
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable Long id) {
        log.debug("REST request to get UserInfo : {}", id);
        UserInfo userInfo = userInfoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userInfo));
    }

    /**
     * DELETE  /user-infos/:id : delete the "id" userInfo.
     *
     * @param id the id of the userInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserInfo(@PathVariable Long id) {
        log.debug("REST request to delete UserInfo : {}", id);
        userInfoRepository.deleteWorkInfoById(id);
        userInfoRepository.deleteTaskInfoById(id);
        userInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
