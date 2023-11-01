INSERT INTO companies (id, name, url, current_job, start_month, start_year, end_month, end_year)
VALUES ('7cd9a4dc-ff61-48da-af2c-c839d6572b3a',
        'Adevinta',
        'https://adevinta.com/',
        true,
        '10',
        '2022',
        null,
        null);

INSERT INTO company_positions (id, company, position, description, current_job, start_month, start_year, end_month,
                               end_year)
VALUES ('87bce598-0d9d-4ba5-b96e-81b2438baa62',
        '7cd9a4dc-ff61-48da-af2c-c839d6572b3a',
        'Backend Engineer',
        'Collaborate developing different solutions in different marketplaces like: Milanuncios, Leboncoin, Kleinanzeigen, mobile.de... using Kotlin, Go and Java (17). Use different technologies as Kafka, AWS, Datadog (metrics, alerts, monitoring), Optimizely (for feature flagging, test A/B), Segment (to monitor user behaviors), Docker and Kubernetes. I am used to give light talks to share knowledge to other mates about new tech, or solutions adopted in our team. Design patterns like DDD, Hexagonal architecture, good practices as TDD, SOLID, pair programming, and very used to work using async flows. Create documentation',
        true,
        '10',
        '2022',
        null,
        null);


INSERT INTO companies (id, name, url, current_job, start_month, start_year, end_month, end_year)
VALUES ('86c144f4-0e1d-408b-b274-106ef8939b4b',
        'adidas',
        'https://adidas.com/',
        false,
        '4',
        '2022',
        '10',
        '2022');

INSERT INTO company_positions (id, company, position, description, current_job, start_month, start_year, end_month,
                               end_year)
VALUES ('ce20fa4a-598b-4794-8c73-df240bc3c811',
        '86c144f4-0e1d-408b-b274-106ef8939b4b',
        'Software Developer',
        'Work in a international team developing components in AWS and APIs with Spring Boot, research new solutions (PoC) to improve performance and use new AWS tools, also use terraform to automatize the deployment of the components in AWS. Monitoring (logs, metrics and traces) with Lenses (for Kafka), AWS Cloudwatch, Instana, Grafana... between others. Create/update documentation in Confluence.',
        false,
        '4',
        '2022',
        '10',
        '2022');
