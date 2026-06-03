package br.com.fiap.orbitagro.domain.entity;

import br.com.fiap.orbitagro.domain.valueobject.Endereco;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_cooperativa", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cooperativa_cnpj", columnNames = "cnpj")
})
public class Cooperativa {
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
    private String cnpj;

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

    @OneToMany(mappedBy = "cooperativa")
    private List<Produtor> produtores = new ArrayList<>();

    protected Cooperativa() {
    }

    public Cooperativa(String nome, String cnpj, String email, String telefone, Endereco endereco) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCnpj() { return cnpj; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public Endereco getEndereco() { return endereco; }
    public List<Produtor> getProdutores() { return produtores; }
}
