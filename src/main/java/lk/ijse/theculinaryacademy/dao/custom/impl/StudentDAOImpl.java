package lk.ijse.theculinaryacademy.dao.custom.impl;


import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.dao.custom.StudentDAO;
import lk.ijse.theculinaryacademy.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public void add(Student entity) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Student> getAll() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        return session.createQuery("FROM Student", Student.class).getResultList();
    }

    @Override
    public Student getStudent(String contact) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        return session.createQuery("FROM Student WHERE contact=:student_contact", Student.class)
                .setParameter("student_contact", contact)
                .uniqueResult();
    }
}
