package com.jugger.springcommerce.apiConstants;

public class ApiConstants {
    private static final String BASE_URL = "/api/v1";
    public static final String ADMIN_PRODUCT_API = BASE_URL + "/admin/products";
    public static final String PUBLIC_PRODUCT_API = BASE_URL + "/public/products";
    public static final String PUBLIC_CART_API = BASE_URL + "/public/cart";
    public static final String ADMIN_CATEGORIES_API = BASE_URL + "/admin/categories";
    public static final String PUBLIC_CATEGORIES_API = BASE_URL + "/public/categories";
    public static final String ADMIN_TAGS_API = BASE_URL + "/admin/tags";
    public static final String ADMIN_ORDER_API = BASE_URL + "/admin/orders";
    public static final String PUBLIC_TAGS_API = BASE_URL + "/public/tags";

    //homepage APIs
    public static final String HOMEPAGE_PUBLIC = BASE_URL + "/public/homepage/home";
    public static final String HOMEPAGE_ADMIN_HERO = BASE_URL + "/admin/homepage/hero";
    public static final String HOMEPAGE_ADMIN_FEATURED_CATEGORY = BASE_URL + "/admin/homepage/featured-categories";
    public static final String HOMEPAGE_ADMIN_TRENDING_PRODUCTS = BASE_URL + "/admin/homepage/trending-products";
    public static final String HOMEPAGE_ADMIN_NEW_ARRIVAL = BASE_URL + "/admin/homepage/new-arrivals";

    //orderapis
    public static final String ORDER_API = BASE_URL + "/public/orders";

    //auth
    public static final String AUTH = BASE_URL + "/public/auth";
}
