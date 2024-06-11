package com.example.flowersdelivery.backend.service;

import com.example.flowersdelivery.backend.entity.Supplie;
import com.example.flowersdelivery.backend.repository.SupplieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplieService {
    private SupplieRepository supplieRepository;

    public SupplieService(SupplieRepository supplieRepository) {
        this.supplieRepository = supplieRepository;
    }

    public List<Supplie> findAll() {
        return supplieRepository.findAll();
    }
}
