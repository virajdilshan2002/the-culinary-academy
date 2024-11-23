module lk.ijse.theculinaryacademy {
    requires javafx.fxml;
    requires com.jfoenix;
    requires javafx.graphics;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.persistence;
    requires java.sql;
    requires javafx.controls;

    exports lk.ijse.theculinaryacademy;
    opens lk.ijse.theculinaryacademy.util to javafx.fxml;
    opens lk.ijse.theculinaryacademy.controller to javafx.fxml;
    opens lk.ijse.theculinaryacademy.model to org.hibernate.orm.core;
}