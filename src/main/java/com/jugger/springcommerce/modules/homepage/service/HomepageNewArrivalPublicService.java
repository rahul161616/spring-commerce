package com.jugger.springcommerce.modules.homepage.service;

import com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival.HomepageNewArrivalPublicResponse;

import java.util.List;

public interface HomepageNewArrivalPublicService {
    List<HomepageNewArrivalPublicResponse> getNewArrival();
}
