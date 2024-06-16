package com.example.flowersdelivery.backend.service;

import com.example.flowersdelivery.backend.entity.Stock;
import com.example.flowersdelivery.backend.entity.Supplie;
import com.example.flowersdelivery.backend.repository.StockRepository;
import com.example.flowersdelivery.backend.repository.SupplieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplieService {
    @Autowired
    private SupplieRepository supplieRepository;

    @Autowired
    private StockService stockService;

    public List<Supplie> findAll() {
        return supplieRepository.findAll();
    }

    public void save(Supplie supplie) {
        if (supplie == null) {
            return;
        }
        supplieRepository.save(supplie);
    }
    public void delete(Supplie supplie) {
        supplieRepository.delete(supplie);
    }

    @Transactional
    public void acceptDelivery(Long id) {
        Supplie supplie = supplieRepository.getReferenceById(id);
        Stock stock = new Stock();
        stock.setStore(supplie.getStore());
        stock.setFlower(supplie.getFlower());
        stock.setQuantity(supplie.getQuantity());
        stock.setFlowerPrice(supplie.getPrice() * 2);

        stockService.save(stock);
        delete(supplie);
    }
}
