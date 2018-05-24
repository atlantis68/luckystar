package com.luckystar.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luckystar.web.domain.UserInfo;


/**
 * Spring Data JPA repository for the UserInfo entity.
 */
@SuppressWarnings("unused")
@Repository
@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    @Query(value = "SELECT user_info.* FROM user_info, labor_union, labor_union_user WHERE user_info.labor_union_id = labor_union.id AND labor_union.id = labor_union_user.labor_unions_id  AND labor_union_user.users_id =?1 /* #pageable */",nativeQuery = true)
    Page<UserInfo> findByUserIsCurrentUser(Long id, Pageable pageable);

    @Query(value = "SELECT room_id FROM user_info WHERE id = ?1",nativeQuery = true)
    String getRoomIdById(Long id);

    @Query(value = "SELECT lu.streaming_ip FROM user_info ui, labor_union lu WHERE ui.id = ?1 and ui.labor_union_id = lu.id",nativeQuery = true)
    String getStreamingIpById(Long id);

    @Query(value = "SELECT * FROM user_info WHERE id = ?1",nativeQuery = true)
    UserInfo getUserInfoById(Long id);

    @Modifying
    @Query(value = "DELETE FROM work_info WHERE task_info_id IN (SELECT id FROM task_info WHERE user_info_id IN (SELECT id FROM user_info WHERE id = ?1))",nativeQuery = true)
    void deleteWorkInfoById(Long id);

    @Modifying
    @Query(value = "DELETE FROM task_info WHERE user_info_id IN (SELECT id FROM user_info WHERE id = ?1)",nativeQuery = true)
    void deleteTaskInfoById(Long id);

    Long countByLaborUnionId(Long laborUnionId);
}
