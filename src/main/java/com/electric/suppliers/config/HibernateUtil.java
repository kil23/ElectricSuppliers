//package com.electric.suppliers.config;
//
//import com.electric.suppliers.pojo.Suppliers;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.cfg.Environment;
//import org.hibernate.service.ServiceRegistry;
//
//import java.util.Properties;
//
//public class HibernateUtil {
//    private static SessionFactory sessionFactory;
//    public static SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
//            try {
//                Configuration configuration = new Configuration();
//                // Hibernate settings equivalent to hibernate.cfg.xml's properties
//                Properties settings = new Properties();
//                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
//                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/testdatabase");
//                settings.put(Environment.USER, "root");
//                settings.put(Environment.PASS, "root123");
//                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
//                settings.put(Environment.SHOW_SQL, "false");
//                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//                settings.put(Environment.HBM2DDL_AUTO, "create");
//                configuration.setProperties(settings);
//                configuration.addAnnotatedClass(Suppliers.class);
//                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                        .applySettings(configuration.getProperties()).build();
//                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return sessionFactory;
//    }
//}
