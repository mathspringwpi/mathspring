/*
 * @author: Huy Tran - hqtran@wpi.edu
 */
package functional.test.dashboard;

import functional.test.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.ResultSet;

/**
 * TEST BREAKDOWN
 * Connect to database
 * Check if the log events in the database work as expected
 * Note: Change the local database info in the code to connect to the database
 */
@Test(groups = "dashboard")
public class DashboardTest extends BaseTest {


    @BeforeClass(description = "Set up connection to database")
    public void setUp() {
        this.dd.databaseConnection();
        this.ld.goToTheLoginPage();
        this.ld.login();
    }


    @Test(description = "demo")
    public void test_demo_database() {
        try {
            String query = "SELECT * FROM problem";
            ResultSet rs = this.dd.getStmt().executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test(description = "dashboard page title")
    public void test_dashboard_page_title() {
        this.wait.until(ExpectedConditions.or(
            ExpectedConditions.titleIs("MathSpring | Existing Session"),
            ExpectedConditions.titleIs("Mathspring | My Garden")
            )
        );

        // Find the login button to the existing session
        if (this.driver.getTitle().equals("MathSpring | Existing Session")) {
            WebElement loginBtn = this.driver.findElement(By.xpath("//input[@class='col-sm-offset-2 col-sm-4 btn btn-primary mathspring-btn']"));
            loginBtn.click();
        }

        Assert.assertEquals("MathSpring | My Garden", this.driver.getTitle());

        this.sessionID = this.dd.getCurrentSessionId();
        System.out.println(sessionID);
    }


}
