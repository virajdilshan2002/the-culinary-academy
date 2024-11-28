package lk.ijse.theculinaryacademy.dao.custom;


import lk.ijse.theculinaryacademy.dao.CrudDAO;
import lk.ijse.theculinaryacademy.entity.Course;

import java.util.List;

public interface CourseDAO extends CrudDAO<Course> {
    List<Course> getAll();

    Course getCourse(String courseDescription);
}
