package cock.com.services;


import cock.com.domain.Product;

public interface ProductService {
    Iterable<Product> listAllProductsByType(int productType);
    Product getProductById(Long id);
    Iterable<Product> listBarnerProducts();
    Iterable<Product> listWalletProducts();
    Iterable<Product> listBagProducts();
    Iterable<Product> listBeltProducts();
}
