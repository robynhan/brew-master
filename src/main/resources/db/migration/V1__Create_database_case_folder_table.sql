create table case_folder (
    id bigint(20) not null auto_increment,
    name varchar(100) not null,
    parent varchar(100),
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp,
    primary key(id)
) ENGINE=InnoDB default charset=utf8;

--INSERT INTO case_folder(name) values('root');