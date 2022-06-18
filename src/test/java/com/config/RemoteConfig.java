package com.config;


import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/remote.properties")
public interface RemoteConfig extends Config {

    String remoteUrl();

}
