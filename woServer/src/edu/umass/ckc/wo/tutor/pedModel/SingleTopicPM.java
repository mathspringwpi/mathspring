package edu.umass.ckc.wo.tutor.pedModel;

import edu.umass.ckc.wo.event.tutorhut.NextProblemEvent;
import edu.umass.ckc.wo.event.tutorhut.TeachTopicEvent;
import edu.umass.ckc.wo.html.tutor.TutorPage;
import edu.umass.ckc.wo.smgr.SessionManager;
import edu.umass.ckc.wo.tutconfig.TopicModelParameters;
import edu.umass.ckc.wo.tutconfig.TutorModelParameters;
import edu.umass.ckc.wo.tutor.TutoringStrategy;
import edu.umass.ckc.wo.tutor.probSel.*;
import edu.umass.ckc.wo.tutor.response.ProblemResponse;
import edu.umass.ckc.wo.tutor.response.Response;
import edu.umass.ckc.wo.tutormeta.*;

/**
 * Created by IntelliJ IDEA.
 * User: marshall
 * Date: 6/21/12
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class SingleTopicPM extends BasePedagogicalModel {


    public void configure(UserTutorParams userParams) {
        String mode = userParams.getMode();
        frequency exFreq=frequency.never;
        if (mode.equals(TeachTopicEvent.EXAMPLE_PRACTICE_MODE) || mode.equals(TeachTopicEvent.EXAMPLE_MODE))
            exFreq= frequency.oncePerSession;
        frequency topicIntroFreq = frequency.never;
        if (userParams.isShowIntro())
            topicIntroFreq = frequency.oncePerSession;
        TopicModelParameters topicModelParameters = new TopicModelParameters(exFreq,topicIntroFreq,userParams.getMaxTime(),TopicModelParameters.MIN_NUM_PROBS_PER_TOPIC,
                userParams.getMaxProbs(),TopicModelParameters.MIN_NUM_PROBS_PER_TOPIC,userParams.getTopicMastery(),TopicModelParameters.CONTENT_FAILURE_THRESHOLD);
        PedagogicalModelParameters pmParams = new PedagogicalModelParameters(userParams.getCcss());
        TutorModelParameters tutParams = new TutorModelParameters(pmParams,topicModelParameters);
        setParams(new TutorModelParameters(pmParams,topicModelParameters));
        ProblemSelector psel = getProblemSelector();
        psel.setParameters(tutParams);
    }


    public SingleTopicPM() {
    }

    public SingleTopicPM(SessionManager smgr, TutoringStrategy p) throws Exception {
        super(smgr,p);
//        this.smgr = smgr;
//        this.studentModel = new BaseStudentModel(smgr.getConnection());
//        this.smgr.setStudentModel(this.studentModel);
//        PedagogicalModelParameters params = smgr.getPedagogicalModelParameters();
//        reviewModeSelector = new ReviewModeProblemSelector();
//        challengeModeSelector = new ChallengeModeProblemSelector();
//        hintSelector = new PercentageHintSelector();
//        problemSelector = new SingleTopicProblemSelector(smgr,params);
//        problemSelector.init(smgr);



    }


    public Response processNextProblemRequest(NextProblemEvent e) throws Exception {
        Response resp = super.processNextProblemRequest(e);
        // if we get back a response which is noMoreProblems, we want to insert a farewell page in the return
        if (resp instanceof ProblemResponse && ((ProblemResponse) resp).getProblem() == null) {
            ((ProblemResponse) resp).setEndPage(TutorPage.END_TUTOR_FRAME_CONTENT);

        }
        return resp;

    }

    protected int switchTopics (int curTopic) throws Exception {
        return -1;
    }


}
