package cock.com.services;

import cock.com.domain.Barner;
import cock.com.repositories.BarnerRepository;
import cock.com.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarnerServiceImpl implements BarnerService {
    private BarnerRepository barnerRepository;

    @Autowired
    public void setBarnerRepository(BarnerRepository barnerRepository) {
        this.barnerRepository = barnerRepository;
    }

    @Override
    public Iterable<Barner> listAllBarner() {
        return barnerRepository.findAll();
    }
}
