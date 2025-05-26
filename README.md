# Sistema Acadêmico - FCTE

## Descrição do Projeto

Desenvolvimento de um sistema acadêmico para gerenciar alunos, disciplinas, professores, turmas, avaliações e frequência, utilizando os conceitos de orientação a objetos (herança, polimorfismo e encapsulamento) e persistência de dados em arquivos.

O enunciado do trabalho pode ser encontrado aqui:
- [Trabalho 1 - Sistema Acadêmico](https://github.com/lboaventura25/OO-T06_2025.1_UnB_FCTE/blob/main/trabalhos/ep1/README.md)

## Dados do Aluno

- **Nome completo:** Leonardo Póvoa Ortegal;
- **Matrícula:** 232014075;
- **Curso:** Eng. de Software;
- **Turma:** 06;

---

## Instruções para Compilação e Execução

1.  **Pré-requisitos:**
    * JDK (Java Development Kit) instalado (Versão openjdk 17.0.15 2025-04-15 ou superior).
    * Git (para clonar o repositório, opcionalmente).

2.  **Obtendo o Código:**
    * Clone o repositório (se aplicável):
        ```bash
        git clone https://github.com/Lpo222/ep1-OO-2025_1
        cd TP1
        ```
    * Ou baixe os arquivos do projeto e descompacte-os.

3.  **Compilação:**
    * Abra um terminal ou prompt de comando.
    * Navegue até a pasta raiz do projeto (a pasta que contém a pasta `src` e `data`).
    * Execute o seguinte comando para compilar os arquivos Java e colocar os arquivos `.class` em uma pasta `bin` (crie a pasta `bin` se não existir):
        ```bash
        javac -d bin -cp src src/Main.java src/models/*.java src/services/*.java src/utils/*.java
        ```
    * *Alternativa (se não usar a pasta `bin` e compilar tudo dentro de `src`):*
        Navegue até a pasta `src`:
        ```bash
        cd src
        javac Main.java models/*.java services/*.java utils/*.java
        cd ..
        ```

4.  **Execução:**
    * No terminal, a partir da pasta raiz do projeto (a mesma onde você compilou):
        * Se usou a pasta `bin`:
            ```bash
            java -cp bin Main
            ```
        * Se compilou tudo dentro de `src` (e está na raiz do projeto):
            ```bash
            java -cp src Main
            ```
    * O programa iniciará e exibirá o menu principal no console. Os arquivos de dados (`alunos.txt`, `disciplinas.txt`, etc.) serão criados/lidos na pasta `data/` (localizada na raiz do projeto).

5.  **Estrutura de Pastas Principal:**
    ```
    [NOME_DA_RAIZ_DO_PROJETO]/
    ├── src/                # Código fonte (.java)
    │   ├── models/         # Classes de entidade (Aluno, Disciplina, Turma, etc.)
    │   ├── services/       # Classes de lógica de negócio e persistência (AlunoService, etc.)
    │   ├── utils/          # Classes utilitárias (ArquivosUtils)
    │   └── Main.java       # Classe principal com a interface do usuário via terminal
    ├── data/               # Arquivos de dados (.txt) gerados/lidos pelo sistema
    ├── bin/                # Arquivos compilados (.class) - (opcional, depende do método de compilação)
    └── README.md           # Este arquivo
    ```

6.  **Versão do JAVA utilizada:**
    * openjdk 17.0.15 2025-04-15

---

## Vídeo de Demonstração

-

---



## Principais Funcionalidades Implementadas



- [ ] **Modo Aluno:**
    - [x] Cadastro de Alunos (Normais e Especiais)
    - [x] Listagem de Alunos
    - [x] Matrícula de Alunos em Turmas (com verificação de vagas, pré-requisitos e tipo de aluno)
    - [ ] Edição de Alunos
    - [ ] Trancamento de Disciplina para Aluno
    - [ ] Trancamento de Semestre para Aluno
- [ ] **Modo Disciplina/Turma:**
    - [x] Cadastro de Disciplinas (com código, nome, carga horária, pré-requisitos)
    - [x] Listagem de Disciplinas
    - [x] Criação de Turmas para Disciplinas (com número, professor, horário, capacidade)
    - [x] Listagem de Turmas (com detalhes básicos)
    - [ ] Listagem de Alunos Matriculados por Turma (detalhada)
- [ ] **Modo Avaliação/Frequência:**
    - [x] Lançamento de Notas para alunos (respeitando Aluno Especial)
    - [x] Registro de Aula Ministrada para Turma
    - [x] Lançamento de Presença para Aluno (com ressalvas sobre a lógica de contagem na Turma)
    - [x] Verificação de Situação do Aluno na Turma (Nota, Frequência Simplificada, Status Aprovação)
    - [ ] Cálculo de Média Final (conforme as duas fórmulas do enunciado)
    - [ ] Relatórios de desempenho (básicos ou completos)
    - [ ] Exibição de Boletim por Aluno
- [ ] **Geral:**
    - [x] Persistência de dados de Alunos, Disciplinas e Turmas em arquivos (`.txt`)
    - [x] Menu principal navegável via terminal
    - [x] Uso de Herança (Aluno -> AlunoNormal, AlunoEspecial)
    - [x] Uso de Polimorfismo (ex: ao tratar a lista `todosAlunos`)
    - [x] Encapsulamento nas classes de modelo e serviço
    - [x] Verificação de duplicidade de matrícula de aluno e código de disciplina

---

## Observações (Extras ou Dificuldades)

- Main mesmo que incompleto ficou imenso. Minha primeira vez fazendo um projeto que envolvesse mais de um arquivo, acabei me perdendo multiplas vezes
na própria estrutua dos folders.

---

