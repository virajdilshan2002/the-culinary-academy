package lk.ijse.theculinaryacademy.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.model.*;
import lk.ijse.theculinaryacademy.model.tablemodel.CoursesTm;
import lk.ijse.theculinaryacademy.model.tablemodel.StudentTm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class StudentController {

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

    private ObservableList<StudentTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        setCellValueFactory();
        loadStudentsTable();
    }

    public void btnAddStudentClickOnAction(ActionEvent actionEvent) {
        if (isValid()){
            Student student = new Student(1,txtFullName.getText(),txtAddress.getText(),txtEmail.getText(),txtContact.getText(),currentUser);

            Session session = SessionFactoryConfig.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
            session.close();

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
        Session session = SessionFactoryConfig.getInstance().getSession();

        //get all courses
        List<Student> studentList = session.createQuery("FROM Student", Student.class).getResultList();

        //add courses to the observable list
        for (Student student : studentList) {

            //create a delete button for each course
            JFXButton btnDelete = createDeleteButton(student.getId());

            //create a CoursesTm object for each course
            StudentTm studentTm = new StudentTm(student.getId(),
                    student.getName(),
                    student.getAddress(),
                    student.getEmail(),
                    student.getContact(),
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
}

