package br.com.fiap.orbitagro.domain.entity;

import br.com.fiap.orbitagro.domain.enums.NivelConectividade;
import br.com.fiap.orbitagro.domain.valueobject.AreaMonitorada;
import br.com.fiap.orbitagro.domain.valueobject.CoordenadaGeografica;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_regiao_rural")
public class RegiaoRural {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String municipio;

    @NotBlank
    @Size(min = 2, max = 2)
    @Column(nullable = false, length = 2)
    private String uf;

    @Embedded
    @Valid
    private CoordenadaGeografica coordenada;

    @Embedded
    @Valid
    private AreaMonitorada areaMonitorada;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelConectividade nivelConectividade;

    @OneToMany(mappedBy = "regiaoRural", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produtor> produtores = new ArrayList<>();

    @OneToMany(mappedBy = "regiaoRural", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DadoOrbital> dadosOrbitais = new ArrayList<>();

    @OneToMany(mappedBy = "regiaoRural", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlertaAgroclimatico> alertas = new ArrayList<>();

    protected RegiaoRural() {
    }

    public RegiaoRural(String nome, String municipio, String uf, CoordenadaGeografica coordenada,
                       AreaMonitorada areaMonitorada, NivelConectividade nivelConectividade) {
        this.nome = nome;
        this.municipio = municipio;
        this.uf = uf;
        this.coordenada = coordenada;
        this.areaMonitorada = areaMonitorada;
        this.nivelConectividade = nivelConectividade;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getMunicipio() { return municipio; }
    public String getUf() { return uf; }
    public CoordenadaGeografica getCoordenada() { return coordenada; }
    public AreaMonitorada getAreaMonitorada() { return areaMonitorada; }
    public NivelConectividade getNivelConectividade() { return nivelConectividade; }
    public List<Produtor> getProdutores() { return produtores; }
    public List<DadoOrbital> getDadosOrbitais() { return dadosOrbitais; }
    public List<AlertaAgroclimatico> getAlertas() { return alertas; }
}
