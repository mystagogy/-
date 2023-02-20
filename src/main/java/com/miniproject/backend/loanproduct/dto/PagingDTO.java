package com.miniproject.backend.loanproduct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PagingDTO {

    private Integer num;
    private List<SearchResponseDto> searchList = new ArrayList<>();
    private Integer current;
    private Integer total;
}
