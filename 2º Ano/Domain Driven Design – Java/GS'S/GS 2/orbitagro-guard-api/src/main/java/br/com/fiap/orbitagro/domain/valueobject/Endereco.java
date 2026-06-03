package br.com.fiap.orbitagro.domain.valueobject;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public class Endereco {
    @NotBlank
    @Size(max = 120)
    private String logradouro;

    @NotBlank
    @Size(max = 80)
    private String municipio;

    @NotBlank
    @Size(min = 2, max = 2)
    private String uf;

    @Size(max = 10)
    private String cep;

    protected Endereco() {
    }

    public Endereco(String logradouro, String municipio, String uf, String cep) {
        this.logradouro = logradouro;
        this.municipio = municipio;
        this.uf = uf;
        this.cep = cep;
    }

    public String getLogradouro() { return logradouro; }
    public String getMunicipio() { return municipio; }
    public String getUf() { return uf; }
    public String getCep() { return cep; }
}
