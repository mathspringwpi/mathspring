package edu.umass.ckc.wo.tutconfig;

import org.jdom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 3/3/15
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class LessonModelDescription {
    private Element lessonModelElt; // XML element read from pedagogies.xml that defines the LessonModel
    private String name;
    private Element controlParamsElt;
    private String ruleClass;
    private int maxNumberProbs;
    private long maxTimeInTopic;
    private double topicMastery;


    public LessonModelDescription(Element lessonModelElt) {
        this.lessonModelElt = lessonModelElt;
        parse(this.lessonModelElt);
    }

    protected void parse (Element lessonModelElt) {
        this.name = lessonModelElt.getAttributeValue("name");
        this.controlParamsElt = lessonModelElt.getChild("controlParameters");
        Element elt = lessonModelElt.getChild("controlRules");
        if (elt != null) {
            ruleClass = elt.getAttributeValue("class");
        }
        else ruleClass = null;
    }

    public boolean hasRules () {
        return ruleClass != null;
    }

    public Class getRuleClass () throws ClassNotFoundException {
        return Class.forName(this.ruleClass);
    }

    public Element getControlParamsElt() {
        return controlParamsElt;
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

    public void setMaxTimeInTopic(long maxTimeInTopic) {
        this.maxTimeInTopic = maxTimeInTopic;
    }

    public double getTopicMastery() {
        return topicMastery;
    }

    public void setTopicMastery(double topicMastery) {
        this.topicMastery = topicMastery;
    }
}
