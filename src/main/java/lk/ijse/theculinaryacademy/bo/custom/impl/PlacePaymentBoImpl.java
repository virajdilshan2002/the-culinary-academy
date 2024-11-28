package lk.ijse.theculinaryacademy.bo.custom.impl;

import lk.ijse.theculinaryacademy.bo.custom.PlacePaymentBo;
import lk.ijse.theculinaryacademy.config.SessionFactoryConfig;
import lk.ijse.theculinaryacademy.dao.DAOFactory;
import lk.ijse.theculinaryacademy.dao.custom.CourseDAO;
import lk.ijse.theculinaryacademy.dao.custom.PaymentDAO;
import lk.ijse.theculinaryacademy.dao.custom.StudentCourseDetailDAO;
import lk.ijse.theculinaryacademy.dao.custom.StudentDAO;
import lk.ijse.theculinaryacademy.dto.CourseDTO;
import lk.ijse.theculinaryacademy.dto.PaymentDTO;
import lk.ijse.theculinaryacademy.dto.StudentCourseDetailDTO;
import lk.ijse.theculinaryacademy.dto.StudentDTO;
import lk.ijse.theculinaryacademy.entity.Course;
import lk.ijse.theculinaryacademy.entity.Payment;
import lk.ijse.theculinaryacademy.entity.Student;
import lk.ijse.theculinaryacademy.entity.StudentCourseDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class PlacePaymentBoImpl implements PlacePaymentBo {

    CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.COURSE);
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);
    StudentCourseDetailDAO studentCourseDetailDAO = (StudentCourseDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT_COURSE_DETAIL);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseDAO.getAll();

        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course course : courses) {
            courseDTOS.add(new CourseDTO(course.getId(),
                    course.getDescription(),
                    course.getDuration(),
                    course.getPrice()
            ));
        }
        return courseDTOS;
    }

    @Override
    public StudentDTO getStudent(String contact) {
        Student student = studentDAO.getStudent(contact);
        return new StudentDTO(student.getId(),
                student.getName(),
                student.getAddress(),
                student.getEmail(),
                student.getContact(),
                student.getUser()
        );
    }

    @Override
    public CourseDTO getCourse(String courseDescription) {
        Course course = courseDAO.getCourse(courseDescription);
        return new CourseDTO(course.getId(),
                course.getDescription(),
                course.getDuration(),
                course.getPrice()
        );
    }

    @Override
    public void placePayment(StudentCourseDetailDTO studentCourseDetailDTO, PaymentDTO paymentDTO) {
        StudentDTO studentDTO = studentCourseDetailDTO.getStudent();
        CourseDTO courseDTO = studentCourseDetailDTO.getCourse();

        Student student = new Student(studentDTO.getId(),
                studentDTO.getName(),
                studentDTO.getAddress(),
                studentDTO.getEmail(),
                studentDTO.getContact(),
                studentDTO.getUser()
        );

        Course course = new Course(courseDTO.getId(),
                courseDTO.getDescription(),
                courseDTO.getDuration(),
                courseDTO.getPrice()
        );


        StudentCourseDetail studentCourseDetail = new StudentCourseDetail(studentCourseDetailDTO.getStuCouDetailId(),
                studentCourseDetailDTO.getRegistrationDate(),
                student,
                course
        );


        Payment payment = new Payment(paymentDTO.getId(),
                paymentDTO.getMethod(),
                paymentDTO.getOrderDateTime(),
                paymentDTO.getBalance(),
                paymentDTO.getTotal(),
                studentCourseDetail
        );

        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        studentCourseDetailDAO.addTransaction(session, studentCourseDetail);
        paymentDAO.addTransaction(session, payment);
        transaction.commit();
        session.close();
    }
}
