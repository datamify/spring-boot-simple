package com.datamify.spring.boot.simple.api;

import com.datamify.spring.boot.simple.api.dto.CreateItemDto;
import com.datamify.spring.boot.simple.api.dto.UpdateItemDto;
import com.datamify.spring.boot.simple.api.mapper.ItemApiMapper;
import com.datamify.spring.boot.simple.service.ItemService;
import com.datamify.spring.boot.simple.service.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static com.datamify.spring.boot.simple.api.ItemController.ENDPOINT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ENDPOINT, produces = APPLICATION_JSON_VALUE)
public class ItemController {

    public static final String ENDPOINT = "/items";
    public static final String ENDPOINT_BY_ID = "/{id}";

    private final ItemService service;
    private final ItemApiMapper mapper;

    @GetMapping
    public Page<Item> find(@PageableDefault(sort = "id") Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping(value = ENDPOINT_BY_ID)
    public Item get(@PathVariable Long id) {
        return service.getOne(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public Item create(@RequestBody CreateItemDto createItemDto) {
        return service.create(mapper.map(createItemDto));
    }

    @PutMapping(value = ENDPOINT_BY_ID)
    public Item update(@PathVariable Long id, @RequestBody UpdateItemDto updateItemDto) {
        return service.update(id, mapper.map(updateItemDto));
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = ENDPOINT_BY_ID)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
