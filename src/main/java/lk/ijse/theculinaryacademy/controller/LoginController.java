package lk.ijse.theculinaryacademy.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.model.User;
import lk.ijse.theculinaryacademy.util.NavigateTo;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.io.IOException;

public class LoginController {
    public JFXTextField userName;
    public JFXPasswordField password;
    public AnchorPane rootNode;
    public static User user = null;

    public void initialize() {
        userName.requestFocus();
    }

    public void btnLoginClickOnAction(ActionEvent actionEvent) {
        try {
            if (checkCredentials()) {
                NavigateTo.parent("/lk/ijse/theculinaryacademy/view/dashboard.fxml",rootNode);
            }else {
                new Alert(Alert.AlertType.WARNING,"Invalid Credentials").show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkCredentials() {
        Session session = null;
        try {
            session = SessionFactoryConfig.getInstance().getSession();
            user = session.get(User.class, userName.getText());
            if (user != null && user.getPassword().equals(password.getText())) {
                return true;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            userName.clear();
            password.clear();
            session.close();
        }
        return false;
    }


    public void btnRegisterClickOnAction(ActionEvent actionEvent) {
        try {
            NavigateTo.parent("/lk/ijse/theculinaryacademy/view/addUser.fxml",rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void txtUserNameClickOnAction(ActionEvent actionEvent) {
        password.requestFocus();
    }

    public void txtPasswordClickOnAction(ActionEvent actionEvent) {
        btnLoginClickOnAction(actionEvent);
    }
}
