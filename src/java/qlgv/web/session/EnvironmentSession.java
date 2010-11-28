/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qlgv.web.session;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author truong
 */
public class EnvironmentSession implements Serializable{
private static final Logger logger = Logger.getLogger(EnvironmentSession.class);
    public static final String VI_KEY = "vi";
    public static final String EN_KEY = "en";
    public static final String DE_KEY = "de";

    /**
     * Biến này cho view root
     */
    private String locale = EN_KEY;
    private Locale currentLocale = new Locale(EN_KEY);

    /**
     * Biến này sử dụng trong combobox language trong header
     */
    private String localeValue = EN_KEY;

    /**
     * @param locale the locale to set
     */
    public void setLocale(String locale) {
        if (locale.equals(VI_KEY)) {
            setLocaleValue(VI_KEY);
        } else if (locale.equals(EN_KEY)) {
            setLocaleValue(EN_KEY);
        } else if (locale.equals(DE_KEY)) {
            setLocaleValue(DE_KEY);
        }
        this.locale = locale;
    }

    /**
     * @return the localeValue
     */
    public String getLocaleValue() {
        return localeValue;
    }

    public void changeLocale(ValueChangeEvent ve) {
        if (ve != null) {
            String newValue = ve.getNewValue().toString();
            logger.debug("Change locale to "+newValue);
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            if (newValue.equals(VI_KEY)) {
                viewRoot.setLocale(new Locale(VI_KEY));
                setLocale(VI_KEY);
                setCurrentLocale(new Locale(VI_KEY));
            } else if (newValue.equals(EN_KEY)) {
                viewRoot.setLocale(new Locale(EN_KEY));
                setLocale(EN_KEY);
                setCurrentLocale(new Locale(EN_KEY));
            } else {
                viewRoot.setLocale(new Locale(DE_KEY));
                setLocale(DE_KEY);
                setCurrentLocale(new Locale(DE_KEY));
            }
        }
    }

    /**
     * @return the currentLocale
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }

    /**
     * @param currentLocale the currentLocale to set
     */
    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }

    /**
     * @param localeValue the localeValue to set
     */
    public void setLocaleValue(String localeValue) {
        this.localeValue = localeValue;
    }

    /**
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

}
