package com.datamify.spring.boot.simple.data.repository;

import com.datamify.spring.boot.simple.data.entity.ProductItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Repository
public class ProductItemRepository {

    private final Map<Long, ProductItem> data = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(3L);

    @PostConstruct
    public void init() {
        data.put(
                1L,
                ProductItem.builder()
                        .id(1L)
                        .title("Book")
                        .build()
        );

        data.put(
                2L,
                ProductItem.builder()
                        .id(2L)
                        .title("Pencil")
                        .build()
        );
    }

    public Page<ProductItem> findAll(Pageable pageable) {
        long offset = pageable.getOffset();
        int pageSize = pageable.getPageSize();

        // Sorting is ignored
        final var items = data.values().stream()
                .skip(offset)
                .limit(pageSize)
                .sorted(comparing(ProductItem::getId))
                .collect(Collectors.toList());

        return new PageImpl<>(items, pageable, data.size());
    }

    public ProductItem getOne(Long id) {
        return Optional.ofNullable(data.get(id))
                .orElseThrow(() -> new IllegalArgumentException("Entity not found: " + id));
    }

    public ProductItem save(ProductItem item) {
        if (item.getId() == null) {
            item.setId(nextId.getAndIncrement());
        }
        data.put(item.getId(), item);
        return item;
    }

    public void delete(ProductItem item) {
        if (!data.containsKey(item.getId())) {
            throw new IllegalArgumentException("Entity not found: " + item.getId());
        }

        data.remove(item.getId());
    }

}
