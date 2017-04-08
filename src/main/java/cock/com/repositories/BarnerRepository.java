package cock.com.repositories;

import cock.com.domain.Barner;
import org.springframework.data.repository.CrudRepository;

public interface BarnerRepository extends CrudRepository<Barner, Integer>{
	public Iterable<Barner> findByStatusOrderByIdDesc(byte type);
}
