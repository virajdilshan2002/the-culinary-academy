package lk.ijse.theculinaryacademy.bo.custom.impl;


import lk.ijse.theculinaryacademy.bo.custom.UserBO;
import lk.ijse.theculinaryacademy.dao.DAOFactory;
import lk.ijse.theculinaryacademy.dao.custom.UserDAO;
import lk.ijse.theculinaryacademy.dto.UserDTO;
import lk.ijse.theculinaryacademy.entity.User;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public void addUser(UserDTO userDTO) {
        User user = new User(userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getJobRole());
        userDAO.add(user);
    }
}
