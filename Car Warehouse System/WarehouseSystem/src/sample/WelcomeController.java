package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;

public class WelcomeController {
    Client client;
    NetworkUtil networkUtil;

    @FXML
    private Button enterButton;
    @FXML
    private TextField nameField;

    @FXML
    void userTypeCheckAction() {
        try {
            if(!nameField.getText().isEmpty()) {
                String s = "U#" + nameField.getText();
                networkUtil.write(s);
                Object o = networkUtil.read();
                if(o!=null) {
                    String received = (String) o;
                    if (received.equals("MANUFACTURER")) {
                        client.showLoginPage(nameField.getText());
                    } else if (received.equals("VIEWER")) {
                        client.showClientViewPage();
                    }else if (received.equals("ALREADY LOGGED IN")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Uh Oh!");
                        alert.setContentText("You are already logged in from another window!");
                        alert.showAndWait();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Uh Oh!");
                        alert.setContentText("User doesn't exist!");
                        alert.showAndWait();
                    }
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Uh Oh!");
                alert.setContentText("Please provide your username!");
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
}
