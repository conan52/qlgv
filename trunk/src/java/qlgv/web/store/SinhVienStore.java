/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qlgv.web.store;

import queenb.net.entitybean.SinhVien;
import java.sql.SQLException;
import java.util.List;
import oracle.jdbc.OracleTypes;
import queenb.net.datacontrol.DataStatement;
import queenb.net.datacontrol.Param;

/**
 *
 * @author truong
 */
public class SinhVienStore extends SinhVien {

    private static final String LIST_SINHVIEN = "listsinhvien";
    private static final String INSERT_SINHVIEN = "UPDATETHONGTINSINHVIEN";
    private static final String DETAIL_SINHVIEN = "DETAILSINHVIEN";
    
    public List<SinhVien> findSinhVien(String macoso, String makhoa, String malop) throws SQLException, InstantiationException, IllegalAccessException {
        DataStatement dataStatement = new DataStatement(LIST_SINHVIEN, OracleTypes.CURSOR, new Param[]{
                    new Param(OracleTypes.CHAR, macoso),
                    new Param(OracleTypes.CHAR, makhoa),
                    new Param(OracleTypes.CHAR, malop)
                });
        return super.find(dataStatement, SinhVien.class);
    }
    public int updateSinhVien(String masvParam ,String hoParam,String tenParam,String gioitinhParam,
                                String ngsinhParam ,String noisinhParam,String hokhauParam,String diachiParam,
                                String dienthoaiParam,String emailParam,String malopParam,int deletedParam,
                                int totnghiepParam) throws SQLException {
        DataStatement dataStatement = new DataStatement(INSERT_SINHVIEN, OracleTypes.INTEGER, new Param[]{
                    new Param(OracleTypes.CHAR, masvParam),
                    new Param(OracleTypes.CHAR, hoParam),
                    new Param(OracleTypes.CHAR, tenParam),
                    new Param(OracleTypes.CHAR, gioitinhParam),
                    new Param(OracleTypes.CHAR, ngsinhParam),
                    new Param(OracleTypes.CHAR, noisinhParam),
                    new Param(OracleTypes.CHAR, hokhauParam),
                    new Param(OracleTypes.CHAR, diachiParam),
                    new Param(OracleTypes.CHAR, dienthoaiParam),
                    new Param(OracleTypes.CHAR, emailParam),
                    new Param(OracleTypes.CHAR, malopParam),
                    new Param(OracleTypes.INTEGER, deletedParam),
                    new Param(OracleTypes.INTEGER, totnghiepParam),
                });
        return super.update(dataStatement, null);
    }
    //Lay cac truong du ieu lien quan de 1 sinh vien(khi Edit sinh vien)
    public List<SinhVien> findSinhVienDetail(String macoso, String makhoa, String malop, String masv) throws SQLException, InstantiationException, IllegalAccessException {
        DataStatement dataStatement = new DataStatement(DETAIL_SINHVIEN, OracleTypes.CURSOR, new Param[]{
                    new Param(OracleTypes.CHAR, macoso),
                    new Param(OracleTypes.CHAR, makhoa),
                    new Param(OracleTypes.CHAR, malop),
                    new Param(OracleTypes.CHAR, masv)
                });
        return super.find(dataStatement, SinhVien.class);
    }

}
