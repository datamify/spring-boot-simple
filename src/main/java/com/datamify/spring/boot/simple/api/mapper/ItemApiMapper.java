package com.datamify.spring.boot.simple.api.mapper;

import com.datamify.spring.boot.simple.api.dto.CreateItemDto;
import com.datamify.spring.boot.simple.api.dto.UpdateItemDto;
import com.datamify.spring.boot.simple.service.domain.CreateItemRequest;
import com.datamify.spring.boot.simple.service.domain.UpdateItemRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemApiMapper {
    
    CreateItemRequest map(CreateItemDto createItemDto);

    UpdateItemRequest map(UpdateItemDto updateItemDto);

}
