--Table: per_perfil

ALTER TABLE per_perfil
    ADD CONSTRAINT per_tx_nome_unique UNIQUE (per_tx_nome);