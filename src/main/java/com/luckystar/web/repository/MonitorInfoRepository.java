package com.luckystar.web.repository;

import com.luckystar.web.domain.MonitorInfo;
import com.luckystar.web.domain.WorkTimeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by coldvmoon on 09/12/2017.
 */
@Repository
public interface MonitorInfoRepository extends JpaRepository<MonitorInfo, Long> {
    @Query(value = " SELECT \n" +
        "    user_info.id,\n" +
        "    user_info.user_name,\n" +
        "    user_info.nick_name,\n" +
        "    user_info.room_id,\n" +
        "    user_info.star_id,\n" +
        "    work_info.online_status\n" +
        "FROM\n" +
        "    user_info,\n" +
        "    work_info\n" +
        "WHERE\n" +
        "    user_info.star_id = work_info.star_id\n" +
        "     AND work_info.cur_day=DATE_FORMAT(NOW(),'%Y-%m-%d')\n" +
        "     AND work_info.online_status = 1"+
        "        AND user_info.labor_union_id IN (SELECT \n" +
        "            labor_unions_id\n" +
        "        FROM\n" +
        "            labor_union_user\n" +
        "        WHERE\n" +
        "            users_id = ?1)",nativeQuery=true)
    List<MonitorInfo> getMonitorInfo(Long id);
}
