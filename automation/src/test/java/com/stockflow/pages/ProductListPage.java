package com.stockflow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductListPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By addProductButton = By.id("add-product-button");
    private final By searchInput = By.id("product-search-input");

    public ProductListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openProductListPage(String baseUrl) {
        driver.get(baseUrl + "/products");
        wait.until(ExpectedConditions.urlContains("/products"));
    }

    public void clickAddProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(addProductButton)).click();
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).clear();
        driver.findElement(searchInput).sendKeys(productName);
    }

    public boolean isProductDisplayed(String productName) {
        By productNameText = By.xpath("//*[contains(text(),'" + productName + "')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productNameText)).isDisplayed();
    }
}
