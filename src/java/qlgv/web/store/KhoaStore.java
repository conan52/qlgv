/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qlgv.web.store;

import queenb.net.entitybean.Khoa;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import oracle.jdbc.OracleTypes;
import queenb.net.datacontrol.DataStatement;
import queenb.net.datacontrol.Param;
/**
 *
 * @author truong
 */
public class KhoaStore extends Khoa {
    private static final String LIST_KHOA = "LISTKHOA";

    public List<Khoa> findKhoa(String macoso) throws SQLException, InstantiationException, IllegalAccessException {
        DataStatement dataStatement = new DataStatement(LIST_KHOA, OracleTypes.CURSOR, new Param[]{
                    new Param(OracleTypes.CHAR, macoso),
                });
        return super.find(dataStatement, Khoa.class);
    }
    public List<SelectItem> getKhoaSelectItems(String macoso) throws SQLException, SQLException, InstantiationException, IllegalAccessException {    
        List<Khoa> listKhoa=null;
        listKhoa = findKhoa(macoso);          
        List<SelectItem> listKhoaSelectItems = new ArrayList<SelectItem>();
        if(listKhoa!=null){
            SelectItem defaultItem= new SelectItem(0, "--Chon Khoa Hoc--");
            listKhoaSelectItems.add(defaultItem);            
            for (int i = 0; i < listKhoa.size(); i++) {
                Khoa khoa = listKhoa.get(i);
                listKhoaSelectItems.add(new SelectItem(khoa.getMakhoa(), khoa.getNienhoc()));
            }

        }
        return listKhoaSelectItems;
    }
    
      public static void main(String agr[]) throws SQLException, InstantiationException, IllegalAccessException {
        KhoaStore khoaStore= new KhoaStore();
        List<SelectItem> listSelect=khoaStore.getKhoaSelectItems("TPHCM");
        if(listSelect==null){
            System.out.println("list Khoa bi null");
        }
        if(listSelect!=null){
            System.out.println("List Khoa khac null. Kich thuot la: "+listSelect.size());

        }

    }
}
