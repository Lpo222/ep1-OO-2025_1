package services;

import models.Aluno;
import utils.ArquivosUtils;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class AlunoService{

    private static final String arquivoAlunos = "data/alunos.txt";

    public void gravarDadosAlunos(List<Aluno> alunos){
        if (alunos == null || alunos.isEmpty()){
            System.out.println("Nenhum aluno para gravar.");
            return;
        }

        List<String> linhasParaSalvar = new ArrayList<>();
        for (Aluno aluno : alunos){
            String tipoAluno = (aluno.getClass().getSimpleName().equals("AlunoEspecial")) ? "ESPECIAL" : "NORMAL"; // Exemplo

            String linha = aluno.getMatricula() + ";" + aluno.getNome() + ";" + aluno.getCurso() + ";" + tipoAluno;
            linhasParaSalvar.add(linha);
        }

        try{
            ArquivosUtils.escreverArquivo(arquivoAlunos, linhasParaSalvar);
            System.out.println("Dados dos alunos gravados com sucesso em " + arquivoAlunos);
        }catch (IOException e) {
            System.err.println("Erro ao gravar dados dos alunos no arquivo " + arquivoAlunos + ": " + e.getMessage());
        }
    }
}
