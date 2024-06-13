package com.example.flowersdelivery.backend.service;

import com.example.flowersdelivery.backend.entity.Sale;
import com.example.flowersdelivery.backend.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    private SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public void save(Sale sale) {
        if (sale == null) {
            return;
        }
        saleRepository.save(sale);
    }

    public void delete(Sale sale) {
        saleRepository.delete(sale);
    }
}
