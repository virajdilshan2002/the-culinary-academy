package lk.ijse.theculinaryacademy.bo.custom;


import lk.ijse.theculinaryacademy.bo.SuperBO;
import lk.ijse.theculinaryacademy.dto.CourseDTO;

import java.util.List;

public interface CourseBo extends SuperBO {

     void addCourse(CourseDTO course);

     List<CourseDTO> getCourses();
}
