package com.example.flowersdelivery.backend.repository;

import com.example.flowersdelivery.backend.entity.Supplie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplieRepository extends JpaRepository<Supplie, Long> {
}
