package br.com.fiap.orbitagro.domain.entity;

import br.com.fiap.orbitagro.domain.valueobject.Endereco;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produtor", uniqueConstraints = {
        @UniqueConstraint(name = "uk_produtor_documento", columnNames = "documento")
})
public class Produtor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String documento;

    @Email
    @Size(max = 120)
    @Column(length = 120)
    private String email;

    @Size(max = 20)
    @Column(length = 20)
    private String telefone;

    @Embedded
    @Valid
    private Endereco endereco;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "regiao_rural_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_produtor_regiao"))
    private RegiaoRural regiaoRural;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperativa_id", foreignKey = @ForeignKey(name = "fk_produtor_cooperativa"))
    private Cooperativa cooperativa;

    protected Produtor() {
    }

    public Produtor(String nome, String documento, String email, String telefone, Endereco endereco,
                    RegiaoRural regiaoRural, Cooperativa cooperativa) {
        this.nome = nome;
        this.documento = documento;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.regiaoRural = regiaoRural;
        this.cooperativa = cooperativa;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public Endereco getEndereco() { return endereco; }
    public RegiaoRural getRegiaoRural() { return regiaoRural; }
    public Cooperativa getCooperativa() { return cooperativa; }
}
