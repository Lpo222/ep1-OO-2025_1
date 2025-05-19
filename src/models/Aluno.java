package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Aluno {
    
    //informaçoes contidas em Aluno
    protected String nome;
    protected String curso;
    protected int matricula;
    protected List<String> nomeTurmasMatriculadas;
    protected List<Turma> turmasMatriculadas;

    public Aluno(String nome, String curso, int matricula){
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo.");
        this.curso = Objects.requireNonNull(curso, "Curso não pode ser nulo.");
        this.matricula = matricula;
        this.nomeTurmasMatriculadas = new ArrayList<>();
        this.turmasMatriculadas = new ArrayList<>();
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

    //controle de turmas

    public void matricularEmDisciplina(Turma turma){
    
        Objects.requireNonNull("Turma nao pode ser nulo.");

        if(turma.getVagas() != 0){

            Disciplina disciplinaEscolhida = turma.getDisciplina();
            nomeTurmasMatriculadas.add(disciplinaEscolhida.getNome());
            turmasMatriculadas.add(turma);
            turma.setVagas(turma.getVagas() - 1);

        } else{
            
            throw new IllegalStateException("Turma " + turma.getNumero() + " está cheia!");
        
        }
    
    }

    public void cancelarMatricula(Turma turma){

        Objects.requireNonNull("turma nao pode ser nulo");

        if(turmasMatriculadas.contains(turma)){
            turmasMatriculadas.remove(turma);
        } else{
            Disciplina disciplina = turma.getDisciplina();
            throw new IllegalStateException("Aluno nao estava matriclado em " + disciplina.getNome());
        }

    }

    public List<String> getNomeTurmasMatriculadas(){
        return nomeTurmasMatriculadas;
    }

    public List<Turma> getTurmasMatriculadas(){
        return turmasMatriculadas;
    }

}