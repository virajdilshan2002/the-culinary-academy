package lk.ijse.theculinaryacademy.dao.custom;


import lk.ijse.theculinaryacademy.dao.CrudDAO;
import lk.ijse.theculinaryacademy.entity.StudentCourseDetail;
import org.hibernate.Session;

import java.util.List;

public interface StudentCourseDetailDAO extends CrudDAO<StudentCourseDetail> {

    void addTransaction(Session session, StudentCourseDetail obj);

    List<StudentCourseDetail> searchPayments(String contact);
}
