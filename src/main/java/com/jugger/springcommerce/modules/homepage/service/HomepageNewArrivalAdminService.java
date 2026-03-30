package com.jugger.springcommerce.modules.homepage.service;

import com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival.HomepageNewArrivalAdminRequest;
import com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival.HomepageNewArrivalAdminResponse;

import java.util.List;

public interface HomepageNewArrivalAdminService {
    HomepageNewArrivalAdminResponse addNewArrival(HomepageNewArrivalAdminRequest request);

    HomepageNewArrivalAdminResponse getNewArrivalByIdForAdmin(Long id);

    List<HomepageNewArrivalAdminResponse> getNewArrivalForAdmin();

    HomepageNewArrivalAdminResponse updateHomepageNewArrival(Long id, HomepageNewArrivalAdminRequest request);

    void deleteNewArrival(Long id);
}
