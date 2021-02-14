package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    Client client;
    NetworkUtil networkUtil;
    String username;

    @FXML
    private Button submitButton;
    @FXML
    private PasswordField passwordField;

    @FXML
    void loginAction() {
        try {
            if(!passwordField.getText().isEmpty()) {
                String s = "P#" + username + "#" + passwordField.getText();
                networkUtil.write(s);
                Object o = networkUtil.read();
                if (o != null) {
                    String received = (String) o;
                    if (received.equals("SUCCESS")) {
                        client.showManufacturerViewPage();
                    } else if (received.equals("FAILURE")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Incorrect Password!");
                        alert.setContentText("Please check your password again.");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Uh Oh!");
                        alert.setContentText("Something went wrong. Please check again.");
                        alert.showAndWait();
                    }
                } else
                    System.out.println("Object is null");
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Uh Oh!");
                alert.setContentText("You have to provide your password for verification.");
                alert.showAndWait();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
