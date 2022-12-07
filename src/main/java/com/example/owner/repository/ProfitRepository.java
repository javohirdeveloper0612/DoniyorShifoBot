package com.example.owner.repository;

import com.example.entity.ProfitEntity;
import com.example.owner.ProfitMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProfitRepository extends JpaRepository<ProfitEntity, Integer> {

    @Query(value = "select n.card-o.card as card, n.cash-o.cash as cash, " +
            " n.total-o.total as total, n.created_date as createdDate from input as n " +
            "inner join output as o on o.created_date = n.created_date " +
            "where n.created_date =?1", nativeQuery = true)
    ProfitMapper getByCreatedDate(LocalDate localDate);


    @Query(value = "select n.card-o.card as card, n.cash-o.cash as cash, " +
            " n.total-o.total as total, n.created_date as createdDate from input as n " +
            "inner join output as o on o.created_date = n.created_date " +
            "where n.created_date BETWEEN :start " +
            " AND :now ", nativeQuery = true)
    List<ProfitMapper> getByCreatedDateBetween(@Param("start") LocalDate start, @Param("now") LocalDate now);


}
