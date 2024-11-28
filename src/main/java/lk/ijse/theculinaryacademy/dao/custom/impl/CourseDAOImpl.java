package lk.ijse.theculinaryacademy.dao.custom.impl;

import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.dao.custom.CourseDAO;
import lk.ijse.theculinaryacademy.dto.CourseDTO;
import lk.ijse.theculinaryacademy.entity.Course;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CourseDAOImpl implements CourseDAO {


    @Override
    public void add(Course entity) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Course> getAll() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        return session.createQuery("FROM Course", Course.class).getResultList();
    }

    @Override
    public Course getCourse(String courseDescription) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        return session.createQuery("FROM Course WHERE description = :desc", Course.class)
                .setParameter("desc", courseDescription)
                .uniqueResult();
    }

    @Override
    public Course searchById(int id) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        return session.get(Course.class, id);
    }

    @Override
    public boolean updateCourse(Course course) {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            transaction = session.beginTransaction();

            session.update(course);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Failed to update course: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
