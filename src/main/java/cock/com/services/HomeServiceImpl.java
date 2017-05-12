package cock.com.services;

import cock.com.domain.Home;
import cock.com.repositories.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {
    private HomeRepository homeRepository;
    @Autowired
    public void setHomeRepository(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }
    @Override
    public Iterable<Home> listHome() {
        return homeRepository.findAll();
    }

    @Override
    public void saveHome(Home home) {
        homeRepository.save(home);
    }

    @Override
    public Home getHomeById(int id) {
        return  homeRepository.findOne(id);
    }
}
