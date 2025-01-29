package marc.dev.ecommerce.spring.service;

import marc.dev.ecommerce.spring.Entity.ImageProductEntity;
import marc.dev.ecommerce.spring.dtorequest.ImageRequest;

public interface ImageService {
    public ImageProductEntity saveImage(ImageRequest request, Long id);
}
