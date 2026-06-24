package com.stockflow.tests;

import com.stockflow.base.BaseTest;
import com.stockflow.pages.LoginPage;
import com.stockflow.pages.ProductFormPage;
import com.stockflow.pages.ProductListPage;
import com.stockflow.pages.StockInPage;
import com.stockflow.utils.TestData;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class StockInTest extends BaseTest {

    @Test(description = "ATC-007: Verify admin can create stock-in transaction successfully")
    public void adminCreatesStockInSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                config.getProperty("adminUsername"),
                config.getProperty("adminPassword")
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        String productName = TestData.uniqueProductName();

        ProductListPage productListPage = new ProductListPage(driver);
        productListPage.openProductListPage(config.getProperty("baseUrl"));
        productListPage.clickAddProduct();

        ProductFormPage productFormPage = new ProductFormPage(driver);
        productFormPage.createProduct(
                productName,
                "Automation",
                "100000",
                "10"
        );

        wait.until(ExpectedConditions.urlContains("/products"));

        StockInPage stockInPage = new StockInPage(driver);
        stockInPage.openStockInPage(config.getProperty("baseUrl"));
        stockInPage.createStockIn(
                productName,
                "5",
                "Automation stock-in test"
        );

        productListPage.openProductListPage(config.getProperty("baseUrl"));
        productListPage.searchProduct(productName);

        Assert.assertTrue(
                productListPage.isProductDisplayed(productName),
                "Product should still be displayed after stock-in transaction"
        );

        Assert.assertTrue(
                driver.getPageSource().contains("15"),
                "Product quantity should increase from 10 to 15 after stock-in"
        );
    }
}
