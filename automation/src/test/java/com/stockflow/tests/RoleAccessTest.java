package com.stockflow.tests;

import com.stockflow.base.BaseTest;
import com.stockflow.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class RoleAccessTest extends BaseTest {

    @Test(description = "ATC-010: Verify staff cannot access product create page")
    public void staffCannotAccessProductCreatePage() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                config.getProperty("staffUsername"),
                config.getProperty("staffPassword")
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        driver.get(config.getProperty("baseUrl") + "/products/create");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/dashboard"),
                ExpectedConditions.urlContains("/products"),
                ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))
        ));

        Assert.assertFalse(
                driver.getCurrentUrl().contains("/products/create"),
                "Staff should not be allowed to access product create page. Current URL: " + driver.getCurrentUrl()
        );

        Assert.assertTrue(
                driver.getPageSource().contains("Dashboard")
                        || driver.getPageSource().contains("Product")
                        || driver.getCurrentUrl().contains("/dashboard")
                        || driver.getCurrentUrl().contains("/products"),
                "Staff should be redirected to an allowed page after trying to access product create page."
        );
    }
}
