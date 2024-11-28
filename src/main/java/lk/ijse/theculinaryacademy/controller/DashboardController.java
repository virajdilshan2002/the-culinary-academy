package lk.ijse.theculinaryacademy.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.theculinaryacademy.bo.BOFactory;
import lk.ijse.theculinaryacademy.bo.custom.DashboardBO;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.dto.StudentCourseDetailDTO;
import lk.ijse.theculinaryacademy.entity.Payment;
import lk.ijse.theculinaryacademy.view.tablemodel.RecentPaymentsTm;
import lk.ijse.theculinaryacademy.util.NavigateTo;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardController {

    public AnchorPane childNode;
    public Label lblCoursesCount;
    public Label lblStudentsCount;
    public Label lblSoldCoursesCount;
    public TableView<RecentPaymentsTm> tblRecentPayments;
    public TableColumn<?,?> colPaymentId;
    public TableColumn<?,?> colStudent;
    public TableColumn<?,?> colCourse;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colContact;
    public TableColumn<?,?> colTotal;
    public Label lblGreeting;
    public Label lblTime;
    public Label lblDate;
    public Label lblUser;
    public TextField txtContactSearch;
    @FXML
    private AnchorPane rootNode;

    DashboardBO dashboardBO = (DashboardBO) BOFactory.getInstance().getBO(BOFactory.BOType.DASHBOARD);

    public void initialize() {
        getStudentCount();
        getCourseCount();
        getSoldCourseCount();
        loadGreeting();
        setUserName();
        setDateTime();
        setCellValueFactory();
        loadRecentPayments();
    }

    private void loadRecentPayments() {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            // Query to fetch the most recent payments (limit to, say, 10 records)
            String hql = "FROM Payment p ORDER BY p.orderDateTime DESC";
            List<Payment> payments = session.createQuery(hql, Payment.class)
                    .setMaxResults(10)
                    .list();

            ObservableList<RecentPaymentsTm> recentPayments = FXCollections.observableArrayList();

            for (Payment payment : payments) {
                RecentPaymentsTm tm = new RecentPaymentsTm(
                        payment.getId(),
                        payment.getStudentCourseDetail().getStudent().getName(),
                        payment.getStudentCourseDetail().getCourse().getDescription(),
                        payment.getStudentCourseDetail().getRegistrationDate(),
                        payment.getTotal()
                );
                recentPayments.add(tm);
            }

            tblRecentPayments.setItems(recentPayments);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("paymentId"));
        colStudent.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("student"));
        colCourse.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("course"));
        colDate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("paymentDate"));
        colTotal.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("total"));
    }

    private void setDateTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        lblDate.setText(dtf.format(now));
    }

    private void setUserName() {
        lblUser.setText(LoginController.user.getUsername());
    }

    private void loadGreeting() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        String status;

        if (hour >= 5 && hour < 12) {
            status = "Good Morning!";
        } else if (hour >= 12 && hour < 18) {
            status = "Good Afternoon!";
        } else {
            status = "Good Evening!";
        }
        lblGreeting.setText(status);
    }

    private void getSoldCourseCount() {
        long count = 0;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String hql = "SELECT COUNT(*) FROM Payment";
            count = (long) session.createQuery(hql).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        lblSoldCoursesCount.setText(String.valueOf(count));
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
        lblStudentsCount.setText(String.valueOf(count));
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

    public void btnPaymentSearchClickOnAction(ActionEvent actionEvent) {
        List<StudentCourseDetailDTO> List = dashboardBO.search(txtContactSearch.getText());
        if (List == null) {
            new Alert(Alert.AlertType.ERROR, "No such payment found").show();
            return;
        }
        tblRecentPayments.getItems().clear();
        ObservableList<RecentPaymentsTm> obList = FXCollections.observableArrayList();

        for (StudentCourseDetailDTO dto : List) {
            RecentPaymentsTm tm = new RecentPaymentsTm(
                    dto.getStuCouDetailId(),
                    dto.getStudent().getName(),
                    dto.getCourse().getDescription(),
                    dto.getRegistrationDate(),
                    dto.getCourse().getPrice()
            );
            obList.add(tm);
        }

        tblRecentPayments.setItems(obList);
    }
}
