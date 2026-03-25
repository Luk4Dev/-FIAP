package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private List<Item> itens;
    private String status;

    public Pedido() {
        this.itens = new ArrayList<>();
        this.status = "INICIADO";
    }

    public void adicionarItem(Item item) {
        this.itens.add(item);
    }

    public void removerItem(String nome) {
        this.itens.removeIf(i -> i.getNome().equalsIgnoreCase(nome));
    }

    public void confirmar() {
        this.status = "CONFIRMADO";
    }

    public void iniciarPreparo() {
        this.status = "EM_PREPARO";
    }

    public void finalizarPreparo() {
        this.status = "PRONTO";
    }

    public void entregar() {
        this.status = "ENTREGUE";
    }

    public void cancelar() {
        this.status = "CANCELADO";
    }

    public List<Item> getItens() {
        return itens;
    }

    public String getStatus() {
        return status;
    }
}
