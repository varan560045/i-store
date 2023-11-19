package com.dmdev.i.store.dto;

public record ClientEntityFilter(int limit,
                                 int offset,
                                 String fName,
                                 String email) {


}
