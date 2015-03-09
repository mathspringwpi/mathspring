package edu.umass.ckc.wo.tutor.probSel;

import edu.umass.ckc.wo.event.tutorhut.TeachTopicEvent;
import edu.umass.ckc.wo.tutormeta.UserTutorParams;
import edu.umass.ckc.wo.tutormeta.frequency;

/**
 * Created by IntelliJ IDEA.
 * User: marshall
 * Date: Apr 18, 2011
 * Time: 9:42:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class PedagogicalModelParameters {

    public static final int DIFFICULTY_RATE = 2;
    public static final int EXTERNAL_ACTIVITY_TIME_TRHRESHOLD = 10;   // given in minutes

    public static final int DEFAULT_PROBLEM_REUSE_INTERVAL_SESSIONS =3;
    public static final int DEFAULT_PROBLEM_REUSE_INTERVAL_DAYS =10;
    public static final boolean DEFAULT_SHOW_MPP =true;


    private String ccss;
    private int difficultyRate ; // this is the divisor that the problem selector uses to find increase/decrease its index into the
    private int externalActivityTimeThreshold;
    private boolean showAllExample;
    private int problemReuseIntervalSessions;
    private int problemReuseIntervalDays;
    private boolean showMPP=true;

    public PedagogicalModelParameters() {
        this.difficultyRate=DIFFICULTY_RATE;
        this.externalActivityTimeThreshold = EXTERNAL_ACTIVITY_TIME_TRHRESHOLD;
        this.problemReuseIntervalSessions =DEFAULT_PROBLEM_REUSE_INTERVAL_SESSIONS;
        this.problemReuseIntervalDays =DEFAULT_PROBLEM_REUSE_INTERVAL_DAYS;
        this.showMPP = DEFAULT_SHOW_MPP;
    }

    public PedagogicalModelParameters (String ccss) {
        this();
        this.ccss = ccss;
    }


    public PedagogicalModelParameters(int probReuseIntervalSessions, int probReuseIntervalDays, int difficultyRate, int externalActThreshold) {
        this.problemReuseIntervalSessions = probReuseIntervalSessions;
        this.problemReuseIntervalDays = probReuseIntervalDays;
        this.difficultyRate=difficultyRate;
        this.externalActivityTimeThreshold=externalActThreshold;
    }

    public PedagogicalModelParameters(int difficultyRate, int externalActivityTimeThreshold) {
        this.difficultyRate= difficultyRate;
        this.externalActivityTimeThreshold= externalActivityTimeThreshold;
        this.problemReuseIntervalSessions =DEFAULT_PROBLEM_REUSE_INTERVAL_SESSIONS;
        this.problemReuseIntervalDays =DEFAULT_PROBLEM_REUSE_INTERVAL_DAYS;
    }

    // overload the params of this with those given for class.
    public PedagogicalModelParameters overload(PedagogicalModelParameters classParams) {
        if (classParams == null) return this;

        if (classParams.getExternalActivityTimeThreshold() > 0)
            this.externalActivityTimeThreshold =classParams.getExternalActivityTimeThreshold();

        if (classParams.getProblemReuseIntervalSessions() > 0)
            this.problemReuseIntervalSessions =classParams.getProblemReuseIntervalSessions();
        return this;

    }

    /**
     * Overload the parameters with those that have been saved for a particular user (e.g. passed in a URL from Assistments)
     * @param userParams
     * @return
     */
    public PedagogicalModelParameters overload (UserTutorParams userParams) {


        return this;
    }













    // Assistments calls pass in a set of configuration params that seek to control a user's session.   These params override the ones
    // that are defined for the Assistments class that the user is in.
    public PedagogicalModelParameters(String mode,  String ccss) {
        this();
        if (mode.equalsIgnoreCase(TeachTopicEvent.EXAMPLE_MODE))  {
            showAllExample = true;
        }
        else showAllExample = false;
        this.ccss = ccss;
    }











    public int getDifficultyRate() {
        return difficultyRate;
    }

    public void setDifficultyRate(int difficultyRate) {
        this.difficultyRate = difficultyRate;
    }

    public int getExternalActivityTimeThreshold() {
        return externalActivityTimeThreshold;
    }

    public void setExternalActivityTimeThreshold(int externalActivityTimeThreshold) {
        this.externalActivityTimeThreshold = externalActivityTimeThreshold;
    }




    public boolean isShowAllExample() {
        return showAllExample;
    }




    public String getCcss(){
        return ccss;
    }







    public void setProblemReuseIntervalSessions(String problemReuseIntervalSessions) {
        this.problemReuseIntervalSessions = Integer.parseInt(problemReuseIntervalSessions);
    }

    public int getProblemReuseIntervalSessions() {
        return problemReuseIntervalSessions;
    }

    public void setProblemReuseIntervalDays(String problemReuseIntervalDays) {
        this.problemReuseIntervalDays = Integer.parseInt(problemReuseIntervalDays);
    }

    public int getProblemReuseIntervalDays() {
        return problemReuseIntervalDays;
    }

    public void setProblemReuseIntervalDays(int problemReuseIntervalDays) {
        this.problemReuseIntervalDays = problemReuseIntervalDays;
    }

    public boolean isShowMPP() {
        return showMPP;
    }

    public void setShowMPP(boolean showMPP) {
        this.showMPP = showMPP;
    }
}