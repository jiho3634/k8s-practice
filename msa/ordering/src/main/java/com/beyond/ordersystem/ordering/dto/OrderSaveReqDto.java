package com.beyond.ordersystem.ordering.dto;
import com.beyond.ordersystem.ordering.domain.Ordering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSaveReqDto {
    private Long productId;
    private Integer productCount;

    public Ordering toEntity(Long memberId){
        return Ordering.builder()
                .memberId(memberId)
                .build();
    }
}