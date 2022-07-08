package Controller;

import Model.Connect.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class LoginController implements Initializable {

    @FXML
    private TextField inpEmail;
    @FXML
    private TextField inpName;
    @FXML
    private TextField inpPassword;
    @FXML
    private Label label;

    Connection con = Connect.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    public void handleSignIn(ActionEvent actionEvent)throws IOException {

         String name = inpName.getText();
         String email = inpEmail.getText();
         String password = inpPassword.getText();
         String sql = "SELECT * FROM dang_nhap WHERE ten_dang_nhap=? AND email =? AND mat_khau=?";
         try {
             ps = con.prepareStatement(sql);
             ps.setString(1,name);
             ps.setString(2,email);
             ps.setString(3,password);
             rs = ps.executeQuery();
             if(!rs.next()){
                 label.setText("Tên tài khoản,mật khẩu hoặc email không đúng!");
             }else{
                 Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                 FXMLLoader loader = new FXMLLoader();
                 loader.setLocation(getClass().getResource("/View/GUI/sample.fxml"));
                 Parent view = loader.load();
                 Scene scene = new Scene(view);
                 Controller controller = loader.getController();
                 stage.setScene(scene);
                 stage.setHeight(750);
                 stage.setWidth(1300);
                 stage.setX(0);
                 stage.setY(0);
             }
         }catch (SQLException e){
             e.printStackTrace();
         }
    }

    public void add(MouseEvent event) throws IOException {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/GUI/NewAccount.fxml"));
        Parent viewSample = loader.load();
        Scene scene = new Scene(viewSample,669,519);
        stage.setScene(scene);
        stage.setX(200);
        stage.setY(80);
        stage.setWidth(669);
        stage.setHeight(519);
    }

}
