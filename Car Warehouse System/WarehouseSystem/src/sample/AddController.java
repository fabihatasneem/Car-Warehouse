package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class AddController {
    Client client;
    NetworkUtil networkUtil;
    @FXML
    private TextField regField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField makerField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField color1Field;
    @FXML
    private TextField color2Field;
    @FXML
    private TextField color3Field;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public void addAction() {
        try {
            Car car = new Car();
            if(!regField.getText().isEmpty())
                car.setRegistration(regField.getText());
            boolean yFlag = false;
            try {
                car.setYear(Integer.parseInt(yearField.getText()));
                yFlag = true;
            }catch (Exception e){
                yFlag = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Uh Oh!");
                alert.setContentText("Wrong Input in Year Field.");
                alert.showAndWait();
            }
            if(!makerField.getText().isEmpty())
                car.setMake(makerField.getText());
            if(!modelField.getText().isEmpty())
                car.setModel(modelField.getText());
            if(!color1Field.getText().isEmpty()) {
                car.setColor1(color1Field.getText());
            }else{
                car.setColor1("");
            }
            if(!color2Field.getText().isEmpty()) {
                car.setColor2(color2Field.getText());
            }else{
                car.setColor2("");
            }
            if(!color3Field.getText().isEmpty()) {
                car.setColor3(color3Field.getText());
            }else{
                car.setColor3("");
            }
            boolean pFlag = false;
            try {
                car.setPrice(Integer.parseInt(priceField.getText()));
                pFlag = true;
            }catch (NumberFormatException e){
                pFlag = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Uh Oh!");
                alert.setContentText("Wrong Input in Price Field.");
                alert.showAndWait();
            }
            boolean qFlag = false;
            try {
                car.setQuantity(Integer.parseInt(quantityField.getText()));
                qFlag = true;
            }catch (NumberFormatException  e){
                qFlag = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Uh Oh!");
                alert.setContentText("Wrong Input in Quantity Field.");
                alert.showAndWait();
            }
            networkUtil.write("V");
            boolean flag = false;
            Object o = networkUtil.read();
            if (o != null) {
                ArrayList<Car> carList = (ArrayList<Car>)o;
                for (Car c : carList) {
                    if(regField.getText().equalsIgnoreCase(c.getRegistration())){
                        flag = true;
                        break;
                    }
                }
            }
            if(flag){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Uh Oh!");
                alert.setContentText("Car already exists with this registration number.");
                alert.showAndWait();
            } else if(!regField.getText().isEmpty() && yFlag && pFlag && qFlag && !makerField.getText().isEmpty() && !modelField.getText().isEmpty()) {
                String s = "A#" + car.getRegistration() + "#" + car.getYear() + "#" + car.getMake() + "#" + car.getModel()
                        + "#" + car.getColor1() + "#" + car.getColor2() + "#" + car.getColor3() + "#" + car.getPrice() + "#" + car.getQuantity();
                networkUtil.write(s);
                Object o2 = networkUtil.read();
                if (o2 != null) {
                    String received = (String) o2;
                    if (received.equals("SUCCESS")) {
                        client.showManufacturerViewPage();
                    } else if (received.equals("FAILURE")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("UH OH!");
                        alert.setContentText("Something went wrong. Car couldn't be added.");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Something went wrong!");
                        alert.showAndWait();
                    }
                }
            }else if (regField.getText().isEmpty() || yearField.getText().isEmpty() || makerField.getText().isEmpty() || modelField.getText().isEmpty() || priceField.getText().isEmpty() || quantityField.getText().isEmpty() ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Uh Oh!");
                alert.setContentText("You cannot leave specified fields blank.");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Uh Oh!");
                alert.setContentText("Something went wrong.");
                alert.showAndWait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void backAction() throws IOException {
        client.showManufacturerViewPage();
    }
}
