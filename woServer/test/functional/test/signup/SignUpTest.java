/*
 * @author: Huy Tran - hqtran@wpi.edu
 */
package functional.test.signup;

import functional.test.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * TEST BREAKDOWN
 * 1. Go to the signup page for student
 * 2. Fill in the info
 * 3. Verify sign up successfully
 */
@Test(groups = "signup")
public class SignUpTest extends BaseTest {


    @Test(description = "Go the the sign up page successfully")
    public void test_go_to_the_sign_up_page() {
        goToSignUpStudent();
        this.wait.until(ExpectedConditions.titleIs("MathSpring | Student Registration"));
        Assert.assertEquals("MathSpring | Student Registration", this.driver.getTitle());
    }


    private void goToSignUpStudent() {
        goToTheLoginPage();
        this.driver.findElement(By.xpath("//button[@type='button'][@class='btn btn-primary btn-lg btn-block signup-btn student-sign-up-btn']"))
                .click();
    }


}
