create table company_positions
(
    id          uuid             not null
        constraint company_positions_pk primary key,
    company     uuid
        constraint company_companies_id_fk
            references companies not null,
    position    varchar          not null,
    description text             not null,
    current_job boolean                  not null,
    start_date  timestamp with time zone not null,
    end_date    timestamp with time zone,
    updated_at  timestamp with time zone not null default now()
);

create index company_index
    on company_positions (company);

create trigger set_timestamp
    before update on company_positions
    for each row
execute function trigger_set_timestamp();


comment on table company_positions is 'This table will contain all the positions inside a company in a defined time';