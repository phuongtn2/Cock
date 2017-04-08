package cock.com.services;


import cock.com.domain.Product;

public interface ProductService {
    Iterable<Product> listAllProductsByType(byte type);
    Product getProductById(Long id);
    Iterable<Product> listMenWalletProducts();
    Iterable<Product> listWomenWalletProducts();
    Iterable<Product> listPasspostWalletProducts();
    Iterable<Product> listMenBagProducts();
    Iterable<Product> listWomenBagProducts();
    Iterable<Product> listMenBeltProducts();
    Iterable<Product> listWomenBeltProducts();
    void saveProduct(Product product);
    Iterable<Product> listAllProduct();
}
