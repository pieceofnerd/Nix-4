create table if not exists location
(
    id bigint unsigned auto_increment,
    name varchar(100) not null,
    constraint id
        unique (id),
    constraint name
        unique (name)
);


create table if not exists problems
(
    id bigint unsigned auto_increment,
    from_id bigint unsigned not null,
    to_id bigint unsigned not null,
    constraint id
        unique (id),
    constraint fk_3
        foreign key (from_id) references location (id),
    constraint fk_4
        foreign key (to_id) references location (id)
);


create table if not exists routes
(
    id bigint unsigned auto_increment,
    from_id bigint unsigned not null,
    to_id bigint unsigned not null,
    cost int null,
    constraint id
        unique (id),
    constraint fk_1
        foreign key (from_id) references location (id),
    constraint fk_2
        foreign key (to_id) references location (id)
);


create table if not exists solutions
(
    problem_id bigint unsigned not null
        primary key,
    cost int null,
    constraint fk_5
        foreign key (problem_id) references problems (id)
);

create table if not exists location
(
    id bigint unsigned auto_increment,
    name varchar(100) not null,
    constraint id
        unique (id),
    constraint name
        unique (name)
);


create table if not exists problems
(
    id bigint unsigned auto_increment,
    from_id bigint unsigned not null,
    to_id bigint unsigned not null,
    constraint id
        unique (id),
    constraint fk_3
        foreign key (from_id) references location (id),
    constraint fk_4
        foreign key (to_id) references location (id)
);


create table if not exists routes
(
    id bigint unsigned auto_increment,
    from_id bigint unsigned not null,
    to_id bigint unsigned not null,
    cost int null,
    constraint id
        unique (id),
    constraint fk_1
        foreign key (from_id) references location (id),
    constraint fk_2
        foreign key (to_id) references location (id)
);


create table if not exists solutions
(
    problem_id bigint unsigned not null
        primary key,
    cost int null,
    constraint fk_5
        foreign key (problem_id) references problems (id)
);

