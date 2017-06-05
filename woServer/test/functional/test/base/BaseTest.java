/*
 * @author: Huy Tran - hqtran@wpi.edu
 */
package functional.test.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * BASE SETUP FOR THE SELENIUM
 * Requirement:
 * 1. Have Chrome Browser
 * 2. Change the path of the chrome browser according to the downloaded driver path
 * 3. Run the MathSpring local server
 */
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static Connection conn = null;
    protected static Statement stmt;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/wayangoutpostdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String dbClass = "com.mysql.jdbc.Driver";
    private static final String frontPageUrl = "http://localhost:8080/mt/index.html";


    @BeforeTest
    public void setUp() {
        // TODO: Find a way to set up this path across the local environment
        System.setProperty("webdriver.chrome.driver", "/Users/huytran172/Downloads/chromedriver");

        // Set up Selenium Web Driver and GET the front page
        this.driver = new ChromeDriver();
        this.wait = new WebDriverWait(this.driver, 30);
        this.driver.get(frontPageUrl);
        this.driver.manage().window().maximize();
    }


    @AfterTest
    public void tearDown() {
        this.driver.close();
        this.driver.quit();
    }


    /**
     * Select the link text and go to the login page
     */
    protected void goToTheLoginPage() {
        this.driver.findElement(By.linkText("K12 version")).click();
        this.wait.until(ExpectedConditions.titleIs("MathSpring Login"));
    }

    /**
     * Connection to databast
     */
    protected void databaseConnection() {
        try {
            Class.forName(dbClass).newInstance();
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
