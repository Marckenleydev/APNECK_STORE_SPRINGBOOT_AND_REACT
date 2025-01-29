package marc.dev.ecommerce.spring.service;


import marc.dev.ecommerce.spring.Entity.OrderEntity;
import marc.dev.ecommerce.spring.Entity.OrderLineEntity;
import marc.dev.ecommerce.spring.dtoResponse.OrderLineResponse;
import marc.dev.ecommerce.spring.dtorequest.OrderLineRequest;

import java.util.List;

public interface OrderLineService {
    public OrderLineEntity saveOrderLine(OrderLineRequest orderLineRequest, OrderEntity order);
    public List<OrderLineResponse> findAllByOrderId(Long orderId);
}
