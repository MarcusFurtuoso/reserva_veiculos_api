-- table vei_veiculo
ALTER TABLE public.vei_veiculo
    ADD COLUMN vei_dc_preco DECIMAL(10),
    ADD COLUMN vei_tx_descricao TEXT;


-- table vus_veiculo_usuario
ALTER TABLE public.vus_veiculo_usuario
    RENAME COLUMN vus_dt_date TO vus_dt_date_inicial;

ALTER TABLE public.vus_veiculo_usuario
    ADD COLUMN vus_dt_date_final DATE;
