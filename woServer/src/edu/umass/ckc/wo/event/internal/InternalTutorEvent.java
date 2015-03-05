package edu.umass.ckc.wo.event.internal;

import edu.umass.ckc.wo.event.SessionEvent;
import edu.umass.ckc.wo.tutor.response.Response;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/23/15
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class InternalTutorEvent extends Response {
    private SessionEvent userEvent;


    public InternalTutorEvent (SessionEvent e) {
        this.userEvent = e;

    }

    public InternalTutorEvent (){

    }

    public SessionEvent getUserEvent() {
        return userEvent;
    }
}
