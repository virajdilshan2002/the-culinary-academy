package lk.ijse.theculinaryacademy.dto;

import lk.ijse.theculinaryacademy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {
    private int id;
    private String name;
    private String address;
    private String email;
    private String contact;
    private User user;
}
