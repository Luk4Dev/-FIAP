package br.com.fiap.orbitagro.domain.entity;

import br.com.fiap.orbitagro.domain.enums.StatusRota;
import br.com.fiap.orbitagro.domain.valueobject.CoordenadaGeografica;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_rota_atendimento")
public class RotaAtendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String origem;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String destino;

    @Embedded
    @Valid
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "latitude_destino")),
            @AttributeOverride(name = "longitude", column = @Column(name = "longitude_destino"))
    })
    private CoordenadaGeografica coordenadaDestino;

    @DecimalMin("0.1")
    @Column(nullable = false)
    private Double distanciaKm;

    @DecimalMin("1")
    @Column(nullable = false)
    private Integer tempoEstimadoMinutos;

    @DecimalMin("0.0")
    @Column(nullable = false)
    private Double custoOperacionalEstimado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusRota status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alerta_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_rota_alerta"))
    private AlertaAgroclimatico alerta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipe_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_rota_equipe"))
    private EquipeResposta equipe;

    protected RotaAtendimento() {
    }

    public RotaAtendimento(String origem, String destino, CoordenadaGeografica coordenadaDestino,
                           Double distanciaKm, Integer tempoEstimadoMinutos, Double custoOperacionalEstimado,
                           AlertaAgroclimatico alerta, EquipeResposta equipe) {
        this.origem = origem;
        this.destino = destino;
        this.coordenadaDestino = coordenadaDestino;
        this.distanciaKm = distanciaKm;
        this.tempoEstimadoMinutos = tempoEstimadoMinutos;
        this.custoOperacionalEstimado = custoOperacionalEstimado;
        this.alerta = alerta;
        this.equipe = equipe;
        this.status = StatusRota.PLANEJADA;
    }

    public Long getId() { return id; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
    public CoordenadaGeografica getCoordenadaDestino() { return coordenadaDestino; }
    public Double getDistanciaKm() { return distanciaKm; }
    public Integer getTempoEstimadoMinutos() { return tempoEstimadoMinutos; }
    public Double getCustoOperacionalEstimado() { return custoOperacionalEstimado; }
    public StatusRota getStatus() { return status; }
    public AlertaAgroclimatico getAlerta() { return alerta; }
    public EquipeResposta getEquipe() { return equipe; }
}
