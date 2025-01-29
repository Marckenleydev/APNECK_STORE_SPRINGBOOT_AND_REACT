package marc.dev.ecommerce.spring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marc.dev.ecommerce.spring.Entity.CartEntity;
import marc.dev.ecommerce.spring.Entity.CartLineEntity;
import marc.dev.ecommerce.spring.Entity.ProductEntity;
import marc.dev.ecommerce.spring.repository.CartLineRepository;
import marc.dev.ecommerce.spring.service.CartLineService;
import marc.dev.ecommerce.spring.utils.CartLineUtils;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class CartLineServiceImpl implements CartLineService {
    private final CartLineRepository cartLineRepository;
    private final CartLineUtils cartLineUtils;
    @Override
    public CartLineEntity saveCartLine(ProductEntity product,CartEntity cartEntity) {
        return cartLineUtils.toCartLine(product,cartEntity);
    }

    @Override
    public void deleteCartLine(Long cartLineId) {
       cartLineRepository.deleteById(cartLineId);
    }
}
