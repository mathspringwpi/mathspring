/*
 * @author Huy Tran - hqtran@wpi.edu
 */
package functional.test.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseDriver {


    private static Connection conn = null;
    private static Statement stmt;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/wayangoutpostdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String dbClass = "com.mysql.jdbc.Driver";


    public Statement getStmt() {
        return stmt;
    }

    /**
     * Connection to databast
     */
    public void databaseConnection() {
        try {
            Class.forName(dbClass).newInstance();
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Get the latest sessionId
     */
    public String getCurrentSessionId() {
        String q = "SELECT * FROM eventlog ORDER BY id DESC LIMIT 1";
        try {
            ResultSet rs = stmt.executeQuery(q);
            return rs.next() ? rs.getString("sessNum") : null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get the last action of the current sessionId
     */
    public String getLastAction() {
        String q = "SELECT * FROM eventlog ORDER BY id DESC LIMIT 1";
        try {
            ResultSet rs = stmt.executeQuery(q);
            return rs.next() ? rs.getString("action") : null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getLastActionTimestamp(String action) {
        String q = "select * from eventlog where sessNum=" + this.getCurrentSessionId() + " AND action='" + action + "'order by id desc limit 1";

        try {
            ResultSet rs = stmt.executeQuery(q);
            return rs.next() ? rs.getString("clickTime") : null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
