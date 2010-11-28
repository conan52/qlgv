/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qlgv.web.controller.sinhvien;

import qlgv.web.controller.GiaoVuController;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import qlgv.web.session.SinhVienSession;
import qlgv.web.store.KhoaStore;
import qlgv.web.store.LopStore;
import qlgv.web.store.SinhVienStore;
import qlgv.web.util.SessionProvider;
import qlgv.web.util.Util;
import queenb.net.entitybean.SinhVien;
//Reading data from excel file
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.extractor.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import qlgv.web.util.ResourcesProvider;
//

/**
 *
 * @author truong
 */
public class SinhVienController extends GiaoVuController {

    private List<SinhVien> listSinhVien;
    private List<SelectItem> listKhoa;
    private List<SelectItem> listLop;
    private SinhVienStore sinhVienStore = new SinhVienStore();
    private KhoaStore khoaStore = new KhoaStore();
    private LopStore lopStore = new LopStore();
    private String makhoa;
    private String malop;
    
    private String masvEdit;
    private String hosvEdit;
    private String tensvEdit;
    private String gioitinhsvEdit;
    private String ngsinhsvEdit;
    private String noisinhsvEdit;
    private String hokhausvEdit;
    private String diachisvEdit;
    private String emailsvEdit;
    private String dienthoaisvEdit;
    private String malopsvEdit;

    private SinhVienSession sinhVienSession;

    public SinhVienController() {
        super();
        Object actionSV= getRequest().getParameter("actionSV");
        if(actionSV!=null){
            System.out.println("Gia tri cua actionSV: "+actionSV.toString());
            if(actionSV.toString().equals("editSV")){
                Object editSVParam=getRequest().getParameter("editSVParam");
                if(editSVParam!=null){
                    getDetailSinhVien(editSVParam.toString());
                }
            }
        }
    }

    public List<SinhVien> getListSinhVien() {
        try {
            System.out.println("List sinh vien  voi idBranch: " + getLogin().getIdBranch() + "makhoa=" + getSinhVienSession().getCurrentMaKhoa() + " malop= " + getSinhVienSession().getCurrentMaLop());
            listSinhVien = getSinhVienStore().findSinhVien(getLogin().getIdBranch(), getSinhVienSession().getCurrentMaKhoa(), getSinhVienSession().getCurrentMaLop());
        } catch (Exception ex) {
            System.out.println("Error when getListSinhVien(). ERROR CODE: " + ex.getMessage());
        }
        return listSinhVien;
    }

    public List<SelectItem> getListKhoa() {
        try {
            System.out.println("############### getListKhoa() with idBranch: " + loginSession.getLogin().getIdBranch());
            listKhoa = getKhoaStore().getKhoaSelectItems(loginSession.getLogin().getIdBranch());
        } catch (Exception ex) {
            System.out.println("Error when getListKhoa(). ERROR CODE: " + ex.getMessage());
        }
        return listKhoa;
    }

    //Lay ra danh sach cac ma lop o dang select item
    public List<SelectItem> getListLop() {
        if (this.getMakhoa() != null) {
            if (getSinhVienSession().getCurrentMaKhoa().equals("0")) {
                getSinhVienSession().removeListLopSession();
            }
        }
        return getSinhVienSession().getListLopSession();
    }

    //Ham nay goi khi thay doi id cua Khoa
    //thay doi danh sach lop tuong ung voi kho, sau do luu vao trong session
    public void changeIdKhoa() {
        try {
            System.out.println("############### getListLop() with idBranch: " + loginSession.getLogin().getIdBranch());
            listLop = getLopStore().getLopSelectItems(getLogin().getIdBranch(), getSinhVienSession().getCurrentMaKhoa());
        } catch (Exception ex) {
            System.out.println("Error when getListLop(). ERROR CODE: " + ex.getMessage());
        }
        getSinhVienSession().setListLopSession(listLop);
    }

    //Ham nay thuc hien khi thay doi id cua lophoc
    public void changeIdLop() {
        System.out.println("Thuc Hien listSinhVien voi malop= " + this.getMalop());        
        getListSinhVien();
    }

    public void uploadDocListener(UploadEvent ue) throws IOException {
        uploadAttachment(ue, 2);
    }

    private void uploadAttachment(UploadEvent ue, int typeUpload) {
        List<UploadItem> uploadItem = ue.getUploadItems();
        for (int i = 0; i < uploadItem.size(); i++) {
            try {
                UploadItem uploadItem1 = uploadItem.get(i);
                String realPath = ResourcesProvider.getUploadFolder() + Util.createGUID() + "_" + uploadItem1.getFileName().replaceAll(" ", "");
                Util.saveFile(uploadItem1, realPath);
                readingDataFromFile(realPath);
            } catch (Exception ex) {
                setMessages("ERROR:COULD NOT ATTACH FILE:CODE:" + ex.getMessage(), "ERROR:COULD NOT ATTACH FILE:CODE:" + ex.getMessage());
            }
        }
    }

    private void readingDataFromFile(String pathFile) {
        File fileExcel = new File(pathFile);
        POIFSFileSystem fsExcel = null;
        try {
            fsExcel = new POIFSFileSystem(new FileInputStream(fileExcel));

            HSSFWorkbook wb = new HSSFWorkbook(fsExcel);
            HSSFSheet sheet = wb.getSheetAt(0);       // first sheet
            System.out.println("TRUONGTEST: " + sheet.getLastRowNum());
            for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
                Row row = rit.next();
                int rowNum = row.getRowNum();//So dong hien tai
                System.out.println("So dong hien tai: " + rowNum);
                if (rowNum > 7) {
                    String masv = "";
                    String ho = "";
                    String ten = "";
                    String gioitinh = "";
                    String ngsinh = "";
                    String noisinh = "";
                    String hokhau = "";
                    String diachi = "";
                    String dienthoai = "";
                    String email = "";
                    String malop = "";
                    int intRow = 0;//Bien dem cot cua tung dong
                    for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {
                        Cell cell = cit.next();
                        switch (intRow) {
                            case 0:
                                masv = cell.getStringCellValue();
                                intRow++;
                                break;
                            case 1:
                                ho = cell.getStringCellValue();
                                intRow++;
                                break;
                            case 2:
                                ten = cell.getStringCellValue();
                                intRow++;
                                break;
                            case 3:
                                gioitinh = cell.getStringCellValue();
                                intRow++;
                                break;
                            case 4:
                                ngsinh = cell.getStringCellValue();
                                intRow++;
                                break;
                            case 5:
                                noisinh = cell.getStringCellValue();
                                intRow++;
                                break;
                            case 6:
                                hokhau = cell.getStringCellValue();
                                intRow++;
                                break;
                            case 7:
                                diachi = cell.getStringCellValue();
                                intRow++;
                                break;
                            case 8:
                                dienthoai = cell.getStringCellValue();
                                intRow++;
                                break;
                            case 9:
                                email = cell.getStringCellValue();
                                intRow++;
                                break;
                            case 10:
                                malop = cell.getStringCellValue();
                                intRow++;
                                break;
                        }
                    }
//                    System.out.println("Insert into SINHVIEN values(" + masv + "," + hoten + "," + gioitinh + "," + ngsinh + "," + noisinh + "," + hokhau + "," + diachi + ")");
                    try {
                        getSinhVienStore().updateSinhVien(masv, ho, ten, gioitinh, ngsinh, noisinh, hokhau, diachi,
                                dienthoai, email, malop, 0, 0);
                    } catch (Exception ex) {
                        System.out.println("Error when insert sinh vien. ERROR CODE: " + ex.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Lay cac thong tin lien quan de sinh vien de thuc hien edit
    public void getDetailSinhVien(String editSVParam) {
        List<SinhVien> listSvDetail = null;
        System.out.println("Lay thong tin sinh vien voi id: "+editSVParam+"macoso:= "+loginSession.getLogin().getIdBranch()+
                                             "makhoa="+getSinhVienSession().getCurrentMaKhoa()+"malop="+getSinhVienSession().getCurrentMaLop());
        try {
            listSvDetail = getSinhVienStore().findSinhVienDetail(loginSession.getLogin().getIdBranch(), getSinhVienSession().getCurrentMaKhoa(), getSinhVienSession().getCurrentMaLop(), editSVParam);
        } catch (Exception ex) {
            System.out.println("Error when get detail sinhvien. ERROR CODE: " + ex.getMessage());
        }
        if (listSvDetail != null) {
            for (int i = 0; i < listSvDetail.size(); i++) {
                this.masvEdit = listSvDetail.get(i).getMasv();
                this.hosvEdit = listSvDetail.get(i).getHosv();
                this.tensvEdit = listSvDetail.get(i).getTensv();
                this.gioitinhsvEdit = listSvDetail.get(i).getGioitinh();
                this.ngsinhsvEdit = listSvDetail.get(i).getNgsinh();
                this.noisinhsvEdit = listSvDetail.get(i).getNoisinh();
                this.hokhausvEdit = listSvDetail.get(i).getHokhau();
                this.diachisvEdit = listSvDetail.get(i).getDiachi();
                this.dienthoaisvEdit=listSvDetail.get(i).getDienthoai();
                this.emailsvEdit=listSvDetail.get(i).getEmail();
                this.malopsvEdit=listSvDetail.get(i).getMalop();
            }
        }
        System.out.println("Thuc hien bind xong voi:");
        System.out.println("masvEdit ="+ this.masvEdit);
        System.out.println("hosvEdit ="+ this.hosvEdit);
        System.out.println("tensvEdit ="+ this.tensvEdit);
        System.out.println("gioitinhsvEdit ="+ this.gioitinhsvEdit);
        System.out.println("ngsinhsvEdit ="+ this.ngsinhsvEdit);
        System.out.println("noisinhsvEdit ="+ this.noisinhsvEdit);
        System.out.println("hokhausvEdit ="+ this.hokhausvEdit);
        System.out.println("diachisvEdit ="+ this.diachisvEdit);
    }

    /**
     * @param listSinhVien the listSinhVien to set
     */
    public void setListSinhVien(List<SinhVien> listSinhVien) {
        this.listSinhVien = listSinhVien;
    }

    /**
     * @return the sinhVienStore
     */
    public SinhVienStore getSinhVienStore() {
        return sinhVienStore;
    }

    /**
     * @param sinhVienStore the sinhVienStore to set
     */
    public void setSinhVienStore(SinhVienStore sinhVienStore) {
        this.sinhVienStore = sinhVienStore;
    }

    /**
     * @return the makhoa
     */
    public String getMakhoa() {
        return makhoa;
    }

    /**
     * @param makhoa the makhoa to set
     */
    public void setMakhoa(String makhoa) {
        this.makhoa = makhoa;
    }

    /**
     * @return the malop
     */
    public String getMalop() {
        System.out.println("Ma lop ne: " + malop);
        return malop;
    }

    /**
     * @param malop the malop to set
     */
    public void setMalop(String malop) {
        this.malop = malop;
    }

    /**
     * @return the khoaStore
     */
    public KhoaStore getKhoaStore() {
        if (khoaStore == null) {
            khoaStore = new KhoaStore();
        }
        return khoaStore;
    }

    /**
     * @param khoaStore the khoaStore to set
     */
    public void setKhoaStore(KhoaStore khoaStore) {
        this.khoaStore = khoaStore;
    }

    /**
     * @param listKhoa the listKhoa to set
     */
    public void setListKhoa(List<SelectItem> listKhoa) {
        this.listKhoa = listKhoa;
    }

    /**
     * @return the lopStore
     */
    public LopStore getLopStore() {
        if (lopStore == null) {
            lopStore = new LopStore();
        }
        return lopStore;
    }

    /**
     * @param lopStore the lopStore to set
     */
    public void setLopStore(LopStore lopStore) {
        this.lopStore = lopStore;
    }

    /**
     * @param listLop the listLop to set
     */
    public void setListLop(List<SelectItem> listLop) {
        this.setListLop(listLop);
    }

    /**
     * @return the sinhVienSession
     */
    public SinhVienSession getSinhVienSession() {
        sinhVienSession = (SinhVienSession) SessionProvider.getSession("sinhVienSession", getContext());
        if (sinhVienSession == null) {
            sinhVienSession = new SinhVienSession();
        }
        return sinhVienSession;
    }

    /**
     * @param sinhVienSession the sinhVienSession to set
     */
    public void setSinhVienSession(SinhVienSession sinhVienSession) {
        this.sinhVienSession = sinhVienSession;
    }

    /**
     * @return the masvEdit
     */
    public String getMasvEdit() {
        return masvEdit;
    }

    /**
     * @param masvEdit the masvEdit to set
     */
    public void setMasvEdit(String masvEdit) {
        this.masvEdit = masvEdit;
    }

    /**
     * @return the hosvEdit
     */
    public String getHosvEdit() {
        return hosvEdit;
    }

    /**
     * @param hosvEdit the hosvEdit to set
     */
    public void setHosvEdit(String hosvEdit) {
        this.hosvEdit = hosvEdit;
    }

    /**
     * @return the tensvEdit
     */
    public String getTensvEdit() {
        return tensvEdit;
    }

    /**
     * @param tensvEdit the tensvEdit to set
     */
    public void setTensvEdit(String tensvEdit) {
        this.tensvEdit = tensvEdit;
    }

    /**
     * @return the gioitinhsvEdit
     */
    public String getGioitinhsvEdit() {
        return gioitinhsvEdit;
    }

    /**
     * @param gioitinhsvEdit the gioitinhsvEdit to set
     */
    public void setGioitinhsvEdit(String gioitinhsvEdit) {
        this.gioitinhsvEdit = gioitinhsvEdit;
    }

    /**
     * @return the ngsinhsvEdit
     */
    public String getNgsinhsvEdit() {
        return ngsinhsvEdit;
    }

    /**
     * @param ngsinhsvEdit the ngsinhsvEdit to set
     */
    public void setNgsinhsvEdit(String ngsinhsvEdit) {
        this.ngsinhsvEdit = ngsinhsvEdit;
    }

    /**
     * @return the noisinhsvEdit
     */
    public String getNoisinhsvEdit() {
        return noisinhsvEdit;
    }

    /**
     * @param noisinhsvEdit the noisinhsvEdit to set
     */
    public void setNoisinhsvEdit(String noisinhsvEdit) {
        this.noisinhsvEdit = noisinhsvEdit;
    }

    /**
     * @return the hokhausvEdit
     */
    public String getHokhausvEdit() {
        return hokhausvEdit;
    }

    /**
     * @param hokhausvEdit the hokhausvEdit to set
     */
    public void setHokhausvEdit(String hokhausvEdit) {
        this.hokhausvEdit = hokhausvEdit;
    }

    /**
     * @return the diachisvEdit
     */
    public String getDiachisvEdit() {
        return diachisvEdit;
    }

    /**
     * @param diachisvEdit the diachisvEdit to set
     */
    public void setDiachisvEdit(String diachisvEdit) {
        this.diachisvEdit = diachisvEdit;
    }

    /**
     * @return the dienthoaisvEdit
     */
    public String getDienthoaisvEdit() {
        return dienthoaisvEdit;
    }

    /**
     * @param dienthoaisvEdit the dienthoaisvEdit to set
     */
    public void setDienthoaisvEdit(String dienthoaisvEdit) {
        this.dienthoaisvEdit = dienthoaisvEdit;
    }

    /**
     * @return the emailsvEdit
     */
    public String getEmailsvEdit() {
        return emailsvEdit;
    }

    /**
     * @param emailsvEdit the emailsvEdit to set
     */
    public void setEmailsvEdit(String emailsvEdit) {
        this.emailsvEdit = emailsvEdit;
    }

    /**
     * @return the malopsvEdit
     */
    public String getMalopsvEdit() {
        return malopsvEdit;
    }

    /**
     * @param malopsvEdit the malopsvEdit to set
     */
    public void setMalopsvEdit(String malopsvEdit) {
        this.malopsvEdit = malopsvEdit;
    }

    
}
