package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

public class SearchController {

    Client client;
    NetworkUtil networkUtil;
    @FXML
    private TableView<Car> searchTable;
    @FXML
    private TableColumn<Car, String> regColumn;
    @FXML
    private TableColumn<Car, Integer> yearColumn;
    @FXML
    private TableColumn<Car, String> makerColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, String> color1Column;
    @FXML
    private TableColumn<Car, String> color2Column;
    @FXML
    private TableColumn<Car, String> color3Column;
    @FXML
    private TableColumn<Car, Integer> priceColumn;
    @FXML
    private TableColumn<Car, Integer> quantityColumn;
    @FXML
    private TextField searchMakeField;
    @FXML
    private TextField searchModelField;
    @FXML
    private Button searchRegButton;
    @FXML
    private Button searchModelButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField searchRegField;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public void searchByReg() {
        if(!searchRegField.getText().isEmpty()) {
            networkUtil.write("SR#" + searchRegField.getText());
            client.showList(networkUtil, searchTable);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Uh Oh!");
            alert.setContentText("You cannot leave search field blank.");
            alert.showAndWait();
        }
    }
    public void searchByModel() {
        if(!searchMakeField.getText().isEmpty() && !searchModelField.getText().isEmpty()) {
            networkUtil.write("SM#" + searchMakeField.getText() + "#" + searchModelField.getText());
            client.showList(networkUtil, searchTable);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Uh Oh!");
            alert.setContentText("You cannot leave search field blank.");
            alert.showAndWait();
        }
    }
    public void backAction() throws IOException {
        client.showClientViewPage();
    }
}
