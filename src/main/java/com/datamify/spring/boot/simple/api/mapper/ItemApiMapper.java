package com.datamify.spring.boot.simple.api.mapper;

import com.datamify.spring.boot.simple.api.dto.CreateItemDto;
import com.datamify.spring.boot.simple.api.dto.UpdateItemDto;
import com.datamify.spring.boot.simple.service.domain.CreateItemRequest;
import com.datamify.spring.boot.simple.service.domain.UpdateItemRequest;
import org.springframework.stereotype.Component;

@Component
public class ItemApiMapper {

    public CreateItemRequest map(CreateItemDto createItemDto) {
        return CreateItemRequest.builder()
                .title(createItemDto.getTitle())
                .build();
    }

    public UpdateItemRequest map(UpdateItemDto updateItemDto) {
        return UpdateItemRequest.builder()
                .title(updateItemDto.getTitle())
                .build();
    }

}
