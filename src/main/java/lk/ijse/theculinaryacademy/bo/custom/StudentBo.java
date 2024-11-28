package lk.ijse.theculinaryacademy.bo.custom;


import lk.ijse.theculinaryacademy.bo.SuperBO;
import lk.ijse.theculinaryacademy.dto.StudentDTO;

import java.util.List;

public interface StudentBo extends SuperBO {

    void addStudent(StudentDTO studentDTO);

    List<StudentDTO> getAllStudents();

    StudentDTO search(String contact);

    boolean update(StudentDTO dto);
}
