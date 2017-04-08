package cock.com.services;

import cock.com.domain.ProductRelation;
import cock.com.repositories.ProductRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRelationServiceImpl implements ProductRelationService {
    private ProductRelationRepository productRelationRepository;

    @Autowired
    public void setProductRelationRepository(ProductRelationRepository productRelationRepository) {
        this.productRelationRepository = productRelationRepository;
    }

    @Override
    public Iterable<ProductRelation> listAllByProductId(long productId) {
        return productRelationRepository.findByProductIdOrderByProductIdDesc(productId);
    }

    @Override
    public ProductRelation getById(Long id) {
        return productRelationRepository.findOne(id);
    }

    @Override
    public void saveProduct(ProductRelation productRelation) {
        productRelationRepository.save(productRelation);
    }

    @Override
    public List<ProductRelation> listActiveProductRelation(long productId, byte status) {
        return productRelationRepository.findByProductIdAndStatusOrderByProductIdDesc(productId, status);
    }
}
