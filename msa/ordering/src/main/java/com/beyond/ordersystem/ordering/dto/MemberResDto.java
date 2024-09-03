package com.beyond.ordersystem.ordering.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResDto {
    private Long id;
    private String name;
    private String email;
    private Address address;
    private int orderCount;
}