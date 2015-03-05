package edu.umass.ckc.wo.tutor2;

import ckc.servlet.servbase.ServletInfo;
import edu.umass.ckc.wo.event.internal.InternalTutorEvent;
import edu.umass.ckc.wo.event.SessionEvent;
import edu.umass.ckc.wo.smgr.SessionManager;
import edu.umass.ckc.wo.tutor.response.Response;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/23/15
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TutorModelInterface {

    public Response processInternalTutorEvent (InternalTutorEvent e, ServletInfo servletInfo, SessionManager smgr) ;

    public Response processUserEvent (SessionEvent e, ServletInfo servletInfo, SessionManager smgr) throws Exception;
}
