create table jobs
(
    id                  uuid                     not null
        constraint jobs_pk
            primary key,
    company_name        varchar                  not null,
    company_url         varchar                  not null,
    current_job         boolean                  not null,
    company_start_month varchar                  not null,
    company_start_year  varchar                  not null,
    company_end_month   varchar,
    company_end_year    varchar,
    jobData             jsonb                    not null,
    updated_at          timestamp with time zone not null default now()
);

create trigger set_timestamp
    before update
    on jobs
    for each row
execute function trigger_set_timestamp();

comment on table jobs is 'This table will contain all the companies I have been working in a defined time';