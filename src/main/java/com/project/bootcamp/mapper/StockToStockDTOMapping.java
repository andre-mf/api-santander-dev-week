package com.project.bootcamp.mapper;

import com.project.bootcamp.model.Stock;
import com.project.bootcamp.model.dto.StockDTO;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class StockToStockDTOMapping extends ConfigurableMapper {

    protected void configure(MapperFactory mapperFactory) {
        mapperFactory.classMap(Stock.class, StockDTO.class)
                .byDefault()
                .register();
    }
}
