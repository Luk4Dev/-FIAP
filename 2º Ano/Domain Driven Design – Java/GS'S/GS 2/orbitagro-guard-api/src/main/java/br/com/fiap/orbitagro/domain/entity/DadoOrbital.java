package br.com.fiap.orbitagro.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "tb_dado_orbital")
public class DadoOrbital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataColeta;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String fonte;

    @DecimalMin(value = "-1.0")
    @DecimalMax(value = "1.0")
    @Column(nullable = false)
    private Double indiceVegetacaoNdvi;

    @Column(nullable = false)
    private Double temperaturaSuperficie;

    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double precipitacaoMm;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(nullable = false)
    private Double umidadeSoloPercentual;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(nullable = false)
    private Double indiceRiscoSeca;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "regiao_rural_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_dado_orbital_regiao"))
    private RegiaoRural regiaoRural;

    protected DadoOrbital() {
    }

    public DadoOrbital(LocalDate dataColeta, String fonte, Double indiceVegetacaoNdvi,
                       Double temperaturaSuperficie, Double precipitacaoMm, Double umidadeSoloPercentual,
                       Double indiceRiscoSeca, RegiaoRural regiaoRural) {
        this.dataColeta = dataColeta;
        this.fonte = fonte;
        this.indiceVegetacaoNdvi = indiceVegetacaoNdvi;
        this.temperaturaSuperficie = temperaturaSuperficie;
        this.precipitacaoMm = precipitacaoMm;
        this.umidadeSoloPercentual = umidadeSoloPercentual;
        this.indiceRiscoSeca = indiceRiscoSeca;
        this.regiaoRural = regiaoRural;
    }

    public Long getId() { return id; }
    public LocalDate getDataColeta() { return dataColeta; }
    public String getFonte() { return fonte; }
    public Double getIndiceVegetacaoNdvi() { return indiceVegetacaoNdvi; }
    public Double getTemperaturaSuperficie() { return temperaturaSuperficie; }
    public Double getPrecipitacaoMm() { return precipitacaoMm; }
    public Double getUmidadeSoloPercentual() { return umidadeSoloPercentual; }
    public Double getIndiceRiscoSeca() { return indiceRiscoSeca; }
    public RegiaoRural getRegiaoRural() { return regiaoRural; }
}
