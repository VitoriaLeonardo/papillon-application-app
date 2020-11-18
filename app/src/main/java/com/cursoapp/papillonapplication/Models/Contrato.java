package com.cursoapp.papillonapplication.Models;

public class Contrato {
        public int id;
        public String servico;
        public String dataPedido;
        public String dataEntrega;
        public String situacao;
        public String comentario;

        public Cliente cliente;
        public Socio socio;

        public Contrato(String servico, String dataPedido, String dataEntrega, String situacao, String comentario, Cliente cliente, Socio socio) {
            this.servico =servico;
            this.dataPedido = dataPedido;
            this.dataEntrega = dataEntrega;
            this.situacao = situacao;
            this.comentario = comentario;
            this.cliente = cliente;
            this.socio = socio;
        }

    public Contrato(int id, String servico, String dataPedido, String dataEntrega, String situacao, String comentario, Cliente cliente, Socio socio) {
        this.id = id;
        this.servico = servico;
        this.dataPedido = dataPedido;
        this.dataEntrega = dataEntrega;
        this.situacao = situacao;
        this.comentario = comentario;
        this.cliente = cliente;
        this.socio = socio;
    }
    }