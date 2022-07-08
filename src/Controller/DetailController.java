package Controller;


import Model.DataDescriptor.Detail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Model.DataDescriptor.InforChildren;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Model.Connect.Connect;

public class DetailController implements Initializable {
    protected static final Connection conn=Connect.getConnection();
    protected static PreparedStatement ps;
    protected static ResultSet rs;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TableView <Detail>growthNumeralTable;
    @FXML
    public TableColumn <Detail,Integer>monthOldColumn;
    @FXML
    public TableColumn <Detail,Double>heightColumn;
    @FXML
    public TableColumn <Detail,Double>weightColumn;
    @FXML
    public Label nameLable;
    @FXML
    public LineChart heightLineChart;
    @FXML
    public LineChart weightLineChart;
    @FXML
    public TextField textTime;
    @FXML
    public TextField textHeight;
    @FXML
    public TextField textWeight;
    @FXML
    public Label IDLabel;
    @FXML
    public TextField fidLabel;
    @FXML
    public ObservableList<Detail> list;
    @FXML
    public ObservableList<Detail> listDetail;
    @FXML
    public Label genderLabel;


    public void AddButton(ActionEvent actionEvent) {
        Detail detail = new Detail(
              Integer.parseInt(fidLabel.getText()),
              IDLabel.getText(),
                Integer.parseInt(textTime.getText()),
                Double.parseDouble(textHeight.getText()),
              Double.parseDouble(textWeight.getText()));

        String sql=" insert into so_lieu_tre_em(fid,ma_dinh_danh_y_te,thang_tuoi,chieu_cao,can_nang)values(?,?,?,?,?)";
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,detail.getFid());
            ps.setString(2,detail.getMedical_id());
            ps.setInt(3,detail.getMonth());
            ps.setDouble(4,detail.getHeight());
            ps.setDouble(5,detail.getWeight());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        drawHeightChart();
        drawWeightChart();
        growthNumeralTable.setItems(FXCollections.observableArrayList(getList()));

        Reset();
    }

    public void EditButton(ActionEvent actionEvent) {
        Detail detail = new Detail(
              Integer.parseInt(fidLabel.getText()),
              IDLabel.getText(),
                Integer.parseInt(textTime.getText()),
              Double.parseDouble(textHeight.getText()),
                Double.parseDouble(textWeight.getText()));

        String sql="update so_lieu_tre_em set ma_dinh_danh_y_te=?,thang_tuoi=?, chieu_cao=?,can_nang=? where fid=?";
        try {
            ps =conn.prepareStatement(sql);
            ps.setString(1,detail.getMedical_id());
            ps.setInt(2,detail.getMonth());
            ps.setDouble(3,detail.getHeight());
            ps.setDouble(4,detail.getWeight());
            ps.setInt(5,detail.getFid());
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        drawHeightChart();
        drawWeightChart();
        growthNumeralTable.setItems(FXCollections.observableArrayList(getList()));
        Reset();
    }

    public void DeleteButton(ActionEvent actionEvent) {
        Detail detail = growthNumeralTable.getSelectionModel().getSelectedItem();
        String sql="delete from so_lieu_tre_em where fid="+detail.getFid();
        try {
            ps=conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        drawHeightChart();
        drawWeightChart();
        growthNumeralTable.setItems(FXCollections.observableArrayList(getList()));
    }
    public void  setInfo(InforChildren inforChildren){
        nameLable.setText(inforChildren.getName());
        IDLabel.setText(inforChildren.getiD());
        genderLabel.setText(inforChildren.getSex());
        String sql = "select * from so_lieu_tre_em where ma_dinh_danh_y_te=\'"+inforChildren.getiD()+"\'";
        ArrayList <Detail> arr= new ArrayList<>();
        try {
            ps= conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()){
                Detail detail = new Detail();
                detail.setFid(rs.getInt("fid"));
                detail.setMedical_id(rs.getString("ma_dinh_danh_y_te"));
                detail.setMonth(rs.getInt("thang_tuoi"));
                detail.setHeight(rs.getDouble("chieu_cao"));
                detail.setWeight(rs.getDouble("can_nang"));
                arr.add(detail);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        defaultChart();
        drawHeightChart();
        drawWeightChart();
        growthNumeralTable.setItems(FXCollections.observableArrayList(arr));

    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/GUI/sample.fxml"));
        Parent viewSample = loader.load();
        Scene scene = new Scene(viewSample,750,560);
        stage.setScene(scene);
    }
    public  void Reset(){
        textHeight.setText("");
        textWeight.setText("");
        textTime.setText("");
    }

    public ArrayList<Detail> getList(){

        String sql="SELECT * FROM so_lieu_tre_em where ma_dinh_danh_y_te =\'"+IDLabel.getText()+"\'";
        ArrayList<Detail> arr=new ArrayList<>();
        try{
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                Detail detail=new Detail();
                detail.setFid(Integer.parseInt(rs.getString(1)));
                detail.setMedical_id(rs.getString(2));
                detail.setMonth(Integer.parseInt(rs.getString(3)));
                detail.setHeight(Double.parseDouble(rs.getString(4)));
                detail.setWeight(Double.parseDouble(rs.getString(5)));
                arr.add(detail);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arr;
    }

    public void defaultChart(){
        if(genderLabel.getText().equals("Nam")){

            XYChart.Series<String, Number> height_upper_limit_male = new XYChart.Series<>();
            XYChart.Data<String,Number> hum0 = new XYChart.Data<>("0 ", 53.7);
            XYChart.Data<String,Number> hum1 = new XYChart.Data<>("1 ", 58.6);
            XYChart.Data<String,Number> hum2 = new XYChart.Data<>("2 ", 62.4);
            XYChart.Data<String,Number> hum3 = new XYChart.Data<>("3 ", 65.5);
            XYChart.Data<String,Number> hum4 = new XYChart.Data<>("4 ", 68.0);
            XYChart.Data<String,Number> hum5 = new XYChart.Data<>("5 ", 70.1);
            XYChart.Data<String,Number> hum6 = new XYChart.Data<>("6 ", 71.9);
            XYChart.Data<String,Number> hum7 = new XYChart.Data<>("7 ", 73.5);
            XYChart.Data<String,Number> hum8 = new XYChart.Data<>("8 ", 75.0);
            XYChart.Data<String,Number> hum9 = new XYChart.Data<>("9 ", 76.5);
            XYChart.Data<String,Number> hum10 = new XYChart.Data<>("10 ", 77.9);
            XYChart.Data<String,Number> hum11 = new XYChart.Data<>("11 ", 79.2);
            XYChart.Data<String,Number> hum12 = new XYChart.Data<>("12 ", 80.5);
            height_upper_limit_male.getData().addAll(hum0,hum1,hum2,hum3,hum4,hum5,hum6,hum7,hum8,hum9,hum10,hum11,hum12);
            height_upper_limit_male.setName("Very Tall");
            heightLineChart.getData().add(height_upper_limit_male);


            XYChart.Series<String, Number> height_lower_limit_male = new XYChart.Series<>();
            XYChart.Data<String,Number> hlm0 = new XYChart.Data<>("0 ", 46.1);
            XYChart.Data<String,Number> hlm1 = new XYChart.Data<>("1 ", 50.8);
            XYChart.Data<String,Number> hlm2 = new XYChart.Data<>("2 ", 54.4);
            XYChart.Data<String,Number> hlm3 = new XYChart.Data<>("3 ", 57.3);
            XYChart.Data<String,Number> hlm4 = new XYChart.Data<>("4 ", 59.7);
            XYChart.Data<String,Number> hlm5 = new XYChart.Data<>("5 ", 61.7);
            XYChart.Data<String,Number> hlm6 = new XYChart.Data<>("6 ", 63.3);
            XYChart.Data<String,Number> hlm7 = new XYChart.Data<>("7 ", 64.8);
            XYChart.Data<String,Number> hlm8 = new XYChart.Data<>("8 ", 66.2);
            XYChart.Data<String,Number> hlm9 = new XYChart.Data<>("9 ", 67.5);
            XYChart.Data<String,Number> hlm10 = new XYChart.Data<>("10 ", 68.7);
            XYChart.Data<String,Number> hlm11 = new XYChart.Data<>("11 ", 69.9);
            XYChart.Data<String,Number> hlm12 = new XYChart.Data<>("12 ", 71.0);
            height_lower_limit_male.setName("Short");
            height_lower_limit_male.getData().addAll(hlm0,hlm1,hlm2,hlm3,hlm4,hlm5,hlm6,hlm7,hlm8,hlm9,hlm10,hlm11,hlm12);
            heightLineChart.getData().add(height_lower_limit_male);

            XYChart.Series<String, Number> weight_upper_limit_male = new XYChart.Series<>();
            XYChart.Data<String,Number> wum0 = new XYChart.Data<>("0 ", 4.4);
            XYChart.Data<String,Number> wum1 = new XYChart.Data<>("1 ", 5.8);
            XYChart.Data<String,Number> wum2 = new XYChart.Data<>("2 ", 7.1);
            XYChart.Data<String,Number> wum3 = new XYChart.Data<>("3 ", 8.0);
            XYChart.Data<String,Number> wum4 = new XYChart.Data<>("4 ", 8.7);
            XYChart.Data<String,Number> wum5 = new XYChart.Data<>("5 ", 9.3);
            XYChart.Data<String,Number> wum6 = new XYChart.Data<>("6 ", 9.8);
            XYChart.Data<String,Number> wum7 = new XYChart.Data<>("7 ", 10.3);
            XYChart.Data<String,Number> wum8 = new XYChart.Data<>("8 ", 10.7);
            XYChart.Data<String,Number> wum9 = new XYChart.Data<>("9 ", 11.0);
            XYChart.Data<String,Number> wum10 = new XYChart.Data<>("10 ", 11.4);
            XYChart.Data<String,Number> wum11 = new XYChart.Data<>("11 ", 11.7);
            XYChart.Data<String,Number> wum12 = new XYChart.Data<>("12 ", 12.0);
            weight_upper_limit_male.setName("Overweight");
            weight_upper_limit_male.getData().addAll(wum0,wum1,wum2,wum3,wum4,wum5,wum6,wum7,wum8,wum9,wum10,wum11,wum12);
            weightLineChart.getData().add(weight_upper_limit_male);


            XYChart.Series<String, Number> weight_lower_limit_male = new XYChart.Series<>();
            XYChart.Data<String,Number> wlm0 = new XYChart.Data<>("0 ", 2.5);
            XYChart.Data<String,Number> wlm1 = new XYChart.Data<>("1 ", 3.4);
            XYChart.Data<String,Number> wlm2 = new XYChart.Data<>("2 ", 4.3);
            XYChart.Data<String,Number> wlm3 = new XYChart.Data<>("3 ", 5.0);
            XYChart.Data<String,Number> wlm4 = new XYChart.Data<>("4 ", 5.6);
            XYChart.Data<String,Number> wlm5 = new XYChart.Data<>("5 ", 6.0);
            XYChart.Data<String,Number> wlm6 = new XYChart.Data<>("6 ", 6.4);
            XYChart.Data<String,Number> wlm7 = new XYChart.Data<>("7 ", 6.7);
            XYChart.Data<String,Number> wlm8 = new XYChart.Data<>("8 ", 6.9);
            XYChart.Data<String,Number> wlm9 = new XYChart.Data<>("9 ", 7.1);
            XYChart.Data<String,Number> wlm10 = new XYChart.Data<>("10 ", 7.4);
            XYChart.Data<String,Number> wlm11 = new XYChart.Data<>("11 ", 7.6);
            XYChart.Data<String,Number> wlm12 = new XYChart.Data<>("12 ", 7.7);
            weight_lower_limit_male.setName("Malnutrition");
            weight_lower_limit_male.getData().addAll(wlm0,wlm1,wlm2,wlm3,wlm4,wlm5,wlm6,wlm7,wlm8,wlm9,wlm10,wlm11,wlm12);
            weightLineChart.getData().add(weight_lower_limit_male);



        }
        else {

            XYChart.Series<String, Number> height_upper_limit_female = new XYChart.Series<>();
            XYChart.Data<String,Number> hufm0 = new XYChart.Data<>("0 ", 52.9);
            XYChart.Data<String,Number> hufm1 = new XYChart.Data<>("1 ", 57.6);
            XYChart.Data<String,Number> hufm2 = new XYChart.Data<>("2 ", 61.1);
            XYChart.Data<String,Number> hufm3 = new XYChart.Data<>("3 ", 64.0);
            XYChart.Data<String,Number> hufm4 = new XYChart.Data<>("4 ", 66.4);
            XYChart.Data<String,Number> hufm5 = new XYChart.Data<>("5 ", 68.5);
            XYChart.Data<String,Number> hufm6 = new XYChart.Data<>("6 ", 70.3);
            XYChart.Data<String,Number> hufm7 = new XYChart.Data<>("7 ", 71.9);
            XYChart.Data<String,Number> hufm8 = new XYChart.Data<>("8 ", 73.5);
            XYChart.Data<String,Number> hufm9 = new XYChart.Data<>("9 ", 75.0);
            XYChart.Data<String,Number> hufm10 = new XYChart.Data<>("10 ", 76.4);
            XYChart.Data<String,Number> hufm11 = new XYChart.Data<>("11 ", 77.8);
            XYChart.Data<String,Number> hufm12 = new XYChart.Data<>("12 ", 79.2);
            height_upper_limit_female.setName("Very Tall");
            height_upper_limit_female.getData().addAll(hufm0,hufm1,hufm2,hufm3,hufm4,hufm5,hufm6,hufm7,hufm8,hufm9,hufm10,hufm11,hufm12);
            heightLineChart.getData().add(height_upper_limit_female);

            XYChart.Series<String, Number> height_lower_limit_female = new XYChart.Series<>();
            XYChart.Data<String,Number> hlfm0 = new XYChart.Data<>("0 ", 45.4);
            XYChart.Data<String,Number> hlfm1 = new XYChart.Data<>("1 ", 49.8);
            XYChart.Data<String,Number> hlfm2 = new XYChart.Data<>("2 ", 53.0);
            XYChart.Data<String,Number> hlfm3 = new XYChart.Data<>("3 ", 55.6);
            XYChart.Data<String,Number> hlfm4 = new XYChart.Data<>("4 ", 57.8);
            XYChart.Data<String,Number> hlfm5 = new XYChart.Data<>("5 ", 59.6);
            XYChart.Data<String,Number> hlfm6 = new XYChart.Data<>("6 ", 61.2);
            XYChart.Data<String,Number> hlfm7 = new XYChart.Data<>("7 ", 62.7);
            XYChart.Data<String,Number> hlfm8 = new XYChart.Data<>("8 ", 64.0);
            XYChart.Data<String,Number> hlfm9 = new XYChart.Data<>("9 ", 65.3);
            XYChart.Data<String,Number> hlfm10 = new XYChart.Data<>("10 ", 66.5);
            XYChart.Data<String,Number> hlfm11 = new XYChart.Data<>("11 ", 67.7);
            XYChart.Data<String,Number> hlfm12 = new XYChart.Data<>("12 ", 68.9);
            height_lower_limit_female.setName("Short");
            height_lower_limit_female.getData().addAll(hlfm0,hlfm1,hlfm2,hlfm3,hlfm4,hlfm5,hlfm6,hlfm7,hlfm8,hlfm9,hlfm10,hlfm11,hlfm12);
            heightLineChart.getData().add(height_lower_limit_female);

            XYChart.Series<String, Number> weight_upper_limit_female = new XYChart.Series<>();
            XYChart.Data<String,Number> wufm0 = new XYChart.Data<>("0 ", 4.2);
            XYChart.Data<String,Number> wufm1 = new XYChart.Data<>("1 ", 5.5);
            XYChart.Data<String,Number> wufm2 = new XYChart.Data<>("2 ", 6.6);
            XYChart.Data<String,Number> wufm3 = new XYChart.Data<>("3 ", 7.5);
            XYChart.Data<String,Number> wufm4 = new XYChart.Data<>("4 ", 8.2);
            XYChart.Data<String,Number> wufm5 = new XYChart.Data<>("5 ", 8.8);
            XYChart.Data<String,Number> wufm6 = new XYChart.Data<>("6 ", 9.3);
            XYChart.Data<String,Number> wufm7 = new XYChart.Data<>("7 ", 9.8);
            XYChart.Data<String,Number> wufm8 = new XYChart.Data<>("8 ", 10.2);
            XYChart.Data<String,Number> wufm9 = new XYChart.Data<>("9 ", 10.5);
            XYChart.Data<String,Number> wufm10 = new XYChart.Data<>("10 ", 10.9);
            XYChart.Data<String,Number> wufm11 = new XYChart.Data<>("11 ", 11.2);
            XYChart.Data<String,Number> wufm12 = new XYChart.Data<>("12 ", 11.5);
            weight_upper_limit_female.setName("Overweight");
            weight_upper_limit_female.getData().addAll(wufm0,wufm1,wufm2,wufm3,wufm4,wufm5,wufm6,wufm7,wufm8,wufm9,wufm10,wufm11,wufm12);
            weightLineChart.getData().add(weight_upper_limit_female);


            XYChart.Series<String, Number> weight_lower_limit_female = new XYChart.Series<>();
            XYChart.Data<String,Number> wlfm0 = new XYChart.Data<>("0 ", 2.4);
            XYChart.Data<String,Number> wlfm1 = new XYChart.Data<>("1 ", 3.2);
            XYChart.Data<String,Number> wlfm2 = new XYChart.Data<>("2 ", 3.9);
            XYChart.Data<String,Number> wlfm3 = new XYChart.Data<>("3 ", 4.5);
            XYChart.Data<String,Number> wlfm4 = new XYChart.Data<>("4 ", 5.0);
            XYChart.Data<String,Number> wlfm5 = new XYChart.Data<>("5 ", 5.4);
            XYChart.Data<String,Number> wlfm6 = new XYChart.Data<>("6 ", 5.7);
            XYChart.Data<String,Number> wlfm7 = new XYChart.Data<>("7 ", 6.0);
            XYChart.Data<String,Number> wlfm8 = new XYChart.Data<>("8 ", 6.3);
            XYChart.Data<String,Number> wlfm9 = new XYChart.Data<>("9 ", 6.5);
            XYChart.Data<String,Number> wlfm10 = new XYChart.Data<>("10 ", 6.7);
            XYChart.Data<String,Number> wlfm11 = new XYChart.Data<>("11 ", 6.9);
            XYChart.Data<String,Number> wlfm12 = new XYChart.Data<>("12 ", 7.0);
            weight_lower_limit_female.setName("Malnutrition");
            weight_lower_limit_female.getData().addAll(wlfm0,wlfm1,wlfm2,wlfm3,wlfm4,wlfm5,wlfm6,wlfm7,wlfm8,wlfm9,wlfm10,wlfm11,wlfm12);
            weightLineChart.getData().add(weight_lower_limit_female);

        }

    }

    public void drawHeightChart(){
        String sql =" select* from so_lieu_tre_em where ma_dinh_danh_y_te =\'"+ IDLabel.getText()+"\'";

        XYChart.Series<String, Number> heightChart = new XYChart.Series<>();

        try {
            ps = conn.prepareStatement(sql);
            rs= ps.executeQuery();

            while(rs.next()){
                XYChart.Data<String,Number> month = new XYChart.Data<>(rs.getInt("thang_tuoi")+" ", rs.getDouble("chieu_cao"));
                heightChart.getData().add(month);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        heightChart.setName("Height Growth ");
        heightLineChart.getData().add(heightChart);


    }

    public void drawWeightChart(){
        String sql =" select* from so_lieu_tre_em where ma_dinh_danh_y_te =\'"+ IDLabel.getText()+"\'";

        XYChart.Series<String, Number> weightChart = new XYChart.Series<>();

        try {
            ps = conn.prepareStatement(sql);
            rs= ps.executeQuery();

            while(rs.next()){

                XYChart.Data<String,Number> month = new XYChart.Data<>(rs.getInt("thang_tuoi")+" ", rs.getDouble("can_nang"));
                weightChart.getData().add(month);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        weightChart.setName("Weight Growth ");
        weightLineChart.getData().add(weightChart);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//      list = FXCollections.observableList(getList());
        monthOldColumn.setCellValueFactory(new PropertyValueFactory<Detail,Integer>("month"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<Detail,Double>("height"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<Detail,Double>("weight"));
        growthNumeralTable.setItems(list);
        growthNumeralTable.setOnMouseClicked(event ->{
            Detail detail = growthNumeralTable.getSelectionModel().getSelectedItem();
            textTime.setText(String.valueOf(detail.getMonth()));
            textHeight.setText(String.valueOf(detail.getHeight()));
            textWeight.setText(String.valueOf(detail.getWeight()));
            if(growthNumeralTable.getSelectionModel().isEmpty()){
                Reset();
            }


        });



    }
}
