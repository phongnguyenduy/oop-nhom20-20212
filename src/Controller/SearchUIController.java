package Controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchUIController implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private Label loadLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine webEngine = webView.getEngine();
        Worker<Void> worker = webEngine.getLoadWorker();
        worker.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                loadLabel.setText("Đang tải:" + newValue.toString());
                if (newValue == Worker.State.SUCCEEDED) {
                    loadLabel.setText("Xong!");
                }
            }
        });

    }

    public void setSearchButton(ActionEvent event) {
        WebEngine webEngine = webView.getEngine();
        String s = searchField.getText();
        String url = "https:/google.com.vn/search?q=";
        s.trim();

        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ' && s.charAt(i + 1) != ' ') {
                url = url + s.substring(j, i) + "+";
                j = i + 1;
            }
        }
        url = url + s.substring(j) + "&aqs=chrome.0.69i59j46i275j0l3j69i61l3.2845j0j4&sourceid=chrome&ie=UTF-8";

        webEngine.load(url);
        searchField.clear();
        Worker<Void> worker = webEngine.getLoadWorker();
        progressBar.progressProperty().bind(worker.progressProperty());
    }

    public void setBackwardButton(ActionEvent event) {
        WebEngine webEngine = webView.getEngine();
        WebHistory webHistory = webEngine.getHistory();
        webHistory.go(-1);
    }

    public void setForwardButton(ActionEvent event) {
        WebEngine webEngine = webView.getEngine();
        WebHistory webHistory = webEngine.getHistory();
        webHistory.go(1);
    }


}