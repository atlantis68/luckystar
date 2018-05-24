package com.luckystar.web.web.rest;

import com.luckystar.web.LuckystarApp;

import com.luckystar.web.domain.TaskInfo;
import com.luckystar.web.repository.TaskInfoRepository;
import com.luckystar.web.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TaskInfoResource REST controller.
 *
 * @see TaskInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LuckystarApp.class)
public class TaskInfoResourceIntTest {

    private static final Integer DEFAULT_MIN_TASK = 1;
    private static final Integer UPDATED_MIN_TASK = 2;

    private static final Integer DEFAULT_MAX_TASK = 1;
    private static final Integer UPDATED_MAX_TASK = 2;

    private static final Integer DEFAULT_CUR_MONTH = 1;
    private static final Integer UPDATED_CUR_MONTH = 2;

    private static final Integer DEFAULT_BOUNDARY_VALUE = 1;
    private static final Integer UPDATED_BOUNDARY_VALUE = 2;

    @Autowired
    private TaskInfoRepository taskInfoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaskInfoMockMvc;

    private TaskInfo taskInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaskInfoResource taskInfoResource = new TaskInfoResource(taskInfoRepository);
        this.restTaskInfoMockMvc = MockMvcBuilders.standaloneSetup(taskInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskInfo createEntity(EntityManager em) {
        TaskInfo taskInfo = new TaskInfo()
            .minTask(DEFAULT_MIN_TASK)
            .maxTask(DEFAULT_MAX_TASK)
            .curMonth(DEFAULT_CUR_MONTH)
            .boundaryValue(DEFAULT_BOUNDARY_VALUE);
        return taskInfo;
    }

    @Before
    public void initTest() {
        taskInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskInfo() throws Exception {
        int databaseSizeBeforeCreate = taskInfoRepository.findAll().size();

        // Create the TaskInfo
        restTaskInfoMockMvc.perform(post("/api/task-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskInfo)))
            .andExpect(status().isCreated());

        // Validate the TaskInfo in the database
        List<TaskInfo> taskInfoList = taskInfoRepository.findAll();
        assertThat(taskInfoList).hasSize(databaseSizeBeforeCreate + 1);
        TaskInfo testTaskInfo = taskInfoList.get(taskInfoList.size() - 1);
        assertThat(testTaskInfo.getMinTask()).isEqualTo(DEFAULT_MIN_TASK);
        assertThat(testTaskInfo.getMaxTask()).isEqualTo(DEFAULT_MAX_TASK);
        assertThat(testTaskInfo.getCurMonth()).isEqualTo(DEFAULT_CUR_MONTH);
        assertThat(testTaskInfo.getBoundaryValue()).isEqualTo(DEFAULT_BOUNDARY_VALUE);
    }

    @Test
    @Transactional
    public void createTaskInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskInfoRepository.findAll().size();

        // Create the TaskInfo with an existing ID
        taskInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskInfoMockMvc.perform(post("/api/task-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskInfo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TaskInfo> taskInfoList = taskInfoRepository.findAll();
        assertThat(taskInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMinTaskIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskInfoRepository.findAll().size();
        // set the field null
        taskInfo.setMinTask(null);

        // Create the TaskInfo, which fails.

        restTaskInfoMockMvc.perform(post("/api/task-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskInfo)))
            .andExpect(status().isBadRequest());

        List<TaskInfo> taskInfoList = taskInfoRepository.findAll();
        assertThat(taskInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxTaskIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskInfoRepository.findAll().size();
        // set the field null
        taskInfo.setMaxTask(null);

        // Create the TaskInfo, which fails.

        restTaskInfoMockMvc.perform(post("/api/task-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskInfo)))
            .andExpect(status().isBadRequest());

        List<TaskInfo> taskInfoList = taskInfoRepository.findAll();
        assertThat(taskInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskInfoRepository.findAll().size();
        // set the field null
        taskInfo.setCurMonth(null);

        // Create the TaskInfo, which fails.

        restTaskInfoMockMvc.perform(post("/api/task-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskInfo)))
            .andExpect(status().isBadRequest());

        List<TaskInfo> taskInfoList = taskInfoRepository.findAll();
        assertThat(taskInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaskInfos() throws Exception {
        // Initialize the database
        taskInfoRepository.saveAndFlush(taskInfo);

        // Get all the taskInfoList
        restTaskInfoMockMvc.perform(get("/api/task-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].minTask").value(hasItem(DEFAULT_MIN_TASK)))
            .andExpect(jsonPath("$.[*].maxTask").value(hasItem(DEFAULT_MAX_TASK)))
            .andExpect(jsonPath("$.[*].curMonth").value(hasItem(DEFAULT_CUR_MONTH)))
            .andExpect(jsonPath("$.[*].boundaryValue").value(hasItem(DEFAULT_BOUNDARY_VALUE)));
    }

    @Test
    @Transactional
    public void getTaskInfo() throws Exception {
        // Initialize the database
        taskInfoRepository.saveAndFlush(taskInfo);

        // Get the taskInfo
        restTaskInfoMockMvc.perform(get("/api/task-infos/{id}", taskInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taskInfo.getId().intValue()))
            .andExpect(jsonPath("$.minTask").value(DEFAULT_MIN_TASK))
            .andExpect(jsonPath("$.maxTask").value(DEFAULT_MAX_TASK))
            .andExpect(jsonPath("$.curMonth").value(DEFAULT_CUR_MONTH))
            .andExpect(jsonPath("$.boundaryValue").value(DEFAULT_BOUNDARY_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingTaskInfo() throws Exception {
        // Get the taskInfo
        restTaskInfoMockMvc.perform(get("/api/task-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskInfo() throws Exception {
        // Initialize the database
        taskInfoRepository.saveAndFlush(taskInfo);
        int databaseSizeBeforeUpdate = taskInfoRepository.findAll().size();

        // Update the taskInfo
        TaskInfo updatedTaskInfo = taskInfoRepository.findOne(taskInfo.getId());
        updatedTaskInfo
            .minTask(UPDATED_MIN_TASK)
            .maxTask(UPDATED_MAX_TASK)
            .curMonth(UPDATED_CUR_MONTH)
            .boundaryValue(UPDATED_BOUNDARY_VALUE);

        restTaskInfoMockMvc.perform(put("/api/task-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaskInfo)))
            .andExpect(status().isOk());

        // Validate the TaskInfo in the database
        List<TaskInfo> taskInfoList = taskInfoRepository.findAll();
        assertThat(taskInfoList).hasSize(databaseSizeBeforeUpdate);
        TaskInfo testTaskInfo = taskInfoList.get(taskInfoList.size() - 1);
        assertThat(testTaskInfo.getMinTask()).isEqualTo(UPDATED_MIN_TASK);
        assertThat(testTaskInfo.getMaxTask()).isEqualTo(UPDATED_MAX_TASK);
        assertThat(testTaskInfo.getCurMonth()).isEqualTo(UPDATED_CUR_MONTH);
        assertThat(testTaskInfo.getBoundaryValue()).isEqualTo(UPDATED_BOUNDARY_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskInfo() throws Exception {
        int databaseSizeBeforeUpdate = taskInfoRepository.findAll().size();

        // Create the TaskInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaskInfoMockMvc.perform(put("/api/task-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskInfo)))
            .andExpect(status().isCreated());

        // Validate the TaskInfo in the database
        List<TaskInfo> taskInfoList = taskInfoRepository.findAll();
        assertThat(taskInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaskInfo() throws Exception {
        // Initialize the database
        taskInfoRepository.saveAndFlush(taskInfo);
        int databaseSizeBeforeDelete = taskInfoRepository.findAll().size();

        // Get the taskInfo
        restTaskInfoMockMvc.perform(delete("/api/task-infos/{id}", taskInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaskInfo> taskInfoList = taskInfoRepository.findAll();
        assertThat(taskInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskInfo.class);
        TaskInfo taskInfo1 = new TaskInfo();
        taskInfo1.setId(1L);
        TaskInfo taskInfo2 = new TaskInfo();
        taskInfo2.setId(taskInfo1.getId());
        assertThat(taskInfo1).isEqualTo(taskInfo2);
        taskInfo2.setId(2L);
        assertThat(taskInfo1).isNotEqualTo(taskInfo2);
        taskInfo1.setId(null);
        assertThat(taskInfo1).isNotEqualTo(taskInfo2);
    }
}
