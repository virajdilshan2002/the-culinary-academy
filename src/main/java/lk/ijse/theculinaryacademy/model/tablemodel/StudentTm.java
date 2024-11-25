package lk.ijse.theculinaryacademy.model.tablemodel;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StudentTm {
    private int studentId;
    private String fullName;
    private String address;
    private String email;
    private String contact;
    private JFXButton action;
}
