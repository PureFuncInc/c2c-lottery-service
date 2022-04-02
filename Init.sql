drop table if exists member;
create table member
(
    id    serial
        constraint member_id_pk primary key,
    email varchar(255),
    role  varchar(255)
);

drop table if exists wallet;
create table wallet
(
    id      serial
        constraint wallet_id_pk primary key,
    email   varchar(255),
    address varchar(255),
    balance numeric(19, 4)
);

drop table if exists game;
create table game
(
    id         serial
        constraint game_id_pk primary key,
    uuid       varchar(255),
    guest_name varchar(255),
    host_name  varchar(255),
    sport_type varchar(255),
    start_date varchar(255),
    end_date   bigint
);

drop table if exists bet_item;
create table bet_item
(
    id      serial
        constraint bet_item_id_pk primary key,
    game_id integer,
    type    varchar(255),
    value   varchar(255),
    balance numeric(19, 4)
);
