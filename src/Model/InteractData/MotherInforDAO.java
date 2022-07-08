package Model.InteractData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import Model.DataDescriptor.MotherInfor;
import javafx.scene.control.Alert;
import javafx.collections.ObservableList;
import Model.Connect.Connect;

public class MotherInforDAO {
    //Tạo kết nối, khai báo ps và rs để thực hiện truy vấn và lấy dữ liệu từ cơ sở dữ liệu
    protected static final Connection con = Connect.getConnection();
    protected static PreparedStatement ps = null;
    protected static ResultSet rs = null;
    protected static ObservableList<MotherInfor> Mothers;

    public ArrayList<MotherInfor> getAllMotherInfor() {
        String sql = "SELECT * FROM ba_me_mang_thai ORDER BY ma_dinh_danh_y_te ASC";
        ArrayList<MotherInfor> Mothers = new ArrayList<MotherInfor>();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Mothers.add(new MotherInfor(
                        rs.getString("ma_dinh_danh_y_te"),
                        rs.getInt("can_nang"),
                        rs.getString("lan_kham_gan_nhat"),
                        rs.getString("tham_kham_sap_toi")));
            }
        } catch (SQLException s) {
            s.printStackTrace();
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.showAndWait();
        }
        return Mothers;
    }

    //Tìm kiếm danh sách các đối tượng mà 1 thuộc tính của nó có giá trị = search trừ số mũi tiêm trừ cân nặng
    public ArrayList<MotherInfor> getMotherInfor(String search,boolean checkIsTime,boolean checkIsNumber) {

        String sql;
        if (checkIsTime){
            sql = "SELECT * FROM ba_me_mang_thai WHERE lan_kham_gan_nhat =\'"+search+"\'OR"+
            "lan_kham_sap_toi= \'"+search+"\'";

        } else {

            if(checkIsNumber){
                sql = "SELECT * FROM ba_me_mang_thai WHERE can_nang ="+search;

            }else{
                sql = "SELECT * FROM ba_me_mang_thai WHERE ma_dinh_danh_y_te LIKE \'"+search+"\'";
        }};


        ArrayList<MotherInfor> Mothers = new ArrayList<MotherInfor>();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Mothers.add(new MotherInfor(
                        rs.getString("ma_dinh_danh_y_te"),
                        rs.getInt("can_nang"),
                        rs.getString("lan_kham_gan_nhat"),
                        rs.getString("tham_kham_sap_toi")));
            }
        } catch (SQLException s) {
            s.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
        return Mothers;
    }

    //Thêm vaccine và các thông tin liên quan khi một người được tiêm
    public void addMotherInfor (MotherInfor motherInfor){

        String sql = "INSERT INTO ba_me_mang_thai (ma_dinh_danh_y_te, can_nang, lan_kham_gan_nhat, tham_kham_sap_toi) VALUES (?,?,?,?)";

        String time= motherInfor.getDate();
        LocalDate localDate = LocalDate.of(Integer.parseInt(time.substring(0,4)),
                Integer.parseInt(time.substring(5, 7)),
                Integer.parseInt(time.substring(8,10)));

        String time1= motherInfor.getNextDate();
        LocalDate localDate1 = LocalDate.of(Integer.parseInt(time1.substring(0,4)),
                Integer.parseInt(time1.substring(5, 7)),
                Integer.parseInt(time1.substring(8,10)));

        try{
            ps = con.prepareStatement(sql);
            ps.setString(1,motherInfor.getMedicalIdentifier());
            ps.setInt(2,motherInfor.getWeight());
            ps.setObject(3,localDate);
            ps.setObject(4,localDate1);
            ps.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
    }

    // vaccine và các thông tin liên quan khi một người được tiêm
    public void deleteMotherInfor(String medicalId){

        String sql = "DELETE FROM ba_me_mang_thai WHERE ma_dinh_danh_y_te = \'"+medicalId+"\'";
        try{
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
    }

    //Cập nhật lại thông tin vaccine và các thông tin liên quan khi một người được tiêm
    public void updateMotherInfor(MotherInfor motherInfor){

        String sql = "UPDATE ba_me_mang_thai SET can_nang = ?, " +
                "lan_kham_gan_nhat = ?,tham_kham_sap_toi =? WHERE  ma_dinh_danh_y_te = ?";

        String time= motherInfor.getDate();
        LocalDate localDate = LocalDate.of(Integer.parseInt(time.substring(0,4)),
                Integer.parseInt(time.substring(5, 7)),
                Integer.parseInt(time.substring(8,10)));

        String time1= motherInfor.getNextDate();
        LocalDate localDate1 = LocalDate.of(Integer.parseInt(time1.substring(0,4)),
                Integer.parseInt(time1.substring(5, 7)),
                Integer.parseInt(time1.substring(8,10)));
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1,motherInfor.getWeight());
            ps.setObject(2,localDate);
            ps.setObject(3,localDate1);
            ps.setString(4,motherInfor.getMedicalIdentifier());
            ps.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
    }
}