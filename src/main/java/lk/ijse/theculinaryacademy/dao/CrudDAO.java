package lk.ijse.theculinaryacademy.dao;

import lk.ijse.theculinaryacademy.entity.Student;
import lk.ijse.theculinaryacademy.entity.StudentCourseDetail;
import org.hibernate.Session;

import java.util.List;

public interface CrudDAO<T> {

    void add(T entity);

    List<T> getAll();

}
