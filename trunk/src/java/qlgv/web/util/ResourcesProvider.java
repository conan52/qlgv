/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qlgv.web.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author truong
 */
public class ResourcesProvider {

    private static Logger logger = Logger.getLogger(ResourcesProvider.class);
    public static final String ICON_RESOURCES = "../../resources/icons/";
    public static final String UPLOAD_FOLDER = "resources/uploadfiles/";
    public static final String REPORT_FOLDER = "resources/reports/";
   

    public static String getRootPath() {
        URL url = ResourcesProvider.class.getResource("ResourcesProvider.class");
        logger.fatal("New Upload URL: " + url);
        logger.fatal("New Upload path: " + url.getPath());
        int indexOfWork = indexOfDeloyFolder("work", url);
        int indexOfWebApps = indexOfDeloyFolder("webapps", url);
        int indexOfWebINF = indexOfDeloyFolder("WEB-INF", url);
        String reVal = null;
        if (indexOfWork > 0) {
            //this case for deloy on tomcat
            reVal = url.getPath().substring(1, indexOfWork) + "webapps/";
            return reVal;
        } else if (indexOfWebApps > 0) {
            //this case for deloy on tomcat and work at tomcat's work folder
            reVal = url.getPath().substring(1, indexOfWebApps) + "webapps/";
            return reVal;
        } else {
            //this case for deloy on netbeans - may be have another case
            //if have any exception please check deloy folder to add on or contact me: thanhct
            reVal = url.getPath().substring(1, indexOfWebINF);
        }
        return reVal;
    }

    private static int indexOfDeloyFolder(String folder, URL url) {
        return url.getPath().indexOf(folder);
    }

    public static String getUploadFolder() {
        logger.fatal("Upload Folder path: ------ || ------");
        String rootPath = getRootPath().replaceAll("%20", " ");
        logger.fatal("Folder to upload files: ||||=_=_=_=>>>>> " + rootPath + UPLOAD_FOLDER);
        return rootPath + UPLOAD_FOLDER;
    }
    public static String getReportFolder() {
        logger.fatal("Upload Folder path: ------ || ------");
        String rootPath = getRootPath().replaceAll("%20", " ");
        logger.fatal("Folder to upload files: ||||=_=_=_=>>>>> " + rootPath + UPLOAD_FOLDER);
        return rootPath + REPORT_FOLDER;
    }

//    public static String getResources(String bundleKey) {
//        String reVal = "";
//        try {
//            String resourcesValue = getProperty().getProperty(bundleKey);
//            if (resourcesValue == null) {
//                reVal = bundleKey + Resources.BUNDLE_KEY_NOT_FOUND;
//            } else {
//                reVal = resourcesValue;
//            }
//        } catch (IOException ex) {
//            logger.debug("cann't get resources with key: " + bundleKey + " details:" + ex.getMessage());
//            reVal = Resources.BUNDLE_FILE_NOT_FOUND;
//        }
//        return reVal;
//    }

//    public static Properties getProperty() throws IOException {
//        Properties properties = new Properties();
//        properties.load(ResourcesProvider.class.getResourceAsStream(Resources.RESOURCES_FILE_PATH));
//        return properties;
//    }
    
}
