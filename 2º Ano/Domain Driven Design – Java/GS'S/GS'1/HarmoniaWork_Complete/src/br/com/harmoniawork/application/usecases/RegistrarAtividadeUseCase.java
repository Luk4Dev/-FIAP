package br.com.harmoniawork.application.usecases;

import br.com.harmoniawork.domain.entities.*;
import br.com.harmoniawork.domain.repositories.AtividadeRepository;
import br.com.harmoniawork.domain.repositories.ColaboradorRepository;
import br.com.harmoniawork.domain.repositories.SessaoRepository;

import java.util.Optional;

public class RegistrarAtividadeUseCase {
    private final ColaboradorRepository colaboradorRepo;
    private final AtividadeRepository atividadeRepo;
    private final SessaoRepository sessaoRepo;

    public RegistrarAtividadeUseCase(ColaboradorRepository colaboradorRepo,
                                     AtividadeRepository atividadeRepo,
                                     SessaoRepository sessaoRepo) {
        this.colaboradorRepo = colaboradorRepo;
        this.atividadeRepo = atividadeRepo;
        this.sessaoRepo = sessaoRepo;
    }

    public String execute(Long colaboradorId, TipoAtividade tipo) {
        Optional<Colaborador> oc = colaboradorRepo.findById(colaboradorId);
        if (!oc.isPresent()) return "Colaborador não encontrado.";

        Colaborador c = oc.get();
        Atividade atividade = Atividade.padrao(null, tipo);
        // salvar atividade no repo para ter id
        atividade = atividadeRepo.save(atividade);

        // aplicar na entidade de domínio
        c.aplicarAtividade(atividade);
        colaboradorRepo.update(c);

        // registrar em uma sessão simples
        SessaoDeTrabalho sessao = new SessaoDeTrabalho(null, c.getId());
        sessao.registrarAtividade(atividade);
        sessaoRepo.save(sessao);

        return "Atividade " + tipo + " registrada para " + c.getNome() + ".";
    }
}
