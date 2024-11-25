package lk.ijse.theculinaryacademy.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.model.Course;
import lk.ijse.theculinaryacademy.tablemodel.CoursesTm;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

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
    private TableView<CoursesTm> tblCourses;

    @FXML
    private TextField txtCourseDesc;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtSearchCourseId;

    ObservableList<CoursesTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        setCellValueFactory();
        loadCoursesTable();
    }

    private void loadCoursesTable() {
        tblCourses.getItems().clear();
        Session session = SessionFactoryConfig.getInstance().getSession();

        //get all courses
        List<Course> courseList = session.createQuery("FROM Course", Course.class).getResultList();

        //add courses to the table
        for (Course course : courseList) {
            JFXButton btnDelete = new JFXButton("Delete");
            CoursesTm coursesTm = new CoursesTm(course.getId(),
                    course.getDescription(),
                    course.getDuration(),
                    course.getPrice(),
                    btnDelete);
            obList.add(coursesTm);
        }
        tblCourses.setItems(obList);
    }

    public void btnAddCourseClickOnAction(ActionEvent actionEvent) {
        if (isValid()){
            Course course = new Course(1,txtCourseDesc.getText(), txtDuration.getText(), Double.parseDouble(txtPrice.getText()));

            Session session = SessionFactoryConfig.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
            session.close();

            loadCoursesTable();
            new Alert(Alert.AlertType.INFORMATION, "Course added successfully").show();
        }
    }

    public void btnCourseSearchClickOnAction(ActionEvent actionEvent) {
    }

    private void setCellValueFactory() {
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    private boolean isValid() {
        if (txtCourseDesc.getText() == null || txtCourseDesc.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Course description cannot be empty").show();
            return false;
        }
        if (txtDuration.getText() == null || txtDuration.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Course duration cannot be empty").show();
            return false;
        }
        if (txtPrice.getText() == null || txtPrice.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Price cannot be empty").show();
            return false;
        }
        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            if (price < 1000) {
                new Alert(Alert.AlertType.WARNING, "Price must be 1000/= or higher").show();
                return false;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Price must be a valid number").show();
            return false;
        }
        return true;
    }

}
