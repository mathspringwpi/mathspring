package edu.umass.ckc.wo.tutconfig;

import edu.umass.ckc.wo.tutor.probSel.PedagogicalModelParameters;

/**
 * Created with IntelliJ IDEA.
 * User: marshall
 * Date: 3/6/15
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class TutorModelParameters {
    private PedagogicalModelParameters pedagogicalModelParameters;
    private TopicModelParameters topicModelParameters;

    public TutorModelParameters(PedagogicalModelParameters pedagogicalModelParameters, TopicModelParameters topicModelParameters) {
        this.pedagogicalModelParameters = pedagogicalModelParameters;
        this.topicModelParameters = topicModelParameters;
    }

    public TutorModelParameters() {
        this.pedagogicalModelParameters = new PedagogicalModelParameters();
        this.topicModelParameters = new TopicModelParameters();
    }

    public PedagogicalModelParameters getPedagogicalModelParameters() {
        return pedagogicalModelParameters;
    }

    public TopicModelParameters getTopicModelParameters() {
        return topicModelParameters;
    }
}
