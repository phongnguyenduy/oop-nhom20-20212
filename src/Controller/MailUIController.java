package Controller;

import Model.InteractData.UpdateHome;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class MailUIController implements Initializable {
    @FXML
    private TextArea ContentText;
    @FXML
    public TextField EmailToText;
    @FXML
    private TextField SubjectText;
    @FXML
    private Button SendButton;
    @FXML
    private TextField CCText;
    @FXML
    private TextField BCCText;
    @FXML
    private ListView<String> listView;
    ObservableList<String> emaillist;
    @FXML
    private TextField EmailAddress;

    @FXML
    private void setSendButton(ActionEvent event) {
        boolean isSuccess = false;
        String username = "minhctthvn2@gmail.com";
        String password = "KINGMAN1";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", "465");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(EmailToText.getText())
            );
            message.setSubject(SubjectText.getText());
            message.setText(ContentText.getText());
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(BCCText.getText()));
            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(CCText.getText()));
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            isSuccess = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR!!!//nTRY AGAIN");
            alert.show();
        }
        if (!isSuccess) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Send Email Successful");
            alert.show();
        }
    }

    @FXML
    private void setAddCCButton(ActionEvent event) {
        CCText.setText(EmailAddress.getText());
        EmailAddress.clear();
    }

    @FXML
    private void setAddBCCButton(ActionEvent event) {
        BCCText.setText(EmailAddress.getText());
        EmailAddress.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emaillist = FXCollections.observableList(new UpdateHome().getAddressEmail());
        listView.setItems(emaillist);
        listView.setOnMouseClicked(event -> {
            String s2 = listView.getSelectionModel().getSelectedItem();
            if (EmailAddress.getText() == "") {
                EmailAddress.setText(s2);
            } else {
                String s3 = EmailAddress.getText();
                EmailAddress.setText(s3 + "," + s2);
            }
        });
    }
}
