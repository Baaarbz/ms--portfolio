create table abouts
(
    id         uuid                     not null
        constraint abouts_pk
            primary key,
    about      text                     not null,
    updated_at timestamp with time zone not null default now()
);

create trigger set_timestamp
    before update
    on abouts
    for each row
execute function trigger_set_timestamp();

comment on table abouts is 'This table will contain the about section';

insert into abouts
values ('7cd9a4dc-ff61-48da-af2c-c839d6572b3a', '<p className="mb-4">
                Back in 2018, I was studying mechanic engineering and, in the middle of the year I decided to change to
                Software development, one of my best ever decisions I have ever taken. Nowadays, I&apos;ve had the
                privilege of building software for world-wide companies like
                <a className="font-medium text-slate-200 hover:text-teal-300 focus-visible:text-teal-300"
                   href="https://www.bancosantander.es/" target="_blank" rel="noreferrer">
                    &ensp;Banco Santander
                </a>
                ,
                <a className="font-medium text-slate-200 hover:text-teal-300 focus-visible:text-teal-300"
                   href="https://adevinta.com/our-brands" target="_blank" rel="noreferrer">
                    &ensp;Adevinta
                </a>
                &ensp;(
                <a className="font-medium text-slate-200 hover:text-teal-300 focus-visible:text-teal-300"
                   href="https://www.milanuncios.es/" target="_blank" rel="noreferrer">
                    Milanuncios
                </a>
                ,
                <a
                    className="font-medium text-slate-200 hover:text-teal-300 focus-visible:text-teal-300"
                    href="https://www.leboncoin.fr/" target="_blank" rel="noreferrer">
                    &ensp;Leboncoin
                </a>
                ,
                <a className="font-medium text-slate-200 hover:text-teal-300 focus-visible:text-teal-300"
                   href="https://www.kleinanzeigen.de/" target="_blank" rel="noreferrer">
                    &ensp;Kleinanzeigen
                </a>
                &ensp;and other marketplaces inside the company),
                <a className="font-medium text-slate-200 hover:text-teal-300 focus-visible:text-teal-300"
                   href="https://adidas.com/" target="_blank" rel="noreferrer">
                    &ensp;adidas
                </a>, and also public projects for spanish government.
            </p>
            <p className="mb-4">
                In my free time I like to expand my knowledge learning new techs, design patterns, and also planning to
                start collaborating to open source projects!
            </p>
            <p>
                When I&apos;m not at the computer, I’m usually practicing some sport like rock climbing, hiking, karting
                or hanging out with my friends enjoying some typical spanish &quot;tapas&quot;.
            </p>')