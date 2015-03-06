package edu.umass.ckc.wo.tutconfig;

import edu.umass.ckc.wo.event.tutorhut.TeachTopicEvent;
import edu.umass.ckc.wo.tutormeta.UserTutorParams;
import edu.umass.ckc.wo.tutormeta.frequency;
import org.jdom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 3/5/15
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class TopicModelParameters {

    private TopicModelDescription descr;

    private frequency topicIntroFrequency;
    private frequency topicExampleFrequency;
    private long maxTimeInTopic;   // this is in milliseconds
    private long minTimeInTopic;    // in milliseconds
    private int maxNumberProbs;
    private int minNumberProbs ;
    private double topicMastery;
    private boolean showTopicIntro;
    private int contentFailureThreshold ; // the number of times it will select a problem within this topic when it can't meet
    private boolean showExampleFirst;
    private boolean singleTopicMode;

    public static final int MAX_NUM_PROBS_PER_TOPIC = 8;
    public static final int MIN_NUM_PROBS_PER_TOPIC = 3;
    public static final int MAX_TIME_IN_TOPIC = 10 * 60 * 1000;
    public static final int MIN_TIME_IN_TOPIC = 30 * 1000;
    public static final int CONTENT_FAILURE_THRESHOLD = 1;
    public static final double TOPIC_MASTERY = 0.85;
    public static final frequency DEFAULT_TOPIC_INTRO_FREQ = frequency.always;
    public static final frequency DEFAULT_EXAMPLE_FREQ = frequency.always;


    public TopicModelParameters(TopicModelDescription descr) {
        this.descr = descr;
        setParams(descr);
    }

    public TopicModelParameters () {
        this.maxNumberProbs = MAX_NUM_PROBS_PER_TOPIC;
        this.maxTimeInTopic = MAX_TIME_IN_TOPIC;
        this.minNumberProbs=MIN_NUM_PROBS_PER_TOPIC;
        this.topicMastery = TOPIC_MASTERY;
        this.contentFailureThreshold = CONTENT_FAILURE_THRESHOLD;
        this.minTimeInTopic=MIN_TIME_IN_TOPIC;
        this.showTopicIntro = true;
        this.topicIntroFrequency = DEFAULT_TOPIC_INTRO_FREQ;
        this.topicExampleFrequency = DEFAULT_EXAMPLE_FREQ;
        this.singleTopicMode = false;
    }

    public TopicModelParameters(frequency topicIntroFrequency, frequency topicExampleFrequency, long maxTimeInTopicMin,
                                long minTimeInTopicMin, int maxNumberProbs, int minNumberProbs, double topicMastery,
                                int contentFailureThreshold) {
        this.topicIntroFrequency = topicIntroFrequency;
        this.topicExampleFrequency = topicExampleFrequency;
        setMaxTimeInTopicMinutes(maxTimeInTopicMin);
        setMinTimeInTopicMinutes(minTimeInTopicMin);
        this.maxNumberProbs = maxNumberProbs;
        this.minNumberProbs = minNumberProbs;
        this.topicMastery = topicMastery;
        this.contentFailureThreshold = contentFailureThreshold;
    }

    private void setParams(TopicModelDescription descr) {
        Element p = descr.getControlParamsElt() ;
        Element c = p.getChild("maxTimeInTopicSecs");
        String s;
        if (c != null) {
            s = c.getValue();
            int maxTimeSecs = Integer.parseInt(s);
            setMaxTimeInTopicSecs(maxTimeSecs);
        }
        c = p.getChild("topicMastery");
        if (c != null) {
            s = c.getValue();
            double topicMastery = Double.parseDouble(s);
            setTopicMastery(topicMastery);
        }
        c = p.getChild("showTopicIntro");
        if (c != null) {
            s = c.getValue();
            boolean showTopicIntro = Boolean.parseBoolean(s);
            setShowTopicIntro(showTopicIntro);
        }
        c = p.getChild("minTimeInTopicSecs");
        if (c != null) {
            s = c.getValue();
            int minTimeInTopicSecs = Integer.parseInt(s);
            setMinTimeInTopicSecs(minTimeInTopicSecs);
        }

        c = p.getChild("topicIntroFrequency");
        if (c != null) {
            s = c.getValue();
            setTopicIntroFrequency(s);
        }

        c = p.getChild("topicExampleFrequency");
        if (c != null) {
            s = c.getValue();
            setTopicExampleFrequency(s);
        }
        c = p.getChild("contentFailureThreshold");
        if (c != null) {
            s = c.getValue();
            int contentFailureThreshold = Integer.parseInt(s);
            setContentFailureThreshold(contentFailureThreshold);
        }
        c = p.getChild("showExampleFirst");
        if (c != null) {
            s = c.getValue();
            boolean showExampleFirst = Boolean.parseBoolean(s);
            setShowExampleFirst(showExampleFirst);
        }

    }

    public TopicModelParameters overload (TopicModelParameters classParams) {
        if (classParams == null) return this;
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

        if (classParams.getTopicIntroFrequency() != null)
            this.topicIntroFrequency =classParams.getTopicIntroFrequency();
        if (classParams.getTopicExampleFrequency() != null)
            this.topicExampleFrequency =classParams.getTopicExampleFrequency();
        return this;
    }

    public TopicModelParameters overload (UserTutorParams userParams) {
        if (userParams == null)
            return this;
        // user parameters have fields (showIntro and mode) that come from an external call such as Assistments.   This are then translated into showing
        // these things or not.
        topicExampleFrequency = (userParams.getMode().equals(TeachTopicEvent.EXAMPLE_PRACTICE_MODE) || userParams.getMode().equals(TeachTopicEvent.EXAMPLE_MODE)) ?
                frequency.oncePerSession : frequency.never;
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



    // gets the given TopicIntro frequency from a string
    public static frequency convertTopicIntroFrequency (String s) {
        if (s != null && !s.trim().equalsIgnoreCase(""))
            return frequency.valueOf(s);
        else return DEFAULT_TOPIC_INTRO_FREQ;
    }

    // gets the given TopicIntro frequency from a string
    public static frequency convertExampleFrequency (String s) {
        if (s != null && !s.trim().equalsIgnoreCase(""))
            return frequency.valueOf(s);
        else return DEFAULT_EXAMPLE_FREQ;
    }






    public boolean isShowTopicIntro() {
        return showTopicIntro;
    }

    public void setShowTopicIntro(boolean showTopicIntro) {
        this.showTopicIntro = showTopicIntro;
    }




    public void setMinNumberProbs(int minNumberProbs) {
        this.minNumberProbs = minNumberProbs;
    }

    public int getMinNumberProbs() {
        return minNumberProbs;
    }

    public int getMaxNumberProbs() {
        return maxNumberProbs;
    }

    public void setMaxNumberProbs(int maxNumberProbs) {
        this.maxNumberProbs = maxNumberProbs;
    }

    public long getMaxTimeInTopic() {
        return maxTimeInTopic;
    }

    public void setMaxTimeInTopicMinutes (double minutes) {
        maxTimeInTopic = (long) (minutes * 1000 * 60);
    }

    public void setMaxTimeInTopicSecs(int maxTimeInTopicSecs) {
        setMaxTimeInTopic(maxTimeInTopicSecs * 1000);
    }

    public int getMaxTimeInTopicMinutes () {
        return (int) (maxTimeInTopic / 60000);
    }

    public void setMaxTimeInTopic(long maxTimeInTopic) {
        this.maxTimeInTopic = maxTimeInTopic;
    }

    public double getTopicMastery() {
        return topicMastery;
    }

    public void setTopicMastery(double topicMastery) {
        this.topicMastery = topicMastery;
    }



    public void setMinTimeInTopicMinutes (double minutes) {
        minTimeInTopic = (long) (minutes * 1000 * 60);
    }

    public void setMinTimeInTopicSecs(int minTimeInTopicSecs) {
        this.minTimeInTopic = minTimeInTopicSecs * 1000;
    }

    public long getMinTimeInTopic() {
        return minTimeInTopic;
    }

    public long getMinTimeInTopicMinutes() {
        return minTimeInTopic/60000;
    }

    public void setTopicIntroFrequency(String topicIntroFrequency) {
        this.topicIntroFrequency = frequency.valueOf(topicIntroFrequency);
    }

    public frequency getTopicIntroFrequency() {
        return topicIntroFrequency;
    }

    public void setTopicExampleFrequency(String topicExampleFrequency) {
        this.topicExampleFrequency = frequency.valueOf(topicExampleFrequency);
    }

    public frequency getTopicExampleFrequency() {
        return topicExampleFrequency;
    }

    public int getContentFailureThreshold() {
        return contentFailureThreshold;
    }

    public void setContentFailureThreshold(int contentFailureThreshold) {
        this.contentFailureThreshold = contentFailureThreshold;
    }

    public boolean isShowExampleFirst() {
        return showExampleFirst;
    }

    public void setShowExampleFirst(boolean showExampleFirst) {
        this.showExampleFirst = showExampleFirst;
    }

}
