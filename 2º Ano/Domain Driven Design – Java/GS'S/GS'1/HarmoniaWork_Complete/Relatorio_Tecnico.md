Relatório Técnico - HarmoniaWork
=================================

1. Domínio escolhido e problema resolvido
-----------------------------------------
Domínio: Gestão Inteligente de Bem-Estar e Integração no Trabalho Híbrido.
Propósito: reduzir burnout (Problema A) e diminuir desigualdade entre trabalho remoto e presencial (Problema D).
O sistema HarmoniaWork monitora indicadores de bem-estar, registra atividades e gera recomendações que promovem ergonomia,
pausas e ações de integração para colaboradores remotos e presenciais.

2. Decisões de modelagem (entidades e serviços)
-----------------------------------------------
Entidades principais:
- Colaborador: representa o usuário do sistema, armazena nível de estresse, índice de integração, modo de trabalho e indicadores.
- Atividade: representa ações realizadas (reunião, pausa, meditação etc.) que impactam estresse e integração.
- SessaoDeTrabalho: agregado que relaciona colaborador e atividades realizadas em um período.
Objeto de valor:
- IndicadoresDeBemEstar: encapsula fadiga, ergonomia, luz, qualidade do ar e clima.
Serviços de domínio:
- CalculoIndicadoresService: encapsula lógica de cálculo do score de bem-estar.
- IARecomendacoesService: encapsula heurísticas que geram recomendações baseadas no estado do colaborador.

3. Justificativa do design da interface Swing
---------------------------------------------
A interface é propositalmente simples e funcional, com três telas centrais:
cadastro de colaboradores, registro de atividades e dashboard de recomendações.
A separação entre UI e casos de uso mantém a interface leve: a UI apenas coleta dados do usuário e delega a lógica aos use cases,
preservando regras de negócio no domínio. O uso do Swing atende ao requisito da matéria.

4. Boas práticas de OO aplicadas
--------------------------------
- Encapsulamento: atributos privados e métodos públicos controlam acesso ao estado interno.
- Coesão: entidades contêm regras de negócio relacionadas (por exemplo, aplicarAtividade em Colaborador).
- Baixo acoplamento: repositórios são interfaces no domínio; a infraestrutura fornece implementações em memória.
- Abstração: use cases expõem operações de alto nível (cadastrar, registrar, gerar recomendações).
- Reutilização: serviços de domínio (IARecomendacoesService, CalculoIndicadoresService) reutilizáveis por diferentes casos de uso.

5. Como o projeto atende ao enunciado
-------------------------------------
- Modelagem DDD: domínio centralizado com linguagem ubíqua e separação clara entre camadas.
- OO: herança não foi necessária, mas foram usados encapsulamento, composição e abstração.
- Implementação Java + Swing: código pronto para compilar e executar com interface funcional.
- Persistência: repositórios em memória (coleções) conforme pedido; fácil substituição por DB.
- Relatório técnico: este arquivo descreve as escolhas arquiteturais e de modelagem.

6. Observações finais
---------------------
O projeto é uma base sólida para expansão: adicionar persistência (SQLite/H2), autenticação, relatórios exportáveis,
indicadores históricos e integração com sensores ambientais. A IA aqui é heurística e pode ser substituída por modelos ML/IA
se necessário.
