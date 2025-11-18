package br.com.harmoniawork.ui.swing;

import br.com.harmoniawork.application.usecases.ListarColaboradoresUseCase;
import br.com.harmoniawork.application.usecases.RegistrarAtividadeUseCase;
import br.com.harmoniawork.domain.entities.Colaborador;
import br.com.harmoniawork.domain.entities.TipoAtividade;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RegistroAtividadeScreen extends JFrame {
    private final ListarColaboradoresUseCase listarUC;
    private final RegistrarAtividadeUseCase registrarUC;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public RegistroAtividadeScreen(ListarColaboradoresUseCase listarUC, RegistrarAtividadeUseCase registrarUC) {
        this.listarUC = listarUC;
        this.registrarUC = registrarUC;
        init();
    }

    private void init() {
        setTitle("Registrar Atividade");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        var north = new JPanel(new FlowLayout());
        var cbColaboradores = new JComboBox<String>();
        north.add(new JLabel("Colaborador:"));
        north.add(cbColaboradores);

        var cbAtividades = new JComboBox<>(new String[]{"REUNIAO","FOCO","PAUSA","MEDITACAO","TREINAMENTO","HIBRIDA"});
        north.add(new JLabel("Atividade:"));
        north.add(cbAtividades);

        var btnRegistrar = new JButton("Registrar");
        north.add(btnRegistrar);

        add(north, BorderLayout.NORTH);

        var list = new JList<>(listModel);
        add(new JScrollPane(list), BorderLayout.CENTER);

        // preencher combo
        cbColaboradores.removeAllItems();
        List<Colaborador> todos = listarUC.execute();
        for (Colaborador c : todos) {
            cbColaboradores.addItem(c.getId() + " - " + c.getNome());
        }

        btnRegistrar.addActionListener(e -> {
            if (cbColaboradores.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Nenhum colaborador cadastrado.");
                return;
            }
            String selected = (String) cbColaboradores.getSelectedItem();
            Long id = Long.parseLong(selected.split(" - ")[0]);
            TipoAtividade tipo = TipoAtividade.valueOf(cbAtividades.getSelectedItem().toString());
            String resultado = registrarUC.execute(id, tipo);
            atualizarLista();
            JOptionPane.showMessageDialog(this, resultado);
        });

        atualizarLista();
    }

    private void atualizarLista() {
        listModel.clear();
        List<Colaborador> todos = listarUC.execute();
        for (Colaborador c : todos) {
            listModel.addElement(c.toString());
        }
    }
}
