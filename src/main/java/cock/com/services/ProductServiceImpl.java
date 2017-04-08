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
    public Iterable<Product> listAllProductsByType(byte type) {
        return productRepository.findByTypeAndStatus(type, (byte) 1);
    }

    @Override
    public Iterable<Product> listMenWalletProducts() {
        return productRepository.findTop3ByTypeAndStatusOrderByProductIdAsc((byte) 1, (byte)1);
    }
    @Override
    public Iterable<Product> listWomenWalletProducts() {
        return productRepository.findTop3ByTypeAndStatusOrderByProductIdAsc((byte) 2, (byte)1);
    }

    @Override
    public Iterable<Product> listPasspostWalletProducts() {
        return productRepository.findTop3ByTypeAndStatusOrderByProductIdAsc((byte) 3, (byte)1);
    }

    @Override
    public Iterable<Product> listMenBagProducts() {
        return productRepository.findTop3ByTypeAndStatusOrderByProductIdAsc((byte) 4, (byte)1);
    }
    @Override
    public Iterable<Product> listWomenBagProducts() {
        return productRepository.findTop3ByTypeAndStatusOrderByProductIdAsc((byte) 5, (byte)1);
    }

    @Override
    public Iterable<Product> listMenBeltProducts() {
        return productRepository.findTop3ByTypeAndStatusOrderByProductIdAsc((byte) 6, (byte)1);
    }
    @Override
    public Iterable<Product> listWomenBeltProducts() {
        return productRepository.findTop3ByTypeAndStatusOrderByProductIdAsc((byte) 7, (byte)1);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    public Iterable<Product> listAllProduct() {
        return productRepository.findAll();
    }

}
