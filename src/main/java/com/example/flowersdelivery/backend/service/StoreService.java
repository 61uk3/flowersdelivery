package com.example.flowersdelivery.backend.service;

import com.example.flowersdelivery.backend.entity.Store;
import com.example.flowersdelivery.backend.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> findAll() {
        return storeRepository.findAll();
    }
}
