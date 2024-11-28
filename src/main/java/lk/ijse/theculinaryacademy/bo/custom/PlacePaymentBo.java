package lk.ijse.theculinaryacademy.bo.custom;


import lk.ijse.theculinaryacademy.bo.SuperBO;
import lk.ijse.theculinaryacademy.dto.CourseDTO;
import lk.ijse.theculinaryacademy.dto.PaymentDTO;
import lk.ijse.theculinaryacademy.dto.StudentCourseDetailDTO;
import lk.ijse.theculinaryacademy.dto.StudentDTO;
import lk.ijse.theculinaryacademy.entity.Course;
import lk.ijse.theculinaryacademy.entity.Student;

import java.util.List;

public interface PlacePaymentBo extends SuperBO {

    List<CourseDTO> getAllCourses();

    StudentDTO getStudent(String contact);

    CourseDTO getCourse(String selectedCourseDescription);

    void placePayment(StudentCourseDetailDTO studentCourseDetailDTO, PaymentDTO paymentDTO);
}
