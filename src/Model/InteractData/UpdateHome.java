package Model.InteractData;

import Model.DataDescriptor.VaccinationSchedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import Model.Connect.Connect;
import javafx.scene.control.Alert;

public class UpdateHome {
    protected static final Connection conn=Connect.getConnection();
    protected static PreparedStatement ps;
    protected static ResultSet rs;
    public ArrayList<VaccinationSchedule> getList(){
        String sql="Select * From lich_tiem_chung";
        ArrayList<VaccinationSchedule> arr=new ArrayList<>();
        try{
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                VaccinationSchedule s=new VaccinationSchedule();
                s.setId(Integer.parseInt(rs.getString(1)));
                s.setName(rs.getString(4));
                s.setAddress(rs.getString(3));
                s.setAmount(Integer.parseInt(rs.getString(5)));
                s.setTime(rs.getString(2));
                arr.add(s);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arr;
    }
    public void AddStudent(VaccinationSchedule s){
        String sql="INSERT INTO lich_tiem_chung(id,time,address,name,amount)"+"VALUES (?,?,?,?,?)";
        String time= s.getTime();
        LocalDate localDate = LocalDate.of(Integer.parseInt(time.substring(0,4)),
                Integer.parseInt(time.substring(5, 7)),
                Integer.parseInt(time.substring(8,10)));
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,s.getId());
            ps.setObject(2,localDate);
            ps.setString(3,s.getAddress());
            ps.setString(4,s.getName());
            ps.setInt(5,s.getAmount());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
    }
    public void updateTable(VaccinationSchedule s){
        String sql="UPDATE lich_tiem_chung SET id=?, time=?, address=?, name=?, amount=? WHERE id=?";
        String time= s.getTime();
        int indext =s.getId();
        LocalDate localDate = LocalDate.of(Integer.parseInt(time.substring(0,4)),
                Integer.parseInt(time.substring(5, 7)),
                Integer.parseInt(time.substring(8,10)));
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,s.getId());
            ps.setObject(2,localDate);
            ps.setString(3,s.getAddress());
            ps.setString(4,s.getName());
            ps.setInt(5,s.getAmount());
            ps.setInt(6,indext);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
    }
    public void Delete(String ID){

        int value = Integer.parseInt(ID);
        String sql="DELETE FROM lich_tiem_chung WHERE id ="+value;
        try{
            ps=conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }

    }
    public ArrayList<VaccinationSchedule> SearchTable(String search,boolean checkIsTime,boolean checkIsNumber){

        String sql;
        if (checkIsTime){
            sql = "SELECT * FROM lich_tiem_chung WHERE time=\'"+search+"\'";

        } else {

            if(checkIsNumber){
                sql = "SELECT * FROM lich_tiem_chung WHERE id ="+search+
                "OR amount ="+ search;

            }else{
                sql = "SELECT * FROM lich_tiem_chung WHERE name LIKE \'"+search+"\'";
            }};

        ArrayList<VaccinationSchedule> arr=new ArrayList<>();
        try{
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                VaccinationSchedule s = new VaccinationSchedule();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setAddress(rs.getString("address"));
                s.setAmount(rs.getInt("amount"));
                s.setTime(rs.getString("time"));
                arr.add(s);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }

        return arr;
    }
    public ArrayList<String> getAddressEmail(){
        String sql="select email from thong_tin_ca_nhan";
        ArrayList<String> arr=new ArrayList<>();
        try{
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                String s=rs.getString("email");
                arr.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
        return arr;
    }

    public int sumNumberVaccine(String search1, String search2){
        String sql = "SELECT SUM(so_mui_tiem) AS \"sum\" FROM thong_tin_tiem_chung WHERE ngay_tiem BETWEEN \'"+
                     search1 +"\'AND\'"+ search2+ "\'";
        int sum = 0;
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                sum = sum + rs.getInt("sum");
            }
        }catch (SQLException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
        return sum;
    }
}

