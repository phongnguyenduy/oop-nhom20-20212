package Model.InteractData;


import Model.DataDescriptor.Immunization;
import javafx.scene.control.Alert;
import javafx.collections.ObservableList;
import Model.Connect.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ImmunizationDAO {

    //Tạo kết nối, khai báo ps và rs để thực hiện truy vấn và lấy dữ liệu từ cơ sở dữ liệu
    protected static final Connection con = Connect.getConnection();
    protected static PreparedStatement ps = null;
    protected static ResultSet rs = null;
    protected static ObservableList<Immunization> vaccines;

    //Lấy toàn bộ dữ liệu và gán vào danh sách các đối tượng
    public ArrayList<Immunization> getAllImmunization() {
        String sql = "SELECT * FROM thong_tin_tiem_chung ORDER BY ma_dinh_danh_y_te ASC";
        ArrayList<Immunization> vaccines = new ArrayList<Immunization>();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                vaccines.add(new Immunization(
                        rs.getString("id_1"),
                        rs.getString("ma_dinh_danh_y_te"),
                        rs.getString("loai_vaccine"),
                        rs.getString("ngay_tiem"),
                        rs.getString("so_lo_vaccine"),
                        rs.getString("tinh_trang_sau_tiem"),
                        rs.getInt("so_mui_tiem")));
            }
        } catch (SQLException s) {
            s.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
        return vaccines;
    }

    //Tìm kiếm danh sách các đối tượng mà 1 thuộc tính của nó có giá trị = search trừ số mũi tiêm
    public ArrayList<Immunization> getImmunization(String search,boolean checkIsNumber,boolean checkIsTime) {

        String sql;
        if (checkIsTime){
            sql = "SELECT * FROM thong_tin_tiem_chung WHERE ngay_tiem =\'"+search+"\'";

        } else {

            if(checkIsNumber){
                sql = "SELECT * FROM thong_tin_tiem_chung WHERE so_mui_tiem ="+search;

            }else{

                sql = "SELECT * FROM thong_tin_tiem_chung WHERE id_1 LIKE \'" + search + "\'"+
                "OR ma_dinh_danh_y_te LIKE \'"+search+"\'"+
                "OR loai_vaccine LIKE \'"+search+"\'"+
                "OR ngay_tiem LIKE \'"+search+"\'"+
                "OR so_lo_vaccine LIKE \'"+search+"\'"+
                "OR tinh_trang_sau_tiem LIKE \'"+search+"\'";
            }
        }

        ArrayList<Immunization> vaccines = new ArrayList<Immunization>();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                vaccines.add(new Immunization(
                        rs.getString("id_1"),
                        rs.getString("ma_dinh_danh_y_te"),
                        rs.getString("loai_vaccine"),
                        rs.getString("ngay_tiem"),
                        rs.getString("so_lo_vaccine"),
                        rs.getString("tinh_trang_sau_tiem"),
                        rs.getInt("so_mui_tiem")));
            }
        } catch (SQLException s) {
            s.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
        return vaccines;
    }

    //Thêm vaccine và các thông tin liên quan khi một người được tiêm
    public void addImmunization (Immunization vaccine){

        String sql = "INSERT INTO thong_tin_tiem_chung VALUES (?,?,?,?,?,?,?)";
        String time= vaccine.getDate();
        LocalDate localDate = LocalDate.of(Integer.parseInt(time.substring(0,4)),
                Integer.parseInt(time.substring(5, 7)),
                Integer.parseInt(time.substring(8,10)));
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1,vaccine.getId());
            ps.setString(2,vaccine.getMedicalIdentifier());
            ps.setString(3,vaccine.getType());
            ps.setObject(4,localDate);
            ps.setString(5,vaccine.getLotNumber());
            ps.setString(6,vaccine.getPostInjectionCondition());
            ps.setInt(7,vaccine.getNumberInjections());
            ps.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
    }

    // vaccine và các thông tin liên quan khi một người được tiêm
    public void deleteImmunization(String Id){

        String sql = "DELETE FROM thong_tin_tiem_chung WHERE id_1 = \'"+Id+"\'";
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
    public void updateImmunization(Immunization vaccine){

        String sql = "UPDATE thong_tin_tiem_chung SET  ma_dinh_danh_y_te = ?,loai_vaccine = ?, " +
                "ngay_tiem = ?,so_lo_vaccine =?, tinh_trang_sau_tiem = ? , so_mui_tiem = ? " +
                "WHERE  id_1 = ?";
        String time= vaccine.getDate();
        LocalDate localDate = LocalDate.of(Integer.parseInt(time.substring(0,4)),
                Integer.parseInt(time.substring(5, 7)),
                Integer.parseInt(time.substring(8,10)));
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1,vaccine.getMedicalIdentifier());
            ps.setString(2,vaccine.getType());
            ps.setObject(3,localDate);
            ps.setString(4,vaccine.getLotNumber());
            ps.setString(5 ,vaccine.getPostInjectionCondition());
            ps.setInt( 6,vaccine.getNumberInjections());
            ps.setString(7,vaccine.getId());

            ps.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
    }
}

