package com.luckystar.web.repository;

import com.luckystar.web.domain.UserInfoBoard;
import com.luckystar.web.domain.WorkTimeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by coldvmoon on 16/08/2017.
 */
@SuppressWarnings("unused")
@Repository

public interface WorkTimeBoardRepository  extends JpaRepository<WorkTimeBoard, Long> {
	@Query(value = "SELECT * from (SELECT wi.id, CONCAT(ci.user_name, '/', lu.name) as user_name, ci.nick_name, ci.star_id, (SELECT    SUM(wi2.work_time * time_rate)  FROM work_info wi2  "
			+ "WHERE ti.cur_month = wi2.cur_month      AND wi2.star_id = wi.star_id) AS worktime_by_month, "
			+ "(SELECT    CONCAT(SUM(IF(work_time * time_rate >= ti.boundary_value * 60, 1, 0)),'/',"
			+ "SUM(IF(work_time * time_rate < ti.boundary_value * 60 AND work_time * time_rate > 0, 1, 0)),'/',"
			+ "SUM(IF(work_time * time_rate = 0 or work_time IS NULL, 1, 0)))  FROM work_info wi2  "
			+ "WHERE ti.cur_month = wi2.cur_month      AND wi2.star_id = wi.star_id) AS judge_by_month,    "
			+ "wi.work_time * time_rate as work_time, wi.cur_day, ti.boundary_value FROM labor_union lu, user_info ci, task_info ti, work_info wi "
			+ "WHERE lu.id = ci.labor_union_id   AND ci.star_id = wi.star_id   AND wi.task_info_id = ti.id   AND lu.id = ?1   "
			+ "AND wi.cur_month = ?2   AND (ci.user_name LIKE ?3   OR ci.nick_name LIKE ?3   OR ci.star_id LIKE ?3   OR ci.phone_number LIKE ?3   "
			+ "OR ci.qq LIKE ?3   OR ci.wei_chat LIKE ?3)) wi2 ",nativeQuery = true)
	List<WorkTimeBoard> getWorkTimeBoardCurMonthByLid(Long laborUnionId, Long days, String searchCondition);
	
	@Query(value = "SELECT * from (SELECT wi.id, CONCAT(ci.user_name, '/', lu.name) as user_name, ci.nick_name, ci.star_id, (SELECT    SUM(wi2.work_time * time_rate)  "
			+ "FROM work_info wi2  WHERE ti.cur_month = wi2.cur_month      AND wi2.star_id = wi.star_id) AS worktime_by_month, "
			+ "(SELECT    CONCAT(SUM(IF(work_time * time_rate >= ti.boundary_value * 60, 1, 0)),'/',"
			+ "SUM(IF(work_time * time_rate < ti.boundary_value * 60 AND work_time * time_rate > 0, 1, 0)),'/',"
			+ "SUM(IF(work_time * time_rate = 0 or work_time IS NULL, 1, 0)))  FROM work_info wi2  WHERE ti.cur_month = wi2.cur_month      "
			+ "AND wi2.star_id = wi.star_id) AS judge_by_month,    wi.work_time * time_rate as work_time, wi.cur_day, ti.boundary_value "
			+ "FROM labor_union lu, user_info ci, task_info ti, work_info wi WHERE lu.id = ci.labor_union_id   AND ci.star_id = wi.star_id   "
			+ "AND wi.task_info_id = ti.id   AND lu.id = ?1   AND wi.cur_day IN ?2   AND (ci.user_name LIKE ?3   OR ci.nick_name LIKE ?3   "
			+ "OR ci.star_id LIKE ?3   OR ci.phone_number LIKE ?3   OR ci.qq LIKE ?3   OR ci.wei_chat LIKE ?3)) wi2 ",nativeQuery = true)
	List<WorkTimeBoard> getWorkTimeBoardByDayByLid(Long laborUnionId, List<String> days, String searchCondition);
	
	@Query(value = "SELECT * from (SELECT wi.id, CONCAT(ci.user_name, '/', lu.name) as user_name, ci.nick_name, ci.star_id, (SELECT    SUM(wi2.work_time * time_rate)  FROM work_info wi2  "
			+ "WHERE ti.cur_month = wi2.cur_month      AND wi2.star_id = wi.star_id) AS worktime_by_month, "
			+ "(SELECT    CONCAT(SUM(IF(work_time * time_rate >= ti.boundary_value * 60, 1, 0)),'/',"
			+ "SUM(IF(work_time * time_rate < ti.boundary_value * 60 AND work_time * time_rate > 0, 1, 0)),'/',"
			+ "SUM(IF(work_time * time_rate = 0 or work_time IS NULL, 1, 0)))  FROM work_info wi2  "
			+ "WHERE ti.cur_month = wi2.cur_month      AND wi2.star_id = wi.star_id) AS judge_by_month,    "
			+ "wi.work_time * time_rate as work_time, wi.cur_day, ti.boundary_value FROM labor_union lu, user_info ci, task_info ti, work_info wi, "
			+ "labor_union_user luu WHERE lu.id = ci.labor_union_id   AND ci.star_id = wi.star_id   AND wi.task_info_id = ti.id  AND luu.users_id = ?1 "
			+ "AND luu.labor_unions_id = ci.labor_union_id AND wi.cur_month = ?2   AND (ci.user_name LIKE ?3   OR ci.nick_name LIKE ?3   OR ci.star_id LIKE ?3   "
			+ "OR ci.phone_number LIKE ?3  OR ci.qq LIKE ?3   OR ci.wei_chat LIKE ?3)) wi2 ",nativeQuery = true)
	List<WorkTimeBoard> getWorkTimeBoardCurMonth(Long userId, Long days, String searchCondition);
	
	@Query(value = "SELECT * from (SELECT wi.id, CONCAT(ci.user_name, '/', lu.name) as user_name, ci.nick_name, ci.star_id, (SELECT    SUM(wi2.work_time * time_rate)  "
			+ "FROM work_info wi2  WHERE ti.cur_month = wi2.cur_month      AND wi2.star_id = wi.star_id) AS worktime_by_month, "
			+ "(SELECT    CONCAT(SUM(IF(work_time * time_rate >= ti.boundary_value * 60, 1, 0)),'/',"
			+ "SUM(IF(work_time * time_rate < ti.boundary_value * 60 AND work_time * time_rate > 0, 1, 0)),'/',"
			+ "SUM(IF(work_time * time_rate = 0 or work_time IS NULL, 1, 0)))  FROM work_info wi2  WHERE ti.cur_month = wi2.cur_month      "
			+ "AND wi2.star_id = wi.star_id) AS judge_by_month,    wi.work_time * time_rate as work_time, wi.cur_day, ti.boundary_value "
			+ "FROM labor_union lu, user_info ci, task_info ti, work_info wi, labor_union_user luu WHERE lu.id = ci.labor_union_id   AND ci.star_id = wi.star_id   "
			+ "AND luu.users_id = ?1 AND luu.labor_unions_id = ci.labor_union_id AND wi.task_info_id = ti.id   AND wi.cur_day IN ?2  "
			+ "AND (ci.user_name LIKE ?3   OR ci.nick_name LIKE ?3  OR ci.star_id LIKE ?3   OR ci.phone_number LIKE ?3   "
			+ "OR ci.qq LIKE ?3   OR ci.wei_chat LIKE ?3)) wi2 ",nativeQuery = true)
	List<WorkTimeBoard> getWorkTimeBoardByDay(Long userId, List<String> days, String searchCondition);
}
