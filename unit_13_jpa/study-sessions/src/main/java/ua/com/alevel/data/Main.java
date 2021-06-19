package ua.com.alevel.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.com.alevel.data.exception.StudySessionDataLayerException;
import ua.com.alevel.data.exception.StudySessionDataNotFoundException;
import ua.com.alevel.data.model.dto.save.*;
import ua.com.alevel.data.services.*;
import ua.com.alevel.data.services.jpa.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.function.Supplier;


public class Main {
    public static void main(String[] args) throws StudySessionDataNotFoundException, StudySessionDataLayerException {
        Configuration configuration = new Configuration().configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            Supplier<EntityManager> supplier = session::getSession;

            TopicService topicService = new JPATopicService(supplier);
            CourseService courseService = new JPACourseService(supplier);
            LessonService lessonService = new JPALessonService(supplier);
            TeacherService teacherService = new JPATeacherService(supplier);
            StudentService studentService = new JPAStudentService(supplier);
            AssessmentService assessmentService = new JPAAssessmentService(supplier);
            GroupService groupService = new JPAGroupService(supplier);

            courseService.create(new CourseRecordSave(
                    "Java",
                    60
            ));

            groupService.create(new GroupRecordSave(
                    "Nix-4"
            ));
            groupService.create(new GroupRecordSave(
                    "Nix-3"
            ));

            topicService.create(new TopicSaveRecord(
                    "Final test",
                    "This is final test to check your knowledge",
                    courseService.getById(4L).get()

            ));

            LocalDate dateStart = LocalDate.parse("2022-02-02");
            LocalDate dateEnd = LocalDate.parse("2022-02-02");
            lessonService.create(new LessonRecordSave(
                    dateEnd.atTime(8,20).toInstant(ZoneOffset.UTC),
                    dateEnd.atTime(10,20).toInstant(ZoneOffset.UTC)
            ));


            teacherService.create(new TeacherRecordSave(
                    "Ivan",
                    "Ivanov",
                    29,
                    "ivan@gmail.com",
                    "5 years"
            ));

            studentService.create(new StudentRecordSave(
                    "Daria",
                    "Dorenska",
                    20,
                    "daria@gmail.com",
                    groupService.getById(7L).get()));
            studentService.create(new StudentRecordSave(
                    "Artem",
                    "Artemov",
                    20,
                    "artem@gmail.com",
                    groupService.getById(7L).get()));
            studentService.create(new StudentRecordSave(
                    "Yevgeny",
                    "Golubev",
                    30,
                    "yevgeny@gmail.com",
                    groupService.getById(7L).get()));
            studentService.create(new StudentRecordSave(
                    "Boris",
                    "Borisov",
                    27,
                    "boris@gmail.com",
                    groupService.getById(8L).get()));

            studentService.create(new StudentRecordSave(
                    "Artur",
                    "Arturov",
                    27,
                    "arturartur@gmail.com",
                    groupService.getById(8L).get()
            ));

            assessmentService.create(new AssessmentRecordSave(
                    100,
                    "very well",
                    studentService.getById(1L).get(),
                    lessonService.getById(1L).get()
            ));
            assessmentService.create(new AssessmentRecordSave(
                    85,
                    "good job",
                    studentService.getById(1L).get(),
                    lessonService.getById(1L).get()
            ));
            assessmentService.create(new AssessmentRecordSave(
                    20,
                    "bad result",
                    studentService.getById(2L).get(),
                    lessonService.getById(1L).get()
            ));
            assessmentService.create(new AssessmentRecordSave(
                    60,
                    "normal",
                    studentService.getById(3L).get(),
                    lessonService.getById(1L).get()
            ));
            assessmentService.create(new AssessmentRecordSave(
                    100,
                    "perfect",
                    studentService.getById(4L).get(),
                    lessonService.getById(1L).get()
            ));
            assessmentService.create(new AssessmentRecordSave(
                    2,
                    "low result",
                    studentService.getById(5L).get(),
                    lessonService.getById(1L).get()
            ));

            lessonService.addTeacherById(1L, 1L);

            topicService.addLessonById(1L, 1L);

            courseService.addGroup(1L, 1L);
            courseService.addGroup(1L, 2L);

            courseService.addTeacher(1L, 1L);

            courseService.addTopic(1L, 1L);

            teacherService.defineTheMostSuccessGroupByTeacherId(1L);
            studentService.findNextLessonByStudentId(1L);

            session.close();

        }

    }
}
