package lk.ijse.theculinaryacademy.dao;

import lk.ijse.theculinaryacademy.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private static StudentDAOImpl studentDAO;
    private static CourseDAOImpl courseDAO;
    private static PaymentDAOImpl paymentDAO;
    private static StudentCourseDetailDAOImpl studentCourseDetailDAO;
    private static UserDAOImpl userDAO;
    private DAOFactory(){
        studentDAO = new StudentDAOImpl();
        courseDAO = new CourseDAOImpl();
        paymentDAO = new PaymentDAOImpl();
        studentCourseDetailDAO = new StudentCourseDetailDAOImpl();
        userDAO = new UserDAOImpl();
    }

    public static DAOFactory getInstance(){
        return daoFactory = daoFactory == null ? new DAOFactory() : daoFactory;
    }

    //enum
    public enum DAOType{
        USER,STUDENT,COURSE,PAYMENT,STUDENT_COURSE_DETAIL
    }

    public CrudDAO getDAO(DAOType type){
        return switch (type){
            case USER -> userDAO;
            case STUDENT -> studentDAO;
            case COURSE -> courseDAO;
            case PAYMENT -> paymentDAO;
            case STUDENT_COURSE_DETAIL -> studentCourseDetailDAO;
        };
    }
}
