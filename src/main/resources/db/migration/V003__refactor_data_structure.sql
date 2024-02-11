truncate table jobs;

alter table jobs
    drop column company_start_month;
alter table jobs
    drop column company_start_year;
alter table jobs
    drop column company_end_month;
alter table jobs
    drop column company_end_year;
alter table jobs
    drop column current_job;
alter table jobs
    rename column jobdata to job_data;

drop trigger set_timestamp on jobs;

alter table jobs
    add end_date date;

alter table jobs
    add start_date date not null;
