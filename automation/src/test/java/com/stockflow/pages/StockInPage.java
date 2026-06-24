package com.stockflow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class StockInPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productSelect = By.id("stock-in-product-select");
    private final By quantityInput = By.id("stock-in-quantity-input");
    private final By noteInput = By.id("stock-in-note-input");
    private final By submitButton = By.id("stock-in-submit-button");

    public StockInPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openStockInPage(String baseUrl) {
        driver.get(baseUrl + "/stock-in/create");
        wait.until(ExpectedConditions.urlContains("/stock-in/create"));
    }

    public void selectProduct(String productName) {
        WebElement select = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".stock-in-product-select")
        ));
        select.click();

        By searchInput = By.cssSelector(".stock-in-product-select input[type='search']");
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));

        input.clear();
        input.sendKeys(productName);

        By optionLocator = By.xpath(
                "//div[contains(@class,'stock-in-product-dropdown')]" +
                        "//div[contains(@class,'ant-select-item-option-content') and contains(normalize-space(),'" + productName + "')]"
        );

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
    }

    public void enterQuantity(String quantity) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(quantity);
    }

    public void enterNote(String note) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(noteInput));
        input.clear();
        input.sendKeys(note);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public void createStockIn(String productName, String quantity, String note) {
        selectProduct(productName);
        enterQuantity(quantity);
        enterNote(note);
        clickSubmit();
    }
}
