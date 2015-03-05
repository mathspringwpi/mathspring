package edu.umass.ckc.wo.tutconfig;

import edu.umass.ckc.wo.tutor2.LessonModel;
import edu.umass.ckc.wo.xml.JDOMUtils;
import org.jdom.Document;
import org.jdom.Element;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 3/3/15
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class LessonModels {
    public static final String STYLE_TOPICS = "topics";
    public static final String STYLE_CCLESSONS = "cclessons";

    private static Map<String,LessonModelDescription> models;
    private static boolean loaded = false;

    public static void readLessonModels (InputStream str) {
        models = new Hashtable<String,LessonModelDescription>();
        Document d = JDOMUtils.makeDocument(str);
        Element root = d.getRootElement();
        List<Element> children = root.getChildren("LessonModel");
        for (Element lessonModel: children) {
            readLessonModel(lessonModel);
        }
        loaded = true;
    }

    private static void readLessonModel (Element lmElt) {
        String type = lmElt.getAttributeValue("style");
        String name = lmElt.getAttributeValue("name");
        if (type.equalsIgnoreCase(STYLE_TOPICS))
            models.put(name,new LessonModelDescription(lmElt)) ;
        else models.put(name,new TopicModelDescription(lmElt));

    }

    public static LessonModelDescription getLessonDescription (String name) {
        return models.get(name);
    }

    public static boolean lessonsLoaded () {
        return loaded;
    }
}
