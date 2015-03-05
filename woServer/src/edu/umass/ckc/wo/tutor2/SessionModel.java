package edu.umass.ckc.wo.tutor2;

import edu.umass.ckc.wo.event.internal.InternalTutorEvent;
import edu.umass.ckc.wo.event.LoginEvent;
import edu.umass.ckc.wo.event.SessionEvent;
import edu.umass.ckc.wo.smgr.SessionManager;
import edu.umass.ckc.wo.tutor.response.Response;
import ckc.servlet.servbase.ServletInfo;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/23/15
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionModel implements TutorModelInterface {
    private ServletInfo servletInfo;


    @Override
    public Response processInternalTutorEvent(InternalTutorEvent e, ServletInfo servletInfo, SessionManager smgr) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response processUserEvent(SessionEvent e, ServletInfo servletInfo, SessionManager smgr) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Response processLoginEvent(LoginEvent e) {
        if (e.isAdultInterface())
            return null; // new LoginAdult_1().process(servletInfo);
        return null;
    }
}
