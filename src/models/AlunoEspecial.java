package models;

import java.util.Objects;

public class AlunoEspecial extends Aluno{
    
    public AlunoEspecial(String nome, String curso, int matricula){
        super(nome, curso, matricula);
    }

    @Override
    public float consultarNota(Turma turma) {
        throw new UnsupportedOperationException("Alunos especiais não recebem notas");
    }


    private final int maxDisciplinas = 2;

    @Override
    public void matricularEmDisciplina(Turma turma){

        Objects.requireNonNull(turma, "Turma nao pode ser nulo.");

        if(turmasMatriculadas.size() >= maxDisciplinas){
            throw new IllegalStateException("Alunos especiais podem se matricular em, no máximo: " + maxDisciplinas + " disciplinas.");
        }

        super.matricularEmDisciplina(turma);

    }

}