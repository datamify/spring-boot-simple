package com.datamify.spring.boot.simple.service;

import com.datamify.spring.boot.simple.service.domain.CreateItemRequest;
import com.datamify.spring.boot.simple.service.domain.Item;
import com.datamify.spring.boot.simple.service.domain.UpdateItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface ItemService {

    Page<Item> findAll(@NotNull Pageable pageable);

    Item getOne(@NotNull Long id);

    Item create(@NotNull @Valid CreateItemRequest createItemRequest);

    Item update(@NotNull Long id, @Valid UpdateItemRequest updateItemRequest);

    void delete(@NotNull Long id);

}
