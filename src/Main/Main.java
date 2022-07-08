package Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/GUI/LoginUI.fxml"));
        primaryStage.setScene(new Scene(root, 669, 519));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
