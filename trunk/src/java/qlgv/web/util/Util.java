/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qlgv.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import org.richfaces.model.UploadItem;
//import qlgv.web.bean.FileDescription;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//import org.hibernate.lob.SerializableClob;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
//import queenb.net.entitybean.SimpleObject;
import queenb.net.services.M650Services;
//import qlgv.web.store.AttachmentStore;

/**
 *
 * @author truong
 */
public class Util {

    private static final Logger logger = Logger.getLogger(Util.class);
    public static final int MODULE_PROJECT = 3;
    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String DATE_PATTERN1 = "yyyy/MM/dd";
    public static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";

    

//    public static List<SelectItem> convertSO2SI(List<SimpleObject> listSO){
//        List<SelectItem> listSI=null;
//        if(listSO!=null && listSO.size()>0){
//            listSI=new ArrayList(listSO.size());
//            for(SimpleObject obj: listSO){
//                listSI.add(new SelectItem(obj.getVal(), obj.getLabel()));
//            }
//        }
//        return listSI;
//    }

    public static Date stringToSQLDate(String str, String pattern) throws ParseException {
        if (str == null || str.trim().equals("")) {
            return null;
        }

        DateFormat formater = new SimpleDateFormat(pattern);
        java.util.Date parsedUtilDate = formater.parse(str);
        return new Date(parsedUtilDate.getTime());

    }

    public static String toVnShortDate(Object object, String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(object);
    }

    public static String toVnShortDate1(Object object, String pattern) throws ParseException {
        return toVnShortDate(object, pattern).replaceAll("/", "");
    }

    public static boolean parseBoolean(Object object) {
        if (parseInt(object) == 0) {
            return false;
        }
        return true;
    }

    public static int parseBoolToInt(Boolean b) {
        if (b == true) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int parseInt(Object object) {
        if (object == null || object.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(parseString(object));
        }
    }

    public static Date toVnDate(Object object, String pattern) throws ParseException {
        if (object == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return df.parse(toVnShortDate(object, pattern));
    }

    public static java.sql.Date toSQLDate(Date date) {
        if (date == null) {
            return null;
        } else {
            return new java.sql.Date(date.getTime());
        }
    }

    public static java.sql.Date toSQLDate(String date, String pattern) throws ParseException {
        if (date == null) {
            return null;
        } else {
            DateFormat df = new SimpleDateFormat(pattern);
            return new java.sql.Date(df.parse(date).getTime());
        }
    }

    public static Timestamp toTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return new Timestamp(date.getTime());
        }

    }

    public static Timestamp hourToTimestamp(String hourMinute) {
        Timestamp timestamp = null;
        if (hourMinute != null && !hourMinute.trim().equals("")) {
            String[] hourMinuteSplit = hourMinute.split(":");
            Calendar cl = Calendar.getInstance();
            cl.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourMinuteSplit[0]));
            cl.set(Calendar.MINUTE, Integer.parseInt(hourMinuteSplit[1]));
            timestamp = new Timestamp(cl.getTimeInMillis());
        }
        return timestamp;
    }

    public static Date parseDate(Date date, String pattern) throws ParseException {
        if (date == null) {
            return null;
        }
        return date;
    }

    public static Date parseDate(String date, String pattern) throws ParseException {
        if (date == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(Util.DATE_PATTERN);
        return (Date) df.parse(date);
    }

    public static String parseString(Object object) {
        return object == null ? "" : object.toString();
    }

    public static String parseEnabled(Object object) {
//        int a= parseInt(object);
//        if (a==0){
//            return "false";
//        }else{
//            return "true";
//        }
        return "false";
    }

    public static String convertMD5(String arg) {
        String hashed_key = "";
        try {
            byte[] intext = arg.getBytes();
            StringBuffer sb = new StringBuffer();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5rslt = md5.digest(intext);
            for (int i = 0; i < md5rslt.length; i++) {
                sb.append(String.format("%02x", 0xff & md5rslt[i]));
            }
            hashed_key = sb.toString();
        } catch (Exception e2) {
            System.out.println(e2);
        }
        return hashed_key;
    }


    public ResourceBundle getResourceBundle(String fileName, Locale locale) {
        return ResourceBundle.getBundle(fileName, locale);
    }


    public static byte[] getBytesFromFile(File file) throws IOException {
        byte[] bytes = null;
        InputStream is = new FileInputStream(file); // Get the size of the file
        long length = file.length();
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        // Create the byte array to hold the data
        bytes = new byte[(int) length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;

        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    public static String createGUID() {
        return UUID.randomUUID().toString();
    }

    public static String getFullUserName(Object userId, Object firstName, Object lastName) {
        return parseString(firstName) + " " + parseString(lastName) + " (" + parseString(userId) + ")";
    }

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    public static void copy(File fromFile, File toFile) throws IOException {
        if (!fromFile.exists()) {
            throw new IOException("FileCopy: " + "no such source file: "
                    + fromFile.getName());
        }
        if (!fromFile.isFile()) {
            throw new IOException("FileCopy: " + "can't copy directory: "
                    + fromFile.getName());
        }
        if (!fromFile.canRead()) {
            throw new IOException("FileCopy: " + "source file is unreadable: "
                    + fromFile.getName());
        }

        if (toFile.isDirectory()) {
            toFile = new File(toFile, fromFile.getName());
        }

        if (toFile.exists()) {
            if (!toFile.canWrite()) {
                throw new IOException("FileCopy: "
                        + "destination file is unwriteable: " + toFile.getName());
            }
            System.out.print("Overwrite existing file " + toFile.getName()
                    + "? (Y/N): ");
            System.out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    System.in));
            String response = in.readLine();
            if (!response.equals("Y") && !response.equals("y")) {
                throw new IOException("FileCopy: "
                        + "existing file was not overwritten.");
            }
        } else {
            String parent = toFile.getParent();
            if (parent == null) {
                parent = System.getProperty("user.dir");
            }
            File dir = new File(parent);
            if (!dir.exists()) {
                throw new IOException("FileCopy: "
                        + "destination directory doesn't exist: " + parent);
            }
            if (dir.isFile()) {
                throw new IOException("FileCopy: "
                        + "destination is not a directory: " + parent);
            }
            if (!dir.canWrite()) {
                throw new IOException("FileCopy: "
                        + "destination directory is unwriteable: " + parent);
            }
        }

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesRead); // write
            }
        } finally {
            if (from != null) {
                try {
                    from.close();
                } catch (IOException e) {
                }
            }
            if (to != null) {
                try {
                    to.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void copy(String fromFileName, String toFileName)
            throws IOException {
        File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);
        copy(fromFile, toFile);
    }
    

    public static boolean contains(String String, String searchStr) {

        if (String.indexOf(searchStr) != -1) {
            return true;
        } else {
            return false;
        }
    }
     public static void saveFile(UploadItem item, String realPath) throws IOException {
        File fromFile = new File(item.getFile().getPath());
        System.out.println("##############################current temp file:" + item.getFile()+"file name ne: "+item.getFileName());
        System.out.println("#############Duong dan chinh xac: "+ResourcesProvider.getUploadFolder() + Util.createGUID() + "_" + item.getFileName().replaceAll(" ", ""));
//        File toFile = new File(ResourcesProvider.getUploadFolder() + Util.createGUID() + "_" + item.getFileName().replaceAll(" ", ""));        
        File toFile = new File(realPath);
        copy(fromFile, toFile);
        System.out.println("###########Xong roi do");
    }

   
}

