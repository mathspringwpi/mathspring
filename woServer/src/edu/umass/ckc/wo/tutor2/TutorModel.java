package edu.umass.ckc.wo.tutor2;

import ckc.servlet.servbase.ServletInfo;
import edu.umass.ckc.wo.event.internal.InternalTutorEvent;
import edu.umass.ckc.wo.event.LoginEvent;
import edu.umass.ckc.wo.event.SessionEvent;
import edu.umass.ckc.wo.login.LoginResult;
import edu.umass.ckc.wo.smgr.SessionManager;
import edu.umass.ckc.wo.tutconfig.TutorModelParameters;
import edu.umass.ckc.wo.tutor.pedModel.PedagogicalModel;
import edu.umass.ckc.wo.tutor.response.Response;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/23/15
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class TutorModel implements TutorModelInterface {
    private LearningCompanionModel lcModel;
    private SessionModel sessModel;
    private TutorModelParameters params;
    private PedagogicalModel pedagogicalModel;

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
            r = this.pedagogicalModel.processUserEvent(e, servletInfo, smgr);

        // TODO
        // if the user event was converted to an internalTutorEvent which method on the lcModel do I call?
        lcModel.processUserEvent(e,servletInfo,smgr);
        return null;

    }

    public Response processLoginEvent (LoginEvent e) {
        return sessModel.processLoginEvent(e);
    }


    public PedagogicalModel getPedagogicalModel() {
        return this.pedagogicalModel;
    }

    public void setPedagogicalModel(PedagogicalModel pedagogicalModel) {
        this.pedagogicalModel = pedagogicalModel;
    }

    public TutorModelParameters getParams() {
        return params;
    }

    public void setParams(TutorModelParameters params) {
        this.params = params;
    }

    public LearningCompanionModel getLearningCompanionModel () {
        return this.lcModel;
    }

    public void newSession (int sessId) throws SQLException {
        this.pedagogicalModel.newSession(sessId);
    }





}
