package lk.ijse.theculinaryacademy.dto;

import lk.ijse.theculinaryacademy.entity.Course;
import lk.ijse.theculinaryacademy.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StudentCourseDetailDTO {
    private int stuCouDetailId;
    private Date registrationDate;
    private StudentDTO student;
    private CourseDTO course;

}
