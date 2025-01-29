package marc.dev.ecommerce.spring.utils;


import lombok.Getter;
import lombok.Setter;
import marc.dev.ecommerce.spring.Entity.CartEntity;
import marc.dev.ecommerce.spring.Entity.OrderEntity;
import marc.dev.ecommerce.spring.Entity.OrderLineEntity;
import marc.dev.ecommerce.spring.Entity.UserEntity;
import marc.dev.ecommerce.spring.dtoResponse.OrderLineResponse;
import marc.dev.ecommerce.spring.dtoResponse.OrderResponse;
import marc.dev.ecommerce.spring.dtorequest.OrderRequest;
import marc.dev.ecommerce.spring.enumeration.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class OrderUtils {
    public OrderEntity toOrder(CartEntity cartEntity, UserEntity userEntity) {
        return  OrderEntity.builder()
                .customer(userEntity)
                .totalAmount(cartEntity.getTotalAmount())
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .build();
    }
    public OrderResponse fromOrder(OrderEntity order) {
        return new OrderResponse(
                order.getId(),
                order.getReferenceId(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomer().getFirstName(),
                order.getOrderLines() != null ?
                        order.getOrderLines().stream()
                                .map(orderLine -> new OrderLineResponse(
                                        orderLine.getId(),
                                        orderLine.getProductName(),
                                        orderLine.getQuantity(),
                                        orderLine.getImage()
                                ))
                                .collect(Collectors.toList()) : new ArrayList<OrderLineResponse>()
        );
    }

}