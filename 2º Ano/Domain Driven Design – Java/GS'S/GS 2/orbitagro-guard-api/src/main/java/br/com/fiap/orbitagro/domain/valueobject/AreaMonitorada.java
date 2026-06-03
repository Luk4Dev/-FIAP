package br.com.fiap.orbitagro.domain.valueobject;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMin;

@Embeddable
public class AreaMonitorada {
    @DecimalMin(value = "0.1")
    private Double hectares;

    @DecimalMin(value = "0.0")
    private Double percentualAgriculturaFamiliar;

    protected AreaMonitorada() {
    }

    public AreaMonitorada(Double hectares, Double percentualAgriculturaFamiliar) {
        this.hectares = hectares;
        this.percentualAgriculturaFamiliar = percentualAgriculturaFamiliar;
    }

    public Double getHectares() { return hectares; }
    public Double getPercentualAgriculturaFamiliar() { return percentualAgriculturaFamiliar; }
}
