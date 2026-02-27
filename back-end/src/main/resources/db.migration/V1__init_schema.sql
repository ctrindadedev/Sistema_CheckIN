-- V1__init_schema.sql

-- 1. Criação da tabela de Usuários (com os campos da EntidadeGenerica)
CREATE TABLE usuario_tb (
                            usuario_id BIGSERIAL PRIMARY KEY,
                            nome VARCHAR(255),
                            email VARCHAR(255) UNIQUE,
                            senha VARCHAR(255),
                            telefone VARCHAR(255) UNIQUE,
                            ativo BOOLEAN,
                            criado_em TIMESTAMP,
                            atualizado_em TIMESTAMP
);

-- 2. Criação da tabela auxiliar de Perfis (Roles) do usuário (@ElementCollection)
CREATE TABLE perfis_usuario (
                                usuario_id BIGINT NOT NULL,
                                role VARCHAR(255),
                                CONSTRAINT fk_perfis_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario_tb (usuario_id) ON DELETE CASCADE
);

-- 3. Criação da tabela de Eventos
CREATE TABLE eventos_tb (
                            evento_id BIGSERIAL PRIMARY KEY,
                            titulo VARCHAR(255) NOT NULL,
                            descricao VARCHAR(5000),
                            data_evento TIMESTAMP,
                            local_evento VARCHAR(255),
                            vagas INTEGER,
                            imagem_url VARCHAR(1000),
                            usuario_organizador_id BIGINT,
                            criado_em TIMESTAMP,
                            atualizado_em TIMESTAMP,
                            CONSTRAINT fk_evento_organizador FOREIGN KEY (usuario_organizador_id) REFERENCES usuario_tb (usuario_id)
);

-- 4. Criação da tabela de Inscrições
CREATE TABLE evento_inscricoes (
                                   id BIGSERIAL PRIMARY KEY,
                                   evento_id BIGINT NOT NULL,
                                   usuario_id BIGINT NOT NULL,
                                   data_checkin TIMESTAMP,
                                   status_checkin VARCHAR(255),
                                   criado_em TIMESTAMP,
                                   atualizado_em TIMESTAMP,
                                   CONSTRAINT fk_inscricao_evento FOREIGN KEY (evento_id) REFERENCES eventos_tb (evento_id),
                                   CONSTRAINT fk_inscricao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario_tb (usuario_id)
);