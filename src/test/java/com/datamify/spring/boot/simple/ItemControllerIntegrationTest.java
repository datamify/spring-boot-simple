package com.datamify.spring.boot.simple;

import com.datamify.spring.boot.simple.api.dto.CreateItemDto;
import com.datamify.spring.boot.simple.api.dto.UpdateItemDto;
import com.datamify.spring.boot.simple.service.domain.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Sql(scripts = {
        "/sql/clean.sql",
        "/sql/product_items.sql"
})
public class ItemControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void shouldGetItem() {
        final HttpEntity<Void> entity = new HttpEntity<>(httpHeaders());

        ResponseEntity<Item> foundItem = restTemplate.exchange(url("/items/1"), HttpMethod.GET, entity, Item.class);
        assertThat(foundItem.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(foundItem.getBody()).isNotNull();
        assertThat(foundItem.getBody().getId()).isEqualTo(1L);
        assertThat(foundItem.getBody().getTitle()).isEqualTo("Test Book");
    }

    @Test
    public void shouldFindAllItems() throws JsonProcessingException {
        final HttpEntity<Void> entity = new HttpEntity<>(httpHeaders());

        ResponseEntity<String> foundItemsPage = restTemplate.exchange(
                url("/items?page=0&size=1"),
                HttpMethod.GET,
                entity,
                String.class
        );
        assertThat(foundItemsPage.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(foundItemsPage.getBody()).isNotNull();

        PageImpl<Item> foundItems = objectMapper.readValue(
                foundItemsPage.getBody(),
                new TypeReference<TestPageImpl<Item>>() {
                }
        );

        assertThat(foundItems).hasSize(1);
        assertThat(foundItems.getContent().get(0).getTitle()).isEqualTo("Test Book");
    }

    @Test
    public void shouldCreateItem() {
        final HttpEntity<CreateItemDto> entity = new HttpEntity<>(
                new CreateItemDto("new item"),
                httpHeaders()
        );

        ResponseEntity<Item> createdItem = restTemplate.exchange(
                url("/items"),
                HttpMethod.POST,
                entity,
                Item.class
        );

        assertThat(createdItem.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createdItem.getBody()).isNotNull();
        assertThat(createdItem.getBody().getTitle()).isEqualTo("new item");
        assertThat(createdItem.getBody().getId()).isNotNull();
    }

    @Test
    public void shouldUpdateItem() {
        final HttpEntity<UpdateItemDto> entity = new HttpEntity<>(
                new UpdateItemDto("updated item"),
                httpHeaders()
        );

        ResponseEntity<Item> createdItem = restTemplate.exchange(
                url("/items/2"),
                HttpMethod.PUT,
                entity,
                Item.class
        );

        assertThat(createdItem.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createdItem.getBody()).isNotNull();
        assertThat(createdItem.getBody().getTitle()).isEqualTo("updated item");
        assertThat(createdItem.getBody().getId()).isNotNull();
    }

    @Test
    public void shouldDeleteItem() {
        final HttpEntity<UpdateItemDto> entity = new HttpEntity<>(httpHeaders());

        ResponseEntity<Item> createdItem = restTemplate.exchange(
                url("/items/2"),
                HttpMethod.DELETE,
                entity,
                Item.class
        );

        assertThat(createdItem.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private HttpHeaders httpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.setAccept(List.of(APPLICATION_JSON));
        return headers;
    }

}
