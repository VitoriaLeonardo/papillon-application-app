package com.cursoapp.papillonapplication.Models;

public class Socio {
    public int id;
    public String nome;
    public String cargo;
    public String login;
    public String senha;

    public Socio(String nome, String cargo, String login, String senha){
        this.nome = nome;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
    }

    public Socio(int id, String nome, String cargo){
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
    }
}
