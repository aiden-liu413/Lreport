--独立表放开
create table IF NOT EXISTS sys_param
(
    id          VARCHAR(32)  not null,
    key         VARCHAR(64)  null,
    value       VARCHAR      null,
    description VARCHAR(128) null,
    constraint PK_SYS_PARAM primary key (id)
);