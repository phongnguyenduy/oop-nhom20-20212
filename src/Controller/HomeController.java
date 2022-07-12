package Controller;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HomeController implements Initializable {

    @FXML
    //private BorderPane borderPane;
    private GridPane gridPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    @FXML
    public void LoadIndividualUI(MouseEvent event){
        Load("IndividualUI");
    }

    @FXML
    public void LoadVaccinationUI(MouseEvent event){
        Load("VaccinationUI");
    }

    @FXML
    public void LoadMotherUI(MouseEvent event){
        Load("MotherUI");
    }

    @FXML
    public void LoadChildrenUI(MouseEvent event){
        Load("ChildrenUI");
    }

    @FXML
    public void LoadVaccinationScheduleUI(MouseEvent event){
        Load("VaccinationScheduleUI");
    }

    @FXML
    public void LoadMailUI(MouseEvent event){
        Load("MailUI");
    }

    @FXML
    public void LoadSearchUI(MouseEvent event){
        Load("SearchUI");
    }

    @FXML
    public void showAbout(MouseEvent event){ Load("About");}

    Parent root;
    public Node getNodeByRC(int r, int c, GridPane gp) {
        ObservableList<Node> children = gp.getChildren();
        for (Node node: children) {
            int dr, dc;
            Integer tr = gp.getRowIndex(node);
            if (tr == null) dr = 0; else dr = tr.intValue();
            Integer tc = gp.getColumnIndex(node);
            if (tc == null) dc = 0; else dc = tc.intValue();
            if (dr == r && dc == c) return node;
        }
        return null;
    }

    public void Load(String Ui){
        try {
            root = FXMLLoader.load(getClass().getResource("/View/GUI/" +Ui + ".fxml"));
        }catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null,ex);
        }
        Node contentNode = getNodeByRC(1,1,gridPane);
        if (contentNode != null) gridPane.getChildren().remove(contentNode);
        gridPane.add(root,1,1);
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


