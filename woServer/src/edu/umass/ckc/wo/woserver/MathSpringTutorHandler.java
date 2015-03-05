package edu.umass.ckc.wo.woserver;


import ckc.servlet.servbase.ServletEvent;
import ckc.servlet.servbase.ServletInfo;
import edu.umass.ckc.wo.event.*;
import edu.umass.ckc.wo.smgr.SessionManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MathSpringTutorHandler {
//    private WoModel model_;
    private TutorBrainEventFactory eventFactory;

    private static Logger logger =   Logger.getLogger(MathSpringTutorHandler.class);

    private ServletInfo servletInfo;




    public MathSpringTutorHandler(ServletInfo info) throws Exception {
        this.servletInfo = info;
        eventFactory = new TutorBrainEventFactory();
    }


    public boolean handleRequest() throws Throwable {
        ServletEvent e = eventFactory.buildEvent(servletInfo.getParams(), "tutorhut");
        SessionManager smgr = new SessionManager(servletInfo.getConn(),((SessionEvent) e).getSessionId(), servletInfo.getHostPath(),
                servletInfo.getContextPath()).buildExistingSession();
        if (e instanceof DbTestEvent) {
            if (testDB(servletInfo.getConn()))
                servletInfo.getOutput().append("Successful db query ran.");
            else servletInfo.getOutput().append("Db query did not return result set.");
        }
        return true;
    }

    private boolean testDB (Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String q = "select count(*) from student";
            ps = conn.prepareStatement(q);
            rs = ps.executeQuery();
            if (rs.next())
                return true;
            else return false;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
        }
    }


}