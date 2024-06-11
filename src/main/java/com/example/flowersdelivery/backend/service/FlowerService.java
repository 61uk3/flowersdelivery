package com.example.flowersdelivery.backend.service;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.repository.FlowerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowerService {
    private FlowerRepository flowerRepository;

    public FlowerService(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    public List<Flower> findAll() {
        return flowerRepository.findAll();
    }
}
