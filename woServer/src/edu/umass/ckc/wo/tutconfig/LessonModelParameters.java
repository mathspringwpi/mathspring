package edu.umass.ckc.wo.tutconfig;

import edu.umass.ckc.wo.tutormeta.UserTutorParams;

/**
 * Created with IntelliJ IDEA.
 * User: marshall
 * Date: 3/9/15
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class LessonModelParameters {

    public LessonModelParameters overload (LessonModelParameters p) {
        return p;
    }

    public LessonModelParameters overload (UserTutorParams p) {
        return null;
    }
}
