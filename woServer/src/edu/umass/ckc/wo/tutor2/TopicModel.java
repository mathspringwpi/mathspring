package edu.umass.ckc.wo.tutor2;

import ckc.servlet.servbase.ServletInfo;
import edu.umass.ckc.wo.cache.ProblemMgr;
import edu.umass.ckc.wo.content.Problem;
import edu.umass.ckc.wo.content.TopicIntro;
import edu.umass.ckc.wo.event.SessionEvent;
import edu.umass.ckc.wo.event.internal.BeginTopicEvent;
import edu.umass.ckc.wo.event.internal.EndTopicEvent;
import edu.umass.ckc.wo.event.internal.InternalTutorEvent;
import edu.umass.ckc.wo.event.tutorhut.IntraProblemEvent;
import edu.umass.ckc.wo.smgr.SessionManager;
import edu.umass.ckc.wo.smgr.StudentState;
import edu.umass.ckc.wo.tutconfig.LessonModelDescription;
import edu.umass.ckc.wo.tutconfig.TopicModelDescription;
import edu.umass.ckc.wo.tutor.pedModel.EndOfTopicInfo;
import edu.umass.ckc.wo.tutor.pedModel.ProblemGrader;
import edu.umass.ckc.wo.tutor.pedModel.ProblemScore;
import edu.umass.ckc.wo.tutor.pedModel.TopicSelectorImpl;
import edu.umass.ckc.wo.tutor.probSel.PedagogicalModelParameters;
import edu.umass.ckc.wo.tutor.response.Response;
import edu.umass.ckc.wo.tutormeta.TopicSelector;
import edu.umass.ckc.wo.tutormeta.frequency;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/27/15
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class TopicModel extends LessonModel {

    protected TopicSelector topicSelector;
    protected ProblemGrader.difficulty nextDiff;
    protected EndOfTopicInfo reasonsForEndOfTopic;





    /**
     *
     * @param smgr
     * @param topicModelDescription
     * @throws SQLException
     */
    public TopicModel(SessionManager smgr, PedagogicalModelParameters pmParams, TopicModelDescription topicModelDescription) throws SQLException {
        super(smgr, pmParams,topicModelDescription);
        // TODO need to extract the rules for BeginTopic/EndTopic processing and get any other parameters that define how
        // the topic model should work
        topicSelector = new TopicSelectorImpl(smgr,pmParams,topicModelDescription);
    }

    protected void buildModelFromDescription(LessonModelDescription lessonModelDescription) {

    }

    protected boolean gradeProblem (long probElapsedTime) throws Exception {
        StudentState state = smgr.getStudentState();
        ProblemGrader grader = new ProblemGrader();
        Problem lastProb = ProblemMgr.getProblem(state.getCurProblem());
        String lastProbMode = state.getCurProblemMode();
        Problem curProb=null;
        ProblemScore score=null;

        if (lastProb != null  && lastProbMode.equals(Problem.PRACTICE))     {
            score = grader.gradePerformance(smgr.getConnection(),lastProb,smgr.getStudentState());
            nextDiff = grader.getNextProblemDifficulty(score);
        }
        else nextDiff = ProblemGrader.difficulty.SAME;
        this.reasonsForEndOfTopic=  topicSelector.isEndOfTopic(probElapsedTime, nextDiff);
        boolean topicDone = reasonsForEndOfTopic.isTopicDone();
        return topicDone;
    }

    protected TopicIntro getTopicIntro (int curTopic) throws Exception {
        frequency topicIntroFreq, exampleFreq;
        topicIntroFreq= this.params.getTopicIntroFrequency();
        // if the topic intro sho
        if (topicIntroFreq == frequency.always) {
            if (!smgr.getStudentState().isTopicIntroSeen(curTopic))
                smgr.getStudentState().addTopicIntrosSeen(curTopic);
            TopicIntro intro =topicSelector.getIntro(curTopic);
            lessonIntroGiven(intro ); // inform pedagogical move listeners that an intervention is given
            return intro;
        }
        else if (topicIntroFreq == frequency.oncePerSession &&
                !smgr.getStudentState().isTopicIntroSeen(curTopic)) {
            smgr.getStudentState().addTopicIntrosSeen(curTopic);
            TopicIntro intro =topicSelector.getIntro(curTopic);
            lessonIntroGiven(intro); // inform pedagogical move listeners that an intervention is given
            return intro;
        }

        return null;
    }

    /**
     * Process user events (TutorHutEvents).   Will figure out if the current topic is done.
     * Will either return a new internal event like EndTopicEvent or null.
     * @param e
     * @param servletInfo
     * @param smgr
     * @return
     * @throws Exception
     */
    @Override
    public Response processUserEvent(SessionEvent e, ServletInfo servletInfo, SessionManager smgr) throws Exception {

        assert(e instanceof IntraProblemEvent);  // This should only be called while processing events during tutoring
        IntraProblemEvent ee = (IntraProblemEvent) e;

        gradeProblem(ee.getProbElapsedTime());

        int curTopic = smgr.getStudentState().getCurTopic();
        this.reasonsForEndOfTopic=  topicSelector.isEndOfTopic(ee.getProbElapsedTime(), nextDiff);
        boolean topicDone = curTopic == -1 || reasonsForEndOfTopic.isTopicDone();
        // If the topic is done return an EndTopicEvent
        if (topicDone)
            return new EndTopicEvent(e, curTopic,this.reasonsForEndOfTopic);
        else return null;
    }

    @Override
    public Response processInternalTutorEvent(InternalTutorEvent e, ServletInfo servletInfo, SessionManager smgr) {
        if (e instanceof EndTopicEvent) {
            // run the rules for EndTopic and produce the appropriate action
            return null;
        }
        else if (e instanceof BeginTopicEvent) {
            // return stuff like topicIntros, examples, interventions
        }
        return null;
    }


}
