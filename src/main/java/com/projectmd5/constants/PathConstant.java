package com.projectmd5.constants;

public class PathConstant {

   public static final String API_V1 = "/api.myservice.com/v1";
   public static final String API_V1_ADMIN = "/api.myservice.com/v1/admin";
   public static final String API_V1_AUTH = "/api.myservice.com/v1/auth";
   public static final String REGISTER = "/sign-up";
   public static final String LOGIN = "/sign-in";
   public static final String LOGOUT = "/sign-out";
   public static final String API_V1_USERS = "/api.myservice.com/v1/users";
   public static final String REFRESH_TOKEN = "/refresh-token";
   public static final String FORGOT_EMAIL = "/forgot/{email}";
   public static final String RESET = "/reset";
   public static final String CHANGE_PASSWORD = "/user/change-password";
   public static final String USER_ADDRESS_ID = "/user/address/{addressId}";
   public static final String USER_ADDRESS_FULL = "/user/address/{userId}";

   public static final String PRODUCTS = "/products";
   public static final String DELETE_BY_PRODUCT_ID = "/delete/{productId}";
   public static final String PRODUCT_ID = "/products/{productId}";
   public static final String CATEGORIES = "/categories";
   public static final String CATEGORY_ID = "/categories/{categoryId}";

   public static final String USER = "/user";
   public static final String API_V1_USER = "/api.myservice.com/v1/user";
   public static final String USER_ID = "/user/{userId}";
   public static final String ROLES = "/roles";
   public static final String ROLES_TO_USER = "/roles/{userId}";
   public static final String ORDER = "/order";
   public static final String ORDERS = "/orders";


   public static final String API_V1_ORDER = API_V1 + ORDER;


   public static final String ADD = "/create";
   public static final String EDIT = "/edit";
   public static final String CART = "/cart";

   public static final String DELETE_BY_ORDER_ID = ORDER + "/delete/{orderId}";
   public static final String USER_BY_ID = USER + "/{userId}";


   public static final String CODE = "/{code}";
   public static final String RESET_CODE = RESET + CODE;
   public static final String ACTIVATE_CODE = "/activate" + CODE;


   public static final String ORDER_ID = "/{orderId}";
   public static final String ORDER_ID_ITEMS = ORDER_ID + "/items";


   public static final String SEARCH = "/search";
   public static final String SEARCH_BY_USER = USER + SEARCH;
}
