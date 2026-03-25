package com.example.demo.service;

import com.example.demo.domain.Item;
import com.example.demo.domain.Pedido;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    public Pedido iniciarPedido() {
        return new Pedido();
    }

    public Pedido adicionarItem(Pedido pedido, Item item) {
        pedido.adicionarItem(item);
        return pedido;
    }

    public Pedido removerItem(Pedido pedido, String nome) {
        pedido.removerItem(nome);
        return pedido;
    }

    public Pedido confirmarPedido(Pedido pedido) {
        pedido.confirmar();
        return pedido;
    }

    public Pedido iniciarPreparo(Pedido pedido) {
        pedido.iniciarPreparo();
        return pedido;
    }

    public Pedido finalizarPreparo(Pedido pedido) {
        pedido.finalizarPreparo();
        return pedido;
    }

    public Pedido entregar(Pedido pedido) {
        pedido.entregar();
        return pedido;
    }

    public Pedido cancelar(Pedido pedido) {
        pedido.cancelar();
        return pedido;
    }
}
