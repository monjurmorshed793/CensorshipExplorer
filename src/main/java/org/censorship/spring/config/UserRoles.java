package org.censorship.spring.config;

public class UserRoles {
    public static final String USER = "USER";

    private UserRoles(){

    }

    public static String[] getAllRoles(){
        return new String[]{USER};
    }
}
