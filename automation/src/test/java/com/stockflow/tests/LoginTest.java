package com.stockflow.tests;

import com.stockflow.base.BaseTest;
import com.stockflow.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test(description = "ATC-001: Verify admin can login successfully")
    public void loginSuccessfullyWithAdminAccount() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                config.getProperty("adminUsername"),
                config.getProperty("adminPassword")
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/dashboard"),
                "User should be redirected to dashboard after successful login. Current URL: " + driver.getCurrentUrl()
        );
    }

    @Test(description = "ATC-002: Verify login fails with invalid password")
    public void loginFailedWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                config.getProperty("adminUsername"),
                config.getProperty("invalidPassword")
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/login"),
                ExpectedConditions.visibilityOfElementLocated(By.id("username"))
        ));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "User should stay on login page when password is invalid. Current URL: " + driver.getCurrentUrl()
        );
    }
}
