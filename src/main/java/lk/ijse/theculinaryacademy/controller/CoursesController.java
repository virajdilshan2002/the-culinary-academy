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
import lk.ijse.theculinaryacademy.model.Course;
import lk.ijse.theculinaryacademy.model.tablemodel.CoursesTm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

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

    private void loadCoursesTable() {
        tblCourses.getItems().clear();
        Session session = SessionFactoryConfig.getInstance().getSession();

        //get all courses
        List<Course> courseList = session.createQuery("FROM Course", Course.class).getResultList();

        //add courses to the observable list
        for (Course course : courseList) {

            //create a delete button for each course
            JFXButton btnDelete = createDeleteButton(course.getId());

            //create a CoursesTm object for each course
            CoursesTm coursesTm = new CoursesTm(course.getId(),
                    course.getDescription(),
                    course.getDuration(),
                    course.getPrice(),
                    btnDelete);
            obList.add(coursesTm);
        }

        //set the observable list to the table
        tblCourses.setItems(obList);
    }

    private JFXButton createDeleteButton(int id) {
        JFXButton btnDelete = new JFXButton("Delete");
        btnDelete.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-background-radius: 20;");
        btnDelete.setOnAction(event -> {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this course?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                Session session = SessionFactoryConfig.getInstance().getSession();
                Transaction transaction = session.beginTransaction();
                Course course = session.get(Course.class, id);
                session.delete(course);
                transaction.commit();
                session.close();
                loadCoursesTable();
            }
        });
        return btnDelete;
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
