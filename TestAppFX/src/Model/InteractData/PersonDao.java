package Model.InteractData;

import Model.Connect.Connect;
import Model.DataDescriptor.Person;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class   PersonDao {

    //Tạo kết nối tới cơ sở dữ liệu, khai báo ps,rs để thực hiện truy xuất và lấy kết quả
    protected static final Connection connection = Connect.getConnection();
    protected static PreparedStatement ps = null;
    protected static ResultSet rs = null;
    protected static ObservableList<Person> People;

    //Lấy danh sách mọi người từ cơ sở dữ liệu và gán vào danh sách các đối tượng
    public ArrayList<Person> getPeople(){
        String sql = "SELECT * FROM thong_tin_ca_nhan ORDER BY ten ASC";
        ArrayList<Person> people = new ArrayList<>();
        try{
            ps = connection.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()){
                Person person = new Person();
                person.setMedicalIdentifier(rs.getString("ma_dinh_danh_y_te"));
                person.setName(rs.getString("ten"));
                person.setCitizenIdentification(rs.getString("can_cuoc_cong_dan"));
                person.setGender(rs.getString("gioi_tinh"));
                person.setBirthday(rs.getString("ngay_sinh"));
                person.setAddress(rs.getString("dia_chi"));
                person.setNumberPhone(rs.getString("so_dien_thoai"));
                person.setEmail(rs.getString("email"));
                person.setVaccinationtatus(rs.getString("tinh_trang_tiem_vaccine"));
                people.add(person);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return people;
    }

    //Lấy danh sách các đối tượng mà 1 thuộc tính của nó có giá trị = search
    public ArrayList<Person> getPeople(String search,boolean checkIsTime) {

        String sql;
        if (checkIsTime){
            sql = "SELECT * FROM thong_tin_ca_nhan WHERE ngay_sinh =\'"+search+"\'";

        } else {
            sql = "SELECT * FROM thong_tin_ca_nhan WHERE ma_dinh_danh_y_te LIKE \'" + search + "\'"+
                    "OR ten LIKE \'"+ search +"\'"+
                    "OR can_cuoc_cong_dan LIKE \'"+search+"\'"+
                    "OR gioi_tinh LIKE \'"+search+"\'"+
                    "OR dia_chi LIKE \'"+search+"\'"+
                    "OR so_dien_thoai LIKE \'"+search+"\'"+
                    "OR email LIKE \'"+search+"\'"+
                    "OR tinh_trang_tiem_vaccine LIKE \'"+ search+"\'";
        }
        ArrayList<Person> people = new ArrayList<>();

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

         while(rs.next()) {
                people.add(new Person(
                        rs.getString("ma_dinh_danh_y_te"),
                        rs.getString("ten"),
                        rs.getString("can_cuoc_cong_dan"),
                        rs.getString("gioi_tinh"),
                        rs.getString("ngay_sinh"),
                        rs.getString("dia_chi"),
                        rs.getString("so_dien_thoai"),
                        rs.getString("email"),
                        rs.getString("tinh_trang_tiem_vaccine")
                ));
         }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
        return people;
    }

    //Thêm một người vào cơ sở dữ liệu
    public void addPerson(Person person){
        String sql = "INSERT INTO thong_tin_ca_nhan(ma_dinh_danh_y_te,ten,can_cuoc_cong_dan,gioi_tinh,ngay_sinh,dia_chi,so_dien_thoai,email,tinh_trang_tiem_vaccine) VALUES (?,?,?,?,?,?,?,?,?)";
        String time= person.getBirthday();
        LocalDate localDate = LocalDate.of(Integer.parseInt(time.substring(0,4)),
                Integer.parseInt(time.substring(5, 7)),
                Integer.parseInt(time.substring(8,10)));
        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1,person.getMedicalIdentifier());
            ps.setString(2,person.getName());
            ps.setString(3,person.getCitizenIdentification());
            ps.setString(4,person.getGender());
            ps.setObject(5,localDate);
            ps.setString(6,person.getAddress());
            ps.setString(7,person.getNumberPhone());
            ps.setString(8,person.getEmail());
            ps.setString(9,person.getVaccinationtatus());
            ps.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Thêm không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
    }

    //Xóa một người khỏi cơ sở dữ liệu
    public void deletePerson(String medicalId){
        String sql = "DELETE FROM thong_tin_ca_nhan WHERE ma_dinh_danh_y_te= \'"+ medicalId+"\'";
        try{
            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
    }

    //Sửa thông tin của một
    public void updatePerson(Person person){
        String sql = "UPDATE thong_tin_ca_nhan SET ten =?,"+
                "can_cuoc_cong_dan =?," +
                "gioi_tinh =?, ngay_sinh = ?," +
                " dia_chi = ?,"+"so_dien_thoai =?," +
                "email =? where ma_dinh_danh_y_te = ?";
        /*,tinh_trang_tiem_vaccine =?*/
        String time= person.getBirthday();
        LocalDate localDate = LocalDate.of(Integer.parseInt(time.substring(0,4)),
                Integer.parseInt(time.substring(5, 7)),
                Integer.parseInt(time.substring(8,10)));
        try{
            ps = connection.prepareStatement(sql);
            ps.setString(8, person.getMedicalIdentifier());
            ps.setString(1,person.getName());
            ps.setString(2,person.getCitizenIdentification());
            ps.setString(3,person.getGender());
            ps.setObject(4,localDate);
            ps.setString(5,person.getAddress());
            ps.setString(6,person.getNumberPhone());
            ps.setString(7,person.getEmail());
            //System.out.println(person.getBirthday());
            ps.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
    }

}