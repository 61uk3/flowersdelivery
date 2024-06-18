package com.example.flowersdelivery.backend.service;

import com.example.flowersdelivery.backend.DTOs.FlowerSalesDTO;
import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Sale;
import com.example.flowersdelivery.backend.entity.Stock;
import com.example.flowersdelivery.backend.entity.Store;
import com.example.flowersdelivery.backend.repository.SaleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<FlowerSalesDTO> findTopFlower(Long id) {
        List<Object[]> results = saleRepository.findTopFlower(
                id,
                Date.from(LocalDate.now().minusWeeks(1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                PageRequest.of(0, 2)
        );

        return results.stream()
                .map(res -> new FlowerSalesDTO((Flower) res[0], (Long) res[1]))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> rotation() {
        List<Object[]> totalSupPrices = saleRepository.getTotalSupPrice();
        List<Object[]> totalSalePrices = saleRepository.getTotalSalePrice(Date.from(LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Map<String, Double> supPriceMap = new HashMap<>();
        for (Object[] result : totalSupPrices) {
            supPriceMap.put((String) result[0], (Double) result[1]);
        }

        List<Map<String, Object>> report = new ArrayList<>();
        for (Object[] result : totalSalePrices) {
            String storeName = (String) result[0];
            Double totalSalePrice = (Double) result[1];
            Double totalSupPrice = supPriceMap.getOrDefault(storeName, 0.0);
            Double difference = totalSalePrice - totalSupPrice;

            Map<String, Object> reportEntry = new HashMap<>();
            reportEntry.put("storeName", storeName);
            reportEntry.put("totalSupPrice", totalSupPrice);
            reportEntry.put("totalSalePrice", totalSalePrice);
            reportEntry.put("difference", difference);

            report.add(reportEntry);
        }

        return report;
    }

}
