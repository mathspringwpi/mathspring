package edu.umass.ckc.wo.tutconfig;

import edu.umass.ckc.wo.tutor.TutoringStrategy;
import edu.umass.ckc.wo.tutor.intervSel2.InterventionSelectorParam;
import edu.umass.ckc.wo.tutor.intervSel2.InterventionSelectorSpec;
import edu.umass.ckc.wo.tutor.probSel.PedagogicalModelParameters;
import edu.umass.ckc.wo.xml.JDOMUtils;
import org.jdom.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;



/**
 * <p> Created by IntelliJ IDEA.
 * User: david
 * Date: Dec 19, 2007
 * Time: 4:22:53 PM    kk
 */
public class PedagogyParser {
    private List<TutoringStrategy> pedagogies;

    public static final String DEFAULT_PROBLEM_SELECTOR = "BaseTopicProblemSelector";
    public static final String DEFAULT_HINT_SELECTOR = "PercentageHintSelector";
    public static final String DEFAULT_PEDAGOGICAL_MODEL = "BasePedagogicalModel";
    public static final String DEFAULT_STUDENT_MODEL = "BaseStudentModel";
    public static final String DEFAULT_REVIEW_MODE_PROBLEM_SELECTOR = "ReviewModeProblemSelector";
    public static final String DEFAULT_CHALLENGE_MODE_PROBLEM_SELECTOR = "ChallengeModeProblemSelector";

    public PedagogyParser(InputStream str) throws ClassNotFoundException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, DataConversionException {
//        File f = new File(filename);
        Document d = JDOMUtils.makeDocument(str);
        pedagogies = readPedagogies(d);
    }



    /**
     *
     * @return  a List of Pedagogy objects created from the pedagogies.xml file
     */
    public List<TutoringStrategy> getPedagogies() {
        return pedagogies;
    }


    public List<TutoringStrategy> readPedagogies (Document xmlDoc) throws NoSuchMethodException, DataConversionException, IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException {
        Element root = xmlDoc.getRootElement();
        List<TutoringStrategy> result= new ArrayList<TutoringStrategy>();
        List children = root.getChildren("pedagogy");
        for (int i = 0; i < children.size(); i++) {
            Element element = (Element) children.get(i);
            result.add(readPed(element));
        }
        return result;
        }



    private TutoringStrategy readPed(Element pedElt) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, DataConversionException {
        Element e;
        TutoringStrategy tutoringStrategy = new TutoringStrategy();
        boolean isDefault=false;
        Attribute defaultAttr = pedElt.getAttribute("default");
        if (defaultAttr != null)
            tutoringStrategy.setDefault(defaultAttr.getBooleanValue());
        e = pedElt.getChild("pedagogicalModelClass");
        // by default we build a BasePedagogicalModel unless a class is provided.
        String pedModClass = DEFAULT_PEDAGOGICAL_MODEL;
        if (e != null)
            pedModClass = e.getValue();
        tutoringStrategy.setPedagogicalModelClass(pedModClass);

        e = pedElt.getChild("id");
        String id = e.getValue();
        tutoringStrategy.setId(id);

        e = pedElt.getChild("name");
        String name = e.getValue();
        tutoringStrategy.setName(name);

        e = pedElt.getChild("comment");
        if (e != null)  {
            String comment = e.getValue();
            tutoringStrategy.setComment(comment);
        }

        e = pedElt.getChild("studentModelClass");
        if (e != null) {
            String smClass = e.getValue();
            tutoringStrategy.setStudentModelClass(smClass);
        }
        else tutoringStrategy.setStudentModelClass(DEFAULT_STUDENT_MODEL);

        e = pedElt.getChild("problemSelectorClass");
        if (e != null) {
            String psClass = e.getValue();
            tutoringStrategy.setProblemSelectorClass(psClass);
        }
        else tutoringStrategy.setProblemSelectorClass(DEFAULT_PROBLEM_SELECTOR);

        e = pedElt.getChild("reviewModeProblemSelectorClass");
        if (e != null) {
            String psClass = e.getValue();
            tutoringStrategy.setReviewModeProblemSelectorClass(psClass);
        }
        else tutoringStrategy.setReviewModeProblemSelectorClass(DEFAULT_REVIEW_MODE_PROBLEM_SELECTOR);

        e = pedElt.getChild("challengeModeProblemSelectorClass");
        if (e != null) {
            String psClass = e.getValue();
            tutoringStrategy.setChallengeModeProblemSelectorClass(psClass);
        }
        else tutoringStrategy.setChallengeModeProblemSelectorClass(DEFAULT_CHALLENGE_MODE_PROBLEM_SELECTOR);

        e = pedElt.getChild("hintSelectorClass");
        if (e != null) {
            String hClass = e.getValue();
            tutoringStrategy.setHintSelectorClass(hClass);
        }
        else tutoringStrategy.setHintSelectorClass(DEFAULT_HINT_SELECTOR);

        e = pedElt.getChild("nextProblemInterventionSelector");
        if (e != null)
            readNextProblemInterventionSelectors(tutoringStrategy,e);
        e = pedElt.getChild("attemptInterventionSelector");
        if (e != null)
            readAttemptInterventionSelectors(tutoringStrategy,e);
        e = pedElt.getChild("controlParameters");
        // get the default params
        PedagogicalModelParameters params = new PedagogicalModelParameters();
        // if user provided some, overwrite the individual settings
        if (e != null)
            readControlParams(params, e);
        tutoringStrategy.setPedagogicalModelParams(params);
        e = pedElt.getChild("lessonModel");
        if (e != null) {
            String lessonName = e.getText();
            LessonModelDescription lmDescr = LessonModels.getLessonDescription(lessonName) ;
            tutoringStrategy.setLessonModelDescription(lmDescr);
        }

        e = pedElt.getChild("package");
        String packg = null ;
        if ( e != null )
            packg = e.getValue();
        tutoringStrategy.setPackg(packg);

        e = pedElt.getChild("learningCompanionClass");
        if ( e != null )
            tutoringStrategy.setLearningCompanionClass(e.getValue());

        return tutoringStrategy;

    }



    // Given a set of pm params that have default settings this reads the XML config and
    // overwrites any that are provided.
    private void readControlParams(PedagogicalModelParameters params, Element p) {

        Element c;
        String s;






        c = p.getChild("difficultyRate");
        if (c != null) {
            s = c.getValue();
            int difficultyRate = Integer.parseInt(s);
            params.setDifficultyRate(difficultyRate);
        }

        c = p.getChild("externalActivityTimeThresholdMins");
        if (c != null) {
            s = c.getValue();
            int externalActivityTimeThresholdMins = Integer.parseInt(s);
            params.setExternalActivityTimeThreshold(externalActivityTimeThresholdMins);
        }


        c = p.getChild("problemReuseIntervalSessions");
        if (c != null) {
            s = c.getValue();
            params.setProblemReuseIntervalSessions(s);
        }
        c = p.getChild("problemReuseIntervalDays");
        if (c != null) {
            s = c.getValue();
            params.setProblemReuseIntervalDays(s);
        }
        c = p.getChild("displayMyProgressPage");
        if (c != null) {
            s = c.getValue();
            boolean showMpp = Boolean.parseBoolean(s);
            params.setShowMPP(showMpp);
        }

    }

    private void readAttemptInterventionSelectors(TutoringStrategy p, Element e) {
        InterventionSelectorSpec spec = parseSelector(e);
        p.setAttemptInterventionSelector(spec);
        List<Element> subSelectors = e.getChildren("attemptInterventionSelector");
        List<InterventionSelectorSpec> subs = new ArrayList<InterventionSelectorSpec>();
        for (Element elt : subSelectors) {
            subs.add(parseSelector(elt));
        }
        p.setSubAttemptInterventionSelectors(subs);

    }

    private void readNextProblemInterventionSelectors(TutoringStrategy p, Element e) {
        InterventionSelectorSpec spec = parseSelector(e);
        p.setNextProblemInterventionSelector(spec);
        List<Element> subSelectors = e.getChildren("nextProblemInterventionSelector");
        List<InterventionSelectorSpec> subs = new ArrayList<InterventionSelectorSpec>();
        for (Element elt : subSelectors) {
            subs.add(parseSelector(elt));
        }
        p.setSubNextProblemInterventionSelectors(subs);
    }

    private InterventionSelectorSpec parseSelector (Element elt) {
        String className = elt.getAttributeValue("class");
        String selectProblem = elt.getAttributeValue("selectProblem");
        List<InterventionSelectorParam> paramSpecs = new ArrayList<InterventionSelectorParam>();
        List<Element> params = elt.getChildren("param");
        for (Element param: params) {
            String name = param.getAttributeValue("name");
            String value = param.getValue();
            InterventionSelectorParam pSpec = new InterventionSelectorParam(name,value);
            paramSpecs.add(pSpec);
        }
        // We want this intervention to show at the same time as a problem, so the selectProblem flag tells the PM
        // to return a problem that contains the intervention
        InterventionSelectorParam selectParam = new InterventionSelectorParam("selectProblem", selectProblem);
        paramSpecs.add(selectParam);
        Element config = elt.getChild("config");
        InterventionSelectorSpec spec  = new  InterventionSelectorSpec(className,paramSpecs, config);
        return spec;
    }




}
