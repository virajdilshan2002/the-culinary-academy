package lk.ijse.theculinaryacademy.bo.custom.impl;


import lk.ijse.theculinaryacademy.bo.custom.DashboardBO;
import lk.ijse.theculinaryacademy.dao.DAOFactory;
import lk.ijse.theculinaryacademy.dao.custom.StudentCourseDetailDAO;
import lk.ijse.theculinaryacademy.dto.CourseDTO;
import lk.ijse.theculinaryacademy.dto.StudentCourseDetailDTO;
import lk.ijse.theculinaryacademy.dto.StudentDTO;
import lk.ijse.theculinaryacademy.entity.StudentCourseDetail;

import java.util.ArrayList;
import java.util.List;

public class DashboardBoImpl implements DashboardBO {

    StudentCourseDetailDAO studentCourseDetailDAO = (StudentCourseDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT_COURSE_DETAIL);

    @Override
    public List<StudentCourseDetailDTO> search(String contact) {
        List<StudentCourseDetail> list = studentCourseDetailDAO.searchPayments(contact);
        List<StudentCourseDetailDTO> dtos = new ArrayList<>();

        for (StudentCourseDetail detail : list) {
            dtos.add(new StudentCourseDetailDTO(detail.getStuCouDetailId(),
                    detail.getRegistrationDate(),
                    new StudentDTO(detail.getStudent().getId(), detail.getStudent().getName(), detail.getStudent().getAddress(), detail.getStudent().getEmail(), detail.getStudent().getContact(), detail.getStudent().getUser()),
                    new CourseDTO(detail.getCourse().getId(),detail.getCourse().getDescription(),detail.getCourse().getDuration(),detail.getCourse().getPrice())
            ));
        }

        return dtos;
    }
}
