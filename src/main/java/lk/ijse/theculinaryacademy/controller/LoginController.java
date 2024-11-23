package lk.ijse.theculinaryacademy.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.theculinaryacademy.util.NavigateTo;

import java.io.IOException;

public class LoginController {
    public JFXTextField userName;
    public JFXPasswordField password;
    public AnchorPane rootNode;

    public void btnLoginClickOnAction(ActionEvent actionEvent) {
        try {
            NavigateTo.parent("/lk/ijse/theculinaryacademy/view/dashboard.fxml",rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
