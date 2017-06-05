/*
 * @author: Huy Tran - hqtran@wpi.edu
 */
package functional.test.signin;

import functional.test.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


/**
 * TEST BREAKDOWN
 * 1. Go the Login page
 * 2. Type in the username and password
 * 3. Verify that login is successful
 */
@Test(groups = { "signin" })
public class SignInTest extends BaseTest {


    @BeforeTest
    public void setUp() {
        super.setUp();
        super.goToTheLoginPage();
    }


    @Test(description = "Verify login page title")
    public void test_login_page_title() {
        Assert.assertEquals("MathSpring Login", this.driver.getTitle());
    }


    @Test(description = "Verify login successfully to the next page")
    public void test_login_successfully() {
        login();

        this.wait.until(ExpectedConditions.or(
                ExpectedConditions.titleIs("MathSpring | Existing Session"),
                ExpectedConditions.titleIs("Mathspring | My Garden")
            )
        );
    }


    /**
     * Login user
     * Type in username and password
     * Click the sign in button
     * Can go to either the existing session page or dashboard
     */
    private void login() {
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
