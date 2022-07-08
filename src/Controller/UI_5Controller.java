package Controller;

import Model.DataDescriptor.VaccinationSchedule;
import Model.InteractData.UpdateHome;
import com.gluonhq.charm.glisten.control.TimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import Model.InteractData.UpdateHome;

public class UI_5Controller implements Initializable {

    @FXML
    private TextField nameText;
    @FXML
    private TextField searchText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField amountText;
    @FXML
    private DatePicker timePicker;
    @FXML
    private TextField idText;
    @FXML
    private CheckBox dateCheck;
    @FXML
    private CheckBox numberCheck;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateFinish;
    @FXML
    private Text sumText;
    @FXML
    private ObservableList<VaccinationSchedule> ListSchedule;
    @FXML
    private TableView<VaccinationSchedule> tableView;
    @FXML
    private TableColumn<VaccinationSchedule,Integer> idColumn;
    @FXML
    private TableColumn<VaccinationSchedule,String> nameColumn;
    @FXML
    private TableColumn<VaccinationSchedule,String> addressColumn;
    @FXML
    private TableColumn<VaccinationSchedule,String> timeColumn;
    @FXML
    private TableColumn<VaccinationSchedule,Integer> amountColumn;
    @FXML
    private ObservableList<VaccinationSchedule> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list=  FXCollections.observableArrayList(new UpdateHome().getList());
        idColumn.setCellValueFactory(new PropertyValueFactory<VaccinationSchedule,Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<VaccinationSchedule,String>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<VaccinationSchedule,String>("address"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<VaccinationSchedule,String>("time"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<VaccinationSchedule,Integer>("amount"));
        tableView.setItems(list);
        tableView.setOnMouseClicked(event -> {
            VaccinationSchedule s=tableView.getSelectionModel().getSelectedItem();
            idText.setText(String.valueOf(s.getId()));
            nameText.setText(s.getName());
            amountText.setText(String.valueOf(s.getAmount()));
            addressText.setText(s.getAddress());
            String time=s.getTime();
            timePicker.setValue(LocalDate.of(Integer.parseInt(time.substring(0,4)),
                    Integer.parseInt(time.substring(5, 7)),
                    Integer.parseInt(time.substring(8,10))));
            if(tableView.getSelectionModel().isEmpty()) {
                Reset();
            }
        });

    }
    public void Reset(){
        idText.clear();
        addressText.clear();
        nameText.clear();
        amountText.clear();
        timePicker.setValue(null);
    }
    public void setShowButton(ActionEvent event){
        tableView.setItems(FXCollections.observableArrayList(new UpdateHome().getList()));
        Reset();
    }
    public void setAddButton(ActionEvent event){
        String tmp= String.valueOf(timePicker.getValue());
        VaccinationSchedule s = new VaccinationSchedule(
                Integer.parseInt(idText.getText()),
                nameText.getText(),
                Integer.parseInt(amountText.getText()),
                addressText.getText(),
                tmp );
        new UpdateHome().AddStudent(s);
        ListSchedule=FXCollections.observableArrayList(new UpdateHome().getList());
        tableView.setItems(ListSchedule);
        Reset();
    }
    public void Update(ActionEvent event){
        String tmp1= String.valueOf(timePicker.getValue());
        VaccinationSchedule s=new VaccinationSchedule(
                Integer.parseInt(idText.getText()),
                nameText.getText(),
                Integer.parseInt(amountText.getText()),
                addressText.getText(),
                tmp1);
        new UpdateHome().updateTable(s);
        ListSchedule=FXCollections.observableArrayList(new UpdateHome().getList());
        tableView.setItems(ListSchedule);
        Reset();
    }
    public void setDeleteButton(ActionEvent event){
        String ID = idText.getText();
        new UpdateHome().Delete(ID);
        ListSchedule=FXCollections.observableArrayList(new UpdateHome().getList());
        tableView.setItems(ListSchedule);
        Reset();
    }
    public void setSearchButton(ActionEvent event){
        String search = searchText.getText();
        boolean checkIsTime = dateCheck.isSelected();
        boolean checkIsNumber = numberCheck.isSelected();
        tableView.setItems(FXCollections.observableList(new UpdateHome().SearchTable(search,checkIsTime,checkIsNumber)));
        Reset();
    }
    public void showSum(ActionEvent event){
        String search1= String.valueOf(dateStart.getValue());
        String search2 = String.valueOf(dateFinish.getValue());
        sumText.setText(String.valueOf(new UpdateHome().sumNumberVaccine(search1,search2)));
    }

}
