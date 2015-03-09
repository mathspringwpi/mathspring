package edu.umass.ckc.wo.handler;

import edu.umass.ckc.wo.beans.Classes;
import edu.umass.ckc.wo.db.DbClass;
import edu.umass.ckc.wo.db.DbPrePost;
import edu.umass.ckc.wo.db.DbClassPedagogies;
import edu.umass.ckc.wo.beans.ClassInfo;
import edu.umass.ckc.wo.beans.PretestPool;
import edu.umass.ckc.wo.tutor.TutoringStrategy;
import edu.umass.ckc.wo.tutor.Settings;
import edu.umass.ckc.wo.exc.DeveloperException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: marshall
 * Date: Mar 17, 2009
 * Time: 2:28:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassAdminHelper {

    public static void processSelectedPretestSubmission(Connection conn, int classId, int poolId,
                                                        HttpServletRequest req, HttpServletResponse resp,
                                                        String jspPage, boolean givePretest) throws IOException, ServletException, SQLException, DeveloperException {
        req.setAttribute("classId",classId);

        DbClass.setPretestPool(conn,classId,poolId);
        DbClass.setGivePretest(conn,classId, givePretest);
        ClassInfo info = DbClass.getClass(conn,classId);
        PretestPool pool = DbPrePost.getPretestPool(conn,classId);
        List<TutoringStrategy> peds = DbClassPedagogies.getClassPedagogies(conn, classId);
        req.setAttribute("pedagogies",peds);
        req.setAttribute("pool",pool);
        req.setAttribute("classInfo",info);
        req.setAttribute("classId",classId);
        req.getRequestDispatcher(jspPage).forward(req,resp);
    }

    private static boolean defaultSelected (List<String> pedagogyIds) {
        for (String id: pedagogyIds)
            if (id.equals("0"))
                return true;
        return false;
    }

    public static List<TutoringStrategy> getDefaultPedagogies () {
        List<TutoringStrategy> peds = new ArrayList<TutoringStrategy>();
        for (TutoringStrategy p : Settings.pedagogyGroups.values()) {
            if (p.isDefault())
                peds.add(p);
        }
        return peds;
    }

    /**
     *  Checks the selected pedagogies to make sure that inputs are valid.   If something is
     * wrong, the next JSP page is created with the appropriate error message and true is returned.
     * If no error occurs, then false is returned.
     * @param classId
     * @param pedagogyIds
     * @param req
     * @param resp
     * @param submissionEventName
     * @param teacherId
     * @return  true if there is an error, false otherwise
     * @throws IOException
     * @throws ServletException
     * @throws SQLException
     */
    public static boolean errorCheckSelectedPedagogySubmission(int classId,
                                                               List<String> pedagogyIds,
                                                               HttpServletRequest req,
                                                               HttpServletResponse resp,
                                                               String submissionEventName, int teacherId, Connection conn) throws IOException, ServletException, SQLException {
        List<TutoringStrategy> selectedPedagogies = new ArrayList<TutoringStrategy>();
        // check to see if user selected Default pedagogy + some others - an error
        if (pedagogyIds.size() > 1 && defaultSelected(pedagogyIds)) {
            // the URL to which the form is submitted is a parameter since this JSP is used to alter and create
            req.setAttribute("formSubmissionEvent",submissionEventName);
            req.setAttribute("pedagogies", DbClassPedagogies.getAllPedagogies());
            req.setAttribute("message","If you select default, you may not select other pedagogies.");
            req.setAttribute("classId",classId);
            req.setAttribute("teacherId",teacherId);
            ClassInfo info = DbClass.getClass(conn,classId);
            ClassInfo[] classes1 = DbClass.getClasses(conn, teacherId);
            Classes bean1 = new Classes(classes1);
            req.setAttribute("bean", bean1);
            req.setAttribute("classInfo", info);
            req.getRequestDispatcher(CreateClassHandler.SELECT_PEDAGOGIES_JSP).forward(req, resp);
            return true;
        }
        // Use the list of default pedagogies if user chose default
        if (defaultSelected(pedagogyIds))  {
            selectedPedagogies = getDefaultPedagogies();
            // special error check to make sure a default pedagogy really exists
            if (selectedPedagogies.size() < 1) {
                // the URL to which the form is submitted is a parameter since this JSP is used to alter and create
                ClassInfo info = DbClass.getClass(conn,classId);
                ClassInfo[] classes1 = DbClass.getClasses(conn, teacherId);
                Classes bean1 = new Classes(classes1);
                req.setAttribute("bean", bean1);
                req.setAttribute("classInfo", info);
                req.setAttribute("formSubmissionEvent",submissionEventName);
                req.setAttribute("pedagogies", DbClassPedagogies.getAllPedagogies());
                req.setAttribute("message","No default pedagogies have been set up by an administrator of the system.<br/>" +
                        "Please notify an administrator and choose a pedagogy which you can change later.");
                req.setAttribute("classId",classId);
                req.setAttribute("teacherId",teacherId);

                req.getRequestDispatcher(CreateClassHandler.SELECT_PEDAGOGIES_JSP).forward(req,resp);
                return true;
            }
        }
         else if (pedagogyIds.size() == 0) {
            // the URL to which the form is submitted is a parameter since this JSP is used to alter and create
            ClassInfo info = DbClass.getClass(conn,classId);
            ClassInfo[] classes1 = DbClass.getClasses(conn, teacherId);
            Classes bean1 = new Classes(classes1);
            req.setAttribute("bean", bean1);
            req.setAttribute("classInfo", info);
            req.setAttribute("formSubmissionEvent",submissionEventName);
            req.setAttribute("pedagogies", DbClassPedagogies.getAllPedagogies());
            req.setAttribute("message","You must select at least one pedagogy");
                req.setAttribute("classId",classId);
            req.setAttribute("teacherId",teacherId);
            
                req.getRequestDispatcher(CreateClassHandler.SELECT_PEDAGOGIES_JSP).forward(req,resp);
            return true;
        }
        return false; // no errors
    }

    public static void saveSelectedPedagogies (Connection conn, int classId, List<String> pedagogyIds) throws SQLException, DeveloperException {
         List<TutoringStrategy> selectedPedagogies = DbClassPedagogies.getPedagogiesFromIds(pedagogyIds);
        // eliminate any old pedagogy settings
            DbClassPedagogies.removeClassPedagogies(conn, classId);
            for (TutoringStrategy ped: selectedPedagogies)
                DbClassPedagogies.setClassPedagogy(conn, classId, ped.getId());
    }



   /* Called to process the pedagogies selected by the student in the last stage of defining
    a new class.
     */
    /*
    public static void processSelectedPedagogiesSubmission(Connection conn, int classId,
                                                           List<String> pedagogyIds,
                                                           HttpServletRequest req,
                                                           HttpServletResponse resp,
                                                           String errorJSPPage,
                                                           String nextJSPPage,
                                                           String nextPageFormSubmissionEvent) throws SQLException, IOException, ServletException, DeveloperException {
        List<Pedagogy> selectedPedagogies = new ArrayList<Pedagogy>();
        // check to see if user selected Default pedagogy + some others - an error
        if (pedagogyIds.size() > 1 && defaultSelected(pedagogyIds)) {
            req.setAttribute("pedagogies", DbClassPedagogies.getAllPedagogies());
            req.setAttribute("message","If you select default, you may not select other pedagogies.");
            req.setAttribute("classId",classId);
            req.getRequestDispatcher(errorJSPPage).forward(req,resp);
            return;
        }
        // Use the list of default pedagogies if user chose default
        if (defaultSelected(pedagogyIds))  {
            selectedPedagogies = getDefaultPedagogies();
            // special error check to make sure a default pedagogy really exists
            if (selectedPedagogies.size() < 1) {
                req.setAttribute("pedagogies", DbClassPedagogies.getAllPedagogies());
                req.setAttribute("message","No default pedagogies have been set up by an administrator of the system.<br/>" +
                        "Please notify an administrator and choose a pedagogy which you can change later.");
                req.setAttribute("classId",classId);
                req.getRequestDispatcher(errorJSPPage).forward(req,resp);
                return;
            }
        }
        // otherwise; build a list of Pedagogy objects from the list of pedagogy ids selected by user
        else
            selectedPedagogies = DbClassPedagogies.getPedagogiesFromIds(pedagogyIds);
        // eliminate any old pedagogy settings
        DbClassPedagogies.removeClassPedagogies(conn, classId);
        for (Pedagogy ped: selectedPedagogies)
            DbClassPedagogies.setClassPedagogy(conn, classId, ped.getId());
        if (selectedPedagogies.size() == 0) {
            req.setAttribute("pedagogies", DbClassPedagogies.getAllPedagogies());
            req.setAttribute("message","You must select at least one pedagogy");
            req.setAttribute("classId",classId);
            req.getRequestDispatcher(errorJSPPage).forward(req,resp);
        }
        // We generate the next page (from nextJSPPage)
        else {
            req.setAttribute("formSubmissionEvent",nextPageFormSubmissionEvent);
            ClassInfo info = DbClass.getClass(conn,classId);
            req.setAttribute("classInfo",info);
            req.setAttribute("pedagogies",selectedPedagogies);
            req.setAttribute("classId",classId);
            PretestPool pool = DbPrePost.getPretestPool(conn,classId);
            req.setAttribute("pool",pool);
            req.getRequestDispatcher(nextJSPPage).forward(req,resp);
        }
    }
    */


}
