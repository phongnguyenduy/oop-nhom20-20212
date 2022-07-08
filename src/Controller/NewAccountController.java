package Controller;

import Model.Connect.Connect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class NewAccountController implements Initializable {

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
        public void initialize(URL url, ResourceBundle resourceBundle) {
        }

        public void add(MouseEvent event) throws IOException {
            String name = inpName.getText();
            String email = inpEmail.getText();
            String password = inpPassword.getText();
            String sql = " INSERT INTO dang_nhap(ten_dang_nhap,email,mat_khau) VALUES (?,?,?)";
            try {
                ps = con.prepareStatement(sql);
                ps.setString(1,name);
                ps.setString(2,email);
                ps.setString(3,password);
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        public void back(MouseEvent event) throws IOException {
            Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/GUI/LoginUI.fxml"));
            Parent viewSample = loader.load();
            Scene scene = new Scene(viewSample,669,519);
            stage.setScene(scene);
            stage.setX(200);
            stage.setY(80);
            stage.setWidth(669);
            stage.setHeight(519);
    }
}