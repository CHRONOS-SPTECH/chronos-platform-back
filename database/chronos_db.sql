-- =============================================================================
-- CRIAÇÃO DO BANCO DE DADOS
-- =============================================================================
CREATE DATABASE IF NOT EXISTS chronos_db; 
USE chronos_db;

-- =============================================================================
-- 1. ESTRUTURA DE TABELAS
-- =============================================================================

-- Tabela: tipo_vinculo
CREATE TABLE tipo_vinculo (
    id_tipo_vinculo INT AUTO_INCREMENT PRIMARY KEY,
    nome_vinculo VARCHAR(50) NOT NULL, 
    descricao VARCHAR(255)
);

-- Tabela: perfil_acesso
CREATE TABLE perfil_acesso (
    id_perfil INT AUTO_INCREMENT PRIMARY KEY,
    nome_perfil VARCHAR(50) NOT NULL,
    descricao VARCHAR(255)
);

-- Tabela: nivel_formacao
CREATE TABLE nivel_formacao (
    id_nivel INT AUTO_INCREMENT PRIMARY KEY,
    nome_nivel VARCHAR(25) NOT NULL,
    ordem_hierarquia INT NOT NULL 
);

-- Tabela: secretaria
CREATE TABLE secretaria (
    id_secretaria INT AUTO_INCREMENT PRIMARY KEY,
    nome_secretaria VARCHAR(100) NOT NULL,
    descricao_secretaria VARCHAR(255)
);

-- Tabela: categoria_atividade
CREATE TABLE categoria_atividade (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(50) NOT NULL
);

-- Tabela: tipo_participacao
CREATE TABLE tipo_participacao (
    id_tipo_participacao INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL,
    tipo_hora_gerada ENUM('Formacao', 'Voluntariado', 'Cultura') NOT NULL
);

-- Tabela: turma
CREATE TABLE turma (
    id_turma INT AUTO_INCREMENT PRIMARY KEY,
    nome_turma VARCHAR(100) NOT NULL,
    data_inicio DATE NOT NULL,
    data_encerramento DATE,
    status_turma ENUM('Nao Iniciada', 'Em Andamento', 'Concluida') DEFAULT 'Nao Iniciada'
);

-- Tabela: pessoa
CREATE TABLE pessoa (
    id_pessoa INT AUTO_INCREMENT PRIMARY KEY,
    id_tipo_vinculo INT NOT NULL,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    genero VARCHAR(25) NULL, 
    cpf CHAR(11) UNIQUE NULL, 
    bolsista TINYINT(1) DEFAULT 0,
    url_foto_perfil VARCHAR(512),
    biometria_facial TEXT DEFAULT NULL,
    data_nascimento DATE,
    data_ingresso DATE,
    data_membro DATE,
    data_saida DATE,
    CONSTRAINT fk_pessoa_vinculo FOREIGN KEY (id_tipo_vinculo) 
        REFERENCES tipo_vinculo(id_tipo_vinculo)
);

-- Tabela: endereco_pessoa
CREATE TABLE endereco_pessoa (
    id_endereco INT AUTO_INCREMENT PRIMARY KEY,
    id_pessoa INT UNIQUE NOT NULL, 
    cep VARCHAR(10) NOT NULL,
    logradouro VARCHAR(150) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(100),  
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf CHAR(2) NOT NULL,
    CONSTRAINT fk_endereco_pessoa FOREIGN KEY (id_pessoa) 
        REFERENCES pessoa(id_pessoa) ON DELETE CASCADE
);

-- Tabela: usuario
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_pessoa INT UNIQUE NOT NULL,
    email_login VARCHAR(150) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    status_ativo TINYINT(1) DEFAULT 1, 
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_pessoa FOREIGN KEY (id_pessoa) 
        REFERENCES pessoa(id_pessoa)
);

-- Tabela: usuario_perfil
CREATE TABLE usuario_perfil (
    id_usuario INT NOT NULL,
    id_perfil INT NOT NULL,
    CONSTRAINT pk_usuario_perfil PRIMARY KEY (id_usuario, id_perfil),
    CONSTRAINT fk_up_usuario FOREIGN KEY (id_usuario) 
        REFERENCES usuario(id_usuario),
    CONSTRAINT fk_up_perfil FOREIGN KEY (id_perfil) 
        REFERENCES perfil_acesso(id_perfil)
);

-- Tabela: matricula_turma
CREATE TABLE matricula_turma (
    id_turma INT NOT NULL,
    id_pessoa INT NOT NULL,
    data_matricula DATE NOT NULL,
    CONSTRAINT pk_matricula_turma PRIMARY KEY (id_turma, id_pessoa),
    CONSTRAINT fk_matricula_turma FOREIGN KEY (id_turma) 
        REFERENCES turma(id_turma),
    CONSTRAINT fk_matricula_pessoa FOREIGN KEY (id_pessoa) 
        REFERENCES pessoa(id_pessoa)
);

-- Tabela: materia
CREATE TABLE materia (
    id_materia INT AUTO_INCREMENT PRIMARY KEY,
    id_nivel INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    CONSTRAINT fk_materia_nivel FOREIGN KEY (id_nivel) 
        REFERENCES nivel_formacao(id_nivel) 
);

-- Tabela: tema_aula
CREATE TABLE tema_aula (
    id_tema INT AUTO_INCREMENT PRIMARY KEY,
    id_materia INT NOT NULL,
    titulo_tema VARCHAR(150) NOT NULL, 
    ordem_prevista INT NOT NULL, 
    CONSTRAINT fk_tema_materia FOREIGN KEY (id_materia) 
        REFERENCES materia(id_materia)
);

-- Tabela: aula
CREATE TABLE aula (
    id_aula INT AUTO_INCREMENT PRIMARY KEY,
    id_turma INT NOT NULL,
    id_tema INT NOT NULL,
    id_instrutor INT NOT NULL, 
    data_aula DATE,
    hora_inicio TIME,
    hora_fim TIME,
    status_aula ENUM('Agendada', 'Realizada', 'Cancelada', 'Feriado') DEFAULT 'Agendada',
    data_criacao_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_aula_turma FOREIGN KEY (id_turma) 
        REFERENCES turma(id_turma),
    CONSTRAINT fk_aula_tema FOREIGN KEY (id_tema) 
        REFERENCES tema_aula(id_tema),
    CONSTRAINT fk_aula_instrutor FOREIGN KEY (id_instrutor) 
        REFERENCES pessoa(id_pessoa)
);

-- Tabela: chamada_aula
CREATE TABLE chamada_aula (
    id_aula INT NOT NULL,
    id_pessoa INT NOT NULL,
    compareceu TINYINT(1) DEFAULT 1,
    CONSTRAINT pk_chamada_aula PRIMARY KEY (id_aula, id_pessoa),
    CONSTRAINT fk_chamada_aula_ref FOREIGN KEY (id_aula) 
        REFERENCES aula(id_aula),
    CONSTRAINT fk_chamada_pessoa_ref FOREIGN KEY (id_pessoa) 
        REFERENCES pessoa(id_pessoa)
);

-- Tabela: historico_academico
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
);

-- Tabela: gestao_secretaria
CREATE TABLE gestao_secretaria (
    id_pessoa INT NOT NULL,
    id_secretaria INT NOT NULL,
    cargo_acesso ENUM('Diretor', 'Secretario', 'Assistente') NOT NULL,
    CONSTRAINT pk_gestao_secretaria PRIMARY KEY (id_pessoa, id_secretaria),
    CONSTRAINT fk_gestao_pessoa FOREIGN KEY (id_pessoa) 
        REFERENCES pessoa(id_pessoa),
    CONSTRAINT fk_gestao_secretaria FOREIGN KEY (id_secretaria) 
        REFERENCES secretaria(id_secretaria)
);

-- Tabela: evento
CREATE TABLE evento (
    id_evento INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT NOT NULL,
    id_secretaria INT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT, -- CAMPO ADICIONADO
    status_evento VARCHAR(50) DEFAULT 'Agendado',
    local_evento VARCHAR(150),
    data_evento DATE NOT NULL,
    hora_inicio_evento TIME NOT NULL,
    hora_fim_evento TIME NOT NULL,
    CONSTRAINT fk_evento_categoria FOREIGN KEY (id_categoria) 
        REFERENCES categoria_atividade(id_categoria),
    CONSTRAINT fk_evento_secretaria FOREIGN KEY (id_secretaria) 
        REFERENCES secretaria(id_secretaria)
);

-- Tabela: participacao_evento
CREATE TABLE participacao_evento (
    id_evento INT NOT NULL,
    id_pessoa INT NOT NULL,
    id_tipo_participacao INT NOT NULL,
    compareceu TINYINT(1) DEFAULT 1,
    CONSTRAINT pk_participacao_evento PRIMARY KEY (id_evento, id_pessoa),
    CONSTRAINT fk_part_evento FOREIGN KEY (id_evento) 
        REFERENCES evento(id_evento) ON DELETE CASCADE,
    CONSTRAINT fk_part_pessoa FOREIGN KEY (id_pessoa) 
        REFERENCES pessoa(id_pessoa),
    CONSTRAINT fk_part_tipo FOREIGN KEY (id_tipo_participacao) 
        REFERENCES tipo_participacao(id_tipo_participacao)
);


-- =============================================================================
-- 2. INSERTS DE CONFIGURAÇÃO
-- =============================================================================

-- -----------------------------------------------------------------------------
-- ETAPA 2.1: Parâmetros do Sistema (Tabelas de Configuração Base)
-- -----------------------------------------------------------------------------
INSERT INTO tipo_vinculo (nome_vinculo, descricao) VALUES 
('Público Externo', 'Pessoas que frequentam palestras abertas, mas não estão matriculados.'),
('Provacionista', 'Alunos que estão no período inicial de formação filosófica, primeiro nível.'),
('Membro', 'Aqueles que já concluíram o curso de Filosofia Para Viver e integram o quadro da escola.'),
('Membro Força Viva', 'Membros que assumem a responsabilidade de serem instrutores, secretários ou diretores');

INSERT INTO perfil_acesso (nome_perfil, descricao) VALUES 
('Administrador', 'Acesso total a todas as configurações, usuários e logs do sistema.'),
('Diretor', 'Visão gerencial e estratégica do sistema. Acesso indicadores de engajamento, acompanhamento de turmas e aprovação de eventos.'),
('Secretário', 'Gestão de matrículas, presenças, eventos e dados de contato das pessoas.'),
('Instrutor', 'Lançamento de temas de aula e registro de frequência das turmas sob sua responsabilidade.');

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
