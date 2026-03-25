package com.example.demo.controller;

import com.example.demo.domain.Item;
import com.example.demo.domain.Pedido;
import com.example.demo.service.PedidoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping("/iniciar")
    public Pedido iniciar() {
        return service.iniciarPedido();
    }

    @PostMapping("/adicionar-item")
    public Pedido adicionarItem(@RequestBody Pedido pedido, @RequestParam String nome, @RequestParam int quantidade) {
        Item item = new Item(nome, quantidade);
        return service.adicionarItem(pedido, item);
    }

    @PostMapping("/remover-item")
    public Pedido removerItem(@RequestBody Pedido pedido, @RequestParam String nome) {
        return service.removerItem(pedido, nome);
    }

    @PostMapping("/confirmar")
    public Pedido confirmar(@RequestBody Pedido pedido) {
        return service.confirmarPedido(pedido);
    }

    @PostMapping("/iniciar-preparo")
    public Pedido iniciarPreparo(@RequestBody Pedido pedido) {
        return service.iniciarPreparo(pedido);
    }

    @PostMapping("/finalizar-preparo")
    public Pedido finalizarPreparo(@RequestBody Pedido pedido) {
        return service.finalizarPreparo(pedido);
    }

    @PostMapping("/entregar")
    public Pedido entregar(@RequestBody Pedido pedido) {
        return service.entregar(pedido);
    }

    @PostMapping("/cancelar")
    public Pedido cancelar(@RequestBody Pedido pedido) {
        return service.cancelar(pedido);
    }
}
