package com.luckystar.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.luckystar.web.domain.User;
import com.luckystar.web.domain.UserInfo;
import com.luckystar.web.domain.WorkInfo;

import com.luckystar.web.repository.UserRepository;
import com.luckystar.web.repository.WorkInfoRepository;
import com.luckystar.web.security.SecurityUtils;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WorkInfo.
 */
@RestController
@RequestMapping("/api")
public class WorkInfoResource {

    private final Logger log = LoggerFactory.getLogger(WorkInfoResource.class);

    private static final String ENTITY_NAME = "workInfo";

    private final WorkInfoRepository workInfoRepository;

    @Autowired
    private UserRepository userRepository;
    public WorkInfoResource(WorkInfoRepository workInfoRepository) {
        this.workInfoRepository = workInfoRepository;
    }

    /**
     * POST  /work-infos : Create a new workInfo.
     *
     * @param workInfo the workInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workInfo, or with status 400 (Bad Request) if the workInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-infos")
    @Timed
    public ResponseEntity<WorkInfo> createWorkInfo(@Valid @RequestBody WorkInfo workInfo) throws URISyntaxException {
        log.debug("REST request to save WorkInfo : {}", workInfo);
        if (workInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new workInfo cannot already have an ID")).body(null);
        }
        WorkInfo result = workInfoRepository.save(workInfo);
        return ResponseEntity.created(new URI("/api/work-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-infos : Updates an existing workInfo.
     *
     * @param workInfo the workInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workInfo,
     * or with status 400 (Bad Request) if the workInfo is not valid,
     * or with status 500 (Internal Server Error) if the workInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-infos")
    @Timed
    public ResponseEntity<WorkInfo> updateWorkInfo(@Valid @RequestBody WorkInfo workInfo) throws URISyntaxException {
        log.debug("REST request to update WorkInfo : {}", workInfo);
        if (workInfo.getId() == null) {
            return createWorkInfo(workInfo);
        }
        WorkInfo result = workInfoRepository.save(workInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-infos : get all the workInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of workInfos in body
     */
    @GetMapping("/work-infos")
    @Timed
    public ResponseEntity<List<WorkInfo>> getAllWorkInfos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of WorkInfos");
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        Page<WorkInfo> page=null;
        if(user.get().getLogin().equals("system")){
            page = workInfoRepository.findAll(pageable);
        }else {
            Tools.humpToline(pageable);
            page = workInfoRepository.findByUserIsCurrentUser(user.get().getId(),pageable);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /work-infos/:id : get the "id" workInfo.
     *
     * @param id the id of the workInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workInfo, or with status 404 (Not Found)
     */
    @GetMapping("/work-infos/{id}")
    @Timed
    public ResponseEntity<WorkInfo> getWorkInfo(@PathVariable Long id) {
        log.debug("REST request to get WorkInfo : {}", id);
        WorkInfo workInfo = workInfoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workInfo));
    }

    /**
     * DELETE  /work-infos/:id : delete the "id" workInfo.
     *
     * @param id the id of the workInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkInfo(@PathVariable Long id) {
        log.debug("REST request to delete WorkInfo : {}", id);
        workInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
