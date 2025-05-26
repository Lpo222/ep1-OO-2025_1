package services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import models.Aluno;
import models.AlunoEspecial;
import models.Disciplina;
import models.Turma;

public class MatriculaService {

    public boolean cumpriuRequisitos(Aluno aluno, Turma turma){
    
        List<String> codigosPreRequisitos = turma.getDisciplina().getPreRequisitos();

        List<String> codigosDisciplinasConcluidas = aluno.getDisciplinasConcluidas().stream().map(Disciplina::getCodigo).collect(Collectors.toList());

        return codigosDisciplinasConcluidas.containsAll(codigosPreRequisitos);
    }

    private List<String> getPreRequisitosFaltantes(Aluno aluno, Turma turma){
        List<String> codigosDisciplinasConcluidas = aluno.getDisciplinasConcluidas().stream().map(Disciplina::getCodigo).collect(Collectors.toList());
        return turma.getDisciplina().getPreRequisitos().stream().filter(req -> !codigosDisciplinasConcluidas.contains(req)).toList();    
    }


    public void matricular(Aluno aluno, Turma turma){
        Objects.requireNonNull(aluno, "aluno nao pode ser nulo.");
        Objects.requireNonNull(turma, "turma nao pode ser nula.");

        if (aluno instanceof AlunoEspecial && aluno.getTurmasMatriculadas().size() >= 2){
            throw new IllegalStateException("Alunos especiais podem se matricular em no máximo 2 disciplinas");
        }

        if(!cumpriuRequisitos(aluno, turma)){
            throw new IllegalStateException("aluno não cumpre os pré-requisitos da disciplina: " + turma.getDisciplina().getCodigo() + ". Faltam: " + getPreRequisitosFaltantes(aluno, turma));
        }

        aluno.matricularEmDisciplina(turma);
    }
    
}
