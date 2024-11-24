package lk.ijse.theculinaryacademy.controller;

import javafx.event.ActionEvent;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.model.Course;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CoursesController {
    public void btnAddCourseClickOnAction(ActionEvent actionEvent) {
        Course course = new Course(1,"Diploma in Software Engineering", "6 Months", 50000.00);

        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(course);
        transaction.commit();
        session.close();
    }
}
