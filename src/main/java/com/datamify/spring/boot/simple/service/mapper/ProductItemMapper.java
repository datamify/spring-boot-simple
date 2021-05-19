package com.datamify.spring.boot.simple.service.mapper;

import com.datamify.spring.boot.simple.data.entity.ProductItem;
import com.datamify.spring.boot.simple.service.domain.CreateItemRequest;
import com.datamify.spring.boot.simple.service.domain.Item;
import com.datamify.spring.boot.simple.service.domain.UpdateItemRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProductItemMapper {

    default Page<Item> map(Page<ProductItem> page) {
        return page.map(this::map);
    }

    Item map(ProductItem productItem);

    @Mapping(target = "id", ignore = true)
    ProductItem map(CreateItemRequest createItemRequest);

    @Mapping(target = "id", ignore = true)
    void map(@MappingTarget ProductItem item, UpdateItemRequest updateItemRequest);

}
