package com.cursoapp.papillonapplication.Models;

public class Cliente {
    public int id;
    public String nome;
    public String cnpj;
    public String cep;
    public String numero;
    public String email;
    public String telefone;

    public Cliente(String nome, String cnpj, String cep, String numero, String email, String telefone) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.cep = cep;
        this.numero = numero;
        this.email = email;
        this.telefone = telefone;
    }

    public Cliente(int id, String nome, String cnpj, String telefone){
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }
}
