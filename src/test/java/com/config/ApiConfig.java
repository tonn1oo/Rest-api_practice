package com.config;

import org.aeonbits.owner.Config;


@Config.Sources("classpath:config/api.properties")
public interface ApiConfig extends Config {

    String webUrl();
    String apiUrl();
    String userLogin();
    String userPassword();
    String authCookieName();


}
