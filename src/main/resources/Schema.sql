CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


create table orgs
(
  name varchar(128) not null
    constraint orgs_pkey
    primary key,
  location varchar(64),
  raw_location varchar(128)
)
;

create index orgs_location_index
  on orgs (location)
;

create index orgs_raw_location_index
  on orgs (raw_location)
;

create table users
(
  name varchar(128) not null
    constraint users_pkey
    primary key,
  location varchar(64),
  company varchar(64),
  raw_location varchar(128)
)
;

create index users_location_index
  on users (location)
;

create index users_raw_location_index
  on users (raw_location)
;

create table repos
(
  name varchar(128) not null
    constraint repos_pkey
    primary key
)
;

create table languages
(
  name varchar(64) not null
    constraint languages_pkey
    primary key
)
;

create table repo_languages
(
  id uuid default uuid_generate_v4() not null
    constraint repo_languages_pkey
    primary key,
  repo varchar(64) not null
    constraint repo_languages_repos_name_fk
    references repos
    on update cascade on delete cascade,
  language varchar(64) not null
    constraint repo_languages_languages_name_fk
    references languages
    on update cascade on delete cascade
)
;

create index repo_languages_repo_name_index
  on repo_languages (repo)
;

create index repo_languages_language_index
  on repo_languages (language)
;

create table repo_users
(
  id uuid default uuid_generate_v4() not null
    constraint repo_users_pkey
    primary key,
  "user" varchar(128) not null
    constraint repo_users_users_name_fk
    references users
    on update cascade on delete cascade,
  repo varchar(128) not null
    constraint repo_users_repos_name_fk
    references repos
    on update cascade on delete cascade
)
;

create index repo_users_user_index
  on repo_users ("user")
;

create index repo_users_repo_index
  on repo_users (repo)
;

create table org_repo
(
  id uuid default uuid_generate_v4() not null
    constraint org_repo_pkey
    primary key,
  repo varchar(128) not null
    constraint org_repo_repos_name_fk
    references repos
    on update cascade on delete cascade,
  name varchar(128) not null
    constraint org_repo_orgs_name_fk
    references orgs
    on update cascade on delete cascade
)
;

create index org_repo_repo_index
  on org_repo (repo)
;

create index org_repo_name_index
  on org_repo (name)
;

create table locations
(
  name varchar(64) not null
    constraint locations_pkey
    primary key
)
;

alter table orgs
  add constraint orgs_locations_name_fk
foreign key (location) references locations
;

alter table users
  add constraint users_locations_name_fk
foreign key (location) references locations
on update cascade on delete set null
;

create table repo_it
(
  id serial not null
    constraint repo_it_pkey
    primary key,
  last_pos bigint
)
;

create table repo_emails
(
  id uuid default uuid_generate_v4() not null
    constraint repo_emails_pkey
    primary key,
  name varchar(128),
  email varchar(128) not null,
  repo varchar(128) not null
    constraint repo_emails_repos_name_fk
    references repos
)
;

create index repo_emails_name_index
  on repo_emails (name)
;

create index repo_emails_email_index
  on repo_emails (email)
;

create index repo_emails_repo_index
  on repo_emails (repo)
;