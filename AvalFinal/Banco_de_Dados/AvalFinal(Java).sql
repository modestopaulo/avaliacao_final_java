CREATE SCHEMA IF NOT EXISTS `evento` DEFAULT CHARACTER SET utf8;
USE `evento`;
 
-- Criando a tabela Pessoa
CREATE TABLE Pessoa (
    id INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);
 
-- Criando a tabela Organizador (herda de Pessoa)
CREATE TABLE Organizador (
    id INT PRIMARY KEY, -- FK de Pessoa
    email VARCHAR(100) NOT NULL,
    FOREIGN KEY (id) REFERENCES Pessoa(id)
);
 
-- Criando a tabela Participante (herda de Pessoa)
CREATE TABLE Participante (
    id INT PRIMARY KEY, -- FK de Pessoa
    telefone VARCHAR(15),
    FOREIGN KEY (id) REFERENCES Pessoa(id)
);
 
-- Criando a tabela Local
CREATE TABLE Local (
    id INT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    vagas INT NOT NULL
);
 
-- Criando a tabela Evento
CREATE TABLE Evento (
    id INT PRIMARY KEY,
    idOrganizador INT NOT NULL,
    idLocal INT NOT NULL,
    data DATETIME NOT NULL,
    descricao VARCHAR(255),
    FOREIGN KEY (idOrganizador) REFERENCES Organizador(id),
    FOREIGN KEY (idLocal) REFERENCES Local(id)
);
 
-- Criando a tabela de relacionamento entre Evento e Participante
CREATE TABLE Evento_Participante (
    idEvento INT NOT NULL,
    idParticipante INT NOT NULL,
    PRIMARY KEY (idEvento, idParticipante),
    FOREIGN KEY (idEvento) REFERENCES Evento(id),
    FOREIGN KEY (idParticipante) REFERENCES Participante(id)
);
 
-- Criando a tabela Notificacao
CREATE TABLE Notificacao (
    id INT PRIMARY KEY AUTO_INCREMENT,
    mensagem VARCHAR(255) NOT NULL
);
 
-- Criando as tabelas para Notificacao por Email e por Telefone
CREATE TABLE NotificacaoEmail (
    id INT PRIMARY KEY, -- FK de Notificacao
    destinatarioEmail VARCHAR(100) NOT NULL,
    FOREIGN KEY (id) REFERENCES Notificacao(id)
);
 
CREATE TABLE NotificacaoTelefone (
    id INT PRIMARY KEY, -- FK de Notificacao
    destinatarioTelefone VARCHAR(15) NOT NULL,
    FOREIGN KEY (id) REFERENCES Notificacao(id)
);

SELECT * FROM local;idtelefone