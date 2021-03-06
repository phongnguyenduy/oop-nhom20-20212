package Controller;

import Model.DataDescriptor.MotherInfor;
import Model.InteractData.MotherInforDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;

public class MotherUIController implements Initializable {

    @FXML
    private TableView<MotherInfor> tableView;
    @FXML
    private TableColumn<MotherInfor, String> medicalIdentifierColumn;
    @FXML
    private TableColumn<MotherInfor, Integer> weightColumn;
    @FXML
    private TableColumn<MotherInfor, String> dateColumn;
    @FXML
    private TableColumn<MotherInfor, String> nextDateColumn;
    @FXML
    ObservableList<MotherInfor> Mothers;

    @FXML
    private TextField searchText;
    @FXML
    private TextField medicalIdentifierText;
    @FXML
    private TextField weightText;
    @FXML
    private DatePicker datePicker;
    @FXML
    private DatePicker nextDatePicker;
    @FXML
    private CheckBox dateCheck;
    @FXML
    private CheckBox numberCheck;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        tableView.setOnMouseClicked(event -> {
            MotherInfor motherInfor = tableView.getSelectionModel().getSelectedItem();
            medicalIdentifierText.setText(motherInfor.getMedicalIdentifier());
            weightText.setText(String.valueOf(motherInfor.getWeight()));
            String time = motherInfor.getDate();
            datePicker.setValue(LocalDate.of(Integer.parseInt(time.substring(0, 4)),
                    Integer.parseInt(time.substring(5, 7)),
                    Integer.parseInt(time.substring(8, 10))));
            String time1 = motherInfor.getNextDate();
            nextDatePicker.setValue(LocalDate.of(Integer.parseInt(time1.substring(0, 4)),
                    Integer.parseInt(time1.substring(5, 7)),
                    Integer.parseInt(time1.substring(8, 10))));
            if (tableView.getSelectionModel().isEmpty()) {
                clear();
            }
        });
    }

    //T???i d??? li???u l??n b???ng tableView
    public void loadData() {
        Mothers = FXCollections.observableArrayList(new MotherInforDAO().getAllMotherInfor());
        medicalIdentifierColumn.setCellValueFactory(new PropertyValueFactory<>("medicalIdentifier"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nextDateColumn.setCellValueFactory(new PropertyValueFactory<>("nextDate"));
        tableView.setItems(Mothers);
    }

    //X??a c??c textField
    public void clear() {

        medicalIdentifierText.clear();
        weightText.clear();
        datePicker.setValue(null);
        nextDatePicker.setValue(null);
    }

    //Hi???n th??? l???i to??n b??? d??? li???u
    public void handleShowAll(MouseEvent event) {
        tableView.setItems(FXCollections.observableArrayList(new MotherInforDAO().getAllMotherInfor()));
    }

    //T??m ki???m th??ng tin
    public void handleShow(MouseEvent event) {
        String search = searchText.getText();
        boolean checkIsTime = dateCheck.isSelected();
        boolean checkIsNumber = numberCheck.isSelected();
        tableView.setItems(FXCollections.observableArrayList(new MotherInforDAO().getMotherInfor(search, checkIsTime, checkIsNumber)));
    }

    //Th??m th??ng tin
    public void handleAdd(MouseEvent event) {
        if (medicalIdentifierText.getText().isEmpty() || weightText.getText().isEmpty() || nextDatePicker.getValue() == null || nextDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nh???p thi???u th??ng tin!!!");
            alert.show();
        } else {
            String tmp = String.valueOf(datePicker.getValue());
            String tmp1 = String.valueOf(nextDatePicker.getValue());
            MotherInfor motherInfor = new MotherInfor(
                    medicalIdentifierText.getText(),
                    Integer.parseInt(weightText.getText()),
                    tmp,
                    tmp1
            );

            new MotherInforDAO().addMotherInfor(motherInfor);
            tableView.setItems(FXCollections.observableArrayList(new MotherInforDAO().getAllMotherInfor()));
            clear();
        }
    }

    //X??a th??ng tin
    public void hanldeDelete(MouseEvent event) {

        String medicalId = medicalIdentifierText.getText();
        new MotherInforDAO().deleteMotherInfor(medicalId);
        tableView.setItems(FXCollections.observableArrayList(new MotherInforDAO().getAllMotherInfor()));
        clear();
    }

    //C???p nh???t l???i th??ng tin
    public void handleUpdate(MouseEvent event) {

        String tmp1 = String.valueOf(datePicker.getValue());
        String tmp2 = String.valueOf(nextDatePicker.getValue());
        MotherInfor motherInfor = new MotherInfor(

                medicalIdentifierText.getText(),
                Integer.parseInt(weightText.getText()),
                tmp1,
                tmp2
        );
        new MotherInforDAO().updateMotherInfor(motherInfor);
        tableView.setItems(FXCollections.observableArrayList(new MotherInforDAO().getAllMotherInfor()));
        clear();
    }


}
