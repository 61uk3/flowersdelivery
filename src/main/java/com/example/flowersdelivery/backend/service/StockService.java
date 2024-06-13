package com.example.flowersdelivery.backend.service;

import com.example.flowersdelivery.backend.entity.Stock;
import com.example.flowersdelivery.backend.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public void save(Stock stock) {
        if (stock == null) {
            return;
        }
        stockRepository.save(stock);
    }

    public void delete(Stock stock) {
        stockRepository.delete(stock);
    }
}
