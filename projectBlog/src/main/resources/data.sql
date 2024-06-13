CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    text TEXT,
    favorite BOOLEAN,
    data_post DATE
);

INSERT INTO post (title, text, favorite, data_post) VALUES
        ('Apple lança novo iPhone 15', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Nam vitae finibus ante.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Nam vitae finibus ante.', false, '2024-06-01'),
        ('Google anuncia novas funcionalidades no Android 14', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis lobortis tellus vel diam fringilla, eu ullamcorper ex iaculis.', false, '2024-06-05'),
        ('Meta revela novas ferramentas para o metaverso', 'Praesent et auctor justo. Lorem ipsum dolor sit amet, consectetur adipiscing elit.Praesent et auctor justo. Lorem ipsum dolor sit amet, consectetur adipiscing elit.Praesent et auctor justo. Lorem ipsum dolor sit amet, consectetur adipiscing elit.', false, '2024-05-28'),
        ('Tesla apresenta modelo de carro elétrico com maior autonomia', 'Nam vitae finibus ante. Duis lobortis tellus vel diam fringilla.', true, '2024-04-15'),
        ('SpaceX realiza lançamento histórico com foguete reutilizável', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est.', false, '2023-03-22'),
        ('Netflix lança série documental sobre inteligência artificial', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Nam vitae finibus ante.', true, '2023-05-25'),
        ('Microsoft anuncia atualização do Windows 12', 'Duis lobortis tellus vel diam fringilla, eu ullamcorper ex iaculis.', false, '2024-06-03'),
        ('Amazon expande serviços de entrega com drones', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent et auctor justo. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent et auctor justo.', true, '2024-05-30'),
        ('Samsung revela nova linha de TVs 8K', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vitae finibus ante. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vitae finibus ante.', false, '2024-04-06'),
        ('Intel lança processadores de nova geração', 'Duis lobortis tellus vel diam fringilla, eu ullamcorper ex iaculis. Lorem ipsum dolor sit amet, consectetur adipiscing elit.', true, '2023-06-08'),
        ('NASA planeja nova missão à Lua em 2025', 'Vestibulum vestibulum auctor est. Nam vitae finibus ante. Duis lobortis tellus vel diam fringilla.', true, '2024-01-17'),
        ('Uber testa táxis autônomos em São Francisco', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Praesent et auctor justo.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Praesent et auctor justo.', true, '2023-02-12'),
        ('Sony lança novo console PlayStation 6', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vitae finibus ante. Duis lobortis tellus vel diam fringilla. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vitae finibus ante. Duis lobortis tellus vel diam fringilla.', true, '2024-03-09'),
        ('Facebook introduz ferramentas avançadas de privacidade', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est.', true, '2024-04-11'),
        ('IBM desenvolve novo chip quântico', 'Nam vitae finibus ante. Duis lobortis tellus vel diam fringilla, eu ullamcorper ex iaculis.', false, '2024-05-01'),
        ('Adobe lança atualização do Photoshop com inteligência artificial', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Praesent et auctor justo.', true, '2023-03-05'),
        ('TikTok adiciona funcionalidades para criadores de conteúdo', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vitae finibus ante. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vitae finibus ante.', false, '2024-02-27'),
        ('NVIDIA anuncia nova linha de GPUs para gamers', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis lobortis tellus vel diam fringilla. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis lobortis tellus vel diam fringilla.', true, '2024-06-02'),
        ('Reddit lança novo design para sua plataforma', 'Vestibulum vestibulum auctor est. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Lorem ipsum dolor sit amet, consectetur adipiscing elit.', false, '2024-01-31'),
        ('Twitter testa recurso de edição de tweets', 'Nam vitae finibus ante. Duis lobortis tellus vel diam fringilla, eu ullamcorper ex iaculis.', true, '2023-02-14'),
        ('WhatsApp implementa chamadas de vídeo em grupo com até 50 pessoas', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est.', false, '2024-05-16'),
        ('Spotify introduz novos recursos de personalização de playlists', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent et auctor justo. Nam vitae finibus ante. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent et auctor justo. Nam vitae finibus ante.', false, '2024-04-18'),
        ('Zoom lança plataforma para eventos virtuais', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vestibulum auctor est. Duis lobortis tellus vel diam fringilla.', false, '2023-06-10'),
        ('Oracle apresenta novas soluções de banco de dados na nuvem', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vitae finibus ante. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vitae finibus ante.', true, '2024-02-20'),
        ('Huawei revela novo smartphone dobrável', 'Duis lobortis tellus vel diam fringilla, eu ullamcorper ex iaculis. Lorem ipsum dolor sit amet, consectetur adipiscing elit.', false, '2023-03-12');