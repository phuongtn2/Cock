package cock.com.services;

import cock.com.domain.Home;


public interface HomeService {
    Iterable<Home> listHome();
    void saveHome(Home home);
    Home getHomeById(int id);
}
