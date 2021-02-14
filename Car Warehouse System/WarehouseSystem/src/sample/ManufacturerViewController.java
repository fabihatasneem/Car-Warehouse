package sample;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ManufacturerViewController implements Initializable {
    Client client;
    NetworkUtil networkUtil;

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;

    @FXML
    private TableView<Car> manufacturerTable;
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

    public void showAllCars() {
        networkUtil.write("V2");
        client.showList(networkUtil, manufacturerTable);
    }

    public void addNewCarAction() throws IOException {
        client.showAddPage();
    }

    public void deleteCarAction() throws IOException {
        Car car = manufacturerTable.getSelectionModel().getSelectedItem();
        if (manufacturerTable.getSelectionModel().getSelectedItems().isEmpty()){
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("ERROR");
            alert2.setHeaderText("ERROR!");
            alert2.setContentText("Please select a car first.");
            alert2.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING");
            alert.setHeaderText("WAIT!");
            alert.setContentText("Are you sure you want to delete this car?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                manufacturerTable.getItems().removeAll(manufacturerTable.getSelectionModel().getSelectedItems());
                networkUtil.write("D#" + car.getId());
                Object o = networkUtil.read();
                if (o != null) {
                    String received = (String) o;
                    if (received.equals("SUCCESS")) {
                        client.showManufacturerViewPage();
                    } else if (received.equals("FAILURE")) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("ERROR");
                        alert2.setHeaderText("Uh Oh!");
                        alert2.setContentText("The car might be deleted already! Please refresh your page.");
                        alert2.showAndWait();
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("ERROR");
                        alert2.setHeaderText("Something went wrong! Please try again");
                        alert2.showAndWait();
                    }
                }
            }
        }
    }

    public void refreshAction() {
        networkUtil.write("V2");
        client.showList(networkUtil, manufacturerTable);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manufacturerTable.setEditable(true);
        regColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        regColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> event) -> {
            Car car = event.getTableView().getItems().get(event.getTablePosition().getRow());
            if (event.getNewValue() != null) {
                car.setRegistration(event.getNewValue());
                saveToDatabase(car);
            }
        });
        yearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        yearColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, Integer> event) -> {
            Car car = event.getTableView().getItems().get(event.getTablePosition().getRow());
            if (event.getNewValue() != null) {
                car.setYear(event.getNewValue());
                saveToDatabase(car);
            }
        });
        makerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        makerColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> event) -> {
            Car car = event.getTableView().getItems().get(event.getTablePosition().getRow());
            if (event.getNewValue() != null) {
                car.setMake(event.getNewValue());
                saveToDatabase(car);
            }
        });
        modelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelColumn.setOnEditCommit((TableColumn.CellEditEvent<Car, String> event) -> {
            Car car = event.getTableView().getItems().get(event.getTablePosition().getRow());
            if (event.getNewValue() != null) {
                car.setModel(event.getNewValue());
                saveToDatabase(car);
            }
        });
        color1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        color1Column.setOnEditCommit((TableColumn.CellEditEvent<Car, String> event) -> {
            Car car = event.getTableView().getItems().get(event.getTablePosition().getRow());
            car.setColor1(event.getNewValue());
            saveToDatabase(car);
        });
        color2Column.setCellFactory(TextFieldTableCell.forTableColumn());
        color2Column.setOnEditCommit((TableColumn.CellEditEvent<Car, String> event) -> {
            Car car = event.getTableView().getItems().get(event.getTablePosition().getRow());
            car.setColor2(event.getNewValue());
            saveToDatabase(car);
        });
        color3Column.setCellFactory(TextFieldTableCell.forTableColumn());
        color3Column.setOnEditCommit((TableColumn.CellEditEvent<Car, String> event) -> {
            Car car = event.getTableView().getItems().get(event.getTablePosition().getRow());
            car.setColor3(event.getNewValue());
            saveToDatabase(car);
        });
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        priceColumn.setOnEditCommit(event -> {
            Car car = event.getTableView().getItems().get(event.getTablePosition().getRow());
            if (event.getNewValue() != null) {
                car.setPrice(event.getNewValue());
                saveToDatabase(car);
            }
        });
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityColumn.setOnEditCommit(event -> {
            Car car = event.getTableView().getItems().get(event.getTablePosition().getRow());
            if (event.getNewValue() != null) {
                car.setQuantity(event.getNewValue());
                saveToDatabase(car);
            }
        });
    }

    private void saveToDatabase(Car car) {
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                networkUtil.write(car);
                Object o = networkUtil.read();
                if (o != null) {
                    String received = (String) o;
                    if (received.equals("SUCCESS")) {
                        manufacturerTable.refresh();
                    } else if (received.equals("FAILURE")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Uh Oh!");
                        alert.setContentText("Something went wrong. Couldn't Save Data!");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Something went wrong!");
                        alert.showAndWait();
                    }
                } else
                    System.out.println("Object null");
                return true;
            }
        };
        task.setOnSucceeded(e -> {
            if (task.getValue()) {
                manufacturerTable.refresh();
            }
        });
        new Thread(task).start();
    }
}
