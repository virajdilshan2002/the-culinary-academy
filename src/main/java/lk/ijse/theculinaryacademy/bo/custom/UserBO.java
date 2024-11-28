package lk.ijse.theculinaryacademy.bo.custom;

import lk.ijse.theculinaryacademy.bo.SuperBO;
import lk.ijse.theculinaryacademy.dto.UserDTO;

public interface UserBO extends SuperBO {

    void addUser(UserDTO userDTO);
}
