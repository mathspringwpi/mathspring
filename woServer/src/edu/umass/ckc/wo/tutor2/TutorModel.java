package edu.umass.ckc.wo.tutor2;

import ckc.servlet.servbase.ServletInfo;
import edu.umass.ckc.wo.event.internal.InternalTutorEvent;
import edu.umass.ckc.wo.event.LoginEvent;
import edu.umass.ckc.wo.event.SessionEvent;
import edu.umass.ckc.wo.smgr.SessionManager;
import edu.umass.ckc.wo.tutor.pedModel.PedagogicalModel;
import edu.umass.ckc.wo.tutor.response.Response;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/23/15
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class TutorModel implements TutorModelInterface {
    private PedagogicalModel pedModel;
    private LearningCompanionModel lcModel;
    private SessionModel sessModel;

    /**
     * Given the specification of a tutor from definition files (pedagogy, learning companion, lesson, session) build the appropriate
     * components into this TutorModel
     */
    public void buildTutorModel () {

    }

    @Override
    public Response processInternalTutorEvent(InternalTutorEvent e, ServletInfo servletInfo, SessionManager smgr) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response processUserEvent(SessionEvent e, ServletInfo servletInfo, SessionManager smgr) {
        // All user events other than logins are handled by the PedagogicalModel.   When it finally chooses
        // a response, we then pass this to the LearningCompanionModel to augment the response with a learning companion behavior
        Response r;
        if (e instanceof SessionEvent)
            r = pedModel.processUserEvent(e, servletInfo, smgr);

        // TODO
        // if the user event was converted to an internalTutorEvent which method on the lcModel do I call?
        lcModel.processUserEvent(e,servletInfo,smgr);
        return null;

    }

    public Response processLoginEvent (LoginEvent e) {
        return sessModel.processLoginEvent(e);
    }


}
