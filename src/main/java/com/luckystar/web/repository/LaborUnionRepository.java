package com.luckystar.web.repository;

import com.luckystar.web.domain.LaborUnion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the LaborUnion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LaborUnionRepository extends JpaRepository<LaborUnion,Long> {

    @Query(value = "select * FROM labor_union,labor_union_user WHERE labor_union.id=labor_union_user.labor_unions_id AND labor_union_user.users_id = ?1 /* #pageable */",nativeQuery = true)
    Page<LaborUnion> findByUserIsCurrentUser(Long id,Pageable pageable);

    @Query(value = "select * FROM labor_union,labor_union_user WHERE labor_union.id=labor_union_user.labor_unions_id AND labor_union_user.users_id = ?1 /* #pageable */",nativeQuery = true)
    List<LaborUnion> findByUserIsCurrentUser(Long id);

    @Query("select distinct labor_union from LaborUnion labor_union left join fetch labor_union.users")
    List<LaborUnion> findAllWithEagerRelationships();

    @Query("select labor_union from LaborUnion labor_union left join fetch labor_union.users where labor_union.id =:id")
    LaborUnion findOneWithEagerRelationships(@Param("id") Long id);

    LaborUnion findLaborUnionById(Long id);
}
