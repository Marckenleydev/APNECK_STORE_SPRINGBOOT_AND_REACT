package marc.dev.ecommerce.spring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marc.dev.ecommerce.spring.Entity.ImageProductEntity;
import marc.dev.ecommerce.spring.dtorequest.ImageRequest;
import marc.dev.ecommerce.spring.exception.ApiException;
import marc.dev.ecommerce.spring.repository.ImageProductRepository;
import marc.dev.ecommerce.spring.repository.ProductRepository;
import marc.dev.ecommerce.spring.service.ImageService;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private  final ProductRepository productRepository;
    private final ImageProductRepository imageProductRepository;
    @Override
    public ImageProductEntity saveImage(ImageRequest request, Long id) {
        var product = productRepository.findById(id).orElseThrow(()->new ApiException("Product not found"));
        if(product != null){
            ImageProductEntity imageProductEntity = new ImageProductEntity();
            imageProductEntity.setImageUrl(request.getImageUrl());
            imageProductEntity.setProduct(product);

            return imageProductRepository.save(imageProductEntity);
        }


       return null;
    }
}
