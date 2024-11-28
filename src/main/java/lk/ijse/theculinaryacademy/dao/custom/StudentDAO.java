package lk.ijse.theculinaryacademy.dao.custom;


import lk.ijse.theculinaryacademy.dao.CrudDAO;
import lk.ijse.theculinaryacademy.entity.Student;


public interface StudentDAO extends CrudDAO<Student> {

    Student searchByContact(String contact);

    boolean updateStudent(Student student);
}
