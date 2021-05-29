package com.project.bootcamp.service;

import com.project.bootcamp.exceptions.BusinessException;
import com.project.bootcamp.exceptions.NotFoundException;
import com.project.bootcamp.model.Stock;
import com.project.bootcamp.model.dto.StockDTO;
import com.project.bootcamp.repository.StockRepository;
import com.project.bootcamp.util.MessageUtils;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    @Autowired
    private MapperFacade facade;

    @Transactional
    public StockDTO save(StockDTO dto) {
        Optional<Stock> optionalStock = repository.findByNameAndDate(dto.getName(), dto.getDate());

        if (optionalStock.isPresent()) {
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }

        Stock stock = facade.map(dto, Stock.class);
        repository.save(stock);
        return facade.map(stock, StockDTO.class);
    }

    @Transactional
    public StockDTO update(StockDTO dto) {
        Optional<Stock> optionalStock = repository.findByStockUpdate(dto.getName(), dto.getDate(), dto.getId());

        if (optionalStock.isPresent()) {
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }

        Stock stock = facade.map(dto, Stock.class);
        repository.save(stock);
        return facade.map(stock, StockDTO.class);
    }

    @Transactional
    public StockDTO delete(Long id) {
        StockDTO dto = findById(id);
        repository.deleteById(dto.getId());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<StockDTO> findAll() {
        return facade.mapAsList(repository.findAll(), StockDTO.class);
    }

    @Transactional(readOnly = true)
    public StockDTO findById(Long id) {
        return repository.findById(id)
                .map(stock -> facade.map(stock, StockDTO.class))
                .orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<StockDTO> findByToday() {
        return repository.findByToday(LocalDate.now())
                .map(stock -> facade.mapAsList(stock, StockDTO.class))
                .orElseThrow(NotFoundException::new);
    }
}
