package lk.ijse.theculinaryacademy.view.tablemodel;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CoursesTm {
    private int courseId;
    private String description;
    private String duration;
    private double price;
    private JFXButton action;
}
