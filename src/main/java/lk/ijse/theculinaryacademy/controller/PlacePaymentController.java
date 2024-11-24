package lk.ijse.theculinaryacademy.controller;

import javafx.event.ActionEvent;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.sql.Timestamp;

public class PlacePaymentController {
    public void btnPayClickOnAction(ActionEvent actionEvent) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Date currentDate = new Date(System.currentTimeMillis());

        User user = new User("user","Ijse@1234","admin");
        Course course = new Course(1,"Diploma in Software Engineering", "6 Months", 50000.00);
        Student student = new Student(1,"viraj","matara","virajdilshan2019@gmail.com","0741092019",user);

        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        StudentCourseDetail studentCourseDetail = new StudentCourseDetail(1, currentDate, student, course);
        session.save(studentCourseDetail);
        Payment payment = new Payment(1, "Cash", currentTimestamp, 0, 50000, studentCourseDetail);
        session.save(payment);
        transaction.commit();
        session.close();
    }
}
