package com.bfh.springbootsecurity.config;

public final class Constants {

    private static final String ROLE_PRAEFIX = "ROLE_";
    public static final String USER = "USER";
    public static final String SUPERUSER = "SUPERUSER";
    public static final String ADMINISTRATOR = "ADMINISTRATOR";
    public static final String USER_AUTHORITY = ROLE_PRAEFIX + USER;
    public static final String SUPERUSER_AUTHORITY = ROLE_PRAEFIX + SUPERUSER;
    public static final String ADMINISTRATOR_AUTHORITY = ROLE_PRAEFIX + ADMINISTRATOR;

    public static String JWT_SECRET = "JWTMy$SuperJWT!Secret";
    public static String JWT_BEARER = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";
    public static long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

}
