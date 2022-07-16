package Controller;

import Model.Connect.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void handleSignIn(ActionEvent actionEvent) throws IOException {
        String email = inpEmail.getText();
        String password = inpPassword.getText();
        String sqlEmail = "SELECT * FROM dang_nhap WHERE email = ?";
        try {
            ps = con.prepareStatement(sqlEmail);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (!rs.next()) {
                label.setVisible(true);
                label.setText("Tài khoản không tồn tại trong hệ thống!");
            } else {
                String sqlPassword = "SELECT * FROM dang_nhap WHERE email = ? AND mat_khau = ?";
                ps = con.prepareStatement(sqlPassword);
                ps.setString(1, email);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    label.setVisible(true);
                    label.setText("Mật khẩu không đúng!");
                } else {
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/View/GUI/Home.fxml"));
                    Parent view = loader.load();
                    Scene scene = new Scene(view);
                    HomeController controller = loader.getController();
                    stage.setScene(scene);
                    stage.setHeight(750);
                    stage.setWidth(1300);
                    stage.setX(0);
                    stage.setY(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAccount(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/GUI/NewAccount.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.setX(300);
        stage.setY(100);
        stage.setWidth(400);
        stage.setHeight(500);
    }

}
