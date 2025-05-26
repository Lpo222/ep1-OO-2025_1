package services;

import models.Aluno;
import models.Disciplina;
import models.Turma;
import utils.ArquivosUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurmaService {

    private static final String ARQUIVO_TURMAS = "data/turmas.txt";
    private static final String ARQUIVO_TURMA_ALUNOS_DADOS = "data/turma_alunos_dados.txt";

    public void gravarDadosTurmas(List<Turma> turmas) {
        if (turmas == null) {
            turmas = new ArrayList<>();
        }

        List<String> linhasTurmas = new ArrayList<>();
        List<String> linhasTurmaAlunosDados = new ArrayList<>();

        for (Turma turma : turmas) {

            String linhaTurma = turma.getNumero() + ";" +
                                turma.getDisciplina().getCodigo() + ";" +
                                turma.getHorario() + ";" +
                                turma.getProfessor() + ";" +
                                turma.getVagas() + ";" + 
                                turma.getSala() + ";" +
                                turma.getAvaliacao() + ";" +
                                turma.getAulasMinistradas();
            linhasTurmas.add(linhaTurma);

            if (turma.getAlunosMatriculados() != null) {
                for (Map.Entry<Aluno, Float> entryNota : turma.getAlunosMatriculados().entrySet()) {
                    Aluno aluno = entryNota.getKey();
                    Float nota = entryNota.getValue();

                    Integer presencasCount = turma.getPresenca().getOrDefault(aluno, 0);

                    String linhaAlunoDado = turma.getNumero() + ";" +
                                            aluno.getMatricula() + ";" +
                                            nota + ";" +
                                            presencasCount;
                    linhasTurmaAlunosDados.add(linhaAlunoDado);
                }
            }
        }

        try {
            ArquivosUtils.escreverArquivo(ARQUIVO_TURMAS, linhasTurmas);
            ArquivosUtils.escreverArquivo(ARQUIVO_TURMA_ALUNOS_DADOS, linhasTurmaAlunosDados);
            System.out.println("Dados das turmas e detalhes dos alunos gravados com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao gravar dados das turmas: " + e.getMessage());
        }
    }

    public List<Turma> carregarTurmas(Map<String, Disciplina> todasDisciplinas, Map<Integer, Aluno> todosAlunos) {
        Map<Integer, Turma> turmasCarregadasMap = new HashMap<>();

        try {
            List<String> linhasTurmas = ArquivosUtils.lerLinhasDoArquivo(ARQUIVO_TURMAS);
            for (String linha : linhasTurmas) {
                if (linha == null || linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";", -1);

                if (dados.length >= 8) { // numero;codDisciplina;horario;prof;capacVagas;sala;avaliacao;aulasMinistradas
                    try {
                        int numero = Integer.parseInt(dados[0].trim());
                        String codDisciplina = dados[1].trim();
                        String horario = dados[2].trim();
                        String professor = dados[3].trim();
                        int capacidadeVagas = Integer.parseInt(dados[4].trim());
                        String sala = dados[5].trim();
                        String avaliacao = dados[6].trim();
                        int aulasMinistradas = Integer.parseInt(dados[7].trim());

                        Disciplina disciplina = todasDisciplinas.get(codDisciplina);
                        if (disciplina == null) {
                            System.err.println("Disciplina com código " + codDisciplina + " não encontrada para turma " + numero + ". Pulando turma.");
                            continue;
                        }

                        Turma turma = new Turma(numero, disciplina, horario, professor, capacidadeVagas, sala, avaliacao);
                        turma.setAulasMinistradas(aulasMinistradas);
                        turmasCarregadasMap.put(numero, turma);

                    } catch (NumberFormatException e) {
                        System.err.println("Erro de formato numérico ao carregar turma: '" + linha + "'. " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Erro ao processar linha da turma: '" + linha + "'. " + e.getMessage());
                    }
                } else {
                     System.err.println("Formato de linha inválido para turma: '" + linha + "'. Esperava 8 campos.");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de turmas " + ARQUIVO_TURMAS + ": " + e.getMessage());
        }

        try {
            List<String> linhasTurmaAlunosDados = ArquivosUtils.lerLinhasDoArquivo(ARQUIVO_TURMA_ALUNOS_DADOS);
            for (String linha : linhasTurmaAlunosDados) {
                if (linha == null || linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";", -1);

                if (dados.length == 4) { // numeroTurma;matriculaAluno;notaAluno;presencasAluno
                    try {
                        int numeroTurma = Integer.parseInt(dados[0].trim());
                        int matriculaAluno = Integer.parseInt(dados[1].trim());
                        float nota = Float.parseFloat(dados[2].trim());
                        int presencasCount = Integer.parseInt(dados[3].trim());

                        Turma turma = turmasCarregadasMap.get(numeroTurma);
                        Aluno aluno = todosAlunos.get(matriculaAluno);

                        if (turma != null && aluno != null) {
                            if (!turma.getAlunosMatriculados().containsKey(aluno)) {
                                turma.getAlunosMatriculados().put(aluno, nota);
                            } else {
                                turma.getAlunosMatriculados().put(aluno, nota);
                            }
                            
                            turma.getPresenca().put(aluno, presencasCount);

                        } else {
                            if (turma == null) System.err.println("Turma " + numeroTurma + " não encontrada para dados do aluno.");
                            if (aluno == null) System.err.println("Aluno com matrícula " + matriculaAluno + " não encontrado para dados da turma.");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Erro de formato numérico ao carregar dados de aluno na turma: '" + linha + "'. " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Erro ao processar linha de dados de aluno na turma: '" + linha + "'. " + e.getMessage());
                    }
                } else {
                    System.err.println("Formato de linha inválido para dados de aluno na turma: '" + linha + "'. Esperava 4 campos.");
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de dados de alunos nas turmas " + ARQUIVO_TURMA_ALUNOS_DADOS + ": " + e.getMessage());
        }

        return new ArrayList<>(turmasCarregadasMap.values());
    }
}