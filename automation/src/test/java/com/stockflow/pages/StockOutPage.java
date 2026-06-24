package com.stockflow.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class StockOutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By quantityInput = By.id("stock-out-quantity-input");
    private final By reasonInput = By.id("stock-out-reason-input");
    private final By submitButton = By.id("stock-out-submit-button");

    public StockOutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openStockOutPage(String baseUrl) {
        driver.get(baseUrl + "/stock-out/create");
        wait.until(ExpectedConditions.urlContains("/stock-out/create"));
    }

    public void selectProduct(String productName) {
        WebElement select = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".stock-out-product-select")
        ));
        select.click();

        By searchInput = By.cssSelector(".stock-out-product-select input[type='search']");
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));

        input.clear();
        input.sendKeys(productName);

        By optionLocator = By.xpath(
                "//div[contains(@class,'stock-out-product-dropdown')]" +
                        "//div[contains(@class,'ant-select-item-option-content') and contains(normalize-space(),'" + productName + "')]"
        );

        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
    }

    public void enterQuantity(String quantity) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(quantity);
    }

    public void enterReason(String reason) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(reasonInput));
        input.clear();
        input.sendKeys(reason);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public void createStockOut(String productName, String quantity, String reason) {
        selectProduct(productName);
        enterQuantity(quantity);
        enterReason(reason);
        clickSubmit();
    }
}
