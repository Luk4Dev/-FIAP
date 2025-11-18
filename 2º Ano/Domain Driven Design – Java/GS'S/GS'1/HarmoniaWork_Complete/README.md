# Nomes:
Diego Garcia Tosta RM: 556724
Joud Jihad Jaber   RM: 556482
Lucca Pereira      RM: 560731

# HarmoniaWork

Projeto Em Java - GS 2025.

Estrutura: DDD (Domain-Driven Design) com Java e Swing. Repositórios em memória.

## Como usar
1. Abra o projeto no VS Code ou sua IDE Java preferida.
2. Certifique-se de ter JDK 11+ instalado.
3. Compile e execute a classe `br.com.harmoniawork.ui.swing.MainWindow`.
4. Use a UI para cadastrar colaboradores, registrar atividades e gerar recomendações.

## Estrutura de pacotes
- `domain` - entidades, value objects, services e interfaces de repositório.
- `application` - casos de uso (orquestra lógica entre domínio e infra).
- `infrastructure` - implementações de repositórios em memória.
- `ui.swing` - telas Swing (MainWindow, cadastro, registro, dashboard).

