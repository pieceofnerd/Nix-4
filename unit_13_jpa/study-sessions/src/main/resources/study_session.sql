create table assessment
(
    id         int auto_increment
        primary key,
    assessment int          not null,
    comment    varchar(500) null,
    student_id int          not null,
    lesson_id  int          not null,
    constraint lesson_id2
        foreign key (lesson_id) references lesson (id)
            on update cascade on delete cascade,
    constraint student_id2
        foreign key (student_id) references student (id)
);

create index lesson_id_idx
    on assessment (lesson_id);

create index student_id_idx
    on assessment (student_id);


create table course
(
    id           int auto_increment
        primary key,
    course_name  varchar(50) not null,
    hours_number int         not null,
    constraint course_name_UNIQUE
        unique (course_name)
);


create table lesson
(
    id           int auto_increment
        primary key,
    lesson_start timestamp not null,
    lesson_end   timestamp not null,
    teacher_id   int       null,
    constraint teacher_id_l
        foreign key (teacher_id) references teacher (id)
);

create index teacher_id_l_idx
    on lesson (teacher_id);


create table student
(
    id             int auto_increment
        primary key,
    first_name     varchar(50)  not null,
    last_name      varchar(50)  not null,
    age            int          not null,
    email          varchar(100) not null,
    employees_type varchar(45)  null,
    group_id       bigint       null
);


create table teacher
(
    id              int auto_increment
        primary key,
    first_name      varchar(100) not null,
    last_name       varchar(45)  not null,
    age             int          not null,
    email           varchar(100) not null,
    work_experience varchar(500) null,
    constraint email_UNIQUE
        unique (email)
);


create table student_group
(
    id          int auto_increment
        primary key,
    title_group varchar(60) not null
);

create table topic
(
    id          int auto_increment
        primary key,
    title       varchar(100) not null,
    description varchar(500) null,
    course_id   int          null,
    constraint UK_gcjr91g0oo8ak5qnjb882o1l3
        unique (title),
    constraint title_UNIQUE
        unique (title),
    constraint FKtktaeeogyyjfv5ylr4r06ig1l
        foreign key (course_id) references course (id)
);

create index course_id_t_idx
    on topic (course_id);