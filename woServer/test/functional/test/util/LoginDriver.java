/*
 * @author Huy Tran - hqtran@wpi.edu
 */
package functional.test.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginDriver {


    private WebDriver driver;
    private WebDriverWait wait;


    public LoginDriver(WebDriver d, WebDriverWait w) {
        this.driver = d;
        this.wait = w;
    }


    /**
     * Select the link text and go to the login page
     */
    public void goToTheLoginPage() {
        this.driver.findElement(By.linkText("K12 version")).click();
        this.wait.until(ExpectedConditions.titleIs("MathSpring Login"));
    }


    /**
     * Login user
     * Type in username and password
     * Click the sign in button
     * Can go to either the existing session page or dashboard
     */
    public void login() {
        WebElement usernameField = this.driver.findElement(By.name("uname"));
        usernameField.clear();
        usernameField.sendKeys("hmngo");

        WebElement passwordField = this.driver.findElement(By.name("password"));
        passwordField.clear();
        passwordField.sendKeys("test");

        WebElement loginButton = this.driver.findElement(By.xpath("//button[@type='submit'][@class='btn btn-default btn-block sign-in-btn js-login-btn']"));
        loginButton.click();
    }
}
