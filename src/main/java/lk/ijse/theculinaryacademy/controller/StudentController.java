package lk.ijse.theculinaryacademy.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.theculinaryacademy.bo.BOFactory;
import lk.ijse.theculinaryacademy.bo.custom.StudentBo;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.dto.StudentDTO;
import lk.ijse.theculinaryacademy.entity.*;
import lk.ijse.theculinaryacademy.view.tablemodel.StudentTm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class StudentController {

    public TextField txtSearchContact;
    public JFXButton btnUpdate;
    @FXML
    private AnchorPane childNode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colFullName;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableView<StudentTm> tblStudents;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFullName;

    User currentUser = LoginController.user;

    StudentDTO searchStudent = null;

    private ObservableList<StudentTm> obList = FXCollections.observableArrayList();

    StudentBo studentBo = (StudentBo) BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);

    public void initialize() {
        setCellValueFactory();
        loadStudentsTable();
    }

    public void btnAddStudentClickOnAction(ActionEvent actionEvent) {
        if (isValid()){
            StudentDTO studentDTO = new StudentDTO(1,txtFullName.getText(),txtAddress.getText(),txtEmail.getText(),txtContact.getText(),currentUser);

            studentBo.addStudent(studentDTO);
            loadStudentsTable();
        }
    }

    private void setCellValueFactory() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    private void loadStudentsTable() {
        tblStudents.getItems().clear();

        //get all courses
        List<StudentDTO> studentList = studentBo.getAllStudents();
        if (studentList == null) {
            return;
        }

        //add courses to the observable list
        for (StudentDTO studentDTO : studentList) {

            //create a delete button for each course
            JFXButton btnDelete = createDeleteButton(studentDTO.getId());

            //create a CoursesTm object for each course
            StudentTm studentTm = new StudentTm(studentDTO.getId(),
                    studentDTO.getName(),
                    studentDTO.getAddress(),
                    studentDTO.getEmail(),
                    studentDTO.getContact(),
                    btnDelete);
            obList.add(studentTm);
        }

        //set the observable list to the table
        tblStudents.setItems(obList);
    }

    private JFXButton createDeleteButton(int id) {
        JFXButton btnDelete = new JFXButton("Delete");
        btnDelete.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-background-radius: 20;");
        btnDelete.setOnAction(event -> {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this student?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                Session session = SessionFactoryConfig.getInstance().getSession();
                Transaction transaction = session.beginTransaction();
                Student student = session.get(Student.class, id);
                session.delete(student);
                transaction.commit();
                session.close();
                loadStudentsTable();
            }
        });
        return btnDelete;
    }

    private boolean isValid() {
        if (txtFullName.getText() == null || txtFullName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Full name cannot be empty").show();
            return false;
        }
        if (txtAddress.getText() == null || txtAddress.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Address cannot be empty").show();
            return false;
        }
        if (txtEmail.getText() == null || txtEmail.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Email cannot be empty").show();
            return false;
        }
        if (txtContact.getText() == null || txtContact.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Enter a valid contact number").show();
            return false;
        }
        return true;
    }

    public void btnSearchStudentClickOnAction(ActionEvent actionEvent) {
        searchStudent = studentBo.search(txtSearchContact.getText());
        if (searchStudent == null){
            new Alert(Alert.AlertType.ERROR,"No student found").show();
            return;
        }
        txtFullName.setText(searchStudent.getName());
        txtAddress.setText(searchStudent.getAddress());
        txtEmail.setText(searchStudent.getEmail());
        txtContact.setText(searchStudent.getContact());

        new Alert(Alert.AlertType.INFORMATION,"Student found").show();
    }

    public void btnUpdateClickOnAction(ActionEvent actionEvent) {
        if (searchStudent == null){
            new Alert(Alert.AlertType.WARNING,"Select student first").show();
            return;
        }

        StudentDTO studentDTO = new StudentDTO(searchStudent.getId(),
                txtFullName.getText(),
                txtAddress.getText(),
                txtEmail.getText(),
                txtContact.getText(),
                currentUser
        );

        try{
            boolean isupdate = studentBo.update(studentDTO);
            if (isupdate){
                new Alert(Alert.AlertType.INFORMATION,"Course updated!").show();
                loadStudentsTable();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"something went wrong ").show();
        }
        clearFields();

    }

    private void clearFields() {
        txtFullName.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtContact.clear();
    }
}

