/*
 * @author: Huy Tran - hqtran@wpi.edu
 */
package functional.test.dashboard;

import functional.test.base.BaseTest;
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
        super.setUp();
        databaseConnection();
    }


    @Test(description = "demo")
    public void testDemo() {
        try {
            String query = "SELECT * FROM problem";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
