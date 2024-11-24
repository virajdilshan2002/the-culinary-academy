package lk.ijse.theculinaryacademy.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.util.NavigateTo;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.io.IOException;

public class DashboardController {

    public AnchorPane childNode;
    public Label lblCoursesCount;
    public Label lblStudentsCount;
    public Label lblSoldCoursesCount;
    @FXML
    private AnchorPane rootNode;

    public void initialize() {
        getStudentCount();
        getCourseCount();
    }

    private void getCourseCount() {
        long count = 0;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT COUNT(*) FROM Course";
            count = (long) session.createQuery(hql).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        lblCoursesCount.setText(String.valueOf(count));
    }

    public void getStudentCount() {
        long count = 0;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT COUNT(*) FROM Student";
            count = (long) session.createQuery(hql).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        lblCoursesCount.setText(String.valueOf(count));
    }


    @FXML
    void btnAdminClickOnAction(ActionEvent event) {

    }

    @FXML
    void btnCoursesClickOnAction(ActionEvent event) {
        try {
            NavigateTo.children("/lk/ijse/theculinaryacademy/view/courses.fxml",childNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPlaceOrderClickOnAction(ActionEvent event) {
        try {
            NavigateTo.children("/lk/ijse/theculinaryacademy/view/placePayment.fxml",childNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnStudentClickOnAction(ActionEvent event) {
        try {
            NavigateTo.children("/lk/ijse/theculinaryacademy/view/student.fxml",childNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnCloseClickOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void btnProfileClickOnAction(ActionEvent actionEvent) {
    }

    public void btnHomeClickOnAction(ActionEvent actionEvent) {
        try {
            NavigateTo.parent("/lk/ijse/theculinaryacademy/view/dashboard.fxml",rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnLogOutClickOnAction(ActionEvent actionEvent) {
        try {
            NavigateTo.parent("/lk/ijse/theculinaryacademy/view/login.fxml",rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
