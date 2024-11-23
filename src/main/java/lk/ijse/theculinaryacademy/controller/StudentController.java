package lk.ijse.theculinaryacademy.controller;

import javafx.event.ActionEvent;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StudentController {
    public void initialize() {
    }

    public void btnAddStudentClickOnAction(ActionEvent actionEvent) {
        Student student = new Student(1,"viraj","colombo","virajdilshan2019@gmail.com","0711092019");
        //1. SAVE
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(student);
        transaction.commit();
        session.close();
    }
}

