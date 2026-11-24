-- Migration: perfis, telas e associacao N:N

ALTER TABLE perfil_acesso
    ADD COLUMN IF NOT EXISTS status_ativo TINYINT NOT NULL DEFAULT 1,
    ADD COLUMN IF NOT EXISTS codigo_tela_inicial VARCHAR(50) NULL;

ALTER TABLE perfil_acesso
    MODIFY COLUMN status_ativo TINYINT NOT NULL DEFAULT 1;

UPDATE perfil_acesso
SET codigo_tela_inicial = 'home'
WHERE codigo_tela_inicial IS NULL;

CREATE TABLE IF NOT EXISTS tela (
    id_tela INT AUTO_INCREMENT PRIMARY KEY,
    codigo_tela VARCHAR(50) NOT NULL,
    nome_tela VARCHAR(100) NOT NULL,
    grupo VARCHAR(100) NULL,
    ordem INT NULL,
    status_ativo TINYINT NOT NULL DEFAULT 1,
    CONSTRAINT uk_tela_codigo UNIQUE (codigo_tela)
);

CREATE TABLE IF NOT EXISTS perfil_tela (
    id_perfil INT NOT NULL,
    id_tela INT NOT NULL,
    CONSTRAINT pk_perfil_tela PRIMARY KEY (id_perfil, id_tela),
    CONSTRAINT fk_perfil_tela_perfil FOREIGN KEY (id_perfil)
        REFERENCES perfil_acesso(id_perfil),
    CONSTRAINT fk_perfil_tela_tela FOREIGN KEY (id_tela)
        REFERENCES tela(id_tela)
);

INSERT IGNORE INTO tela (codigo_tela, nome_tela, grupo, ordem, status_ativo) VALUES
('home', 'Home', 'Principal', 1, 1),
('alunos', 'Alunos', 'Gestão acadêmica', 2, 1),
('turmas', 'Turmas', 'Gestão acadêmica', 3, 1),
('eventos', 'Eventos', 'Atividades', 4, 1),
('cronograma', 'Cronograma', 'Gestão acadêmica', 5, 1),
('chamada', 'Chamada', 'Gestão acadêmica', 6, 1),
('agenda', 'Agenda', 'Atividades', 7, 1),
('usuarios', 'Usuários', 'Administração', 8, 1),
('perfis-acessos', 'Perfis e acessos', 'Administração', 9, 1);
