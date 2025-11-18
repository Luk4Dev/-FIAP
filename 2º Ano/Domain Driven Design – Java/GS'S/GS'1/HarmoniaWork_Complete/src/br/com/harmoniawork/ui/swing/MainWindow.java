package br.com.harmoniawork.ui.swing;

import br.com.harmoniawork.infrastructure.persistence.InMemoryAtividadeRepository;
import br.com.harmoniawork.infrastructure.persistence.InMemoryColaboradorRepository;
import br.com.harmoniawork.infrastructure.persistence.InMemorySessaoRepository;
import br.com.harmoniawork.application.usecases.*;
import br.com.harmoniawork.domain.services.IARecomendacoesService;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final CadastrarColaboradorUseCase cadastrarUC;
    private final ListarColaboradoresUseCase listarUC;
    private final RegistrarAtividadeUseCase registrarUC;
    private final GerarRecomendacoesUseCase gerarRecUC;

    public MainWindow() {
        // infra
        var colaboradorRepo = new InMemoryColaboradorRepository();
        var atividadeRepo = new InMemoryAtividadeRepository();
        var sessaoRepo = new InMemorySessaoRepository();

        // use cases
        this.cadastrarUC = new CadastrarColaboradorUseCase(colaboradorRepo);
        this.listarUC = new ListarColaboradoresUseCase(colaboradorRepo);
        this.registrarUC = new RegistrarAtividadeUseCase(colaboradorRepo, atividadeRepo, sessaoRepo);
        this.gerarRecUC = new GerarRecomendacoesUseCase(colaboradorRepo, new IARecomendacoesService());

        initUI();
    }

    private void initUI() {
        setTitle("HarmoniaWork - Sistema de Bem-Estar Híbrido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        var btnCadastrar = new JButton("Cadastro Colaborador");
        var btnRegistrar = new JButton("Registrar Atividade");
        var btnDashboard = new JButton("Dashboard");

        var panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(btnCadastrar);
        panel.add(btnRegistrar);
        panel.add(btnDashboard);

        add(panel, BorderLayout.NORTH);

        var content = new JLabel("<html><h2>HarmoniaWork</h2><p>Sistema demonstrativo para GS - trabalho híbrido</p></html>");
        content.setHorizontalAlignment(SwingConstants.CENTER);
        add(content, BorderLayout.CENTER);

        btnCadastrar.addActionListener(e -> {
            CadastroColaboradorScreen tela = new CadastroColaboradorScreen(cadastrarUC, listarUC);
            tela.setVisible(true);
        });

        btnRegistrar.addActionListener(e -> {
            RegistroAtividadeScreen tela = new RegistroAtividadeScreen(listarUC, registrarUC);
            tela.setVisible(true);
        });

        btnDashboard.addActionListener(e -> {
            DashboardScreen tela = new DashboardScreen(listarUC, gerarRecUC);
            tela.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow w = new MainWindow();
            w.setVisible(true);
        });
    }
}
