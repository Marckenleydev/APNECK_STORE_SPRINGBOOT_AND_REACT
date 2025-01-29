package marc.dev.ecommerce.spring.service;



import marc.dev.ecommerce.spring.dtoResponse.OrderResponse;
import marc.dev.ecommerce.spring.dtoResponse.StripeResponse;
import marc.dev.ecommerce.spring.dtorequest.OrderRequest;

import java.util.List;

public interface OrderService {
    public StripeResponse createOrderAndCheckout( String userId);
    public List<OrderResponse> getOrders();
    public OrderResponse findById(Long orderId);
}
