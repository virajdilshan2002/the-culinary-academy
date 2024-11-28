package lk.ijse.theculinaryacademy.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.theculinaryacademy.bo.BOFactory;
import lk.ijse.theculinaryacademy.bo.custom.PlacePaymentBo;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.dto.CourseDTO;
import lk.ijse.theculinaryacademy.dto.PaymentDTO;
import lk.ijse.theculinaryacademy.dto.StudentCourseDetailDTO;
import lk.ijse.theculinaryacademy.dto.StudentDTO;
import lk.ijse.theculinaryacademy.entity.*;
import lk.ijse.theculinaryacademy.view.tablemodel.CartTm;
import lk.ijse.theculinaryacademy.util.NavigateTo;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class PlacePaymentController {

    public JFXButton btnStudentSearch;
    public JFXButton btnCourseSelect;
    public TextField txtStudentSearch;
    @FXML
    private AnchorPane childNode;

    @FXML
    private ChoiceBox<String> choiceCourses;

    @FXML
    private ChoiceBox<String> choicePaymentMethod;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private TableView<CartTm> tblCart;

    @FXML
    private TextField txtCourseDesc;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtPaymentAmount;

    @FXML
    private TextField txtPrice;

    User currentUser = LoginController.user;

    Date currentDate = new Date(System.currentTimeMillis());
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

    CourseDTO selectedCourse = null;
    StudentDTO selectedStudent = null;

    double total = 00.00;

    PlacePaymentBo placePaymentBo = (PlacePaymentBo) BOFactory.getInstance().getBO(BOFactory.BOType.PLACEPAYMENT);

    public void initialize() {
        setCellValueFactory();
        loadCourses();
        loadPaymentMethods();
    }

    private void loadCourses() {
        List<CourseDTO> courses = placePaymentBo.getAllCourses();
        // Add courses to the ChoiceBox
        choiceCourses.getItems().clear(); // Clear existing items if any
        for (CourseDTO course : courses) {
            choiceCourses.getItems().add(course.getDescription());
        }
        // Optionally set a default selected value
        if (!courses.isEmpty()) {
            choiceCourses.setValue(courses.get(0).getDescription()); // Set the first course as default
        }
    }

    private void setCellValueFactory() {
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    private void loadPaymentMethods() {
        choicePaymentMethod.getItems().add("Cash");
        choicePaymentMethod.getItems().add("Advance");
        choicePaymentMethod.setValue("Cash");
    }

    @FXML
    void btnAddToCartClickOnAction(ActionEvent event) {
        if (selectedCourse == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a course first.").show();
            return;
        }

        total += selectedCourse.getPrice();
        lblTotalPrice.setText(String.valueOf(total));

        JFXButton remove = createRemoveButton(selectedCourse);

        CartTm cartTm = new CartTm(selectedCourse.getDescription(), remove);
        tblCart.getItems().add(cartTm);
    }

    private JFXButton createRemoveButton(CourseDTO course) {
        JFXButton remove = new JFXButton("Remove");
        remove.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius:20;");
        remove.setOnAction(event -> {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove this course?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                // Remove the course from the cart
                tblCart.getItems().clear();
                total = 00.00;
                lblTotalPrice.setText(String.valueOf(total));
                choiceCourses.setDisable(false);
                btnCourseSelect.setDisable(false);
            }
        });
        return remove;
    }

    @FXML
    void btnPayClickOnAction(ActionEvent event) {
        if (checkDetails()){
            StudentCourseDetailDTO studentCourseDetailDTO = new StudentCourseDetailDTO(1, currentDate, selectedStudent, selectedCourse);
            double balance = selectedCourse.getPrice() - Double.parseDouble(txtPaymentAmount.getText());
            PaymentDTO paymentDTO = new PaymentDTO(1, choicePaymentMethod.getValue(), currentTimestamp, balance, total, studentCourseDetailDTO);

            placePaymentBo.placePayment(studentCourseDetailDTO,paymentDTO);
            new Alert(Alert.AlertType.INFORMATION, "Payment successful.").show();
            reloadChildNode();
        }

    }

    private void reloadChildNode() {
        try {
            NavigateTo.children("/lk/ijse/theculinaryacademy/view/placePayment.fxml",childNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkDetails() {
        if (selectedStudent == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a student first.").show();
            return false;
        }
        if (selectedCourse == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a course first.").show();
            return false;
        }
        if (choicePaymentMethod.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment method first.").show();
            return false;
        }
        return true;
    }

    @FXML
    void btnStudentSearchClickOnAction(ActionEvent event) {
            // Retrieve the student from the database based on the student ID
            selectedStudent = placePaymentBo.getStudent(txtStudentSearch.getText());

            if (selectedStudent == null) {
                new Alert(Alert.AlertType.WARNING, "Student not found.").show();
                return;
            }

            new Alert(Alert.AlertType.INFORMATION, "Student : "+ selectedStudent.getName() +", selected successfully.").show();
            txtStudentSearch.setDisable(true);
            btnStudentSearch.setDisable(true);

    }

    @FXML
    public void btnCourseSelectClickOnAction(ActionEvent actionEvent) {
        // Get the selected course description from the ChoiceBox
        String selectedCourseDescription = choiceCourses.getValue();

        if (selectedCourseDescription == null || selectedCourseDescription.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a course first.").show();
            return;
        }

        // Retrieve the course from the database based on the description
        selectedCourse = placePaymentBo.getCourse(selectedCourseDescription);

        if (selectedCourse == null) {
            new Alert(Alert.AlertType.WARNING, "Course not found.").show();
            return;
        }

        txtCourseDesc.setText(selectedCourse.getDescription());
        txtDuration.setText(String.valueOf(selectedCourse.getDuration()));
        txtPrice.setText(String.valueOf(selectedCourse.getPrice()));

        new Alert(Alert.AlertType.INFORMATION, "Course : "+selectedCourse.getDescription() +", details retrieved successfully.").show();
        choiceCourses.setDisable(true);
        btnCourseSelect.setDisable(true);
    }

}
