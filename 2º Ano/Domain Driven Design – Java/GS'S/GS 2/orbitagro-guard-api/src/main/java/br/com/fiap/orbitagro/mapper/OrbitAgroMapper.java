package br.com.fiap.orbitagro.mapper;

import br.com.fiap.orbitagro.domain.entity.*;
import br.com.fiap.orbitagro.domain.valueobject.AreaMonitorada;
import br.com.fiap.orbitagro.domain.valueobject.CoordenadaGeografica;
import br.com.fiap.orbitagro.domain.valueobject.Endereco;
import br.com.fiap.orbitagro.dto.alerta.AlertaAgroclimaticoResponse;
import br.com.fiap.orbitagro.dto.cooperativa.CooperativaResponse;
import br.com.fiap.orbitagro.dto.common.AreaMonitoradaDTO;
import br.com.fiap.orbitagro.dto.common.CoordenadaDTO;
import br.com.fiap.orbitagro.dto.common.EnderecoDTO;
import br.com.fiap.orbitagro.dto.dado.DadoOrbitalResponse;
import br.com.fiap.orbitagro.dto.equipe.EquipeRespostaResponse;
import br.com.fiap.orbitagro.dto.produtor.ProdutorResponse;
import br.com.fiap.orbitagro.dto.regiao.RegiaoRuralResponse;
import br.com.fiap.orbitagro.dto.rota.RotaAtendimentoResponse;

public final class OrbitAgroMapper {
    private OrbitAgroMapper() {
    }

    public static Endereco toEndereco(EnderecoDTO dto) {
        if (dto == null) return null;
        return new Endereco(dto.logradouro(), dto.municipio(), dto.uf(), dto.cep());
    }

    public static EnderecoDTO toEnderecoDTO(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoDTO(endereco.getLogradouro(), endereco.getMunicipio(), endereco.getUf(), endereco.getCep());
    }

    public static CoordenadaGeografica toCoordenada(CoordenadaDTO dto) {
        if (dto == null) return null;
        return new CoordenadaGeografica(dto.latitude(), dto.longitude());
    }

    public static CoordenadaDTO toCoordenadaDTO(CoordenadaGeografica coordenada) {
        if (coordenada == null) return null;
        return new CoordenadaDTO(coordenada.getLatitude(), coordenada.getLongitude());
    }

    public static AreaMonitorada toAreaMonitorada(AreaMonitoradaDTO dto) {
        if (dto == null) return null;
        return new AreaMonitorada(dto.hectares(), dto.percentualAgriculturaFamiliar());
    }

    public static AreaMonitoradaDTO toAreaMonitoradaDTO(AreaMonitorada area) {
        if (area == null) return null;
        return new AreaMonitoradaDTO(area.getHectares(), area.getPercentualAgriculturaFamiliar());
    }

    public static RegiaoRuralResponse toResponse(RegiaoRural regiao) {
        return new RegiaoRuralResponse(
                regiao.getId(),
                regiao.getNome(),
                regiao.getMunicipio(),
                regiao.getUf(),
                toCoordenadaDTO(regiao.getCoordenada()),
                toAreaMonitoradaDTO(regiao.getAreaMonitorada()),
                regiao.getNivelConectividade()
        );
    }

    public static CooperativaResponse toResponse(Cooperativa cooperativa) {
        return new CooperativaResponse(
                cooperativa.getId(),
                cooperativa.getNome(),
                cooperativa.getCnpj(),
                cooperativa.getEmail(),
                cooperativa.getTelefone(),
                toEnderecoDTO(cooperativa.getEndereco())
        );
    }

    public static ProdutorResponse toResponse(Produtor produtor) {
        Cooperativa cooperativa = produtor.getCooperativa();
        return new ProdutorResponse(
                produtor.getId(),
                produtor.getNome(),
                produtor.getDocumento(),
                produtor.getEmail(),
                produtor.getTelefone(),
                toEnderecoDTO(produtor.getEndereco()),
                produtor.getRegiaoRural().getId(),
                produtor.getRegiaoRural().getNome(),
                cooperativa == null ? null : cooperativa.getId(),
                cooperativa == null ? null : cooperativa.getNome()
        );
    }

    public static DadoOrbitalResponse toResponse(DadoOrbital dado) {
        return new DadoOrbitalResponse(
                dado.getId(),
                dado.getDataColeta(),
                dado.getFonte(),
                dado.getIndiceVegetacaoNdvi(),
                dado.getTemperaturaSuperficie(),
                dado.getPrecipitacaoMm(),
                dado.getUmidadeSoloPercentual(),
                dado.getIndiceRiscoSeca(),
                dado.getRegiaoRural().getId(),
                dado.getRegiaoRural().getNome()
        );
    }

    public static EquipeRespostaResponse toResponse(EquipeResposta equipe) {
        return new EquipeRespostaResponse(
                equipe.getId(),
                equipe.getNome(),
                equipe.getTipoEquipe(),
                equipe.isDisponivel(),
                equipe.getCapacidadeAtendimentoDiaria(),
                equipe.getTelefoneContato()
        );
    }

    public static AlertaAgroclimaticoResponse toResponse(AlertaAgroclimatico alerta) {
        return new AlertaAgroclimaticoResponse(
                alerta.getId(),
                alerta.getCodigo(),
                alerta.getTipoRisco(),
                alerta.getNivelRisco(),
                alerta.getStatus(),
                alerta.getPrioridadeScore(),
                alerta.getDescricao(),
                alerta.getDataGeracao(),
                alerta.getRegiaoRural().getId(),
                alerta.getRegiaoRural().getNome(),
                alerta.getDadoOrbitalBase().getId(),
                alerta.getEquipesResponsaveis().stream().map(EquipeResposta::getNome).sorted().toList()
        );
    }

    public static RotaAtendimentoResponse toResponse(RotaAtendimento rota) {
        return new RotaAtendimentoResponse(
                rota.getId(),
                rota.getAlerta().getId(),
                rota.getEquipe().getId(),
                rota.getOrigem(),
                rota.getDestino(),
                toCoordenadaDTO(rota.getCoordenadaDestino()),
                rota.getDistanciaKm(),
                rota.getTempoEstimadoMinutos(),
                rota.getCustoOperacionalEstimado(),
                rota.getStatus()
        );
    }
}
