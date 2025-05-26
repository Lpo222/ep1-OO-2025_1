import models.*;
import services.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;

public class Main {

    private static AlunoService alunoService = new AlunoService();
    private static DisciplinaService disciplinaService = new DisciplinaService();
    private static TurmaService turmaService = new TurmaService();
    private static MatriculaService matriculaService = new MatriculaService();

    private static List<Aluno> todosAlunos = new ArrayList<>();
    private static Map<String, Disciplina> todasDisciplinasMap = new HashMap<>();
    private static List<Turma> todasTurmas = new ArrayList<>();

    public static void main(String[] args) {
        carregarDadosIniciais();
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        System.out.println("Bem-vindo ao Sistema Acadêmico FCTE!");

        while (executando) {
            exibirMenuPrincipal();
            System.out.print("Escolha uma opção: ");
            String opcaoStr = scanner.nextLine();
            int opcao;

            try {
                opcao = Integer.parseInt(opcaoStr);
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    modoAluno(scanner);
                    break;
                case 2:
                    modoDisciplinaTurma(scanner);
                    break;
                case 3:
                    modoAvaliacaoFrequencia(scanner);
                    break;
                case 4:
                    try {
                        salvarDadosFinais();
                        System.out.println("Sistema encerrado. Dados salvos.");
                    } catch (IOException e) {
                        System.err.println("ERRO CRÍTICO AO SALVAR DADOS: " + e.getMessage());
                        System.out.println("Alguns dados podem não ter sido salvos. Verifique os arquivos ou tente novamente.");
                    }
                    executando = false;
                    break;
                default:
                    System.out.println("Opção Inválida! Tente novamente.");
            }
            if (executando) { 
                 System.out.println("\nPressione Enter para continuar...");
                 scanner.nextLine(); 
            }
        }
        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Modo Aluno");
        System.out.println("2. Modo Disciplina/Turma");
        System.out.println("3. Modo Avaliação/Frequência");
        System.out.println("4. Salvar e Sair");
    }

    private static void carregarDadosIniciais() {
        System.out.println("Carregando dados iniciais...");
        todosAlunos = alunoService.carregarAlunos();
        todasDisciplinasMap = disciplinaService.carregarDisciplinas();

        Map<Integer, Aluno> todosAlunosMap = new HashMap<>();
        for (Aluno aluno : todosAlunos) {
            todosAlunosMap.put(aluno.getMatricula(), aluno);
        }
        todasTurmas = turmaService.carregarTurmas(todasDisciplinasMap, todosAlunosMap);
        System.out.println("Dados iniciais carregados com sucesso!");
    }

    private static void salvarDadosFinais() throws IOException {
        System.out.println("Salvando dados...");
        alunoService.gravarDadosAlunos(todosAlunos);
        disciplinaService.gravarDadosDisciplinas(new ArrayList<>(todasDisciplinasMap.values()));
        turmaService.gravarDadosTurmas(todasTurmas);
        System.out.println("Dados salvos.");
    }

    private static void modoAluno(Scanner scanner) {
        boolean continuarModoAluno = true;
        while (continuarModoAluno) {
            System.out.println("\n--- MODO ALUNO ---");
            System.out.println("1. Cadastrar Aluno (Normal/Especial)");
            System.out.println("2. Editar Aluno");
            System.out.println("3. Listar Alunos Cadastrados");
            System.out.println("4. Matricular Aluno em Disciplina/Turma");
            System.out.println("5. Trancar Disciplina para Aluno");
            System.out.println("6. Trancar Semestre para Aluno");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Opção Modo Aluno: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    cadastrarNovoAluno(scanner);
                    break;
                case "2":
                //////////////////////////
                    break;
                case "3":
                    listarAlunosCadastrados();
                    break;
                case "4":
                    matricularAlunoEmTurma(scanner);
                    break;
                case "5":
                //////////////////////////
                    break;
                case "6":
                //////////////////////////
                    break;
                case "0":
                    continuarModoAluno = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarNovoAluno(Scanner scanner) {
        System.out.println("\n--- Cadastrar Novo Aluno ---");
        try {
            System.out.print("Matrícula: ");
            String matriculaStr = scanner.nextLine();
            int matricula = Integer.parseInt(matriculaStr);

            
            for (Aluno existente : todosAlunos) {
                if (existente.getMatricula() == matricula) {
                    System.out.println("ERRO: Matrícula já cadastrada!");
                    return;
                }
            }

            System.out.print("Nome completo: ");
            String nome = scanner.nextLine();
            System.out.print("Curso de Graduação: ");
            String curso = scanner.nextLine();

            System.out.print("Tipo de Aluno (1-Normal, 2-Especial): ");
            String tipoAlunoStr = scanner.nextLine();
            Aluno novoAluno = null;

            if (tipoAlunoStr.equals("1")) {
                novoAluno = new AlunoNormal(nome, curso, matricula);
            } else if (tipoAlunoStr.equals("2")) {
                novoAluno = new AlunoEspecial(nome, curso, matricula);
            } else {
                System.out.println("Tipo de aluno inválido. Cadastro cancelado.");
                return;
            }

            todosAlunos.add(novoAluno);
            System.out.println("Aluno " + novoAluno.getNome() + " cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("ERRO: Matrícula deve ser um número.");
        } catch (NullPointerException e) { 
            System.out.println("ERRO: Nome e Curso não podem ser vazios. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    private static void listarAlunosCadastrados() {
        System.out.println("\n--- Alunos Cadastrados ---");
        if (todosAlunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            for (Aluno aluno : todosAlunos) {
                String tipo = (aluno instanceof AlunoEspecial) ? "Especial" : "Normal";
                System.out.println("Matrícula: " + aluno.getMatricula() + ", Nome: " + aluno.getNome() +
                                   ", Curso: " + aluno.getCurso() + ", Tipo: " + tipo);
                if (!aluno.getTurmasMatriculadas().isEmpty()){
                    System.out.print("  Turmas: ");
                    for(Turma t : aluno.getTurmasMatriculadas()){
                        System.out.print(t.getDisciplina().getNome() + " (T" + t.getNumero() + "); ");
                    }
                    System.out.println();
                }
            }
        }
    }

    private static void matricularAlunoEmTurma(Scanner scanner) {
        System.out.println("\n--- Matricular Aluno em Disciplina/Turma ---");
        if (todosAlunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado para matricular.");
            return;
        }
        if (todasTurmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada para matrícula.");
            return;
        }

        System.out.println("Alunos disponíveis:");
        listarAlunosCadastrados();
        System.out.print("Digite a matrícula do aluno: ");
        String matAlunoStr = scanner.nextLine();
        Aluno alunoSelecionado = null;
        try {
            int matAluno = Integer.parseInt(matAlunoStr);
            for (Aluno aluno : todosAlunos) {
                if (aluno.getMatricula() == matAluno) {
                    alunoSelecionado = aluno;
                    break;
                }
            }
            if (alunoSelecionado == null) {
                System.out.println("Aluno não encontrado.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Matrícula inválida.");
            return;
        }

        System.out.println("\nTurmas disponíveis (com vagas e que o aluno não esteja já matriculado na disciplina):");
        List<Turma> turmasDisponiveisParaMatricula = new ArrayList<>();
        int cont = 1;
        for (Turma turma : todasTurmas) {
            boolean jaMatriculadoNaDisciplina = alunoSelecionado.getTurmasMatriculadas().stream()
                .anyMatch(t -> t.getDisciplina().getCodigo().equals(turma.getDisciplina().getCodigo()));

            if (turma.getVagas() > 0 && !jaMatriculadoNaDisciplina) {
                if (matriculaService.cumpriuRequisitos(alunoSelecionado, turma)) {
                    System.out.println(cont + ". Disciplina: " + turma.getDisciplina().getNome() +
                                   " (Turma " + turma.getNumero() + ") - Vagas: " + turma.getVagas() +
                                   " - Horário: " + turma.getHorario());
                    turmasDisponiveisParaMatricula.add(turma);
                    cont++;
                }
            }
        }

        if (turmasDisponiveisParaMatricula.isEmpty()) {
            System.out.println("Nenhuma turma disponível para matrícula para este aluno (verifique vagas, matrículas anteriores na disciplina ou pré-requisitos).");
            return;
        }

        System.out.print("Escolha o número da turma para matricular: ");
        String numTurmaStr = scanner.nextLine();
        try {
            int escolhaTurma = Integer.parseInt(numTurmaStr);
            if (escolhaTurma > 0 && escolhaTurma <= turmasDisponiveisParaMatricula.size()) {
                Turma turmaEscolhida = turmasDisponiveisParaMatricula.get(escolhaTurma - 1);
                try {
                    matriculaService.matricular(alunoSelecionado, turmaEscolhida);
                    System.out.println("Matrícula de " + alunoSelecionado.getNome() + " na turma " +
                                       turmaEscolhida.getDisciplina().getNome() + " (T" + turmaEscolhida.getNumero() + ") realizada com sucesso!");
                } catch (IllegalStateException e) {
                    System.out.println("ERRO ao matricular: " + e.getMessage());
                }
            } else {
                System.out.println("Escolha inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida para número da turma.");
        }
    }


    private static void modoDisciplinaTurma(Scanner scanner) {
        System.out.println("\n--- MODO DISCIPLINA/TURMA ---");
        boolean continuarModoDisciplina = true;
        while (continuarModoDisciplina) {
            System.out.println("1. Cadastrar Disciplina");
            System.out.println("2. Criar Turma para Disciplina");
            System.out.println("3. Listar Disciplinas Cadastradas");
            System.out.println("4. Listar Turmas Disponíveis (com detalhes)");
            System.out.println("5. Listar Alunos Matriculados por Turma");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Opção Modo Disciplina/Turma: ");
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1":
                    cadastrarNovaDisciplina(scanner);
                    break;
                case "2":
                    criarNovaTurma(scanner);
                    break;
                case "3":
                    listarDisciplinasCadastradas();
                    break;
                case "4":
                    listarTurmasComDetalhes();
                    break;
                 case "5":
                    listarAlunosPorTurma(scanner);
                    break;
                case "0":
                    continuarModoDisciplina = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarNovaDisciplina(Scanner scanner) {
        System.out.println("\n--- Cadastrar Nova Disciplina ---");
        try {
            System.out.print("Código da Disciplina: ");
            String codigo = scanner.nextLine().toUpperCase();
            if (todasDisciplinasMap.containsKey(codigo)) {
                System.out.println("ERRO: Código de disciplina já existente.");
                return;
            }

            System.out.print("Nome da Disciplina: ");
            String nome = scanner.nextLine();
            System.out.print("Carga Horária: ");
            int cargaHoraria = Integer.parseInt(scanner.nextLine());

            System.out.print("Códigos dos Pré-requisitos (separados por vírgula, ou deixe em branco se não houver): ");
            String preRequisitosInput = scanner.nextLine();
            List<String> preRequisitosList = new ArrayList<>();
            if (!preRequisitosInput.trim().isEmpty()) {
                String[] codigosPreReq = preRequisitosInput.split(",");
                for (String codPreReq : codigosPreReq) {
                    String trimmedCod = codPreReq.trim().toUpperCase();
                    if (!trimmedCod.isEmpty()) {
                         if (!todasDisciplinasMap.containsKey(trimmedCod)) {
                            System.out.println("AVISO: Pré-requisito com código '" + trimmedCod + "' não existe. Será adicionado, mas verifique.");
                        }
                        if (trimmedCod.equals(codigo)){
                            System.out.println("ERRO: Uma disciplina não pode ser pré-requisito de si mesma.");
                            return;
                        }
                        preRequisitosList.add(trimmedCod);
                    }
                }
            }

            Disciplina novaDisciplina = new Disciplina(codigo, nome, preRequisitosList, cargaHoraria);
            todasDisciplinasMap.put(codigo, novaDisciplina);
            System.out.println("Disciplina " + nome + " cadastrada com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("ERRO: Carga horária deve ser um número.");
        } catch (NullPointerException e) {
            System.out.println("ERRO: Código e Nome não podem ser vazios. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
    
    private static void listarDisciplinasCadastradas() {
        System.out.println("\n--- Disciplinas Cadastradas ---");
        if (todasDisciplinasMap.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
        } else {
            for (Disciplina disciplina : todasDisciplinasMap.values()) {
                System.out.print("Código: " + disciplina.getCodigo() + ", Nome: " + disciplina.getNome() +
                                   ", CH: " + disciplina.getCargaHoraria());
                if (!disciplina.getPreRequisitos().isEmpty()) {
                    System.out.print(", Pré-requisitos: " + String.join(", ", disciplina.getPreRequisitos()));
                }
                System.out.println();
            }
        }
    }


    private static void criarNovaTurma(Scanner scanner) {
        System.out.println("\n--- Criar Nova Turma ---");
        if (todasDisciplinasMap.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada. Crie uma disciplina primeiro.");
            return;
        }

        System.out.println("Disciplinas disponíveis:");
        listarDisciplinasCadastradas();
        System.out.print("Digite o código da disciplina para a turma: ");
        String codDisciplina = scanner.nextLine().toUpperCase();
        Disciplina disciplinaSelecionada = todasDisciplinasMap.get(codDisciplina);

        if (disciplinaSelecionada == null) {
            System.out.println("Disciplina não encontrada.");
            return;
        }

        try {
            
            int novoNumeroTurma = 1;
            for(Turma t : todasTurmas){
                if(t.getDisciplina().getCodigo().equals(codDisciplina) && t.getNumero() >= novoNumeroTurma){
                    novoNumeroTurma = t.getNumero() + 1;
                }
            }

            System.out.print("Horário (ex: Seg 10:00-12:00): ");
            String horario = scanner.nextLine();

            for(Turma tExistente : todasTurmas){
                if(tExistente.getDisciplina().getCodigo().equals(codDisciplina) && tExistente.getHorario().equalsIgnoreCase(horario)){
                    System.out.println("ERRO: Já existe uma turma para esta disciplina neste horário.");
                    return;
                }
            }

            System.out.print("Professor: ");
            String professor = scanner.nextLine();
            System.out.print("Capacidade Máxima de Alunos: ");
            int capacidade = Integer.parseInt(scanner.nextLine());
            System.out.print("Sala (deixe em branco se remota): ");
            String sala = scanner.nextLine();
            System.out.print("Forma de Avaliação (descrição): ");
            String formaAvaliacao = scanner.nextLine();
            
            Turma novaTurma = new Turma(novoNumeroTurma, disciplinaSelecionada, horario, professor, capacidade, sala, formaAvaliacao);
            todasTurmas.add(novaTurma);
            System.out.println("Turma " + novoNumeroTurma + " para " + disciplinaSelecionada.getNome() + " criada com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("ERRO: Capacidade máxima deve ser um número.");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    private static void listarTurmasComDetalhes() {
        System.out.println("\n--- Turmas Disponíveis ---");
        if (todasTurmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
        } else {
            for (Turma turma : todasTurmas) {
                System.out.println("Turma Nº: " + turma.getNumero() +
                                   " | Disciplina: " + turma.getDisciplina().getNome() + " (" + turma.getDisciplina().getCodigo() + ")" +
                                   " | Professor: " + turma.getProfessor() +
                                   " | Horário: " + turma.getHorario() +
                                   " | Vagas Restantes: " + turma.getVagas() + 
                                   " | Sala: " + (turma.getSala().isEmpty() ? "Remota/N/A" : turma.getSala()) +
                                   " | Avaliação: " + turma.getAvaliacao() +
                                   " | Aulas Ministradas: " + turma.getAulasMinistradas());
                if (!turma.getAlunosMatriculados().isEmpty()) {
                    System.out.print("    Alunos Matriculados: ");
                    List<String> nomesAlunos = new ArrayList<>();
                    for (Aluno aluno : turma.getAlunosMatriculados().keySet()) {
                        nomesAlunos.add(aluno.getNome() + " (Mat: " + aluno.getMatricula() + ")");
                    }
                    System.out.println(String.join(", ", nomesAlunos));
                } else {
                    System.out.println("    Nenhum aluno matriculado nesta turma.");
                }
                 System.out.println("---");
            }
        }
    }
    
    private static void listarAlunosPorTurma(Scanner scanner){
        System.out.println("\n--- Listar Alunos por Turma ---");
        if (todasTurmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
            return;
        }
        System.out.println("Turmas disponíveis:");
        for(int i=0; i < todasTurmas.size(); i++){
            Turma t = todasTurmas.get(i);
            System.out.println((i+1) + ". " + t.getDisciplina().getNome() + " (Turma " + t.getNumero() + ")");
        }
        System.out.print("Escolha o número da turma: ");
        try {
            int escolha = Integer.parseInt(scanner.nextLine());
            if(escolha > 0 && escolha <= todasTurmas.size()){
                Turma turmaSelecionada = todasTurmas.get(escolha-1);
                System.out.println("\n--- Alunos da Turma: " + turmaSelecionada.getDisciplina().getNome() + " (T" + turmaSelecionada.getNumero() + ") ---");
                if(turmaSelecionada.getAlunosMatriculados().isEmpty()){
                    System.out.println("Nenhum aluno matriculado nesta turma.");
                } else {
                    for(Map.Entry<Aluno, Float> entry : turmaSelecionada.getAlunosMatriculados().entrySet()){
                        Aluno aluno = entry.getKey();
                        Float nota = entry.getValue();
                        System.out.println(" - Nome: " + aluno.getNome() + " (Mat: " + aluno.getMatricula() + "), Nota: " + nota /*+ ", Presenças: " + presencas*/);
                    }
                }
            } else {
                System.out.println("Escolha inválida.");
            }
        } catch (NumberFormatException e){
            System.out.println("Entrada inválida.");
        }
    }


    private static void modoAvaliacaoFrequencia(Scanner scanner) {
        System.out.println("\n--- MODO AVALIAÇÃO/FREQUÊNCIA ---");
        boolean continuarModoAvaliacao = true;
        while (continuarModoAvaliacao) {
            System.out.println("1. Lançar Notas por Turma/Aluno");
            System.out.println("2. Registrar Aula Ministrada para Turma");
            System.out.println("3. Lançar Presença para Aluno em Aula");
            System.out.println("4. Exibir Status de Alunos na Turma (Notas, Frequência, Aprovação)");
            System.out.println("5. Exibir Relatórios (por turma, disciplina, professor) - A implementar");
            System.out.println("6. Exibir Boletim por Aluno - A implementar");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Opção Modo Avaliação/Frequência: ");
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1":
                    lancarNotas(scanner);
                    break;
                case "2":
                     registrarAulaMinistrada(scanner);
                    break;
                case "3":
                    lancarPresencaAluno(scanner);
                    break;
                case "4":
                    exibirStatusAlunosTurma(scanner);
                    break;
                case "0":
                    continuarModoAvaliacao = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void lancarNotas(Scanner scanner) {
        System.out.println("\n--- Lançar Notas ---");
        System.out.println("Funcionalidade 'Lançar Notas' a ser implementada com mais detalhes.");
        System.out.println("Exemplo: Selecionar turma, depois aluno, depois inserir nota.");
        
        if (todasTurmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
            return;
        }
        System.out.println("Selecione a Turma:");
        for (int i = 0; i < todasTurmas.size(); i++) {
            System.out.println((i + 1) + ". " + todasTurmas.get(i).getDisciplina().getNome() + " (T" + todasTurmas.get(i).getNumero() + ")");
        }
        System.out.print("Escolha o número da turma: ");
        try {
            int idxTurma = Integer.parseInt(scanner.nextLine()) - 1;
            if (idxTurma < 0 || idxTurma >= todasTurmas.size()) {
                System.out.println("Seleção de turma inválida.");
                return;
            }
            Turma turmaSelecionada = todasTurmas.get(idxTurma);

            if (turmaSelecionada.getAlunosMatriculados().isEmpty()) {
                System.out.println("Nenhum aluno matriculado nesta turma.");
                return;
            }
            System.out.println("Selecione o Aluno da turma " + turmaSelecionada.getDisciplina().getNome() + ":");
            List<Aluno> alunosDaTurma = new ArrayList<>(turmaSelecionada.getAlunosMatriculados().keySet());
            for (int i = 0; i < alunosDaTurma.size(); i++) {
                System.out.println((i + 1) + ". " + alunosDaTurma.get(i).getNome() + " (Mat: " + alunosDaTurma.get(i).getMatricula() + ")");
            }
             System.out.print("Escolha o número do aluno: ");
            int idxAluno = Integer.parseInt(scanner.nextLine()) - 1;
            if (idxAluno < 0 || idxAluno >= alunosDaTurma.size()) {
                System.out.println("Seleção de aluno inválida.");
                return;
            }
            Aluno alunoSelecionado = alunosDaTurma.get(idxAluno);

            if (alunoSelecionado instanceof AlunoEspecial) {
                System.out.println("Aluno Especial (" + alunoSelecionado.getNome() + ") não recebe notas.");
                return;
            }

            System.out.print("Digite a nota para " + alunoSelecionado.getNome() + " (0.0 a 10.0): ");
            float nota = Float.parseFloat(scanner.nextLine());
            if(nota < 0 || nota > 10){
                System.out.println("Nota inválida. Deve ser entre 0.0 e 10.0.");
                return;
            }
            turmaSelecionada.registrarNota(alunoSelecionado, nota);
            System.out.println("Nota " + nota + " registrada para " + alunoSelecionado.getNome() + " na turma " + turmaSelecionada.getDisciplina().getNome());

        } catch (NumberFormatException e) {
            System.out.println("Entrada numérica inválida.");
        } catch (IllegalStateException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private static void registrarAulaMinistrada(Scanner scanner) {
        System.out.println("\n--- Registrar Aula Ministrada ---");
        if (todasTurmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
            return;
        }
        System.out.println("Selecione a Turma para registrar uma aula ministrada:");
        for (int i = 0; i < todasTurmas.size(); i++) {
            System.out.println((i + 1) + ". " + todasTurmas.get(i).getDisciplina().getNome() + " (T" + todasTurmas.get(i).getNumero() + ")");
        }
        System.out.print("Escolha o número da turma: ");
        try {
            int idxTurma = Integer.parseInt(scanner.nextLine()) - 1;
            if (idxTurma < 0 || idxTurma >= todasTurmas.size()) {
                System.out.println("Seleção de turma inválida.");
                return;
            }
            Turma turmaSelecionada = todasTurmas.get(idxTurma);
            turmaSelecionada.aulaMinistrada();
            System.out.println("Aula registrada para turma " + turmaSelecionada.getDisciplina().getNome() + 
                               ". Total de aulas ministradas: " + turmaSelecionada.getAulasMinistradas());
        } catch (NumberFormatException e) {
            System.out.println("Entrada numérica inválida.");
        }
    }

    private static void lancarPresencaAluno(Scanner scanner) {
        System.out.println("\n--- Lançar Presença de Aluno em Aula ---");
        System.out.println("Funcionalidade 'Lançar Presença' a ser implementada com mais detalhes.");
        System.out.println("Lembre-se de ajustar a lógica de contagem de presença e aulas ministradas na classe Turma.");
    }

    private static void exibirStatusAlunosTurma(Scanner scanner){

    }
}