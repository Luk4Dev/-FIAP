package br.com.fiap.orbitagro.domain.entity;

import br.com.fiap.orbitagro.domain.enums.NivelRisco;
import br.com.fiap.orbitagro.domain.enums.StatusAlerta;
import br.com.fiap.orbitagro.domain.enums.TipoRisco;
import br.com.fiap.orbitagro.exception.BusinessException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_alerta_agroclimatico", uniqueConstraints = {
        @UniqueConstraint(name = "uk_alerta_codigo", columnNames = "codigo")
})
public class AlertaAgroclimatico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    @Column(nullable = false, length = 40)
    private String codigo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoRisco tipoRisco;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelRisco nivelRisco;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusAlerta status;

    @Min(0)
    @Max(100)
    @Column(nullable = false)
    private Integer prioridadeScore;

    @NotBlank
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataGeracao;

    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "regiao_rural_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_alerta_regiao"))
    private RegiaoRural regiaoRural;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dado_orbital_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_alerta_dado_orbital"))
    private DadoOrbital dadoOrbitalBase;

    @ManyToMany
    @JoinTable(
            name = "tb_alerta_equipe",
            joinColumns = @JoinColumn(name = "alerta_id", foreignKey = @ForeignKey(name = "fk_alerta_equipe_alerta")),
            inverseJoinColumns = @JoinColumn(name = "equipe_id", foreignKey = @ForeignKey(name = "fk_alerta_equipe_equipe"))
    )
    private Set<EquipeResposta> equipesResponsaveis = new HashSet<>();

    @OneToMany(mappedBy = "alerta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RotaAtendimento> rotas = new ArrayList<>();

    protected AlertaAgroclimatico() {
    }

    public AlertaAgroclimatico(String codigo, TipoRisco tipoRisco, NivelRisco nivelRisco,
                               Integer prioridadeScore, String descricao,
                               RegiaoRural regiaoRural, DadoOrbital dadoOrbitalBase) {
        this.codigo = codigo;
        this.tipoRisco = tipoRisco;
        this.nivelRisco = nivelRisco;
        this.prioridadeScore = prioridadeScore;
        this.descricao = descricao;
        this.regiaoRural = regiaoRural;
        this.dadoOrbitalBase = dadoOrbitalBase;
        this.status = StatusAlerta.ABERTO;
        this.dataGeracao = LocalDateTime.now();
    }

    public void alocarEquipe(EquipeResposta equipe) {
        if (status == StatusAlerta.RESOLVIDO || status == StatusAlerta.CANCELADO) {
            throw new BusinessException("Não é possível alocar equipe em alerta finalizado ou cancelado.");
        }
        if (!equipe.isDisponivel()) {
            throw new BusinessException("Equipe não está disponível para novo atendimento.");
        }
        this.equipesResponsaveis.add(equipe);
        equipe.reservarParaAtendimento();
        this.status = StatusAlerta.EM_ATENDIMENTO;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void alterarStatus(StatusAlerta novoStatus) {
        if (this.status == StatusAlerta.RESOLVIDO && novoStatus != StatusAlerta.RESOLVIDO) {
            throw new BusinessException("Alerta resolvido não pode ser reaberto.");
        }
        if (novoStatus == StatusAlerta.RESOLVIDO && this.equipesResponsaveis.isEmpty()) {
            throw new BusinessException("Um alerta só pode ser resolvido após alocação de equipe responsável.");
        }
        this.status = novoStatus;
        this.dataAtualizacao = LocalDateTime.now();
        if (novoStatus == StatusAlerta.RESOLVIDO || novoStatus == StatusAlerta.CANCELADO) {
            this.equipesResponsaveis.forEach(EquipeResposta::liberar);
        }
    }

    public void adicionarRota(RotaAtendimento rota) {
        this.rotas.add(rota);
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public TipoRisco getTipoRisco() { return tipoRisco; }
    public NivelRisco getNivelRisco() { return nivelRisco; }
    public StatusAlerta getStatus() { return status; }
    public Integer getPrioridadeScore() { return prioridadeScore; }
    public String getDescricao() { return descricao; }
    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public RegiaoRural getRegiaoRural() { return regiaoRural; }
    public DadoOrbital getDadoOrbitalBase() { return dadoOrbitalBase; }
    public Set<EquipeResposta> getEquipesResponsaveis() { return equipesResponsaveis; }
    public List<RotaAtendimento> getRotas() { return rotas; }
}
