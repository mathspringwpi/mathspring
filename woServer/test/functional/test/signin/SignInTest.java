/*
 * @author: Huy Tran - hqtran@wpi.edu
 */
package functional.test.signin;

import functional.test.base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
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


    @BeforeClass
    public void setUp() {
        this.ld.goToTheLoginPage();
    }


    @Test(description = "Verify login page title")
    public void test_login_page_title() {
        Assert.assertEquals("MathSpring Login", this.driver.getTitle());
    }


    @Test(description = "Verify login successfully to the next page")
    public void test_login_successfully() {
        this.ld.login();

        this.wait.until(ExpectedConditions.or(
                ExpectedConditions.titleIs("MathSpring | Existing Session"),
                ExpectedConditions.titleIs("Mathspring | My Garden")
            )
        );
    }


}
