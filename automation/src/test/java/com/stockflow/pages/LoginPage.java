package com.stockflow.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");

    private final By antMessage = By.cssSelector(".ant-message-notice-content");
    private final By antAlert = By.cssSelector(".ant-alert-message");
    private final By formValidationMessage = By.cssSelector(".ant-form-item-explain-error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForLoginPageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).clear();
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void login(String username, String password) {
        waitForLoginPageLoaded();
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(antMessage)).getText();
        } catch (TimeoutException ignored) {
            // Try next possible error location
        }

        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(antAlert)).getText();
        } catch (TimeoutException ignored) {
            // Try next possible error location
        }

        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(formValidationMessage)).getText();
        } catch (TimeoutException ignored) {
            // Return page body text for easier debugging
        }

        return driver.findElement(By.tagName("body")).getText();
    }
}
