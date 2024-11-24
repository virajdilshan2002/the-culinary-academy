package lk.ijse.theculinaryacademy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int id;
    @Column(name = "payment_method")
    private String method;
    @Column(name = "payment_date_time")
    private Timestamp orderDateTime;
    @Column(name = "balance")
    private double balance = 0;
    @Column(name = "total")
    private double total;

    @ManyToOne
    @JoinColumn(name = "stu_cou_detail_id")
    private StudentCourseDetail studentCourseDetail;

}


