package com.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.config.ApiConfig;
import com.config.RemoteConfig;
import com.helpers.AllureAttachments;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestBase {

    @BeforeAll

    static void setUp() {

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        ApiConfig apiConfig = ConfigFactory.create(ApiConfig.class);
        Configuration.baseUrl = apiConfig.webUrl();
        RestAssured.baseURI = apiConfig.apiUrl();


        RemoteConfig remoteConfig = ConfigFactory.create(RemoteConfig.class);
        Configuration.remote = remoteConfig.remoteUrl();


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

    }

    static ApiConfig apiConfig = ConfigFactory.create(ApiConfig.class);
    static
    String login = apiConfig.userLogin();
    String password = apiConfig.userPassword();
    String authCookieName = apiConfig.authCookieName();

    @AfterEach
    void addAttachments() {
        AllureAttachments.screenshotAs("Screenshot_tests");
        AllureAttachments.pageSource();
        AllureAttachments.browserConsoleLogs();
        AllureAttachments.addVideo();
    }
}




