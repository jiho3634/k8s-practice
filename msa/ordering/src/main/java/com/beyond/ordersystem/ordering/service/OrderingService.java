package com.beyond.ordersystem.ordering.service;
import com.beyond.ordersystem.common.service.StockInventoryService;
import com.beyond.ordersystem.ordering.domain.OrderStatus;
import com.beyond.ordersystem.ordering.domain.Ordering;
import com.beyond.ordersystem.ordering.domain.OrderDetail;
import com.beyond.ordersystem.ordering.dto.MemberResDto;
import com.beyond.ordersystem.ordering.dto.OrderListResDto;
import com.beyond.ordersystem.ordering.dto.OrderSaveReqDto;
import com.beyond.ordersystem.ordering.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderingService {
    private final OrderRepository orderRepository;
    private final StockInventoryService stockInventoryService;
    private MemberServiceClient memberServiceClient;


    @Autowired
    OrderingService(OrderRepository orderRepository
            , StockInventoryService stockInventoryService ) {
        this.orderRepository = orderRepository;
        this.stockInventoryService = stockInventoryService;
    }

//    // synchronized 를 설정한다 하더라도, 재고 감소가 DB 에 반영되는 시점은 트랜잭션이 커밋되고 종료되는 시점
//    public Ordering orderCreate(List<OrderSaveReqDto> dtos) {
//        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//        MemberResDto member = memberServiceClient.getMemberInfo().getBody();
//        Ordering ordering = Ordering.builder().member(member).build();
//        for (OrderSaveReqDto dto : dtos) {
//            Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new EntityNotFoundException("product is not found"));
//            Integer quantity = dto.getProductCount();
//            //  redis 를 통한 재고관리 및 재고 잔량 확인
//            if (product.getName().contains("sale")) {
//                int newQuantity = stockInventoryService.decreaseStock(dto.getProductId(), dto.getProductCount()).intValue();
//                if (newQuantity < 0) {
//                    throw new IllegalArgumentException("재고 부족");
//                }
//                //  rdb 에 재고를 업데이트. rabbitmq 를 통해 비동기적으로 이벤트 처리.
////                stockDecreaseEventHandler.publish(new StockDecreaseEvent(product.getId(), dto.getProductCount()));
//            } else {
//                if (product.getStockQuantity() < quantity) {
//                    throw new IllegalArgumentException("재고 부족");
//                }
//                product.updateStockQuantity(quantity);
//            }
//            //  ordering 이 아직 save 가 되지 않았지만, JPA 가 생성 후에 인자로 넣어준다.
//            ordering.getOrderDetails().add(OrderDetail.builder()
//                            .ordering(ordering)
//                            .product(product)
//                            .quantity(quantity)
//                            .build());
//        }
//        Ordering savedOrdering = orderRepository.save(ordering);
//        //  admin 에게 sse 알림
//        return savedOrdering;
//    }

//    public List<OrderListResDto> orderList() {
//        return orderRepository.findAll().stream().map(ordering -> new OrderListResDto().fromEntity(ordering)).collect(Collectors.toList());
//    }

    public List<OrderListResDto> myOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        MemberResDto member = (MemberResDto) memberServiceClient.getMemberInfo().getBody().getResult();
        return orderRepository.findByMemberId(member.getId()).stream().map(ordering -> new OrderListResDto().fromEntity(ordering)).collect(Collectors.toList());
    }

//    public Ordering orderCancel(Long id) {
//        Ordering ordering = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("order is not found"));
//        ordering.updateOrderStatus(OrderStatus.CANCELED);
//        return ordering;
//    }
}
