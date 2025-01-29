package marc.dev.ecommerce.spring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import marc.dev.ecommerce.spring.Entity.OrderEntity;
import marc.dev.ecommerce.spring.Entity.OrderLineEntity;
import marc.dev.ecommerce.spring.dtoResponse.OrderLineResponse;
import marc.dev.ecommerce.spring.dtorequest.OrderLineRequest;
import marc.dev.ecommerce.spring.repository.OrderLineRepository;
import marc.dev.ecommerce.spring.service.OrderLineService;
import marc.dev.ecommerce.spring.utils.OrderLineUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class OrderLineServiceImpl implements OrderLineService {
    private final OrderLineUtils orderLineUtils;
    private final OrderLineRepository orderLineRepository;
    @Override
    public OrderLineEntity saveOrderLine(OrderLineRequest orderLineRequest, OrderEntity order) {
        var orderLine =orderLineUtils.toOrderLine(orderLineRequest,order);
        return orderLineRepository.save(orderLine);
    }

    @Override
    public List<OrderLineResponse> findAllByOrderId(Long orderId) {
        return  orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineUtils::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}
