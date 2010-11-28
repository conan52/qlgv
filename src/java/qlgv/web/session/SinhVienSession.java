/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package qlgv.web.session;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.SelectItem;
import queenb.net.entitybean.Lop;

/**
 *
 * @author truong
 */
public class SinhVienSession implements Serializable {
    private List<SelectItem> listLopSession;
    private String currentMaKhoa="0";//bien luu  giu trang thai cua makhoa
    private String currentMaLop="0";//bien luu  giu trang thai cua malop
    /**
     * @return the listLopSession
     */
    public List<SelectItem> getListLopSession() {
        return listLopSession;
    }

    /**
     * @param listLopSession the listLopSession to set
     */
    public void setListLopSession(List<SelectItem> listLopSession) {
        this.listLopSession = listLopSession;
    }
    public void removeListLopSession(){
        this.listLopSession.clear();
    }

    /**
     * @return the currentMaKhoa
     */
    public String getCurrentMaKhoa() {
        return currentMaKhoa;
    }

    /**
     * @param currentMaKhoa the currentMaKhoa to set
     */
    public void setCurrentMaKhoa(String currentMaKhoa) {
        this.currentMaKhoa = currentMaKhoa;
    }

    /**
     * @return the currentMaLop
     */
    public String getCurrentMaLop() {
        return currentMaLop;
    }

    /**
     * @param currentMaLop the currentMaLop to set
     */
    public void setCurrentMaLop(String currentMaLop) {
        this.currentMaLop = currentMaLop;
    }

   
}
