package lk.ijse.theculinaryacademy.config;


import lk.ijse.theculinaryacademy.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryConfig {
    private static SessionFactoryConfig factoryConfig;
    private final SessionFactory sessionFactory;

    private SessionFactoryConfig(){
        sessionFactory = new MetadataSources(new StandardServiceRegistryBuilder()
                .loadProperties("hibernate.properties").build())
                .addAnnotatedClass(Student.class)
                .getMetadataBuilder()
                .build().buildSessionFactory();

    }
    public static SessionFactoryConfig getInstance(){
        return (factoryConfig == null)
                ? factoryConfig = new SessionFactoryConfig()
                : factoryConfig;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
