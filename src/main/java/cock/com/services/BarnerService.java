package cock.com.services;

import cock.com.domain.Barner;

public interface BarnerService {
    Barner getBarnerById(int id);
    Iterable<Barner> listBannerActive();
    Iterable<Barner> listAllBarner();
    void saveBaner(Barner barner);
}
