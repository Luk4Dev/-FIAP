package br.com.harmoniawork.ui.swing;

import br.com.harmoniawork.application.usecases.CadastrarColaboradorUseCase;
import br.com.harmoniawork.application.usecases.ListarColaboradoresUseCase;
import br.com.harmoniawork.domain.entities.Colaborador;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CadastroColaboradorScreen extends JFrame {

    private final CadastrarColaboradorUseCase cadastrarUC;
    private final ListarColaboradoresUseCase listarUC;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public CadastroColaboradorScreen(CadastrarColaboradorUseCase cadastrarUC, ListarColaboradoresUseCase listarUC) {
        this.cadastrarUC = cadastrarUC;
        this.listarUC = listarUC;
        init();
    }

    private void init() {
        setTitle("Cadastro de Colaborador");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        var form = new JPanel(new GridLayout(3,2,8,8));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Nome:"));
        var txtNome = new JTextField();
        form.add(txtNome);

        form.add(new JLabel("Modo de trabalho:"));
        var cbModo = new JComboBox<>(new String[]{"REMOTO","PRESENCIAL","HÍBRIDO"});
        form.add(cbModo);

        var btnCadastrar = new JButton("Cadastrar");
        form.add(btnCadastrar);

        add(form, BorderLayout.NORTH);

        var list = new JList<>(listModel);
        add(new JScrollPane(list), BorderLayout.CENTER);

        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String modo = cbModo.getSelectedItem().toString();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o nome.");
                return;
            }
            Colaborador c = cadastrarUC.execute(nome, modo);
            atualizarLista();
            txtNome.setText("");
            JOptionPane.showMessageDialog(this, "Colaborador cadastrado: " + c.getNome());
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
