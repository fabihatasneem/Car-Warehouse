package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class Client extends Application {
    public static NetworkUtil networkUtil;
    Stage window;
    String serverAddress = "127.0.0.1";
    int serverPort = 44444;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        showWelcomePage();
    }

    public void showWelcomePage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("welcome.fxml"));
        Parent root = loader.load();
        WelcomeController controller = loader.getController();
        controller.setClient(this);
        controller.setNetworkUtil(networkUtil);
        window.setTitle("CAR WAREHOUSE");
        window.setScene(new Scene(root));
        window.show();
    }

    public void showSearchPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("search.fxml"));
        Parent root = loader.load();
        SearchController controller = loader.getController();
        controller.setClient(this);
        controller.setNetworkUtil(networkUtil);
        window.setTitle("CAR WAREHOUSE");
        Scene scene = new Scene(root);
        window.setScene(scene);
        scene.getStylesheets().add(Client.class.getResource("tableStyle.css").toExternalForm());
        window.show();
    }

    public void showClientViewPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("clientView.fxml"));
        Parent root = loader.load();
        ClientViewController controller = loader.getController();
        controller.setClient(this);
        controller.setNetworkUtil(networkUtil);
        controller.showAllCars();
        window.setTitle("CAR WAREHOUSE");
        Scene scene = new Scene(root);
        window.setScene(scene);
        scene.getStylesheets().add(Client.class.getResource("tableStyle.css").toExternalForm());
        window.show();
    }

    public void showManufacturerViewPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("manufacturerView.fxml"));
        Parent root = loader.load();
        ManufacturerViewController controller = loader.getController();
        controller.setClient(this);
        controller.setNetworkUtil(networkUtil);
        controller.showAllCars();
        window.setTitle("CAR WAREHOUSE");
        Scene scene = new Scene(root);
        window.setScene(scene);
        scene.getStylesheets().add(Client.class.getResource("tableStyle.css").toExternalForm());
        window.show();
    }

    public void showLoginPage(String username) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setUsername(username);
        controller.setClient(this);
        controller.setNetworkUtil(networkUtil);
        window.setTitle("CAR WAREHOUSE");
        window.setScene(new Scene(root));
        window.show();
    }

    public void showAddPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("add.fxml"));
        Parent root = loader.load();
        AddController controller = loader.getController();
        controller.setClient(this);
        controller.setNetworkUtil(networkUtil);
        window.setTitle("CAR WAREHOUSE");
        window.setScene(new Scene(root));
        window.show();
    }

    static void showList(NetworkUtil networkUtil, TableView<Car> table) {
        Task<ObservableList<Car>> carTask = new Task<ObservableList<Car>>() {
            @Override
            protected ObservableList<Car> call() throws Exception {
                return FXCollections.observableArrayList((List<Car>) networkUtil.read());
            }
        };
        table.itemsProperty().bind(carTask.valueProperty());
        new Thread(carTask).start();
    }
}
