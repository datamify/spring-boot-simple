package com.datamify.spring.boot.simple.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemRequest {

    @NotNull
    @Size(min = 1, max = 255)
    private String title;

}
