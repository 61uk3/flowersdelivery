package com.example.flowersdelivery.backend.repository;

import com.example.flowersdelivery.backend.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {

}
