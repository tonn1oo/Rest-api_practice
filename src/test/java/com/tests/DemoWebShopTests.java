package com.tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class DemoWebShopTests extends TestBase {

    @Test
    @DisplayName("Successful authorization to some demowebshop (UI)")
    void loginTest() {
        step("Open login page", () ->
                open("/login"));

        step("Fill login form", () -> {
            $("#Email").setValue(login);
            $("#Password").setValue(password).pressEnter();

        });

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));

    }

    @Test
    @DisplayName("Successful authorization to some demowebshop (API + UI)")
    void loginTestApi() {
        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", login)
                    .formParam("Password", password)
                    // Альтернатива formParam
                    //.body("Email=" + login + "&Password=" + password + "&RememberMe=false")
                    //.body(format("Email=%s&Password=%s&RememberMe=false", login, password))
                    .log().all()
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(authCookieName);

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));
            step("Set cookie to to browser", () -> {
                Cookie authCookie = new Cookie(authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);

                step("Open main page", () ->
                        open(""));

                step("Verify successful authorization", () ->
                        $(".account").shouldHave(text(login)));

            });
        });
    }

    // Custom Allure Listener
    @Test
    @DisplayName("Successful authorization to some demowebshop (API + UI)")
    void loginTestApiAllure() {
        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .filter(new AllureRestAssured())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", login)
                    .formParam("Password", password)
                    .log().all()
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(authCookieName);

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));
            step("Set cookie to to browser", () -> {
                Cookie authCookie = new Cookie(authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);

                step("Open main page", () ->
                        open(""));

                step("Verify successful authorization", () ->
                        $(".account").shouldHave(text(login)));

            });
        });
    }

    // Custom Listener
    @Test
    @DisplayName("Successful authorization to some demowebshop (API + UI)")
    void loginTestApiCustomListener() {
        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", login)
                    .formParam("Password", password)
                    .log().all()
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(authCookieName);

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));
            step("Set cookie to to browser", () -> {
                Cookie authCookie = new Cookie(authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);

                step("Open main page", () ->
                        open(""));

                step("Verify successful authorization", () ->
                        $(".account").shouldHave(text(login)));

            });
        });
    }

    @Test
    @DisplayName("Successful authorization to some demowebshop (API + UI)")
    void addProductToCartTest() {
        String body = "product_attribute_72_5_18=53&product_attribute_72_6_19=54&product_attribute_72_3_20=57&addtocart_72.EnteredQuantity=1";
        String authorizationCookie = "ARRAffinity=92eb765899e80d8de4d490df907547e5cb10de899e8b754a4d5fa1a7122fad69; __utmc=78382081; __utmz=78382081.1654897570.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); ASP.NET_SessionId=cdkxvsu1sxcfznukmuisiwem; Nop.customer=d9b34205-350b-49bf-9b62-668c0b06e700; __utma=78382081.1335882737.1654897570.1655332573.1655336701.6; NopCommerce.RecentlyViewedProducts=RecentlyViewedProductIds=72; __atuvc=4%7C24; __atuvs=62aa6f01d065ca9c003; __utmt=1; __utmb=78382081.5.10.1655336701";
        step("Get cookie by api and set it to browser", () -> {
            given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded")
                    .body(body)
                    .cookie(authorizationCookie)
                    .log().all()
                    .when()
                    .post("/addproducttocart/details/72/1")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("success", is(true))
                    .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                    .body("updateflyoutcartsectionhtml", notNullValue())
                    .body("updatetopcartsectionhtml", notNullValue())
                    .extract().path("updatetopcartsectionhtml");

            step("Open main page", () ->
                    open(""));
//

        });
    }
}