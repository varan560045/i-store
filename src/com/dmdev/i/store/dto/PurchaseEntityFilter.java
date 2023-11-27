package com.dmdev.i.store.dto;

public record PurchaseEntityFilter (int limit,
                                    int offset,
                                    Long productId,
                                    Long clientId,
                                    Integer purchaseStatusId){
}
