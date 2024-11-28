package lk.ijse.theculinaryacademy.dao.custom.impl;


import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.dao.custom.StudentCourseDetailDAO;
import lk.ijse.theculinaryacademy.entity.StudentCourseDetail;
import org.hibernate.Session;

import java.util.List;

public class StudentCourseDetailDAOImpl implements StudentCourseDetailDAO {

    @Override
    public void add(StudentCourseDetail entity) {

    }

    @Override
    public List<StudentCourseDetail> getAll() {
        return List.of();
    }

    @Override
    public void addTransaction(Session session, StudentCourseDetail obj) {
        session.save(obj);
    }

    @Override
    public List<StudentCourseDetail> searchPayments(String contact) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        return session.createQuery("FROM StudentCourseDetail WHERE student.contact = :student_contact", StudentCourseDetail.class)
                .setParameter("student_contact",contact)
                .getResultList();
    }
}
