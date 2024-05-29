CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    text TEXT,
    favorite BOOLEAN,
    data_post DATE
);

INSERT INTO post (title, text, favorite, data_post) VALUES
    ('Agora é oficial: o Windows 11 está vindo', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Nam vitae finibus ante. Duis lobortis tellus vel diam fringilla, eu ullamcorper ex iaculis.', true, '2022-01-05'),
    ('Tim Berners-Lee vai leiloar código-fonte da web', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Nam vitae finibus ante. Duis lobortis tellus vel diam fringilla, eu ullamcorper ex iaculis. Praesent et auctor justo.', false, '2023-11-05'),
    ('Tem Firefox novo no pedaço e você vai querer migrar', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est.', true, '2021-01-05'),
    ('John McAfee, criador do antivírus McAfee, morre', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Nam vitae finibus ante. Duis lobortis tellus vel diam fringilla, eu ullamcorper ex iaculis. ', false, '2023-08-12'),
    ('Cbum, o fisiculturista que está colecionando medalhas', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Nam vitae finibus ante. Duis lobortis tellus vel diam fringilla, eu ullamcorper ex iaculis. ', true, '2023-10-21');