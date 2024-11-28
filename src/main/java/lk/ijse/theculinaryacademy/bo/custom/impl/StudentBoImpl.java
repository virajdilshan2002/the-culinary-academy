package lk.ijse.theculinaryacademy.bo.custom.impl;

import lk.ijse.theculinaryacademy.bo.custom.StudentBo;
import lk.ijse.theculinaryacademy.dao.DAOFactory;
import lk.ijse.theculinaryacademy.dao.custom.StudentDAO;
import lk.ijse.theculinaryacademy.dto.StudentDTO;
import lk.ijse.theculinaryacademy.entity.Student;

import java.util.ArrayList;
import java.util.List;


public class StudentBoImpl implements StudentBo {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);

    @Override
    public void addStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getId(),
                studentDTO.getName(),
                studentDTO.getAddress(),
                studentDTO.getEmail(),
                studentDTO.getContact(),
                studentDTO.getUser()
        );
        studentDAO.add(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> studentList = studentDAO.getAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : studentList) {
            studentDTOS.add(new StudentDTO(
                    student.getId(),
                    student.getName(),
                    student.getAddress(),
                    student.getEmail(),
                    student.getContact(),
                    student.getUser()
            ));
        }
        return studentDTOS;
    }

    @Override
    public StudentDTO search(String contact) {
        Student student = studentDAO.searchByContact(contact);
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getAddress(),
                student.getEmail(),
                student.getContact(),
                student.getUser()
        );
    }

    @Override
    public boolean update(StudentDTO dto) {
        Student student = new Student(dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getContact(),
                dto.getUser()
        );
        return studentDAO.updateStudent(student);
    }
}
