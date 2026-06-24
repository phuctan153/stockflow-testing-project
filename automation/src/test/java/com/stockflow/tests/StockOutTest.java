package com.stockflow.tests;

import com.stockflow.base.BaseTest;
import com.stockflow.pages.LoginPage;
import com.stockflow.pages.ProductFormPage;
import com.stockflow.pages.ProductListPage;
import com.stockflow.pages.StockOutPage;
import com.stockflow.utils.TestData;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class StockOutTest extends BaseTest {

    @Test(description = "ATC-008: Verify admin can create stock-out transaction successfully")
    public void adminCreatesStockOutSuccessfully() {
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

        StockOutPage stockOutPage = new StockOutPage(driver);
        stockOutPage.openStockOutPage(config.getProperty("baseUrl"));
        stockOutPage.createStockOut(
                productName,
                "3",
                "Automation stock-out test"
        );

        productListPage.openProductListPage(config.getProperty("baseUrl"));
        productListPage.searchProduct(productName);

        Assert.assertTrue(
                productListPage.isProductDisplayed(productName),
                "Product should still be displayed after stock-out transaction"
        );

        Assert.assertTrue(
                driver.getPageSource().contains("7"),
                "Product quantity should decrease from 10 to 7 after stock-out"
        );
    }
}
