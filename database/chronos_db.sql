-- =============================================================================
-- INICIALIZAÇÃO DO BANCO DE DADOS (AMBIENTE LIMPO)
-- =============================================================================
DROP DATABASE IF EXISTS chronos_db;
CREATE DATABASE chronos_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE chronos_db;

-- =============================================================================
-- 1. ESTRUTURA DE TABELAS (DDL)
-- =============================================================================

CREATE TABLE tipo_vinculo (
    id_tipo_vinculo INT AUTO_INCREMENT PRIMARY KEY,
    nome_vinculo VARCHAR(50) NOT NULL UNIQUE,
    descricao VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE perfil_acesso (
    id_perfil INT AUTO_INCREMENT PRIMARY KEY,
    nome_perfil VARCHAR(50) NOT NULL UNIQUE,
    descricao VARCHAR(255),
    status_ativo TINYINT(1) NOT NULL DEFAULT 1,
    codigo_tela_inicial VARCHAR(50) NULL DEFAULT 'home'
) ENGINE=InnoDB;

CREATE TABLE tela (
    id_tela INT AUTO_INCREMENT PRIMARY KEY,
    codigo_tela VARCHAR(50) NOT NULL UNIQUE,
    nome_tela VARCHAR(100) NOT NULL,
    grupo VARCHAR(100) NULL,
    ordem INT NULL,
    status_ativo TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE perfil_tela (
    id_perfil INT NOT NULL,
    id_tela INT NOT NULL,
    CONSTRAINT pk_perfil_tela PRIMARY KEY (id_perfil, id_tela),
    CONSTRAINT fk_pt_perfil FOREIGN KEY (id_perfil)
        REFERENCES perfil_acesso(id_perfil) ON DELETE CASCADE,
    CONSTRAINT fk_pt_tela FOREIGN KEY (id_tela)
        REFERENCES tela(id_tela) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE nivel_formacao (
    id_nivel INT AUTO_INCREMENT PRIMARY KEY,
    nome_nivel VARCHAR(25) NOT NULL UNIQUE,
    ordem_hierarquia INT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE secretaria (
    id_secretaria INT AUTO_INCREMENT PRIMARY KEY,
    nome_secretaria VARCHAR(100) NOT NULL UNIQUE,
    descricao_secretaria VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE categoria_atividade (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE tipo_participacao (
    id_tipo_participacao INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL UNIQUE,
    tipo_hora_gerada ENUM('Formacao', 'Voluntariado', 'Cultura') NOT NULL
) ENGINE=InnoDB;

CREATE TABLE turma (
    id_turma INT AUTO_INCREMENT PRIMARY KEY,
    nome_turma VARCHAR(100) NOT NULL,
    data_inicio DATE NOT NULL,
    data_encerramento DATE NULL,
    status_turma ENUM('Nao Iniciada', 'Em Andamento', 'Concluida', 'Inativa') NOT NULL DEFAULT 'Nao Iniciada'
) ENGINE=InnoDB;

CREATE TABLE pessoa (
    id_pessoa INT AUTO_INCREMENT PRIMARY KEY,
    id_tipo_vinculo INT NOT NULL,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    genero VARCHAR(25) NULL,
    cpf CHAR(11) NULL UNIQUE,
    bolsista TINYINT(1) NOT NULL DEFAULT 0,
    url_foto_perfil VARCHAR(512) NULL,
    biometria_facial TEXT NULL,
    data_nascimento DATE NULL,
    data_ingresso DATE NULL,
    data_membro DATE NULL,
    data_saida DATE NULL,
    CONSTRAINT fk_pessoa_vinculo FOREIGN KEY (id_tipo_vinculo)
        REFERENCES tipo_vinculo(id_tipo_vinculo)
) ENGINE=InnoDB;

CREATE TABLE endereco_pessoa (
    id_endereco INT AUTO_INCREMENT PRIMARY KEY,
    id_pessoa INT NOT NULL UNIQUE,
    cep VARCHAR(10) NOT NULL,
    logradouro VARCHAR(150) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(100) NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf CHAR(2) NOT NULL,
    CONSTRAINT fk_endereco_pessoa FOREIGN KEY (id_pessoa)
        REFERENCES pessoa(id_pessoa) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_pessoa INT NOT NULL UNIQUE,
    email_login VARCHAR(150) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    status_ativo TINYINT(1) NOT NULL DEFAULT 1,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_pessoa FOREIGN KEY (id_pessoa)
        REFERENCES pessoa(id_pessoa) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE usuario_perfil (
    id_usuario INT NOT NULL,
    id_perfil INT NOT NULL,
    CONSTRAINT pk_usuario_perfil PRIMARY KEY (id_usuario, id_perfil),
    CONSTRAINT fk_up_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_up_perfil FOREIGN KEY (id_perfil)
        REFERENCES perfil_acesso(id_perfil) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE matricula_turma (
    id_turma INT NOT NULL,
    id_pessoa INT NOT NULL,
    data_matricula DATE NOT NULL,
    CONSTRAINT pk_matricula_turma PRIMARY KEY (id_turma, id_pessoa),
    CONSTRAINT fk_matricula_turma FOREIGN KEY (id_turma)
        REFERENCES turma(id_turma),
    CONSTRAINT fk_matricula_pessoa FOREIGN KEY (id_pessoa)
        REFERENCES pessoa(id_pessoa)
) ENGINE=InnoDB;

CREATE TABLE avaliacao_formacao_turma (
    id_turma INT NOT NULL,
    id_pessoa INT NOT NULL,
    status_avaliacao ENUM('PENDENTE', 'APROVADO', 'REPROVADO') NOT NULL DEFAULT 'PENDENTE',
    data_decisao DATE NULL,
    id_usuario_decisor INT NULL,
    observacao VARCHAR(500) NULL,
    CONSTRAINT pk_avaliacao_formacao_turma PRIMARY KEY (id_turma, id_pessoa),
    CONSTRAINT fk_avaliacao_formacao_matricula FOREIGN KEY (id_turma, id_pessoa)
        REFERENCES matricula_turma(id_turma, id_pessoa) ON DELETE CASCADE,
    CONSTRAINT fk_avaliacao_formacao_usuario FOREIGN KEY (id_usuario_decisor)
        REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

CREATE TABLE materia (
    id_materia INT AUTO_INCREMENT PRIMARY KEY,
    id_nivel INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    CONSTRAINT fk_materia_nivel FOREIGN KEY (id_nivel)
        REFERENCES nivel_formacao(id_nivel)
) ENGINE=InnoDB;

CREATE TABLE tema_aula (
    id_tema INT AUTO_INCREMENT PRIMARY KEY,
    id_materia INT NOT NULL,
    titulo_tema VARCHAR(150) NOT NULL,
    ordem_prevista INT NOT NULL,
    CONSTRAINT fk_tema_materia FOREIGN KEY (id_materia)
        REFERENCES materia(id_materia)
) ENGINE=InnoDB;

CREATE TABLE aula (
    id_aula INT AUTO_INCREMENT PRIMARY KEY,
    id_turma INT NOT NULL,
    id_tema INT NOT NULL,
    id_instrutor INT NOT NULL,
    data_aula DATE NULL,
    hora_inicio TIME NULL,
    hora_fim TIME NULL,
    status_aula ENUM('Agendada', 'Realizada', 'Cancelada', 'Feriado') NOT NULL DEFAULT 'Agendada',
    data_criacao_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_aula_turma FOREIGN KEY (id_turma)
        REFERENCES turma(id_turma),
    CONSTRAINT fk_aula_tema FOREIGN KEY (id_tema)
        REFERENCES tema_aula(id_tema),
    CONSTRAINT fk_aula_instrutor FOREIGN KEY (id_instrutor)
        REFERENCES pessoa(id_pessoa)
) ENGINE=InnoDB;

CREATE TABLE chamada_aula (
    id_aula INT NOT NULL,
    id_pessoa INT NOT NULL,
    compareceu TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT pk_chamada_aula PRIMARY KEY (id_aula, id_pessoa),
    CONSTRAINT fk_chamada_aula_ref FOREIGN KEY (id_aula)
        REFERENCES aula(id_aula) ON DELETE CASCADE,
    CONSTRAINT fk_chamada_pessoa_ref FOREIGN KEY (id_pessoa)
        REFERENCES pessoa(id_pessoa)
) ENGINE=InnoDB;

CREATE TABLE historico_academico (
    id_historico INT AUTO_INCREMENT PRIMARY KEY,
    id_pessoa INT NOT NULL,
    id_materia INT NOT NULL,
    status_materia ENUM('Cursando', 'Aprovado', 'Reprovado', 'Trancado') NOT NULL,
    data_status DATE NOT NULL,
    CONSTRAINT fk_historico_pessoa FOREIGN KEY (id_pessoa)
        REFERENCES pessoa(id_pessoa),
    CONSTRAINT fk_historico_materia FOREIGN KEY (id_materia)
        REFERENCES materia(id_materia)
) ENGINE=InnoDB;

CREATE TABLE gestao_secretaria (
    id_pessoa INT NOT NULL,
    id_secretaria INT NOT NULL,
    cargo_acesso ENUM('Diretor', 'Secretario', 'Assistente') NOT NULL,
    CONSTRAINT pk_gestao_secretaria PRIMARY KEY (id_pessoa, id_secretaria),
    CONSTRAINT fk_gestao_pessoa FOREIGN KEY (id_pessoa)
        REFERENCES pessoa(id_pessoa),
    CONSTRAINT fk_gestao_secretaria FOREIGN KEY (id_secretaria)
        REFERENCES secretaria(id_secretaria)
) ENGINE=InnoDB;

CREATE TABLE evento (
    id_evento INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT NOT NULL,
    id_secretaria INT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT NULL,
    status_evento VARCHAR(50) NOT NULL DEFAULT 'Agendado',
    local_evento VARCHAR(150) NULL,
    incluir_publico_externo TINYINT(1) NOT NULL DEFAULT 0,
    data_evento DATE NOT NULL,
    hora_inicio_evento TIME NOT NULL,
    hora_fim_evento TIME NOT NULL,
    CONSTRAINT fk_evento_categoria FOREIGN KEY (id_categoria)
        REFERENCES categoria_atividade(id_categoria),
    CONSTRAINT fk_evento_secretaria FOREIGN KEY (id_secretaria)
        REFERENCES secretaria(id_secretaria)
) ENGINE=InnoDB;

CREATE TABLE evento_turma (
    id_evento INT NOT NULL,
    id_turma INT NOT NULL,
    CONSTRAINT pk_evento_turma PRIMARY KEY (id_evento, id_turma),
    CONSTRAINT fk_evento_turma_evento FOREIGN KEY (id_evento)
        REFERENCES evento(id_evento) ON DELETE CASCADE,
    CONSTRAINT fk_evento_turma_turma FOREIGN KEY (id_turma)
        REFERENCES turma(id_turma)
) ENGINE=InnoDB;

CREATE TABLE participacao_evento (
    id_evento INT NOT NULL,
    id_pessoa INT NOT NULL,
    id_tipo_participacao INT NOT NULL,
    compareceu TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT pk_participacao_evento PRIMARY KEY (id_evento, id_pessoa),
    CONSTRAINT fk_part_evento FOREIGN KEY (id_evento)
        REFERENCES evento(id_evento) ON DELETE CASCADE,
    CONSTRAINT fk_part_pessoa FOREIGN KEY (id_pessoa)
        REFERENCES pessoa(id_pessoa),
    CONSTRAINT fk_part_tipo FOREIGN KEY (id_tipo_participacao)
        REFERENCES tipo_participacao(id_tipo_participacao)
) ENGINE=InnoDB;

-- =============================================================================
-- 2. CARGA INICIAL DE DADOS (SEED)
-- =============================================================================
START TRANSACTION;

-- 2.1 Domínios e Configurações
INSERT INTO tipo_vinculo (nome_vinculo, descricao) VALUES
('Público Externo', 'Pessoas que frequentam palestras abertas, mas não estão matriculadas.'),
('Provacionista', 'Alunos que estão no período inicial de formação filosófica, primeiro nível.'),
('Membro', 'Aqueles que já concluíram o curso de Filosofia Para Viver e integram o quadro da escola.'),
('Membro Força Viva', 'Membros que assumem a responsabilidade de serem instrutores, secretários ou diretores.');

INSERT INTO perfil_acesso (nome_perfil, descricao, status_ativo, codigo_tela_inicial) VALUES
('Administrador', 'Acesso total a todas as configurações, usuários e logs do sistema.', 1, 'home'),
('Diretor', 'Visão gerencial e estratégica do sistema. Acesso a indicadores de engajamento, acompanhamento de turmas e aprovação de eventos.', 1, 'home'),
('Secretário', 'Gestão de matrículas, presenças, eventos e dados de contato das pessoas.', 1, 'home'),
('Instrutor', 'Lançamento de temas de aula e registro de frequência das turmas sob sua responsabilidade.', 1, 'home');

INSERT INTO tela (codigo_tela, nome_tela, grupo, ordem, status_ativo) VALUES
('home', 'Home', 'Principal', 1, 1),
('panorama-academico', 'Panorama Acadêmico', 'Dashboard', 2, 1),
('engajamento', 'Engajamento das Atividades', 'Dashboard', 3, 1),
('alunos', 'Alunos', 'Gestão acadêmica', 4, 1),
('turmas', 'Turmas', 'Gestão acadêmica', 5, 1),
('eventos', 'Eventos', 'Gestão acadêmica', 6, 1),
('cronograma', 'Cronograma de Aulas', 'Gestão acadêmica', 7, 1),
('gestao-aulas', 'Gestão de Aulas', 'Gestão acadêmica', 8, 1),
('chamada', 'Chamada do dia', 'Acadêmico', 9, 1),
('agenda', 'Minha Agenda', 'Acadêmico', 10, 1),
('usuarios', 'Usuários', 'Administração', 11, 1),
('perfis-acessos', 'Perfis e acessos', 'Administração', 12, 1);

INSERT INTO perfil_tela (id_perfil, id_tela)
SELECT pa.id_perfil, t.id_tela
FROM perfil_acesso pa
JOIN tela t ON (
    (pa.nome_perfil IN ('Administrador', 'Diretor') AND t.codigo_tela IN (
        'home', 'panorama-academico', 'engajamento', 'alunos', 'turmas', 'eventos', 'usuarios', 'perfis-acessos'
    ))
    OR (pa.nome_perfil = 'Secretário' AND t.codigo_tela IN (
        'home', 'panorama-academico', 'engajamento', 'alunos', 'turmas', 'eventos', 'cronograma', 'gestao-aulas'
    ))
    OR (pa.nome_perfil = 'Instrutor' AND t.codigo_tela IN (
        'home', 'chamada', 'agenda'
    ))
);

INSERT INTO nivel_formacao (nome_nivel, ordem_hierarquia) VALUES
('Filosofia para Viver', 1),
('Nível Intermediário', 2),
('Nível Avançado', 3);

INSERT INTO secretaria (nome_secretaria, descricao_secretaria) VALUES
('Secretaria Acadêmica', 'Gestão de alunos e aulas'),
('Cultura', 'Organização de eventos artísticos e palestras'),
('Voluntariado', 'Manutenção e ações sociais');

INSERT INTO categoria_atividade (nome, descricao) VALUES
('Palestra Pública', 'Evento aberto ao público geral'),
('Mutirão', 'Atividade de limpeza e manutenção'),
('Ensaio', 'Atividade do grupo de teatro ou música');

INSERT INTO tipo_participacao (descricao, tipo_hora_gerada) VALUES
('Assistente', 'Cultura'),
('Organizador', 'Voluntariado'),
('Aluno em Aula', 'Formacao');

-- 2.2 Grade Curricular e Turmas
INSERT INTO turma (nome_turma, data_inicio, status_turma) VALUES
('N1 Turma IX', '2026-01-05', 'Em Andamento'),
('N1 Turma X', '2026-01-06', 'Em Andamento'),
('N1 Turma XI', '2026-01-03', 'Em Andamento'),
('N2 Turma III', '2026-01-05', 'Em Andamento'),
('N2 Turma IV', '2026-01-01', 'Em Andamento'),
('N3 Turma I', '2026-01-07', 'Em Andamento');

INSERT INTO materia (id_nivel, nome) VALUES
(1, 'Filosofia para Viver'),
(2, 'Filosofia Intermediária'),
(3, 'Filosofia Avançada'),
(1, 'Voluntariado'),
(1, 'Oficina de Artes'),
(1, 'Palestras Filosóficas');

INSERT INTO tema_aula (id_materia, titulo_tema, ordem_prevista) VALUES
(1, 'Introdução à Filosofia', 1),
(1, 'O Conhece-te a Ti Mesmo', 2),
(1, 'Virtude e Ética', 3),
(1, 'A Alma e o Corpo', 4),
(2, 'Filosofia da Natureza', 1),
(2, 'Cosmologia Antiga', 2),
(2, 'Simbolismo e Linguagem', 3),
(2, 'O Hermetismo', 4),
(3, 'Metafísica Comparada', 1),
(3, 'Filosofia do Ser', 2),
(4, 'Gestão de Voluntários', 1),
(4, 'Projetos Culturais', 2),
(4, 'Organização de Eventos', 3),
(4, 'Comunicação e Divulgação', 4),
(5, 'Aquarela e Filosofia', 1),
(5, 'Desenho de Observação', 2),
(6, 'A Nova Acrópole e seus Ideais', 1),
(6, 'Filosofia para o Século XXI', 2),
(6, 'O Sentido da Vida segundo os Filósofos', 3),
(6, 'Valores para um Mundo Melhor', 4);

-- 2.3 Pessoas (Corpo Docente, Administrativo e Alunos)
INSERT INTO pessoa (id_tipo_vinculo, nome, email, telefone, genero, cpf, bolsista, data_nascimento, data_ingresso, data_membro) VALUES
(4, 'Henrique Nôleto', 'henrique.contato@email.com', '11999999999', 'Masculino', '12345678901', 0, '1995-05-15', '2026-01-10', '2026-01-15'),
(4, 'Sofia Mendes', 'sofia@nacropoles.org', '11999990002', 'Feminino', '11111111102', 0, NULL, '2020-01-01', '2020-01-01'),
(4, 'Ricardo Alves', 'ricardo@nacropoles.org', '11999990003', 'Masculino', '11111111103', 0, NULL, '2020-01-01', '2020-01-01'),
(4, 'Ulysses Guimarães', 'ulysses@nacropoles.org', '11999990004', 'Masculino', '11111111104', 0, NULL, '2020-01-01', '2020-01-01'),
(4, 'Ana Lima', 'ana@nacropoles.org', '11999990005', 'Feminino', '11111111105', 0, NULL, '2020-01-01', '2020-01-01'),
(4, 'Marcos Ferreira', 'marcos@nacropoles.org', '11999990006', 'Masculino', '11111111106', 0, NULL, '2020-01-01', '2020-01-01'),
(4, 'Carla Santos', 'carla@nacropoles.org', '11999990007', 'Feminino', '11111111107', 0, NULL, '2020-01-01', '2020-01-01'),
(4, 'Patricia Costa', 'patricia@nacropoles.org', '11999990008', 'Feminino', '11111111108', 0, NULL, '2020-01-01', '2020-01-01'),
(4, 'João Oliveira', 'joao@nacropoles.org', '11999990009', 'Masculino', '11111111109', 0, NULL, '2020-01-01', '2020-01-01'),
(1, 'Daniel Costa Avelino', 'danielcosta3448@gmail.com', '(11) 99999-0001', 'Masculino', '12345678907', 1, '2006-02-12', '2026-01-20', '2026-01-20'),
(1, 'Nathally Ribeiro', 'nathally@gmail.com', '(11) 99999-0002', 'Feminino', '45678912300', 1, '2006-01-31', '2026-01-20', '2026-01-20'),
(1, 'Emilly Bastos Reis', 'emillybastos34@gmail.com', '(11) 99999-0003', 'Feminino', '98765432100', 1, '2006-12-20', '2026-01-20', '2026-01-20'),
(2, 'Mateus Zozo', 'mateuszozo245@gmail.com', '(11) 99999-0004', 'Masculino', '32165498700', 0, '2006-05-17', '2026-01-20', NULL),
(2, 'Fernando Lopes Alpe', 'lopesfernando@gmail.com', '(11) 99999-0005', 'Masculino', '78912345600', 0, '2006-10-25', '2026-01-20', NULL);

-- 2.4 Vínculos Operacionais e Avaliações
INSERT INTO matricula_turma (id_turma, id_pessoa, data_matricula) VALUES
(1, 10, CURDATE()),
(1, 11, CURDATE()),
(2, 12, CURDATE()),
(2, 13, CURDATE());

INSERT INTO avaliacao_formacao_turma (id_turma, id_pessoa, status_avaliacao) VALUES
(1, 10, 'PENDENTE'),
(1, 11, 'PENDENTE'),
(2, 12, 'PENDENTE'),
(2, 13, 'PENDENTE');

INSERT INTO gestao_secretaria (id_pessoa, id_secretaria, cargo_acesso) VALUES
(1, 1, 'Diretor');

INSERT INTO evento (id_categoria, id_secretaria, titulo, descricao, status_evento, local_evento, incluir_publico_externo, data_evento, hora_inicio_evento, hora_fim_evento) VALUES
(1, 2, 'Simpósio de Filosofia Egípcia', 'Abordagem sobre o antigo egito', 'Agendado', 'Salão Principal', 1, '2024-05-15', '14:00:00', '18:00:00');

-- 2.5 Usuário Administrador Inicial (Henrique Nôleto) e seus Perfis
INSERT INTO usuario (id_pessoa, email_login, senha_hash, status_ativo) VALUES
(1, 'henrique@gmail.com', '$2a$10$TxjQmK0/qr7P/UzFN4XGvu6SStoqXP7D7zjttQVzoAS1sYieKudnW', 1);

INSERT INTO usuario_perfil (id_usuario, id_perfil) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4);

COMMIT;