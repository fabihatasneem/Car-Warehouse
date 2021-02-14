package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ClientViewController {
    Client client;
    NetworkUtil networkUtil;

    @FXML
    private Button searchButton;
    @FXML
    private Button buyButton;
    @FXML
    private Button refreshButton;
    @FXML
    private TableView<Car> viewerTable;
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

    public void searchAction() {
        try {
            client.showSearchPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buyAction() {
        try {
            Car car = viewerTable.getSelectionModel().getSelectedItem();
            networkUtil.write("B#" + car.getId());
            Object o = networkUtil.read();
            if (o != null) {
                String received = (String) o;
                if (received.equals("SUCCESS")) {
                    viewerTable.refresh();
                    showAllCars();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("SUCCESS");
                    alert.setHeaderText("Congratulations!");
                    alert.setContentText("Car bought Successfully. Have a nice day!");
                    alert.showAndWait();
                } else if (received.equals("FAILURE")) {
                    viewerTable.refresh();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Uh Oh!");
                    alert.setContentText("This car isn't available!");
                    alert.showAndWait();
                    viewerTable.refresh();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Something went wrong! Please try again");
                    alert.showAndWait();
                }
            } else
                System.out.println("Object null");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("UH OH!");
            alert.setContentText("You have to choose your car first.");
            alert.showAndWait();
        }
    }

    public void showAllCars() {
        networkUtil.write("V");
        client.showList(networkUtil, viewerTable);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public void refreshAction() {
        showAllCars();
    }
}