drop table if exists member;
create table member
(
    id    serial
        constraint member_id_pk primary key,
    email varchar(255),
    role  varchar(255)
);
-- INSERT INTO member (email, role) VALUES ('yfr.huang@gmail.com', 'USER');

drop table if exists wallet;
create table wallet
(
    id      serial
        constraint wallet_id_pk primary key,
    email   varchar(255),
    address varchar(255),
    balance numeric(19, 4)
);
-- INSERT INTO wallet (email, address, balance) VALUES ('yfr.huang@gmail.com', '', 0.0000);

drop table if exists wallet_transaction;
create table wallet_transaction
(
    id             serial
        constraint wallet_transaction_id_pk primary key,
    uuid           varchar(255),
    order_uuid     varchar(255),
    wallet_id      integer,
    type           varchar(255),
    before_balance numeric(19, 4),
    amount         numeric(19, 4),
    after_balance  numeric(19, 4),
    create_date    bigint
);

drop table if exists game;
create table game
(
    id              serial
        constraint game_id_pk primary key,
    uuid            varchar(255),
    owner           varchar(255),
    guest_name      varchar(255),
    host_name       varchar(255),
    sport_type      varchar(255),
    end_submit_date bigint
);

drop table if exists bet_item;
create table bet_item
(
    id      serial
        constraint bet_item_id_pk primary key,
    uuid    varchar(255),
    game_id integer,
    owner   varchar(255),
    type    varchar(255),
    value   varchar(255),
    odds    numeric(19, 4),
    status  varchar(255)
);

drop table if exists sport_order;
create table sport_order
(
    id           serial
        constraint sport_order_id_pk primary key,
    uuid         varchar(255),
    email        varchar(255),
    combination  varchar(255),
    multiple     integer,
    total_amount integer,
    win_amount   numeric(19, 4),
    status       varchar(255),
    create_date  bigint
);

drop table if exists slip;
create table slip
(
    id          serial
        constraint slip_id_pk primary key,
    uuid        varchar(255),
    order_id    integer,
    bet_item_id integer,
    odds        numeric(19, 4)
);
