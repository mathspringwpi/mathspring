package edu.umass.ckc.wo.tutor2;

import ckc.servlet.servbase.ServletInfo;
import edu.umass.ckc.wo.event.internal.InternalTutorEvent;
import edu.umass.ckc.wo.event.SessionEvent;
import edu.umass.ckc.wo.smgr.SessionManager;
import edu.umass.ckc.wo.tutor.response.Response;
import edu.umass.ckc.wo.tutormeta.LearningCompanion;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/23/15
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class LearningCompanionModel implements TutorModelInterface {
    private LearningCompanion lc;

    @Override
    public Response processInternalTutorEvent(InternalTutorEvent e, ServletInfo servletInfo, SessionManager smgr) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response processUserEvent(SessionEvent e, ServletInfo servletInfo, SessionManager smgr) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public LearningCompanion getLearningCompanion () {
        return lc;
    }
}
