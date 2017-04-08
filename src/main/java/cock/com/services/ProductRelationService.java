package cock.com.services;

import cock.com.domain.ProductRelation;

import java.util.List;

public interface ProductRelationService {
    Iterable<ProductRelation> listAllByProductId(long productId);
    ProductRelation getById(Long id);
    void saveProduct(ProductRelation productRelation);
    List<ProductRelation> listActiveProductRelation(long productId, byte status);
}
