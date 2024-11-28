package lk.ijse.theculinaryacademy.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.theculinaryacademy.bo.BOFactory;
import lk.ijse.theculinaryacademy.bo.custom.UserBO;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.dto.UserDTO;
import lk.ijse.theculinaryacademy.entity.User;
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

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public void initialize(){
        ObservableList<String> jobRoleList = FXCollections.observableArrayList("Admin", "User");
        choiceJobRole.setItems(jobRoleList);
        choiceJobRole.setValue(jobRoleList.get(1));
    }

    public void btnRegisterClickOnAction(ActionEvent actionEvent) {
        if (txtPw.getText().equals(txtRePw.getText())){
            UserDTO userDTO = new UserDTO(txtUserName.getText(),txtPw.getText(), (String) choiceJobRole.getValue());

            userBO.addUser(userDTO);

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
