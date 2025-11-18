package br.com.harmoniawork.ui.swing;

import br.com.harmoniawork.application.usecases.GerarRecomendacoesUseCase;
import br.com.harmoniawork.application.usecases.ListarColaboradoresUseCase;
import br.com.harmoniawork.domain.entities.Colaborador;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardScreen extends JFrame {

    private final ListarColaboradoresUseCase listarUC;
    private final GerarRecomendacoesUseCase gerarRecUC;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    public DashboardScreen(ListarColaboradoresUseCase listarUC, GerarRecomendacoesUseCase gerarRecUC) {
        this.listarUC = listarUC;
        this.gerarRecUC = gerarRecUC;
        init();
    }

    private void init() {
        setTitle("Dashboard - HarmoniaWork");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        var panelTop = new JPanel(new FlowLayout());
        var cbColaboradores = new JComboBox<String>();
        panelTop.add(new JLabel("Colaborador:"));
        panelTop.add(cbColaboradores);
        var btnRecomendacoes = new JButton("Gerar Recomendações");
        panelTop.add(btnRecomendacoes);
        add(panelTop, BorderLayout.NORTH);

        var center = new JPanel(new BorderLayout());
        var infoArea = new JTextArea();
        infoArea.setEditable(false);
        center.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        var recArea = new JTextArea();
        recArea.setEditable(false);
        recArea.setBorder(BorderFactory.createTitledBorder("Recomendações"));
        recArea.setPreferredSize(new Dimension(300, 200));
        center.add(recArea, BorderLayout.EAST);

        add(center, BorderLayout.CENTER);

        // preencher combo
        cbColaboradores.removeAllItems();
        List<Colaborador> todos = listarUC.execute();
        for (Colaborador c : todos) {
            cbColaboradores.addItem(c.getId() + " - " + c.getNome());
        }

        cbColaboradores.addActionListener(e -> {
            if (cbColaboradores.getItemCount() == 0) return;
            String selected = (String) cbColaboradores.getSelectedItem();
            Long id = Long.parseLong(selected.split(" - ")[0]);
            Colaborador c = listarUC.execute().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
            if (c != null) {
                infoArea.setText("Nome: " + c.getNome() + "\n"
                        + "Modo: " + c.getModoDeTrabalho() + "\n"
                        + "Estresse: " + c.getNivelEstresse() + "\n"
                        + "Índice Integração: " + c.getIndiceIntegracao() + "\n"
                        + "Ergonomia Ajustada: " + (c.isErgonomiaAjustada() ? "SIM" : "NÃO") + "\n"
                        + "Indicadores: " + c.getIndicadores().toString()
                );
            }
        });

        btnRecomendacoes.addActionListener(e -> {
            if (cbColaboradores.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Nenhum colaborador cadastrado.");
                return;
            }
            String selected = (String) cbColaboradores.getSelectedItem();
            Long id = Long.parseLong(selected.split(" - ")[0]);
            List<String> recs = gerarRecUC.execute(id);
            recArea.setText("");
            for (String r : recs) recArea.append("- " + r + "\n");
        });
    }
}
