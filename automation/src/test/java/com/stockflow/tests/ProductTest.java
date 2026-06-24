package com.stockflow.tests;

import com.stockflow.base.BaseTest;
import com.stockflow.pages.LoginPage;
import com.stockflow.pages.ProductFormPage;
import com.stockflow.pages.ProductListPage;
import com.stockflow.utils.TestData;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ProductTest extends BaseTest {

    @Test(description = "ATC-004: Verify admin can create product successfully")
    public void adminCreatesProductSuccessfully() {
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

        productListPage.searchProduct(productName);

        Assert.assertTrue(
                productListPage.isProductDisplayed(productName),
                "Created product should be displayed in product list"
        );
    }

    @Test(description = "ATC-005: Verify create product with empty name shows validation")
    public void createProductWithEmptyNameShowsValidation() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                config.getProperty("adminUsername"),
                config.getProperty("adminPassword")
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        ProductListPage productListPage = new ProductListPage(driver);
        productListPage.openProductListPage(config.getProperty("baseUrl"));
        productListPage.clickAddProduct();

        ProductFormPage productFormPage = new ProductFormPage(driver);
        productFormPage.waitForProductFormLoaded();
        productFormPage.enterCategory("Automation");
        productFormPage.enterPrice("100000");
        productFormPage.enterQuantity("10");
        productFormPage.clickSubmit();

        String actualValidation = productFormPage.getValidationError();

        Assert.assertTrue(
                actualValidation.toLowerCase().contains("name")
                        || actualValidation.toLowerCase().contains("product"),
                "Validation message for empty product name should be displayed. Actual: " + actualValidation
        );
    }

    @Test(description = "ATC-006: Verify search product by name")
    public void searchProductByName() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                config.getProperty("adminUsername"),
                config.getProperty("adminPassword")
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        ProductListPage productListPage = new ProductListPage(driver);
        productListPage.openProductListPage(config.getProperty("baseUrl"));

        productListPage.searchProduct("Auto Product");

        Assert.assertTrue(
                driver.getPageSource().contains("Auto Product"),
                "Product list should show products matching search keyword"
        );
    }
}
