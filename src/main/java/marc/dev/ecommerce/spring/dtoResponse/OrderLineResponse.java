package marc.dev.ecommerce.spring.dtoResponse;

public record OrderLineResponse(
        Long id,
        String productName,
        Long quantity,
        String images
) {
}
