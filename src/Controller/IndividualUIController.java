package Controller;

import Model.DataDescriptor.Person;
import Model.InteractData.PersonDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.LocalDate.of;

public class IndividualUIController implements Initializable {

    @FXML
    private TextField nameText;
    @FXML
    private TextField medicalIdentifierText;
    @FXML
    private TextField citizenIdentificationText;
    @FXML
    private TextField genderText;
    @FXML
    private DatePicker birthdayPicker;
    @FXML
    private TextField addressText;
    @FXML
    private TextField numberPhoneText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField vaccinationtatusText;
    @FXML
    private TextField searchText;
    @FXML
    private CheckBox dateCheck;
    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, String> medicalIdentifierColumn;
    @FXML
    private TableColumn<Person, String> nameColum;
    @FXML
    private TableColumn<Person, String> citizenIdentificationColumn;
    @FXML
    private TableColumn<Person, String> genderColumn;
    @FXML
    private TableColumn<Person, String> birthdayColumn;
    @FXML
    private TableColumn<Person, String> addressColumn;
    @FXML
    private TableColumn<Person, String> numberPhoneColumn;
    @FXML
    private TableColumn<Person, String> emailColumn;
    @FXML
    private TableColumn<Person, String> vaccinationtatusColumn;

    ObservableList<Person> People;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getData();
        tableView.setOnMouseClicked(event1 -> {
            Person person = tableView.getSelectionModel().getSelectedItem();
            medicalIdentifierText.setText(person.getMedicalIdentifier());
            nameText.setText(person.getName());
            citizenIdentificationText.setText(person.getCitizenIdentification());
            genderText.setText(person.getGender());
            String time = person.getBirthday();
            birthdayPicker.setValue(LocalDate.of(Integer.parseInt(time.substring(0, 4)),
                    Integer.parseInt(time.substring(5, 7)),
                    Integer.parseInt(time.substring(8, 10))));
            addressText.setText(person.getAddress());
            numberPhoneText.setText(person.getNumberPhone());
            emailText.setText(person.getEmail());
            vaccinationtatusText.setText(person.getVaccinationtatus());
            if (tableView.getSelectionModel().isEmpty()) {
                clear();
            }

        });
    }

    //Tải dữ liệu lên bảng tableView
    public void getData() {
        People = FXCollections.observableArrayList(new PersonDao().getPeople());
        medicalIdentifierColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("medicalIdentifier"));
        nameColum.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        citizenIdentificationColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("citizenIdentification"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("gender"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("birthday"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
        numberPhoneColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("numberPhone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        vaccinationtatusColumn.setCellValueFactory((new PropertyValueFactory<Person, String>("vaccinationtatus")));
        tableView.setItems(People);
    }

    //Xóa các textField
    public void clear() {
        nameText.clear();
        medicalIdentifierText.clear();
        citizenIdentificationText.clear();
        genderText.clear();
        addressText.clear();
        numberPhoneText.clear();
        emailText.clear();
        birthdayPicker.setValue(null);
        vaccinationtatusText.clear();
    }

    //Hiển thị lại toàn bộ dữ liệu
    public void handleShowAll(MouseEvent event) {
        tableView.setItems(FXCollections.observableArrayList(new PersonDao().getPeople()));
        clear();
    }

    //Thêm
    public void handleAddPeople(MouseEvent event) {

        if (medicalIdentifierText.getText().isEmpty() || nameText.getText().isEmpty() || birthdayPicker.getValue() == null || genderText.getText().isEmpty() || vaccinationtatusText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nhập thiếu thông tin, vui lòng bổ sung!!!\nHọ tên, mã định danh y tế, giới tính, \nngày sinh, tình trạng tiêm chủng \nkhông được để trống!!!");
            alert.show();
        } else {
            String tmp = String.valueOf(birthdayPicker.getValue());
            Person person = new Person(
                    medicalIdentifierText.getText(),
                    nameText.getText(),
                    citizenIdentificationText.getText(),
                    genderText.getText(),
                    tmp,
                    addressText.getText(),
                    numberPhoneText.getText(),
                    emailText.getText(),
                    vaccinationtatusText.getText()
            );

            new PersonDao().addPerson(person);
            tableView.setItems(FXCollections.observableArrayList(new PersonDao().getPeople()));
            clear();
        }

    }

    //Xóa
    public void handleDeletePeople(MouseEvent event) {

        String medicalId = medicalIdentifierText.getText();
        new PersonDao().deletePerson(medicalId);
        People = FXCollections.observableArrayList(new PersonDao().getPeople());
        tableView.setItems(People);
        clear();
    }

    //Sửa
    public void handleUpdatePeople(MouseEvent event) {
        String tmp1 = String.valueOf(birthdayPicker.getValue());
        Person person = new Person(
                medicalIdentifierText.getText(),
                nameText.getText(),
                citizenIdentificationText.getText(),
                genderText.getText(),
                tmp1,
                addressText.getText(),
                numberPhoneText.getText(),
                emailText.getText(),
                vaccinationtatusText.getText()
        );
        new PersonDao().updatePerson(person);
        ObservableList<Person> people = FXCollections.observableArrayList(new PersonDao().getPeople());
        tableView.setItems(people);
        clear();
    }

    //Tìm kiếm
    public void handleSearchPeople(MouseEvent event) {
        String search = searchText.getText();
        boolean checkIsTime = dateCheck.isSelected();
        tableView.setItems(FXCollections.observableArrayList(new PersonDao().getPeople(search, checkIsTime)));
        clear();
    }
}
