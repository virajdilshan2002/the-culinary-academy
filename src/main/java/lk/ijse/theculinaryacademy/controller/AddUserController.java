package lk.ijse.theculinaryacademy.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.model.User;
import lk.ijse.theculinaryacademy.util.NavigateTo;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class AddUserController {
    public ChoiceBox choiceJobRole;
    public TextField txtRePw;
    public TextField txtPw;
    public TextField txtUserName;
    public AnchorPane rootNode;

    public void initialize(){
        ObservableList<String> jobRoleList = FXCollections.observableArrayList("Admin", "User");
        choiceJobRole.setItems(jobRoleList);
        choiceJobRole.setValue(jobRoleList.get(1));
    }

    public void btnRegisterClickOnAction(ActionEvent actionEvent) {
        if (txtPw.getText().equals(txtRePw.getText())){
            User user = new User(txtUserName.getText(),txtPw.getText(), (String) choiceJobRole.getValue());
            Session userSaveSession = SessionFactoryConfig.getInstance().getSession();
            Transaction transaction = userSaveSession.beginTransaction();
            userSaveSession.save(user);
            transaction.commit();
            userSaveSession.close();
            btnBackToLoginClickOnAction(actionEvent);
        }else {
            new Alert(Alert.AlertType.ERROR,"Password Mismatch").show();
            clearFields();
        }

    }

    private void clearFields() {
        txtPw.clear();
        txtRePw.clear();
    }

    public void btnBackToLoginClickOnAction(ActionEvent actionEvent) {
        try {
            NavigateTo.parent("/lk/ijse/theculinaryacademy/view/login.fxml",rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
