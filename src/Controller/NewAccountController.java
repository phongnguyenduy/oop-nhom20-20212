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
import javafx.scene.paint.Color;
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
        label.setVisible(false);
    }

    public void add(MouseEvent event) throws IOException {
        String name = inpName.getText();
        String email = inpEmail.getText();
        String password = inpPassword.getText();
        try {
            String sqlLookup = "SELECT * FROM dang_nhap WHERE email=?";
            ps = con.prepareStatement(sqlLookup);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (!rs.next()) {
                String sqlInsert = " INSERT INTO dang_nhap(ten_dang_nhap,email,mat_khau) VALUES (?,?,?)";
                ps = con.prepareStatement(sqlInsert);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.executeUpdate();
                label.setVisible(true);
                label.setText("Tài khoản đã được tạo thành công!");
                label.setTextFill(Color.GREEN);
            } else {
                label.setVisible(true);
                label.setTextFill(Color.RED);
                label.setText("Tài khoản đã tồn tại trong hệ thống!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void backToLogin(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/GUI/LoginUI.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.setX(300);
        stage.setY(100);
        stage.setWidth(400);
        stage.setHeight(450);
    }
}