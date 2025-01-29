package marc.dev.ecommerce.spring.utils;


import lombok.Getter;
import lombok.Setter;
import marc.dev.ecommerce.spring.Entity.OrderEntity;
import marc.dev.ecommerce.spring.Entity.OrderLineEntity;
import marc.dev.ecommerce.spring.dtoResponse.OrderLineResponse;
import marc.dev.ecommerce.spring.dtorequest.OrderLineRequest;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class OrderLineUtils {
    public OrderLineEntity toOrderLine(OrderLineRequest orderLineRequest, OrderEntity order) {
        return  OrderLineEntity.builder()
                .quantity(orderLineRequest.quantity())
                .image(orderLineRequest.image())
                .productName(orderLineRequest.productName())
                .order(order)
                .productId(orderLineRequest.productId())
                .build();

    }

    public OrderLineResponse toOrderLineResponse(OrderLineEntity orderLine) {
        return  new OrderLineResponse(
                orderLine.getId(),
                orderLine.getProductName(),
                orderLine.getQuantity(),
                orderLine.getImage()
        );
    }
}
