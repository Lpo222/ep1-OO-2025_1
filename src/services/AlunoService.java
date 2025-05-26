package services;

import models.Aluno;
import models.AlunoEspecial;
import models.AlunoNormal;
import utils.ArquivosUtils;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class AlunoService {

    private static final String arquivoAlunos = "data/alunos.txt";

    public void gravarDadosAlunos(List<Aluno> alunos) {
        if (alunos == null || alunos.isEmpty()) {
            System.out.println("Nenhum aluno para gravar.");
            return;
        }

        List<String> linhasParaSalvar = new ArrayList<>();
        for (Aluno aluno : alunos) {
            String tipoAluno;
            if (aluno instanceof AlunoEspecial) {
                tipoAluno = "ESPECIAL";
            } else if (aluno instanceof AlunoNormal) {
                tipoAluno = "NORMAL";
            } else {
                System.err.println("Tipo de aluno não reconhecido durante a gravação: " + aluno.getClass().getName());
                continue;
            }

            String linha = aluno.getMatricula() + ";" +
                           aluno.getNome() + ";" +
                           aluno.getCurso() + ";" +
                           tipoAluno;
            linhasParaSalvar.add(linha);
        }

        try {
            ArquivosUtils.escreverArquivo(arquivoAlunos, linhasParaSalvar);
            System.out.println("Dados dos alunos gravados com sucesso em " + arquivoAlunos);
        } catch (IOException e) {
            System.err.println("Erro ao gravar dados dos alunos no arquivo " + arquivoAlunos + ": " + e.getMessage());
        }
    }

    public List<Aluno> carregarAlunos() {
        List<Aluno> alunosCarregados = new ArrayList<>();
        List<String> linhasDoArquivo;

        try {
            linhasDoArquivo = ArquivosUtils.lerLinhasDoArquivo(arquivoAlunos);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + arquivoAlunos + ": " + e.getMessage());
            return alunosCarregados;
        }

        for (String linha : linhasDoArquivo) {
            if (linha == null || linha.trim().isEmpty()) {
                continue;
            }

            String[] dados = linha.split(";", -1);

            if (dados.length == 4) {
                try {
                    int matricula = Integer.parseInt(dados[0].trim());
                    String nome = dados[1].trim();
                    String curso = dados[2].trim();
                    String tipoAlunoStr = dados[3].trim().toUpperCase();

                    Aluno aluno = null;
                    if ("NORMAL".equals(tipoAlunoStr)) {
                        aluno = new AlunoNormal(nome, curso, matricula);
                    } else if ("ESPECIAL".equals(tipoAlunoStr)) {
                        aluno = new AlunoEspecial(nome, curso, matricula);
                    } else {
                        System.err.println("Tipo de aluno desconhecido na linha: '" + linha + "'. Pulando aluno.");
                        continue;
                    }
                    alunosCarregados.add(aluno);

                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter matrícula para número na linha: '" + linha + "'. Pulando aluno.");
                } catch (Exception e) {
                    System.err.println("Erro ao processar aluno da linha: '" + linha + "': " + e.getMessage() + ". Pulando aluno.");
                }
            } else {
                System.err.println("Formato de linha inválido (esperava 4 campos, obteve " + dados.length + "): '" + linha + "'. Pulando linha.");
            }
        }

        System.out.println(alunosCarregados.size() + " alunos carregados de " + arquivoAlunos);
        return alunosCarregados;
    }
}