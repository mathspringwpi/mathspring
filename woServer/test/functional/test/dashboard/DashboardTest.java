/*
 * @author: Huy Tran - hqtran@wpi.edu
 */
package functional.test.dashboard;

import functional.test.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TEST BREAKDOWN
 * Connect to database
 * Check if the log events in the database work as expected
 * Note: Change the local database info in the code to connect to the database
 */
@Test(groups = "dashboard")
public class DashboardTest extends BaseTest {

    // TODO: Run individual test ok, however fails when run all test suite

    @BeforeTest(description = "Set up connection to database")
    public void setUp() {
        this.dd.databaseConnection();
        this.ld.goToTheLoginPage();
        this.ld.login();

        this.wait.until(ExpectedConditions.or(
                ExpectedConditions.titleIs("MathSpring | Existing Session"),
                ExpectedConditions.titleIs("Mathspring | My Garden")
                )
        );

        // Find the login button to the existing session
        if (this.driver.getTitle().equals("MathSpring | Existing Session")) {
            WebElement loginBtn = this.driver.findElement(By.xpath("//input[contains(@value, 'I want to login')]"));

            loginBtn.click();
        }

        this.sessionID = this.dd.getCurrentSessionId();
    }


    @Test(description = "test dashboard page title and set the sessionId")
    public void dashboard_page_title_should_show_when_logging_in() {
        Assert.assertEquals(this.driver.getTitle(), "MathSpring | My Garden");
        Assert.assertEquals("NewSession", this.dd.getLastAction());
    }


    @Test(description = "test if the eventlog table generates a row when continue a topic")
    public void continue_a_topic_should_add_a_record_to_eventlog_table() {
        WebElement continueFirstTopicButton = this.driver.findElement(By.xpath("//*[@class='container']//div[contains(@class, 'back')][1]//*[contains(@class, 'buttons')]//child::div[1]"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", continueFirstTopicButton);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date clickTimeDate = dateFormat.parse(this.dd.getLastActionTimestamp("MPPContinueTopic"));
            Assert.assertTrue((new Date().getTime() - clickTimeDate.getTime()) < 1000, "Dates are not close enough");

            this.wait.until(ExpectedConditions.titleIs("MathSpring | Tutoring"));
            Assert.assertEquals("MathSpring | Tutoring", this.driver.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test(description = "test if the eventlog table generates a row when review a topic")
    public void review_a_topic_should_add_a_record_to_eventlog_table() {
        WebElement continueFirstTopicButton = this.driver.findElement(By.xpath("//*[@class='container']//div[contains(@class, 'back')][1]//*[contains(@class, 'buttons')]//child::div[2]"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", continueFirstTopicButton);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date clickTimeDate = dateFormat.parse(this.dd.getLastActionTimestamp("MPPReviewTopic"));
            Assert.assertTrue((new Date().getTime() - clickTimeDate.getTime()) < 1000, "Dates are not close enough");

            this.wait.until(ExpectedConditions.titleIs("MathSpring | Tutoring"));
            Assert.assertEquals("MathSpring | Tutoring", this.driver.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test(description = "test if the eventlog table generates a row when challenge a topic")
    public void challenge_a_topic_should_add_a_record_to_eventlog_table() {
        WebElement continueFirstTopicButton = this.driver.findElement(By.xpath("//*[@class='container']//div[contains(@class, 'back')][1]//*[contains(@class, 'buttons')]//child::div[3]"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", continueFirstTopicButton);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date clickTimeDate = dateFormat.parse(this.dd.getLastActionTimestamp("MPPChallengeTopic"));
            Assert.assertTrue((new Date().getTime() - clickTimeDate.getTime()) < 1000, "Dates are not close enough");

            this.wait.until(ExpectedConditions.titleIs("MathSpring | Tutoring"));
            Assert.assertEquals("MathSpring | Tutoring", this.driver.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
