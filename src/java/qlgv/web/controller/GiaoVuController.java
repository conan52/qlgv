/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qlgv.web.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import queenb.net.entitybean.Login;
//import qlgv.web.controller.admin.PermissionController;
import qlgv.web.session.EnvironmentSession;
import qlgv.web.session.LoginSession;
import qlgv.web.util.BundleFinder;
import qlgv.web.util.SessionProvider;

/**
 *
 * @author truong
 */
public class GiaoVuController {

    private static final Logger logger = Logger.getLogger(GiaoVuController.class);
    public LoginSession loginSession;
    private BundleFinder bundleFinder;
    private EnvironmentSession environmentSession;
//    private PermissionController permissionController;
    private int module;
    private boolean hasData;

    public GiaoVuController() {
        try {
            if (!isLogged()) {
                sendRedirect("login.jsp");
            }
            showMessageIfNeed();
        } catch (Exception ex) {
            System.out.println("Error init GIAO VU CONTROLLER:" + ex.getMessage());
        }
    }

    protected void showMessageIfNeed() {
        Object sms = getRequest().getParameter("sms");
        if (sms != null && !sms.toString().equals("")) {
            showPopupMessage(sms.toString(), "ABACUS INFO");
        }
    }

    protected void setMessages(String message, String detailMessage) {
        getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detailMessage));
    }

    protected FacesContext getCurrentFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    public ExternalContext getExContext() {
        return getContext().getExternalContext();
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) getExContext().getRequest();
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) getExContext().getResponse();
    }

    protected void showPopupMessage(String sms, String title) {
        try {
//            <script language='javascript' type='text/script'>" + sms + "</script>"
            getResponse().getWriter().write(
                    "<div id='dialog' title='" + title + "'>"
                    + "<p>" + sms + "</p>"
                    + "</div>");
        } catch (IOException ex) {
            logger.debug("could not write message");
        }
    }

    protected void showAlertMessage(String sms) {
        try {
            getResponse().getWriter().write(
                    "<script language='javascript' type='text/html'>alert(" + sms + ")</script>");
        } catch (IOException ex) {
            logger.debug("could not write message");
        }
    }

    public void sendRedirect(String url) {
        try {
            logger.debug("Bi Loi sendRedirec$$$$$$$$$$$$$$$$$$$$$$$$$");
            url = getExContext().encodeResourceURL(url);
            getResponse().sendRedirect(url);
            getContext().responseComplete();
        } catch (IOException ex) {
            logger.debug("Error: Could not send redirect:" + ex.getMessage());
            setMessages("ERR:SENDREDIRECT:CONLL:cound not send page", "detail:" + ex.getMessage());
        }
    }

    /**
     * @return the loginSession
     */
    public LoginSession getLoginSession() {
        loginSession = (LoginSession) SessionProvider.getSession("loginSession", FacesContext.getCurrentInstance());
        if (loginSession == null) {
            loginSession = new LoginSession();
        }
        return loginSession;
    }

    private boolean isLogged() {
        return getLoginSession().isLogged();
    }

    protected Login getLogin() {
        return getLoginSession().getLogin();
    }

    /**
     * @return the bundleFinder
     */
    public BundleFinder getBundleFinder() {
//        Locale locale = getContext().getViewRoot().getLocale();
        Locale locale = getEnvironmentSession().getCurrentLocale();
        String bundleName = FacesContext.getCurrentInstance().getApplication().getMessageBundle();
        logger.debug("Current Bundle name:" + bundleName);
        bundleFinder = BundleFinder.getInstance(locale, bundleName);
        return bundleFinder;
    }

    /**
     * @param bundleFinder the bundleFinder to set
     */
    public void setBundleFinder(BundleFinder bundleFinder) {
        this.bundleFinder = bundleFinder;
    }

    protected String getResourceString(String key) {
        Locale locale = getEnvironmentSession().getCurrentLocale();
        getBundleFinder().setLocale(locale);
        logger.debug("current locale:" + getBundleFinder().getLocale().toString() + " current key:" + key);
        return getBundleFinder().getLocaleResources(key);
    }

    protected String getResourceString(String key, Object[] params) {
        Locale locale = getEnvironmentSession().getCurrentLocale();
        getBundleFinder().setLocale(locale);
        logger.debug("current locale:" + getBundleFinder().getLocale().toString() + " current key:" + key);
        return getBundleFinder().getLocaleResources(key, params);
    }

    public void showGlobalReport(boolean show, String reportMessage) {
        Login login = getLogin();
        login.setShowGlobalMessage(show);
        login.setGlobalMessage(reportMessage);
    }

    public String displayMessage(String keyMessage, Object[] messageArgument) {
        //keyMessage: Notify can lay
        //messageArgument: cac tham so truyen vao
        MessageFormat formatter = new MessageFormat("");
        Locale currentLocale = getEnvironmentSession().getCurrentLocale();
        formatter.setLocale(currentLocale);
        formatter.applyPattern(getResourceString(keyMessage));
        String outputMessage = formatter.format(messageArgument);
        return outputMessage;
    }

    /**
     * @return the environmentSession
     */
    public EnvironmentSession getEnvironmentSession() {
        environmentSession = (EnvironmentSession) SessionProvider.getSession("environmentSession", getContext());
        return environmentSession;
    }

    /**
     * @param environmentSession the environmentSession to set
     */
    public void setEnvironmentSession(EnvironmentSession environmentSession) {
        this.environmentSession = environmentSession;
    }

//    protected boolean checkPermistion(Object permissionCode) {
//        return getPermissionController().checkPermission(permissionCode);
//    }

    /**
     * @return the permissionController
     */
//    public PermissionController getPermissionController() {
//        permissionController = (PermissionController) SessionProvider.getSession("permissionController", getContext());
//        if (permissionController == null) {
//            permissionController = new PermissionController();
//        }
//        return permissionController;
//    }

    /**
     * @return the module
     */
//    public int getModule() {
//        if (isLogged()) {
//            module = getLogin().getModule();
//        }
//        return module;
//    }

    /**
     * @param module the module to set
     */
//    public void setModule(int module) {
//        this.module = module;
//    }

    /**
     * @return the hasData
     */
    public boolean isHasData() {
        return hasData;
    }

    /**
     * @param hasData the hasData to set
     */
    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
}
