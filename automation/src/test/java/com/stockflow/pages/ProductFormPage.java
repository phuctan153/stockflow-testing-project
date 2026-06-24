package com.stockflow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductFormPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productNameInput = By.id("product-name-input");
    private final By categoryInput = By.id("product-category-input");
    private final By priceInput = By.id("product-price-input");
    private final By quantityInput = By.id("product-quantity-input");
    private final By submitButton = By.id("product-submit-button");
    private final By validationError = By.cssSelector(".ant-form-item-explain-error");

    public ProductFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForProductFormLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNameInput));
    }

    public void enterProductName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNameInput)).clear();
        driver.findElement(productNameInput).sendKeys(name);
    }

    public void enterCategory(String category) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryInput)).clear();
        driver.findElement(categoryInput).sendKeys(category);
    }

    public void enterPrice(String price) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(priceInput)).clear();
        driver.findElement(priceInput).sendKeys(price);
    }

    public void enterQuantity(String quantity) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput)).clear();
        driver.findElement(quantityInput).sendKeys(quantity);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public void createProduct(String name, String category, String price, String quantity) {
        waitForProductFormLoaded();
        enterProductName(name);
        enterCategory(category);
        enterPrice(price);
        enterQuantity(quantity);
        clickSubmit();
    }

    public String getValidationError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(validationError)).getText();
    }
}
