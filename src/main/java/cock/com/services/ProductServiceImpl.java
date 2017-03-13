package cock.com.services;

import cock.com.domain.Product;
import cock.com.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> listAllProductsByType(int productType) {
        return productRepository.findByProductTypeId(productType);
    }
    @Override
    public Iterable<Product> listBarnerProducts() {
        return productRepository.findByTypeOrderByCreatedDesc((byte) 1);
    }

    @Override
    public Iterable<Product> listWalletProducts() {
        return productRepository.findByTypeOrderByCreatedDesc((byte) 2);
    }

    @Override
    public Iterable<Product> listBagProducts() {
        return productRepository.findByTypeOrderByCreatedDesc((byte) 3);
    }

    @Override
    public Iterable<Product> listBeltProducts() {
        return productRepository.findByTypeOrderByCreatedDesc((byte) 4);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findOne(id);
    }

}
