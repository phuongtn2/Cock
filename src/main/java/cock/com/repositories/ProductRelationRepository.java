package cock.com.repositories;

import cock.com.domain.ProductRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRelationRepository extends CrudRepository<ProductRelation, Long> {
	public Iterable<ProductRelation> findByProductIdOrderByProductIdDesc(long productId);
	public List<ProductRelation> findByProductIdAndStatusOrderByProductIdDesc(long productId, byte status);
}
