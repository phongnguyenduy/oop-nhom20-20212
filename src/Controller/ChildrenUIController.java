package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Connect.Connect;
import Model.DataDescriptor.InforChildren;

public class ChildrenUIController implements Initializable {

    protected static final Connection conn = Connect.getConnection();
    protected static PreparedStatement ps;
    protected static ResultSet rs;

    @FXML
    private AnchorPane childrenUI;
    @FXML
    private TextField nameText;
    @FXML
    private TextField searchText;
    @FXML
    private ComboBox<String> sex;
    @FXML
    private TextField farNameText;
    @FXML
    private DatePicker dOBPicker;
    @FXML
    private TextField idText;
    @FXML
    private TextField morNameText;
    @FXML
    private CheckBox dateCheck;

    @FXML
    private ObservableList<InforChildren> ListChild;
    @FXML
    private TableView<InforChildren> tableView;
    @FXML
    private TableColumn<InforChildren, String> iDColumn;
    @FXML
    private TableColumn<InforChildren, String> nameColumn;
    @FXML
    private TableColumn<InforChildren, String> dOBColumn;
    @FXML
    private TableColumn<InforChildren, String> sexColumn;
    @FXML
    private TableColumn<InforChildren, String> farNameColumn;
    @FXML
    private TableColumn<InforChildren, String> morNameColumn;

    @FXML
    private ObservableList<InforChildren> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        list = FXCollections.observableArrayList(getList());
        iDColumn.setCellValueFactory(new PropertyValueFactory<InforChildren, String>("medicalIdentifier"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<InforChildren, String>("name"));
        dOBColumn.setCellValueFactory(new PropertyValueFactory<InforChildren, String>("birthday"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<InforChildren, String>("gender"));
        farNameColumn.setCellValueFactory(new PropertyValueFactory<InforChildren, String>("farName"));
        morNameColumn.setCellValueFactory(new PropertyValueFactory<InforChildren, String>("morName"));
        tableView.setItems(list);
        tableView.setOnMouseClicked(event -> {
            InforChildren s = tableView.getSelectionModel().getSelectedItem();
            idText.setText(s.getiD());
            nameText.setText(s.getName());
            String time = s.getdOB();
            dOBPicker.setValue(LocalDate.of(Integer.parseInt(time.substring(0, 4)),
                    Integer.parseInt(time.substring(5, 7)),
                    Integer.parseInt(time.substring(8, 10))));
            sex.setValue(s.getSex());
            farNameText.setText(s.getFarName());
            morNameText.setText(s.getMorName());
            if (tableView.getSelectionModel().isEmpty()) {
                Reset();
            }
            idText.setDisable(true);

        });
        sex.setItems(FXCollections.observableArrayList("Nam", "Nữ"));

    }

    public void Reset() {
        idText.clear();
        sex.setValue("Options");
        nameText.clear();
        farNameText.clear();
        morNameText.clear();
        dOBPicker.setValue(null);
    }

    public void setAddButton(ActionEvent actionEvent) {

        if (nameText.getText().isEmpty() || sex.getValue() == null || idText.getText().isEmpty() || dOBPicker.getValue() == null || farNameText.getText().isEmpty() || morNameText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nhập thiếu thông tin!!!");
            alert.show();
        } else {
            String time = String.valueOf(dOBPicker.getValue());
            InforChildren s = new InforChildren(
                    idText.getText(),
                    nameText.getText(),
                    time,
                    sex.getValue(),
                    farNameText.getText(),
                    morNameText.getText());

            LocalDate localDate = LocalDate.of(Integer.parseInt(time.substring(0, 4)),
                    Integer.parseInt(time.substring(5, 7)),
                    Integer.parseInt(time.substring(8, 10)));

            String sql = "insert into thong_tin_tre_em(ma_dinh_danh_y_te,ho_ten,ngay_sinh,gioi_tinh,bo,me)values(?,?,?,?,?,?)";
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, s.getiD());
                ps.setString(2, s.getName());
                ps.setObject(3, localDate);
                ps.setString(4, s.getSex());
                ps.setString(5, s.getFarName());
                ps.setString(6, s.getMorName());
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
                alert.show();
            }


            ListChild = FXCollections.observableArrayList(getList());
            tableView.setItems(ListChild);
            Reset();
        }
    }

    public void Update(ActionEvent event) {
        String time = String.valueOf(dOBPicker.getValue());

        InforChildren s = new InforChildren(
                idText.getText(),
                nameText.getText(),
                time,
                sex.getValue(),
                farNameText.getText(),
                morNameText.getText());

        LocalDate localDate = LocalDate.of(Integer.parseInt(time.substring(0, 4)),
                Integer.parseInt(time.substring(5, 7)),
                Integer.parseInt(time.substring(8, 10)));

        String sql = "UPDATE thong_tin_tre_em SET ho_ten=?, ngay_sinh=?, gioi_tinh=?, bo=?,me=? WHERE ma_dinh_danh_y_te=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setObject(2, localDate);
            ps.setString(3, s.getSex());
            ps.setString(4, s.getFarName());
            ps.setString(5, s.getMorName());
            ps.setString(6, s.getiD());
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }

        ListChild = FXCollections.observableArrayList(getList());
        tableView.setItems(ListChild);
        Reset();
    }

    public void setDeleteButton(ActionEvent event) {

        String ID = idText.getText();
        String sql = "DELETE FROM thong_tin_tre_em where ma_dinh_danh_y_te='" + ID + "'";

        try {
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
        ListChild = FXCollections.observableArrayList(getList());
        tableView.setItems(ListChild);
    }

    public ArrayList<InforChildren> SearchTable(String search, boolean checkIsTime) {
        String sql;
        if (checkIsTime) {
            sql = "SELECT * FROM thong_tin_tre_em WHERE ngay_sinh ='" + search + "'";

        } else {
            sql = "SELECT * FROM thong_tin_tre_em WHERE ma_dinh_danh_y_te LIKE '" + search + "'" +
                    "OR ho_ten LIKE '" + search + "'" + "OR gioi_tinh LIKE '" + search + "'" +
                    "OR bo LIKE '" + search + "'" + "OR me LIKE '" + search + "'";
        }

        ArrayList<InforChildren> arr = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                InforChildren s = new InforChildren(
                        rs.getString("ma_dinh_danh_y_te"),
                        rs.getString("ho_ten"),
                        rs.getString("ngay_sinh"),
                        rs.getString("gioi_tinh"),
                        rs.getString("bo"),
                        rs.getString("me"));
                arr.add(s);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }

        return arr;
    }

    public void setSearchButton(ActionEvent event) {
        String search = searchText.getText();
        boolean checkIsTime = dateCheck.isSelected();

        tableView.setItems(FXCollections.observableList(SearchTable(search, checkIsTime)));
        searchText.clear();
    }

    public ArrayList<InforChildren> getList() {
        String sql = "SELECT * FROM thong_tin_tre_em";
        ArrayList<InforChildren> arr = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                InforChildren s = new InforChildren();
                s.setiD(rs.getString(1));
                s.setName(rs.getString(2));
                s.setdOB(rs.getString(3));
                s.setSex(rs.getString(4));
                s.setFarName(rs.getString(5));
                s.setMorName(rs.getString(6));
                arr.add(s);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Không thành công, vui lòng kiểm tra lại!!!");
            alert.show();
        }
        return arr;
    }

    public void setReturn(ActionEvent event) {
        tableView.setItems(FXCollections.observableArrayList(getList()));
        Reset();
        idText.setDisable(false);
    }

    public void ViewGrowthChartButton(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/GUI/Chart.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        DetailController controller = loader.getController();
        InforChildren inforChildren = tableView.getSelectionModel().getSelectedItem();
        controller.setInfo(inforChildren);
        stage.setScene(scene);
        stage.setHeight(670);
        stage.setWidth(1300);
    }

}

