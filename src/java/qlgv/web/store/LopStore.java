/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package qlgv.web.store;

import queenb.net.entitybean.Lop;
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
public class LopStore extends Lop {
    private static final String LIST_LOP = "listlop";

     public List<Lop> findLop(String macoso, String makhoap) throws SQLException, InstantiationException, IllegalAccessException {
        DataStatement dataStatement = new DataStatement(LIST_LOP, OracleTypes.CURSOR, new Param[]{
                    new Param(OracleTypes.CHAR, macoso),
                    new Param(OracleTypes.CHAR, makhoap),
                });
        return super.find(dataStatement, Lop.class);
    }

     //Lay ra danh sach cac lop dua vao ma co so va nien hoc
     public List<SelectItem> getLopSelectItems(String macoso, String makhoap) throws SQLException, SQLException, InstantiationException, IllegalAccessException {
        List<Lop> listLop = findLop(macoso, makhoap);
        List<SelectItem> listLopSelectItems = new ArrayList<SelectItem>();
        if(listLop!=null){
            SelectItem defaultLop= new SelectItem("0", "--Chon Lop--");
            listLopSelectItems.add(defaultLop);
            for (int i = 0; i < listLop.size(); i++) {
                Lop lop = listLop.get(i);
                listLopSelectItems.add(new SelectItem(lop.getMalop(), lop.getTenlop()));
            }
        }
        return listLopSelectItems;
    }
}
