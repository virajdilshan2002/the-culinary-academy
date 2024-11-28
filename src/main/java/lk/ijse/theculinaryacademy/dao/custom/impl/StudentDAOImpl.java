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
    public Student searchByContact(String contact) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        return session.createQuery("FROM Student WHERE contact=:student_contact", Student.class)
                .setParameter("student_contact", contact)
                .uniqueResult();
    }

    @Override
    public boolean updateStudent(Student student) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();

            session.update(student);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Failed to update student: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
