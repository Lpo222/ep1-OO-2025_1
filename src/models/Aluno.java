package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Aluno {
    
    //informaçoes contidas em Aluno
    protected String nome;
    protected String curso;
    protected int matricula;
    protected List<String> codigosDisciplinasMatriculadas;

    public Aluno(String nome, String curso, int matricula){
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo.");
        this.curso = Objects.requireNonNull(curso, "Curso não pode ser nulo.");
        this.matricula = matricula;
        this.codigosDisciplinasMatriculadas = new ArrayList<>();
    }

    //setters para nome, curso, matricula e disicplina:

    public void setNome(String nome){
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
    }

    public void setCurso(String curso){
        this.curso = Objects.requireNonNull(curso, "Curso não pode ser nulo");
    }

    public void setMatricula(int matricula){
        this.matricula = matricula;
    }

    //getters para nome, curso, matricula

    public String getNome(){
        return nome;
    }

    public String getCurso(){
        return curso;
    }

    public int getMatricula(){
        return matricula;
    }

    //controle de disciplinas

    public void matricularEmDisciplina(String codigoDisciplina){
        Objects.requireNonNull(codigoDisciplina, "Código da disciplina não pode ser nulo");

        if(codigosDisciplinasMatriculadas.contains(codigoDisciplina)){
            throw new IllegalStateException("Aluno já matriculado em: " + codigoDisciplina);
        } else{
            codigosDisciplinasMatriculadas.add(codigoDisciplina);
        }
    }

    public void removerDisciplina(String codigoDisciplina){
        codigosDisciplinasMatriculadas.remove(codigoDisciplina);
    }

    public List<String> getDisciplinasMatriculadas(){
        return new ArrayList<>(codigosDisciplinasMatriculadas);
    }

}
