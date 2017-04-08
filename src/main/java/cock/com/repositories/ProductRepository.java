package cock.com.repositories;

import cock.com.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
	public Iterable<Product> findByTypeAndStatus(byte type, byte status);
	public Iterable<Product> findTop3ByTypeAndStatusOrderByProductIdAsc(byte type, byte status);
}
