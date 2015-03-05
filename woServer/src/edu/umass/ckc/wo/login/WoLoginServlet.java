package edu.umass.ckc.wo.login;

import ckc.servlet.servbase.ServletAction;
import ckc.servlet.servbase.BaseServlet;
import ckc.servlet.servbase.ServletInfo;
import ckc.servlet.servbase.ServletParams;
import edu.umass.ckc.wo.cache.ProblemMgr;
import edu.umass.ckc.wo.content.CCContentMgr;
import edu.umass.ckc.wo.content.LessonMgr;
import edu.umass.ckc.wo.mrcommon.Names;
import edu.umass.ckc.wo.tutor.Settings;
import edu.umass.ckc.wo.tutor.probSel.BaseExampleSelector;
import edu.umass.ckc.wo.tutor.vid.BaseVideoSelector;
import edu.umass.ckc.wo.woserver.ServletUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: marshall
 * Date: Dec 1, 2009
 * Time: 2:17:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class WoLoginServlet extends BaseServlet {
    private static Logger logger = Logger.getLogger(WoLoginServlet.class);

    public String getDataSource(ServletConfig servletConfig) {
         return servletConfig.getServletContext().getInitParameter("wodb.datasource");
     }

    protected boolean handleRequest(ServletInfo servletInfo) throws Exception {
        ServletAction action = ActionFactory.buildAction(servletInfo.getParams());
        Settings.getSurveys(servletInfo.getConn()); // makes sure the latest survey URLS from the DB are used.
        logger.info(">>" + servletInfo.getParams().toString());
        String viewName = action.process(servletInfo);
        logger.info("<< JSP: " + viewName);
        if (viewName != null) {
            RequestDispatcher disp = servletInfo.getRequest().getRequestDispatcher(viewName);
            disp.forward(servletInfo.getRequest(),servletInfo.getResponse());
            return false; // tells the superclass servlet not to write to the output stream because the request has been forwarded.
        }
        else return true; // the action just wrote some stuff into servletOutput so the servlet should flush it out
    }

    protected void initialize(ServletConfig servletConfig, ServletContext servletContext, Connection connection) throws Exception {
        logger.debug("Begin init of WOLoginServlet");
        ServletUtil.initialize(servletContext);
        Settings.formalityServletURI = servletConfig.getInitParameter(Names.FORMALITY_SERVLET_URI);
        servletContext.setAttribute("flashClientURI", Settings.flashClientPath);
        Settings.getSurveys(connection); // loads the pre/post Survey URLS
        // Loads all content into a cache for faster access during runtime
        if (!ProblemMgr.isLoaded())  {
            ProblemMgr problemMgr = new ProblemMgr(new BaseExampleSelector(), new BaseVideoSelector());
            problemMgr.loadProbs(connection);
            CCContentMgr.getInstance().loadContent(connection);
            LessonMgr.getAllLessons(connection);  // only to check integrity of content so we see errors early
        }
        logger.debug("end init of WOLoginServlet");

    }

    
}
