package lk.ijse.theculinaryacademy.controller;

import javafx.event.ActionEvent;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class StudentController {
    public void initialize() {
    }

    public void btnAddStudentClickOnAction(ActionEvent actionEvent) {
        User user = new User("user","Ijse@1234","admin");
        Student student = new Student(1,"viraj","matara","virajdilshan2019@gmail.com","0741092019",user);

        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(student);
        transaction.commit();
        session.close();
    }
}

