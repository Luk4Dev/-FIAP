package br.com.fiap.orbitagro.config;

import br.com.fiap.orbitagro.domain.entity.*;
import br.com.fiap.orbitagro.domain.enums.NivelConectividade;
import br.com.fiap.orbitagro.domain.enums.TipoEquipe;
import br.com.fiap.orbitagro.domain.valueobject.AreaMonitorada;
import br.com.fiap.orbitagro.domain.valueobject.CoordenadaGeografica;
import br.com.fiap.orbitagro.domain.valueobject.Endereco;
import br.com.fiap.orbitagro.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    CommandLineRunner seedData(RegiaoRuralRepository regiaoRepository,
                               CooperativaRepository cooperativaRepository,
                               ProdutorRepository produtorRepository,
                               DadoOrbitalRepository dadoRepository,
                               EquipeRespostaRepository equipeRepository) {
        return args -> {
            if (regiaoRepository.count() > 0) return;

            RegiaoRural regiao = regiaoRepository.save(new RegiaoRural(
                    "Polo Rural Baixa Conectividade - MATOPIBA",
                    "Balsas",
                    "MA",
                    new CoordenadaGeografica(-7.5321, -46.0372),
                    new AreaMonitorada(14500.0, 68.0),
                    NivelConectividade.BAIXA
            ));

            Cooperativa cooperativa = cooperativaRepository.save(new Cooperativa(
                    "Cooperativa AgroFamiliar do Cerrado",
                    "12345678000190",
                    "contato@agrofamiliar.org.br",
                    "(99) 90000-1000",
                    new Endereco("Estrada Rural KM 12", "Balsas", "MA", "65800-000")
            ));

            produtorRepository.save(new Produtor(
                    "Maria de Lourdes Silva",
                    "11122233344",
                    "maria.produtora@email.com",
                    "(99) 98888-1111",
                    new Endereco("Sítio Boa Esperança", "Balsas", "MA", "65800-000"),
                    regiao,
                    cooperativa
            ));

            produtorRepository.save(new Produtor(
                    "João Pereira Santos",
                    "55566677788",
                    "joao.produtor@email.com",
                    "(99) 97777-2222",
                    new Endereco("Fazenda Lagoa Seca", "Balsas", "MA", "65800-000"),
                    regiao,
                    cooperativa
            ));

            dadoRepository.save(new DadoOrbital(
                    LocalDate.now().minusDays(1),
                    "NASA POWER / Sentinel Simulado",
                    0.22,
                    36.4,
                    0.8,
                    18.0,
                    88.0,
                    regiao
            ));

            equipeRepository.save(new EquipeResposta(
                    "Equipe Técnica Cerrado Norte",
                    TipoEquipe.TECNICA_AGRICOLA,
                    4,
                    "(99) 96666-3000"
            ));

            equipeRepository.save(new EquipeResposta(
                    "Equipe Conectividade Rural",
                    TipoEquipe.SUPORTE_CONECTIVIDADE,
                    3,
                    "(99) 95555-4000"
            ));
        };
    }
}
