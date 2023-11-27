package com.dmdev.i.store.dto;

public record ProductEntityFilter(int limit,
                                  int offset,
                                  String productName,
                                  int price,
                                  int categoryId,
                                  int manufacturerId) {
}
