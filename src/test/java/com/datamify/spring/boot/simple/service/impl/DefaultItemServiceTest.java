package com.datamify.spring.boot.simple.service.impl;

import com.datamify.spring.boot.simple.data.entity.ProductItem;
import com.datamify.spring.boot.simple.data.repository.ProductItemRepository;
import com.datamify.spring.boot.simple.service.domain.CreateItemRequest;
import com.datamify.spring.boot.simple.service.domain.Item;
import com.datamify.spring.boot.simple.service.domain.UpdateItemRequest;
import com.datamify.spring.boot.simple.service.mapper.ProductItemMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultItemServiceTest {

    private static final Long ITEM_ID = 1L;

    @Mock
    private ProductItemRepository repository;

    @Mock
    private ProductItemMapper mapper;

    @InjectMocks
    private DefaultItemService service;

    @Test
    void shouldFindAll() {
        final Pageable pageable = PageRequest.of(0, 1);
        final int total = 1;
        final ProductItem productItem = getProductItem();
        final Item item = getItem();

        when(repository.findAll(pageable)).thenReturn(
                new PageImpl<>(
                        List.of(productItem),
                        pageable,
                        total
                )
        );

        when(mapper.map(new PageImpl<>(List.of(productItem), pageable, total))).thenReturn(
                new PageImpl<>(
                        List.of(item),
                        pageable,
                        total
                )
        );

        final Page<Item> items = service.findAll(pageable);
        assertThat(items).hasSize(1);
        assertThat(items.getContent().get(0)).isEqualTo(item);
    }

    @Test
    void shouldGetItem() {
        final ProductItem productItem = getProductItem();
        final Item item = getItem();

        when(repository.getOne(ITEM_ID)).thenReturn(productItem);
        when(mapper.map(productItem)).thenReturn(item);

        final Item foundItem = service.getOne(ITEM_ID);
        assertThat(foundItem).isEqualTo(item);
    }

    @Test
    void shouldCreateItem() {
        final CreateItemRequest createItemRequest = CreateItemRequest.builder()
                .title("title")
                .build();
        final ProductItem productItem = getProductItem();
        final Item item = getItem();

        when(mapper.map(createItemRequest)).thenReturn(productItem);
        when(repository.save(productItem)).thenReturn(productItem);
        when(mapper.map(productItem)).thenReturn(item);

        final Item createdItem = service.create(createItemRequest);
        assertThat(createdItem).isEqualTo(item);
    }

    @Test
    void shouldUpdateItem() {
        final UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .title("title")
                .build();
        final ProductItem productItem = getProductItem();
        final Item item = getItem();

        when(repository.getOne(ITEM_ID)).thenReturn(productItem);
        when(repository.save(productItem)).thenReturn(productItem);
        doAnswer(invocation -> {
            final ProductItem existingItem = invocation.getArgument(0);
            final UpdateItemRequest requestedItem = invocation.getArgument(1);
            existingItem.setTitle(requestedItem.getTitle());
            return null;
        }).when(mapper).map(productItem, updateItemRequest);
        when(mapper.map(productItem)).thenReturn(item);

        final Item updatedItem = service.update(ITEM_ID, updateItemRequest);
        assertThat(updatedItem).isEqualTo(item);
        verify(mapper).map(productItem, updateItemRequest);
    }

    @Test
    void shouldDeleteItem() {
        final ProductItem productItem = getProductItem();

        when(repository.getOne(ITEM_ID)).thenReturn(productItem);

        service.delete(ITEM_ID);
        verify(repository).delete(productItem);
    }

    private ProductItem getProductItem() {
        return ProductItem.builder()
                .id(ITEM_ID)
                .title("product title")
                .build();
    }

    private Item getItem() {
        return Item.builder()
                .id(ITEM_ID)
                .title("product title")
                .build();
    }

}
