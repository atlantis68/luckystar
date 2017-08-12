package com.luckystar.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.luckystar.web.repository.LaborUnionRepository;
import io.swagger.annotations.ApiParam;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@RestController
@RequestMapping("/api")
public class DashboardResource {
    private final Logger log = LoggerFactory.getLogger(ChickenInfoResource.class);



    @Autowired
    private LaborUnionRepository laborUnionRepository;
    @Autowired
    private EntityManager em;

    @GetMapping("/chicken-infos-board")
    @Timed
    public ResponseEntity<List<Map>> getAllChickenInfosBoard(@RequestParam("day") String day) {
        log.debug("REST request to get a page of ChickenInfos");
        Query query = em.createNativeQuery("SELECT  ci.user_name,  ci.nick_name,  ci.star_id,  wi.star_level,  wi.rich_level,  wi.fans_count,  (wi.bean_total - wi.fisrt_bean) AS bean_by_day,  ti.min_task,  ti.max_task,  ci.reg_date,  (SELECT     SUM(wi2.bean_total - wi2.fisrt_bean)   FROM work_info wi2   WHERE ti.cur_month = wi2.cur_month       AND wi2.star_id = wi.star_id) AS bean_by_month,  (SELECT     SUM(wi2.work_time) AS bean   FROM work_info wi2   WHERE ti.cur_month = wi2.cur_month       AND wi2.star_id = wi.star_id) AS worktime_by_month1,  (SELECT     SUM(IF(work_time > 14400, 1, 0.5)) AS bean   FROM work_info wi2   WHERE ti.cur_month = wi2.cur_month       AND wi2.star_id = wi.star_id) AS worktime_by_month,         wi.work_time FROM labor_union lu,  chicken_info ci,  task_info ti,  work_info wi WHERE lu.id  = ci.labor_union_id    AND ci.star_id = wi.star_id    AND wi.task_info_id = ti.id     AND lu.l_id = '5544'    AND wi.cur_day = '"+day+"'");
        List<Object[]> list = query.getResultList();
        List<Map> data = new ArrayList<>();
        for (Object[] obj : list) {
            Map res = new HashMap<String, Object>();
            res.put("userName", obj[0]);
            res.put("nickName", obj[1]);
            res.put("starId", obj[2]);
            res.put("starLevel", obj[3]);
            res.put("richLevel", obj[4]);
            res.put("fansCount", obj[5]);
            res.put("beanByDay", obj[6]);

            res.put("minTask", obj[7]);
            res.put("maxTask", obj[8]);
            res.put("regDate", obj[9]);
            res.put("beanByMonth", obj[10]);
            res.put("workTimeByMonth", obj[11]);
            res.put("workTimeByDay", obj[12]);
            data.add(res);
        }


        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/work-time-board")
    @Timed
    public ResponseEntity<List<Map>> getWorkTimeBoard(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ChickenInfos");
        Query query = em.createNativeQuery("SELECT  ci.user_name,  ci.nick_name,  ci.star_id,  (SELECT     SUM(work_time) AS all_time   FROM work_info wi2   WHERE ti.cur_month = wi2.cur_month       AND wi2.star_id = wi.star_id) AS worktime_by_month1,  (SELECT     SUM(IF(work_time > 14400, 1, 0.5)) AS bean   FROM work_info wi2   WHERE ti.cur_month = wi2.cur_month       AND wi2.star_id = wi.star_id) AS worktime_by_month,  wi.work_time,  wi.cur_day FROM labor_union lu,  chicken_info ci,  task_info ti,  work_info wi WHERE lu.id  = ci.labor_union_id    AND ci.star_id = wi.star_id    AND wi.task_info_id = ti.id     AND lu.l_id = '5544'    AND wi.cur_month = 201708");
        List<Object[]> list = query.getResultList();
        List<Map> data = new ArrayList<>();
        for (Object[] obj : list) {
            Map res = new HashMap<String, Object>();
            res.put("userName", obj[0]);
            res.put("nickName", obj[1]);
            res.put("starId", obj[2]);
            res.put("totalTime", obj[3]);
            res.put("totalDay", obj[4]);
            res.put("workTime", obj[5]);
            res.put("curDay", obj[6]);
            data.add(res);
        }


        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/recent-time")
    @Timed
    public ResponseEntity<List<String>> getRecentTime() {
//        List<String> Jot
        List<String> res = new ArrayList();

        DateTime dt = new DateTime();
        for(int i = 0;i<7;i++) {
            res.add(dt.plusDays(-i).toString("yyyy-MM-dd"));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}