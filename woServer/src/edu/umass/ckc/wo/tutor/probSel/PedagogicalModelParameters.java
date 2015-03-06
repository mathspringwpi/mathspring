package edu.umass.ckc.wo.tutor.probSel;

import edu.umass.ckc.wo.tutormeta.PedagogyParams;
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
    private boolean showExampleFirst;

    private boolean showAllExample;

    private boolean singleTopicMode = false;
    private int problemReuseIntervalSessions;
    private int problemReuseIntervalDays;
    private boolean showMPP=true;

    // overload the params of this with those given for class.
    public PedagogicalModelParameters overload(PedagogicalModelParameters classParams) {
        if (classParams == null) return this;
        if (classParams.getDifficultyRate() > 0)
            this.difficultyRate =classParams.getDifficultyRate();
        if (classParams.getMaxNumberProbs() > 0)
            this.maxNumberProbs =classParams.getMaxNumberProbs();
        if (classParams.getMinNumberProbs() > 0)
            this.minNumberProbs =classParams.getMinNumberProbs();
        if (classParams.getMaxTimeInTopic() > 0)
            this.maxTimeInTopic =classParams.getMaxTimeInTopic();
        if (classParams.getMinTimeInTopic() > 0)
            this.minTimeInTopic =classParams.getMinTimeInTopic();
        if (classParams.getContentFailureThreshold() > 0)
            this.contentFailureThreshold =classParams.getContentFailureThreshold();
        if (classParams.getTopicMastery() > 0)
            this.topicMastery =classParams.getTopicMastery();
        if (classParams.getExternalActivityTimeThreshold() > 0)
            this.externalActivityTimeThreshold =classParams.getExternalActivityTimeThreshold();
        if (classParams.getTopicIntroFrequency() != null)
            this.topicIntroFrequency =classParams.getTopicIntroFrequency();
        if (classParams.getTopicExampleFrequency() != null)
            this.topicExampleFrequency =classParams.getTopicExampleFrequency();
        if (classParams.getProblemReuseIntervalSessions() > 0)
            this.problemReuseIntervalSessions =classParams.getProblemReuseIntervalSessions();
        return this;

    }

    public PedagogicalModelParameters overload (PedagogyParams userParams) {
        if (userParams == null)
            return this;
        if (userParams.isShowIntro())
            this.topicIntroFrequency=frequency.oncePerSession;
        if (userParams.getMode().equalsIgnoreCase("Example"))  {
            topicExampleFrequency = frequency.always;
        }
        else if (userParams.getMode().equalsIgnoreCase("Practice")) {
            topicExampleFrequency = frequency.never;
        }
        else {
            topicExampleFrequency = frequency.oncePerSession;
        }
        topicIntroFrequency = userParams.isShowIntro() ? frequency.oncePerSession : frequency.never;
        maxTimeInTopic = userParams.getMaxTime();
        minTimeInTopic = 0;
        maxNumberProbs = userParams.getMaxProbs();
        topicMastery = userParams.getTopicMastery();
        minNumberProbs = 1;
        this.singleTopicMode = userParams.isSingleTopicMode();
        // If we get passed no topic from Assistments, then this translates into setting the maxtime in the topic to 0
        // so we'll show the one forced problem and out.
        if (userParams.getTopicId() == -1)
            this.setMaxTimeInTopic(0);
        return this;
    }





    public PedagogicalModelParameters(int difficultyRate, int externalActivityTimeThreshold) {
        this.difficultyRate= difficultyRate;
        this.externalActivityTimeThreshold= externalActivityTimeThreshold;
        this.problemReuseIntervalSessions =DEFAULT_PROBLEM_REUSE_INTERVAL_SESSIONS;
        this.problemReuseIntervalDays =DEFAULT_PROBLEM_REUSE_INTERVAL_DAYS;
    }

    // Called with parameters read from TeacherAdmin's class config
    public PedagogicalModelParameters(long maxTimeInTopic, int contentFailureThreshold, double topicMastery, int minNumberProbs,
                                      long minTimeInTopic, int difficultyRate, int externalActivityTimeThreshold, int maxNumberProbs,
                                      boolean showTopicIntro, boolean showExampleProblemFirst, frequency topicIntroFreq, frequency exampleFreq,
                                      int probReuseIntervalSessions, int probReuseIntervalDays) {
        this.maxNumberProbs = maxNumberProbs;
        this.contentFailureThreshold = contentFailureThreshold;
        this.topicMastery = topicMastery;
        this.minNumberProbs= minNumberProbs;
        this.difficultyRate= difficultyRate;
        this.externalActivityTimeThreshold= externalActivityTimeThreshold;
        this.showExampleFirst= showExampleProblemFirst;
        this.problemReuseIntervalSessions = probReuseIntervalSessions;
        this.problemReuseIntervalDays = probReuseIntervalDays;
    }

    public PedagogicalModelParameters() {


        this.difficultyRate=DIFFICULTY_RATE;
        this.externalActivityTimeThreshold = EXTERNAL_ACTIVITY_TIME_TRHRESHOLD;
        this.showExampleFirst= true;

        this.problemReuseIntervalSessions =DEFAULT_PROBLEM_REUSE_INTERVAL_SESSIONS;
        this.problemReuseIntervalDays =DEFAULT_PROBLEM_REUSE_INTERVAL_DAYS;
        this.showMPP = DEFAULT_SHOW_MPP;
    }


    public PedagogicalModelParameters(String mode, boolean showIntro, long maxtime, int maxprobs, boolean singleTopicMode) {
        this();
        if (mode.equalsIgnoreCase("Example"))  {
            showAllExample = true;
            showExampleFirst = true;
        }
        else if (mode.equalsIgnoreCase("Practice")) {
            showExampleFirst = false;
            showAllExample = false;
        }
        else {
            showExampleFirst = true;
            showAllExample = false;
        }
        showTopicIntro = showIntro;
        maxTimeInTopic = maxtime;
        minTimeInTopic = 0;
        maxNumberProbs = maxprobs;
        minNumberProbs = 1;
        this.singleTopicMode = singleTopicMode;
    }


    // Assistments calls pass in a set of configuration params that seek to control a user's session.   These params override the ones
    // that are defined for the Assistments class that the user is in.
    public PedagogicalModelParameters(String mode, boolean showIntro, long maxtime, int maxprobs, boolean singleTopicMode, String ccss) {
        this();
        if (mode.equalsIgnoreCase("Example"))  {
            showAllExample = true;
            showExampleFirst = true;
        }
        else if (mode.equalsIgnoreCase("Practice")) {
            showExampleFirst = false;
            showAllExample = false;
        }
        else {
            showExampleFirst = true;
            showAllExample = false;
        }
        showTopicIntro = showIntro;
        maxTimeInTopic = maxtime;
        minTimeInTopic = 0;
        maxNumberProbs = maxprobs;
        minNumberProbs = 1;
        this.singleTopicMode = singleTopicMode;
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



    public boolean isSingleTopicMode() {
        return singleTopicMode;
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