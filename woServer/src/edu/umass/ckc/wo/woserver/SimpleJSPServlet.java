package edu.umass.ckc.wo.woserver;

import ckc.servlet.servbase.BaseServlet;
import ckc.servlet.servbase.ServletInfo;
import ckc.servlet.servbase.ServletParams;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: marshall
 * Date: 6/12/13
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleJSPServlet extends BaseServlet {

    protected void initialize(ServletConfig servletConfig, ServletContext servletContext, Connection connection) throws Exception {
        // sub classes should override this method
    }


    public boolean handleRequest(ServletInfo servletInfo) throws Exception {

        String myVar = servletInfo.getParams().getString("myVar");
        HttpSession sess = servletInfo.getRequest().getSession();
        sess.setAttribute("myVarCookie",myVar);
        servletInfo.getRequest().setAttribute("person", "dave");
        servletInfo.getRequest().getRequestDispatcher("test/test.jsp").forward(servletInfo.getRequest(),servletInfo.getResponse());
        return false;

    }

}
