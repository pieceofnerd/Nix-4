package ua.com.alevel.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.com.alevel.data.services.StudentService;
import ua.com.alevel.data.services.jpa.JPAStudentService;

import javax.persistence.EntityManager;
import java.util.function.Supplier;


public class Main {
    public static void main(String[] args)   {
        Configuration configuration = new Configuration().configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            Supplier<EntityManager> supplier = session::getSession;


            StudentService studentService= new JPAStudentService(supplier);
            studentService.findNextLessonByStudentId(6L);

            session.close();

        }

    }
}
