package edu.umass.ckc.wo.tutor2;

import ckc.servlet.servbase.ServletInfo;
import edu.umass.ckc.wo.event.internal.InternalTutorEvent;
import edu.umass.ckc.wo.event.SessionEvent;
import edu.umass.ckc.wo.smgr.SessionManager;
import edu.umass.ckc.wo.tutconfig.LessonModelDescription;
import edu.umass.ckc.wo.tutconfig.TopicModelDescription;
import edu.umass.ckc.wo.tutor.Pedagogy;
import edu.umass.ckc.wo.tutor.probSel.PedagogicalModelParameters;
import edu.umass.ckc.wo.tutor.response.Response;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/23/15
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class LessonModel implements TutorModelInterface{

    protected SessionManager smgr;
    protected LessonModelDescription descr;
    protected PedagogicalModelParameters  pmParams;


    public LessonModel(SessionManager smgr) {
        this.smgr = smgr;
    }

    /**
     * Given the definition in the LessonModel, extract what's relevant to building a Lesson Model so that this can run
     * based on the definition.  This includes rules that say what should happen on BeginLesson, EndLesson, and BeginTopic
     * and EndTopic (handled in the TopicModel subclass of this which relies on this for some inheritance of model behavior)
     * @param smgr
     * @param params
     * @param lessonModelDescription
     */
    public LessonModel(SessionManager smgr, PedagogicalModelParameters params, LessonModelDescription lessonModelDescription) {
        this.descr = lessonModelDescription;
        this.smgr = smgr;
        this.pmParams=params;
        buildModelFromDescription(lessonModelDescription);
    }

    protected void buildModelFromDescription(LessonModelDescription lessonModelDescription) {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public Response processInternalTutorEvent(InternalTutorEvent e, ServletInfo servletInfo, SessionManager smgr) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response processUserEvent(SessionEvent e, ServletInfo servletInfo, SessionManager smgr) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }



    public static LessonModel buildLessonModel(SessionManager smgr, PedagogicalModelParameters params, LessonModelDescription lessonModelDescription) throws SQLException {
        if (lessonModelDescription instanceof TopicModelDescription)
            return new TopicModel(smgr,params,(TopicModelDescription) lessonModelDescription);
        else return new LessonModel(smgr,params,lessonModelDescription);
    }
}
