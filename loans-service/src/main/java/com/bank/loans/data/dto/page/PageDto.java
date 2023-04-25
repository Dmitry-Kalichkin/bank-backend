package com.bank.loans.data.dto.page;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    private int totalPages;
    private int page;
    private int size;
    private List<T> objects;
}
