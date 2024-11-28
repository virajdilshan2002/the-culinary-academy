package lk.ijse.theculinaryacademy.dao.custom;


import lk.ijse.theculinaryacademy.dao.CrudDAO;
import lk.ijse.theculinaryacademy.entity.Payment;
import org.hibernate.Session;

public interface PaymentDAO extends CrudDAO<Payment> {
    void addTransaction(Session session, Payment obj);

}
