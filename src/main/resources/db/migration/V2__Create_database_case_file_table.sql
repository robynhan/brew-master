create table case_file (
    id bigint(20) not null auto_increment,
    folder_id bigint(20) not null,
    name varchar(100) not null,
    content varchar(1000) ,
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp,
    primary key(id)
) ENGINE=InnoDB default charset=utf8;
