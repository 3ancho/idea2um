# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table todo_post (
  postid                    bigint not null,
  content                   TEXT,
  created_at                timestamp,
  userid                    bigint,
  constraint pk_todo_post primary key (postid))
;

create table todo_user (
  userid                    bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  privilege                 integer,
  since                     timestamp,
  firstname                 varchar(255),
  lastname                  varchar(255),
  twitter_id                varchar(255),
  constraint uq_todo_user_username unique (username),
  constraint pk_todo_user primary key (userid))
;

create sequence todo_post_seq;

create sequence todo_user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists todo_post;

drop table if exists todo_user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists todo_post_seq;

drop sequence if exists todo_user_seq;

