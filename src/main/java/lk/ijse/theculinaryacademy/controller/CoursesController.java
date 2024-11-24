package lk.ijse.theculinaryacademy.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.model.Course;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CoursesController {
    @FXML
    private AnchorPane childNode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colCourseName;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableView<?> tblCourses;

    @FXML
    private TextField txtCourseDesc;

    @FXML
    private TextField txtCourseName;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtSearchCourseId;
    public void btnAddCourseClickOnAction(ActionEvent actionEvent) {
        Course course = new Course(1,txtCourseDesc.getText(), txtDuration.getText(), Double.parseDouble(txtPrice.getText()));

        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(course);
        transaction.commit();
        session.close();
    }

    public void btnCourseSearchClickOnAction(ActionEvent actionEvent) {
    }
}
