package com.luckystar.web.repository;

import com.luckystar.web.domain.LaborUnion;
import com.luckystar.web.domain.UserInfoBoard;
import com.luckystar.web.domain.WorkTimeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by coldvmoon on 13/08/2017.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInfoBoardRepository extends JpaRepository<UserInfoBoard, Long> {

    @Query(value = "SELECT * from (SELECT ci.id, CONCAT(ci.user_name, '/', lu.name) as user_name, ci.nick_name, ci.star_id, CONCAT(wi.star_level,'级','/',wi.star_name) AS star_level, "
    		+ "CONCAT(wi.rich_level,'级','/',wi.rich_name) AS rich_level, wi.fans_count, (wi.bean_total - wi.fisrt_bean) * bean_rate AS bean_by_day, "
    		+ "(SELECT SUM((wi2.bean_total - wi2.fisrt_bean) * bean_rate) FROM work_info wi2  WHERE ti.cur_month = wi2.cur_month AND wi2.star_id = wi.star_id) AS bean_by_month, "
    		+ "ti.min_task, ti.max_task, (SELECT SUM(wi2.work_time * time_rate) FROM work_info wi2  WHERE ti.cur_month = wi2.cur_month AND wi2.star_id = wi.star_id) AS worktime_by_month, "
    		+ "(SELECT CONCAT(SUM(IF(work_time * time_rate >= ti.boundary_value * 60, 1, 0)),'/',SUM(IF(work_time * time_rate < ti.boundary_value * 60 AND work_time * time_rate > 0, 1, 0)),"
    		+ "'/',SUM(IF(work_time * time_rate = 0 or work_time IS NULL, 1, 0)))  FROM work_info wi2  WHERE ti.cur_month = wi2.cur_month AND wi2.star_id = wi.star_id) AS judge_by_month, ci.reg_date "
    		+ "FROM labor_union lu, user_info ci, task_info ti, work_info wi WHERE lu.id = ci.labor_union_id AND ci.star_id = wi.star_id AND wi.task_info_id = ti.id AND lu.id = ?1 "
    		+ "AND wi.cur_day = ?2 AND (ci.user_name LIKE ?3 OR ci.nick_name LIKE ?3 OR ci.star_id LIKE ?3 OR ci.phone_number LIKE ?3 OR ci.qq LIKE ?3 OR ci.wei_chat LIKE ?3)) wi2", nativeQuery = true)
    List<UserInfoBoard> getAllChickenInfosBoardByLid(Long laborUnionId, String day, String searchCondition);


    @Query(value = "SELECT * from (SELECT ci.id, CONCAT(ci.user_name, '/', lu.name) as user_name, ci.nick_name, ci.star_id, CONCAT(wi.star_level,'级','/',wi.star_name) AS star_level, "
    		+ "CONCAT(wi.rich_level,'级','/',wi.rich_name) AS rich_level, wi.fans_count, (wi.bean_total - wi.fisrt_bean) * bean_rate AS bean_by_day, "
    		+ "(SELECT SUM((wi2.bean_total - wi2.fisrt_bean) * bean_rate) FROM work_info wi2  WHERE ti.cur_month = wi2.cur_month AND wi2.star_id = wi.star_id) AS bean_by_month, "
    		+ "ti.min_task, ti.max_task, (SELECT SUM(wi2.work_time * time_rate) FROM work_info wi2  WHERE ti.cur_month = wi2.cur_month AND wi2.star_id = wi.star_id) AS worktime_by_month, "
    		+ "(SELECT CONCAT(SUM(IF(work_time * time_rate >= ti.boundary_value * 60, 1, 0)),'/',SUM(IF(work_time * time_rate < ti.boundary_value * 60 AND work_time * time_rate > 0, 1, 0)),"
    		+ "'/',SUM(IF(work_time * time_rate = 0 or work_time IS NULL, 1, 0)))  FROM work_info wi2  WHERE ti.cur_month = wi2.cur_month AND wi2.star_id = wi.star_id) AS judge_by_month, ci.reg_date "
    		+ "FROM labor_union lu, user_info ci, task_info ti, work_info wi, labor_union_user luu WHERE lu.id = ci.labor_union_id AND ci.star_id = wi.star_id AND wi.task_info_id = ti.id AND luu.users_id = ?1  "
    		+ "AND luu.labor_unions_id = ci.labor_union_id AND wi.cur_day = ?2 AND (ci.user_name LIKE ?3 OR ci.nick_name LIKE ?3 OR ci.star_id LIKE ?3 OR ci.phone_number LIKE ?3 OR ci.qq LIKE ?3 OR ci.wei_chat LIKE ?3)) wi2", nativeQuery = true)
    List<UserInfoBoard> getAllChickenInfosBoard(Long userId, String day, String searchCondition);
}
