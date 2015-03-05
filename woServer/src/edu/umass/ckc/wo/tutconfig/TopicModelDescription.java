package edu.umass.ckc.wo.tutconfig;

import org.jdom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 3/3/15
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class TopicModelDescription extends LessonModelDescription {

    /**
     * Build the TopicModel from the XML.  The TopicModel has two parts: a set of variables/values and a set of rules
     * @param topicModelElt
     */
    public TopicModelDescription(Element topicModelElt) {
        super(topicModelElt);
    }
}
