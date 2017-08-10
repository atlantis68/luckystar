package com.luckystar.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.luckystar.web.domain.TaskInfo;

import com.luckystar.web.repository.TaskInfoRepository;
import com.luckystar.web.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TaskInfo.
 */
@RestController
@RequestMapping("/api")
public class TaskInfoResource {

    private final Logger log = LoggerFactory.getLogger(TaskInfoResource.class);

    private static final String ENTITY_NAME = "taskInfo";

    private final TaskInfoRepository taskInfoRepository;

    public TaskInfoResource(TaskInfoRepository taskInfoRepository) {
        this.taskInfoRepository = taskInfoRepository;
    }

    /**
     * POST  /task-infos : Create a new taskInfo.
     *
     * @param taskInfo the taskInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskInfo, or with status 400 (Bad Request) if the taskInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-infos")
    @Timed
    public ResponseEntity<TaskInfo> createTaskInfo(@Valid @RequestBody TaskInfo taskInfo) throws URISyntaxException {
        log.debug("REST request to save TaskInfo : {}", taskInfo);
        if (taskInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new taskInfo cannot already have an ID")).body(null);
        }
        TaskInfo result = taskInfoRepository.save(taskInfo);
        return ResponseEntity.created(new URI("/api/task-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-infos : Updates an existing taskInfo.
     *
     * @param taskInfo the taskInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskInfo,
     * or with status 400 (Bad Request) if the taskInfo is not valid,
     * or with status 500 (Internal Server Error) if the taskInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-infos")
    @Timed
    public ResponseEntity<TaskInfo> updateTaskInfo(@Valid @RequestBody TaskInfo taskInfo) throws URISyntaxException {
        log.debug("REST request to update TaskInfo : {}", taskInfo);
        if (taskInfo.getId() == null) {
            return createTaskInfo(taskInfo);
        }
        TaskInfo result = taskInfoRepository.save(taskInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /task-infos : get all the taskInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of taskInfos in body
     */
    @GetMapping("/task-infos")
    @Timed
    public List<TaskInfo> getAllTaskInfos() {
        log.debug("REST request to get all TaskInfos");
        return taskInfoRepository.findAll();
    }

    /**
     * GET  /task-infos/:id : get the "id" taskInfo.
     *
     * @param id the id of the taskInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskInfo, or with status 404 (Not Found)
     */
    @GetMapping("/task-infos/{id}")
    @Timed
    public ResponseEntity<TaskInfo> getTaskInfo(@PathVariable Long id) {
        log.debug("REST request to get TaskInfo : {}", id);
        TaskInfo taskInfo = taskInfoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskInfo));
    }

    /**
     * DELETE  /task-infos/:id : delete the "id" taskInfo.
     *
     * @param id the id of the taskInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskInfo(@PathVariable Long id) {
        log.debug("REST request to delete TaskInfo : {}", id);
        taskInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
