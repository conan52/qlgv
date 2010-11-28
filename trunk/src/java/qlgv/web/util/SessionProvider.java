/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qlgv.web.util;

import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author truong
 */
public class SessionProvider {

    public static Object getSession(String name, FacesContext facesContext) {
        Object sessionObject = null;
        if (facesContext != null) {
            Map<String, Object> map = facesContext.getExternalContext().getSessionMap();
            try {
                sessionObject = map.get(name);
            } catch (Exception ex) {
                ex.getMessage();
            }
        }
        return sessionObject;
    }

    public static String getParameterMapValue(String name, FacesContext facesContext) {
        return facesContext.getExternalContext().getRequestParameterMap().get(name);
    }

    public static int getIntParameterMapValue(String name, FacesContext facesContext) throws Exception {
        String paramValue = getParameterMapValue(name, facesContext);
        if (paramValue == null || paramValue.trim().equals("")) {
            throw new Exception("Para " + name + " :not found!");
        }
        return Integer.parseInt(paramValue);
    }

    public static void putRequestMap(String name, Object value, FacesContext facesContext) {
        facesContext.getExternalContext().getRequestMap().put(name, value);
    }

    public static Object getRequestMap(String name, FacesContext facesContext) {
        return facesContext.getExternalContext().getRequestMap().get(name);
    }

    public static boolean isPostBack() {
        return FacesContext.getCurrentInstance().getRenderResponse();
    }

    public static void putSession(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    /**
     * get action attribute from action event when action on button or somthing similar.
     * @param ac
     * @param name
     * @return
     */
    public static String getActionAttribute(ActionEvent ac, String name) {
        return (String) ac.getComponent().getAttributes().get(name);
    }

    public static int getActionIntAttribute(ActionEvent ac, String name) {

        return (Integer) ac.getComponent().getAttributes().get(name);
    }

    public static String getResourcesParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }

    /**
     * get session map value in current session
     * @param key
     * @return
     */
    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    /**
     * set session map value in current session
     * @param key
     * @param value
     */
    public static void setSessionMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    @SuppressWarnings("element-type-mismatch")
    public static void removeSessionMapValue(Object key) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
    }

    /**
     * get application map value accessible for all session
     * @param key
     * @return
     */
    public static Object getApplicationMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(key);
    }

    /**
     * set application map value to access any where in applicaion for all session
     * @param key
     * @param value
     */
    public static void setApplicationMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(key, value);
    }
}
