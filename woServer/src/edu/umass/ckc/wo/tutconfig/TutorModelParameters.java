package edu.umass.ckc.wo.tutconfig;

import edu.umass.ckc.wo.tutor.probSel.PedagogicalModelParameters;
import edu.umass.ckc.wo.tutormeta.UserTutorParams;

/**
 * Created with IntelliJ IDEA.
 * User: marshall
 * Date: 3/6/15
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class TutorModelParameters {
    private PedagogicalModelParameters pedagogicalModelParameters;
    private LessonModelParameters lessonModelParameters;

    public TutorModelParameters(PedagogicalModelParameters pedagogicalModelParameters, LessonModelParameters lessonModelParameters) {
        this.pedagogicalModelParameters = pedagogicalModelParameters;
        this.lessonModelParameters = lessonModelParameters;
    }

    public TutorModelParameters() {
        this.pedagogicalModelParameters = new PedagogicalModelParameters();
        this.lessonModelParameters = new TopicModelParameters();
    }

    public PedagogicalModelParameters getPedagogicalModelParameters() {
        return pedagogicalModelParameters;
    }

    public LessonModelParameters getLessonModelParameters() {
        return lessonModelParameters;
    }

    public void overload(TutorModelParameters classParams) {
        pedagogicalModelParameters = pedagogicalModelParameters.overload(classParams.getPedagogicalModelParameters());
        lessonModelParameters = lessonModelParameters.overload(classParams.getLessonModelParameters());
    }

    public void overload (UserTutorParams params) {
        lessonModelParameters.overload(params);
        pedagogicalModelParameters.overload(params);
    }
}
