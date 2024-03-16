create table jobs
(
    id           uuid                     not null
        constraint jobs_pk
            primary key,
    company_name varchar                  not null,
    description  varchar                  not null,
    role         varchar                  not null,
    company_url  varchar                  not null,
    start_date   date                     not null,
    end_date     date,
    job_data     varchar                  not null,
    updated_at   timestamp with time zone not null default now()
);

comment on table jobs is 'This table will contain all the companies I have been working in a defined time';