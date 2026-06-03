package br.com.fiap.orbitagro.domain.entity;

import br.com.fiap.orbitagro.domain.enums.TipoEquipe;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_equipe_resposta")
public class EquipeResposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TipoEquipe tipoEquipe;

    @Column(nullable = false)
    private boolean disponivel = true;

    @Min(1)
    @Column(nullable = false)
    private Integer capacidadeAtendimentoDiaria;

    @Size(max = 20)
    @Column(length = 20)
    private String telefoneContato;

    @ManyToMany(mappedBy = "equipesResponsaveis")
    private Set<AlertaAgroclimatico> alertas = new HashSet<>();

    protected EquipeResposta() {
    }

    public EquipeResposta(String nome, TipoEquipe tipoEquipe, Integer capacidadeAtendimentoDiaria, String telefoneContato) {
        this.nome = nome;
        this.tipoEquipe = tipoEquipe;
        this.capacidadeAtendimentoDiaria = capacidadeAtendimentoDiaria;
        this.telefoneContato = telefoneContato;
        this.disponivel = true;
    }

    public void reservarParaAtendimento() {
        this.disponivel = false;
    }

    public void liberar() {
        this.disponivel = true;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public TipoEquipe getTipoEquipe() { return tipoEquipe; }
    public boolean isDisponivel() { return disponivel; }
    public Integer getCapacidadeAtendimentoDiaria() { return capacidadeAtendimentoDiaria; }
    public String getTelefoneContato() { return telefoneContato; }
    public Set<AlertaAgroclimatico> getAlertas() { return alertas; }
}
