package com.example.flowersdelivery.backend.service;

import com.example.flowersdelivery.backend.entity.Sale;
import com.example.flowersdelivery.backend.entity.Stock;
import com.example.flowersdelivery.backend.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private SaleService saleService;

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

    @Transactional
    public void sale(Long id, int quantity) {
        Stock stock = stockRepository.getReferenceById(id);

        Sale sale = new Sale();
        sale.setFlower(stock.getFlower());
        sale.setQuantity(quantity);
        sale.setStore(stock.getStore());
        sale.setFlowerPrice(stock.getFlowerPrice());

        stock.setQuantity(stock.getQuantity()-quantity);

        if (stock.getQuantity() == 0) {
            stockRepository.delete(stock);
        } else {
            stockRepository.save(stock);
        }
        saleService.save(sale);
    }
}
