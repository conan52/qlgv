/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qlgv.web.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 *
 * @author truong
 */
public class BundleFinder {
    private static final Logger logger = Logger.getLogger(BundleFinder.class);
    private Locale locale;
    private String bundleName;
    private static BundleFinder bundleFinder;


    public static BundleFinder getInstance(Locale locale, String bundleName) {
        if (bundleFinder == null) {
            bundleFinder = new BundleFinder(locale, bundleName);
        }
        return bundleFinder;
    }

    public BundleFinder(Locale locale, String bundleName) {
        this.locale = locale;
        this.bundleName = bundleName;
    }

    protected ClassLoader getCurrentClassLoader(Object defaultObject) {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        if (loader == null) {
            loader = defaultObject.getClass().getClassLoader();
        }

        return loader;
    }

    public String getMessageResourceString(
            String bundleName,
            String key,
            Object params[],
            Locale locale) {

        String text = null;

        ResourceBundle bundle =
                ResourceBundle.getBundle(bundleName, locale,
                getCurrentClassLoader(params));

        try {
            text = bundle.getString(key);
        } catch (MissingResourceException e) {
            text = "?? key " + key + " not found ??";
        }

        if (params != null) {
            MessageFormat mf = new MessageFormat(text, locale);
            text = mf.format(params, new StringBuffer(), null).toString();
        }

        return text;
    }

    public String getLocaleResources(String key) {
        return getLocaleResources(key, null);
    }

    public String getLocaleResources(String key, Object[] params) {
        return getMessageResourceString("qlgv/web/resources/localization", key, params, getLocale());
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * @return the bundleName
     */
    public String getBundleName() {
        return bundleName;
    }

    /**
     * @param bundleName the bundleName to set
     */
    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }
}
