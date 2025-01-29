package marc.dev.ecommerce.spring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marc.dev.ecommerce.spring.Entity.CartEntity;
import marc.dev.ecommerce.spring.Entity.CartLineEntity;
import marc.dev.ecommerce.spring.dtoResponse.CartResponse;
import marc.dev.ecommerce.spring.exception.ApiException;
import marc.dev.ecommerce.spring.repository.CartLineRepository;
import marc.dev.ecommerce.spring.repository.CartRepository;
import marc.dev.ecommerce.spring.repository.ProductRepository;
import marc.dev.ecommerce.spring.service.CartLineService;
import marc.dev.ecommerce.spring.service.CartService;
import marc.dev.ecommerce.spring.service.ProductService;
import marc.dev.ecommerce.spring.utils.CartUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final ProductService productService;
    private  final ProductRepository productRepository;
    private final CartLineService cartLineService;
    private final UserServiceImpl userService;
    private final CartRepository cartRepository;
    private final CartLineRepository cartLineRepository;
    private final CartUtils cartUtils;
    @Override
    public CartEntity addProductToCart(Long productId, String userId) {
        var customer = userService.getUserEntityByUserId(userId);
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiException("Could not find product"));

        // Retrieve or create the user's cart
        CartEntity customerCart = cartRepository.findByCustomer(customer);
        if (customerCart == null) {
            customerCart = new CartEntity();
            customerCart.setCustomer(customer);
            customerCart.setCartLines(new ArrayList<>()); // Initialize cartLines
        } else if (customerCart.getCartLines() == null) {
            customerCart.setCartLines(new ArrayList<>());
        }

        // Check if the product already exists in the cart
        var existingCartLine = customerCart.getCartLines().stream()
                .filter(cartProduct -> cartProduct.getProductId().equals(product.getId()))
                .findFirst();

        if (existingCartLine.isPresent()) {
            // Update quantity if the product already exists
            var cartLine = existingCartLine.get();
            cartLine.setQuantity(cartLine.getQuantity() + 1);
        } else {
            // Create a new CartLineEntity if product doesn't exist in the cart
            var cartLine = cartLineService.saveCartLine(product, customerCart); // Create CartLineEntity
            cartLine = cartLineRepository.save(cartLine); // Save CartLineEntity
            customerCart.getCartLines().add(cartLine); // Add to cart
        }

        // Recalculate the total amount and total product quantity
        long totalAmount = customerCart.getCartLines().stream()
                .mapToLong(cartLine -> (long) (cartLine.getQuantity() * cartLine.getPrice()))
                .sum();
        long totalProduct = customerCart.getCartLines().stream()
                .mapToLong(CartLineEntity::getQuantity)
                .sum();

        customerCart.setTotalAmount(totalAmount);
        customerCart.setTotalProduct(totalProduct);

        // Save and return the updated cart
        return cartRepository.save(customerCart);
    }



    @Override
    public void removeProductToCart(Long productId, String userId) {
        var customer = userService.getUserEntityByUserId(userId);

        // Retrieve user cart
        CartEntity customerCart = cartRepository.findByCustomer(customer);

        if (customerCart != null && !customerCart.getCartLines().isEmpty()) {

            Optional<CartLineEntity> existingCartLine = customerCart.getCartLines().stream()
                    .filter(cartProduct -> cartProduct.getProductId().equals(productId))
                    .findFirst();

            if (existingCartLine.isPresent()) {

                CartLineEntity cartLine = existingCartLine.get();
                // Decrease the quantity or remove the product from the cart
                if (cartLine.getQuantity() > 1) {
                    cartLine.setQuantity(cartLine.getQuantity() - 1);
                    // Update CartLine in the database
                    cartLineRepository.save(cartLine);

                } else if (cartLine.getQuantity() == 1){
                    customerCart.getCartLines().remove(cartLine);
                    cartLineService.deleteCartLine(cartLine.getId());
                }
                // Recalculate the total amount (sum of product prices multiplied by their quantities)
                double totalAmount = customerCart.getCartLines().stream()
                        .mapToLong(cartLineEntity -> (long) (cartLineEntity.getQuantity() * cartLineEntity.getPrice())).sum();
                long totalProduct = customerCart.getCartLines().stream()
                        .mapToLong(CartLineEntity::getQuantity)
                        .sum();

                customerCart.setTotalProduct(totalProduct);
                customerCart.setTotalAmount(totalAmount);
                cartRepository.save(customerCart);
            }

        }
    }
    @Override
    public void clearCart(String userId) {
        var customer = userService.getUserEntityByUserId(userId);

        // Retrieve user's cart
        CartEntity customerCart = cartRepository.findByCustomer(customer);

        if (customerCart != null && !customerCart.getCartLines().isEmpty()) {
            // Remove all cart lines
            customerCart.getCartLines().forEach(cartLine -> cartLineService.deleteCartLine(cartLine.getId()));
            customerCart.getCartLines().clear();


            customerCart.setTotalProduct(0L);
            customerCart.setTotalAmount(0.0);
            cartRepository.save(customerCart);

            // Recalculate the total amount (sum of product prices multiplied by their quantities)
            customerCart.getCartLines().stream()
                    .mapToLong(cartLineEntity -> (long) (cartLineEntity.getQuantity() * cartLineEntity.getPrice())).sum();
           customerCart.getCartLines().stream()
                    .mapToLong(CartLineEntity::getQuantity)
                    .sum();
            // Reset totals
        }
    }
    @Override
    public CartResponse getUSerCart(String userId) {

        var customer = userService.getUserEntityByUserId(userId);

        // Retrieve user cart
        var cart = cartRepository.findByCustomer(customer);
      return   cartUtils.fromCart(cart);
    }

}
