package edu.umass.ckc.wo.event.internal;

import edu.umass.ckc.wo.event.SessionEvent;
import edu.umass.ckc.wo.tutor.pedModel.EndOfTopicInfo;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/27/15
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class EndTopicEvent extends InternalTutorEvent {
    private int topicId;
    private EndOfTopicInfo info;

    public EndTopicEvent(SessionEvent userEvent, int topicId, EndOfTopicInfo eotInfo) {
        super(userEvent);
        this.topicId = topicId;
        this.info = eotInfo;
    }
}
