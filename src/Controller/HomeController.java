package Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HomeController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    @FXML
    public void LoadUI1(MouseEvent event){
        Load("UI_1");
    }

    @FXML
    public void LoadUI2(MouseEvent event){
        Load("UI_2");
    }

    @FXML
    public void LoadUI3(MouseEvent event){
        Load("UI_3");
    }

    @FXML
    public void LoadUI4(MouseEvent event){
        Load("UI_4");
    }

    @FXML
    public void LoadUI5(MouseEvent event){
        Load("UI_5");
    }

    @FXML
    public void LoadUI6(MouseEvent event){
        Load("UI_6");
    }

    @FXML
    public void LoadUI7(MouseEvent event){
        Load("UI_7");
    }

    @FXML
            public void showHelp(MouseEvent event){ Load("Help");}

    Parent root;
    public void Load(String Ui){
        try {
            root = FXMLLoader.load(getClass().getResource("/View/GUI/" +Ui + ".fxml"));
        }catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null,ex);
        }
        borderPane.setCenter(root);
    }

    public void goBack(MouseEvent event) throws IOException {
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


