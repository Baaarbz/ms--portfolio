create table companies
(
    id          uuid                     not null
        constraint companies_pk
            primary key,
    name        varchar                  not null,
    url         varchar                  not null,
    current_job boolean                  not null,
    start_date  timestamp with time zone not null,
    end_date    timestamp with time zone not null,
    created_at  timestamp with time zone not null
);

comment on table companies is 'This table will contain all the companies I have been working in a defined time';