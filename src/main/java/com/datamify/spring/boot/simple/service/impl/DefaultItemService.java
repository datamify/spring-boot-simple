package com.datamify.spring.boot.simple.service.impl;

import com.datamify.spring.boot.simple.data.repository.ProductItemRepository;
import com.datamify.spring.boot.simple.service.ItemService;
import com.datamify.spring.boot.simple.service.domain.CreateItemRequest;
import com.datamify.spring.boot.simple.service.domain.Item;
import com.datamify.spring.boot.simple.service.domain.UpdateItemRequest;
import com.datamify.spring.boot.simple.service.mapper.ProductItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultItemService implements ItemService {

    private final ProductItemRepository repository;
    private final ProductItemMapper mapper;

    @Override
    public Page<Item> findAll(Pageable pageable) {
        return mapper.map(repository.findAll(pageable));
    }

    @Override
    public Item getOne(Long id) {
        return mapper.map(repository.getOne(id));
    }

    @Override
    public Item create(CreateItemRequest createItemRequest) {
        return mapper.map(repository.save(mapper.map(createItemRequest)));
    }

    @Override
    public Item update(Long id, UpdateItemRequest updateItemRequest) {
        final var item = repository.getOne(id);
        mapper.map(item, updateItemRequest);
        return mapper.map(repository.save(item));
    }

    @Override
    public void delete(Long id) {
        final var item = repository.getOne(id);
        repository.delete(item);
    }

}
