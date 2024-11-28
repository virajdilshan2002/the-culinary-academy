package lk.ijse.theculinaryacademy.bo.custom;


import lk.ijse.theculinaryacademy.bo.SuperBO;
import lk.ijse.theculinaryacademy.dto.PaymentDTO;
import lk.ijse.theculinaryacademy.dto.StudentCourseDetailDTO;
import lk.ijse.theculinaryacademy.view.tablemodel.RecentPaymentsTm;

import java.sql.SQLException;
import java.util.List;

public interface DashboardBO extends SuperBO {


    List<StudentCourseDetailDTO> search(String contact);

    List<PaymentDTO> getRecentPayments();
}
