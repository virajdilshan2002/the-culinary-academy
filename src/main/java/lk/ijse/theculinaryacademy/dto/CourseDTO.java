package lk.ijse.theculinaryacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CourseDTO {
    private int id;
    private String description;
    private String duration;
    private Double price;
}
