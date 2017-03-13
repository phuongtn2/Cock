package cock.com.repositories;

import cock.com.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
	public Iterable<Product> findByTypeOrderByCreatedDesc(byte type);
	public Iterable<Product> findByProductTypeId(Integer productType);
}
