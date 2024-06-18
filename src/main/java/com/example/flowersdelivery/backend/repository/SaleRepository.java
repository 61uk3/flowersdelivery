package com.example.flowersdelivery.backend.repository;

import com.example.flowersdelivery.backend.entity.Sale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s.flower, SUM(s.quantity) as totalQuantity " +
            "FROM Sale s " +
            "WHERE s.store.id = :store AND s.saleDate >= :startDate " +
            "GROUP BY s.flower " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findTopFlower(@Param("store") Long id, @Param("startDate") Date startDate, Pageable pageable);

    @Query("SELECT st.name, SUM(stock.flowerPrice / 2 * stock.quantity) AS totalSupPrice, " +
            "(SUM(s.flowerPrice * s.quantity)/2) AS totalSalePrice, " +
            "(SUM(s.flowerPrice * s.quantity) - SUM(stock.flowerPrice / 2 * stock.quantity)) AS difference " +
            "FROM Sale s " +
            "JOIN s.store st " +
            "JOIN Stock stock ON stock.store.id = st.id " +
            "WHERE s.saleDate >= :startDate " +
            "GROUP BY st.name")
    List<Object[]> rotation(@Param("startDate") Date startDate);
}