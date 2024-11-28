package lk.ijse.theculinaryacademy.dao.custom.impl;


import lk.ijse.theculinaryacademy.dao.custom.PaymentDAO;
import lk.ijse.theculinaryacademy.entity.Payment;
import org.hibernate.Session;

import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public void add(Payment entity) {

    }

    @Override
    public List<Payment> getAll() {
        return List.of();
    }

    @Override
    public void addTransaction(Session session, Payment obj) {
        session.save(obj);
    }
}
