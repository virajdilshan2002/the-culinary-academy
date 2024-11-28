package lk.ijse.theculinaryacademy.bo;


import lk.ijse.theculinaryacademy.bo.custom.impl.*;

public class BOFactory implements SuperBO{
    private static BOFactory boFactory;
    private static StudentBoImpl studentBo;
    private static CourseBoImpl courseBo;
    private static PlacePaymentBoImpl placePaymentBo;
    private static UserBOImpl userBO;
    private static PaymentBOImpl paymentBO;
    private static DashboardBoImpl dashboardBo;

    private BOFactory(){
        userBO = new UserBOImpl();
        studentBo = new StudentBoImpl();
        courseBo = new CourseBoImpl();
        placePaymentBo = new PlacePaymentBoImpl();
        paymentBO = new PaymentBOImpl();
        dashboardBo = new DashboardBoImpl();
    }

    public static BOFactory getInstance(){
        return boFactory = boFactory == null ? new BOFactory() : boFactory;
    }

    public enum BOType {
        USER,STUDENT,COURSE,PLACEPAYMENT,PAYMENT,DASHBOARD
    }

    public SuperBO getBO(BOType type){
        return switch (type) {
            case PAYMENT -> paymentBO;
            case USER -> userBO;
            case STUDENT -> studentBo;
            case COURSE -> courseBo;
            case PLACEPAYMENT -> placePaymentBo;
            case DASHBOARD -> dashboardBo;
        };
    }

}
