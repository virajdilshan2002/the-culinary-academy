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
    requires javafx.base;

    exports lk.ijse.theculinaryacademy;
    opens lk.ijse.theculinaryacademy.util to javafx.fxml;
    opens lk.ijse.theculinaryacademy.controller to javafx.fxml;
    opens lk.ijse.theculinaryacademy.entity to org.hibernate.orm.core;
    opens lk.ijse.theculinaryacademy.view.tablemodel to javafx.base;
}