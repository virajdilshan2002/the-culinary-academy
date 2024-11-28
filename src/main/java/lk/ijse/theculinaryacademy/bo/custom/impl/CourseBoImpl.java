package lk.ijse.theculinaryacademy.bo.custom.impl;

import lk.ijse.theculinaryacademy.bo.custom.CourseBo;
import lk.ijse.theculinaryacademy.dao.DAOFactory;
import lk.ijse.theculinaryacademy.dao.custom.CourseDAO;
import lk.ijse.theculinaryacademy.dto.CourseDTO;
import lk.ijse.theculinaryacademy.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseBoImpl implements CourseBo {

    CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.COURSE);

    @Override
    public void addCourse(CourseDTO courseDTO) {
        Course course = new Course(courseDTO.getId(), courseDTO.getDescription(), courseDTO.getDuration(), courseDTO.getPrice());
        courseDAO.add(course);
    }

    @Override
    public List<CourseDTO> getCourses() {
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
    public CourseDTO search(int id) {
        Course course = courseDAO.searchById(id);
        return new CourseDTO(course.getId(),
                course.getDescription(),
                course.getDuration(),
                course.getPrice()
        );
    }

    @Override
    public boolean update(CourseDTO dto) {
        Course course = new Course(dto.getId(),
                dto.getDescription(),
                dto.getDuration(),
                dto.getPrice()
        );
        return courseDAO.updateCourse(course);
    }
}
