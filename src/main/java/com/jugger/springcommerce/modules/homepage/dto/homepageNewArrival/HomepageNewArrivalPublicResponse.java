package com.jugger.springcommerce.modules.homepage.dto.homepageNewArrival;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomepageNewArrivalPublicResponse {
    private Long id;
    private String family;
    private String title;
    private String price;
    private String compareAt;
    private String image;
    private String href;
}
