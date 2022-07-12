package Controller;


import Model.DataDescriptor.Immunization;
import Model.InteractData.ImmunizationDAO;
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

public class VaccinationUIController implements Initializable {

    //Bảng
    @FXML
    private TableView<Immunization> tableView;
    @FXML
    private TableColumn<Immunization,String> idColumn;
    @FXML
    private TableColumn<Immunization,String> typeColumn;
    @FXML
    private TableColumn<Immunization,String> dateColumn;
    @FXML
    private TableColumn<Immunization,String> lotNumberColumn;
    @FXML
    private TableColumn<Immunization,String> postInjectionColumn;
    @FXML
    private TableColumn<Immunization,Integer> numberInjectionsColumn;
    @FXML
    private TableColumn<Immunization,String> medicalIdentifierColumn;
    @FXML
    ObservableList<Immunization> list;

    //Các Trường nhập dữ liệu
    @FXML
    private TextField searchText;
    @FXML
    private TextField idText;
    @FXML
    private TextField medicalIdentifierText;
    @FXML
    private TextField typeText;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField lotNumberText;
    @FXML
    private TextField postInjectionText;
    @FXML
    private TextField numberInjectionText;
    @FXML
    private CheckBox dateCheck;
    @FXML
    private CheckBox numberCheck;
    //Các

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        tableView.setOnMouseClicked(event -> {
            Immunization vaccine = (Immunization) tableView.getSelectionModel().getSelectedItem();
            idText.setText(vaccine.getId());
            medicalIdentifierText.setText(vaccine.getMedicalIdentifier());
            typeText.setText(vaccine.getType());
            lotNumberText.setText(vaccine.getLotNumber());
            postInjectionText.setText(vaccine.getPostInjectionCondition());
            numberInjectionText.setText(String.valueOf(vaccine.getNumberInjections()));
            String time= vaccine.getDate();
            datePicker.setValue(LocalDate.of(Integer.parseInt(time.substring(0,4)),
                    Integer.parseInt(time.substring(5, 7)),
                    Integer.parseInt(time.substring(8,10))));
            if(tableView.getSelectionModel().isEmpty()) {
                clear();
            }
        });
    }

    //Tải dữ liệu lên bảng tableView
    public void loadData(){
        list = FXCollections.observableArrayList(new ImmunizationDAO().getAllImmunization());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        lotNumberColumn.setCellValueFactory(new PropertyValueFactory<>("lotNumber"));
        postInjectionColumn.setCellValueFactory(new PropertyValueFactory<>("postInjectionCondition"));
        numberInjectionsColumn.setCellValueFactory(new PropertyValueFactory<>("numberInjections"));
        medicalIdentifierColumn.setCellValueFactory(new PropertyValueFactory<>("medicalIdentifier"));
        tableView.setItems(list);
    }

    //Xóa các textField
    public void clear(){
        idText.clear();
        medicalIdentifierText.clear();
        typeText.clear();
        datePicker.setValue(null);
        lotNumberText.clear();
        postInjectionText.clear();
        numberInjectionText.clear();
    }

    //Hiển thị lại toàn bộ dữ liệu
    public void handleShowAll(MouseEvent event){
        tableView.setItems(FXCollections.observableArrayList(new ImmunizationDAO().getAllImmunization()));
    }

    //Tìm kiếm thông tin
    public void handleShow(MouseEvent event){
        String search = searchText.getText();
        boolean checkIsTime = dateCheck.isSelected();
        boolean checkIsNumber = numberCheck.isSelected();
        tableView.setItems(FXCollections.observableArrayList(new ImmunizationDAO().getImmunization(search,checkIsNumber,checkIsTime)));
    }

    //Thêm vaccine và các thông tin liên quan khi một người được tiêm
    public void handleAdd( MouseEvent event){
        if(idColumn.getText().isEmpty() || medicalIdentifierText.getText().isEmpty() || typeText.getText().isEmpty() || datePicker.getValue() == null || lotNumberText.getText().isEmpty()|| postInjectionText.getText().isEmpty() || numberInjectionText.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nhập thiếu thông tin!!!");
            alert.show();
        }else{
            String tmp= String.valueOf(datePicker.getValue());
        Immunization vaccine = new Immunization(
                idText.getText(),
                medicalIdentifierText.getText(),
                typeText.getText(),
                tmp,
                lotNumberText.getText(),
                postInjectionText.getText(),
                Integer.parseInt(numberInjectionText.getText())
        );

        new ImmunizationDAO().addImmunization(vaccine);
        tableView.setItems(FXCollections.observableArrayList(new ImmunizationDAO().getAllImmunization()));
        clear();
        }
    }

    //Xóa vaccine và các thông tin liên quan khi một người được tiêm
    public void hanldeDelete(MouseEvent event){

        String Id = idText.getText();
        new ImmunizationDAO().deleteImmunization(Id);
        tableView.setItems(FXCollections.observableArrayList(new ImmunizationDAO().getAllImmunization()));
        clear();
    }

    //Cập nhật lại thông tin vaccine và các thông tin liên quan khi một người được tiêm
    public void handleUpdate(MouseEvent event){

        String tmp1= String.valueOf(datePicker.getValue());
        Immunization vaccine = new Immunization(
                idText.getText(),
                medicalIdentifierText.getText(),
                typeText.getText(),
                tmp1,
                lotNumberText.getText(),
                postInjectionText.getText(),
                Integer.parseInt(numberInjectionText.getText())
        );
        new ImmunizationDAO().updateImmunization(vaccine);
        tableView.setItems(FXCollections.observableArrayList(new ImmunizationDAO().getAllImmunization()));
        clear();
    }
}
