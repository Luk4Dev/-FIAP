package com.example.demo.domain;

public class Item {

    private String nome;
    private int quantidade;

    public Item() {
    }

    public Item(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
