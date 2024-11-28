package lk.ijse.theculinaryacademy.view.tablemodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RecentPaymentsTm {
    private int paymentId;
    private String student;
    private String course;
    private Date paymentDate;
    private double total;
}
