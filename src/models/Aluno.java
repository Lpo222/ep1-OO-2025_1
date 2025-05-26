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
    protected List<Disciplina> disciplinasConcluidas;


    public Aluno(String nome, String curso, int matricula){
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo.");
        this.curso = Objects.requireNonNull(curso, "Curso não pode ser nulo.");
        this.matricula = matricula;
        this.nomeTurmasMatriculadas = new ArrayList<>();
        this.turmasMatriculadas = new ArrayList<>();
        this.disciplinasConcluidas = new ArrayList<>();
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
    
        Objects.requireNonNull(turma, "Turma nao pode ser nulo.");

        String codigoDisciplina = turma.getDisciplina().getCodigo();
        boolean jaMatriculado = turmasMatriculadas.stream().anyMatch(t -> t.getDisciplina().getCodigo().equals(codigoDisciplina));

        if(jaMatriculado) throw new IllegalStateException("Aluno ja matriculado em: " + codigoDisciplina);

        if(turma.getVagas() != 0){

            Disciplina disciplinaEscolhida = turma.getDisciplina();
            nomeTurmasMatriculadas.add(disciplinaEscolhida.getNome());
            turmasMatriculadas.add(turma);
            turma.setVagas(turma.getVagas() - 1);

        } else{
            
            throw new IllegalStateException("Turma " + turma.getNumero() + " está cheia!");
        
        }
    
    }

    protected void adicionarTurma(Turma turma){
        if(!turmasMatriculadas.contains(turma)){
            turmasMatriculadas.add(turma);
        }
    }

    public void cancelarMatricula(Turma turma){

        Objects.requireNonNull(turma, "turma nao pode ser nulo");

        if(turmasMatriculadas.contains(turma)){
            turmasMatriculadas.remove(turma);
            turma.setVagas(turma.getVagas() + 1);
            nomeTurmasMatriculadas.remove(turma.getDisciplina().getNome());
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


    public float consultarNota(Turma turma) {
        if (!turmasMatriculadas.contains(turma)) {
            throw new IllegalArgumentException("Aluno não está matriculado nesta turma");
        }
        return turma.getNota(this);
    }
    
    public void setDisciplinasConcluidas(Disciplina disciplina){
        Objects.requireNonNull(disciplina, "disciplina não pode ser nulo.");

        if(disciplinasConcluidas.contains(disciplina)){
            throw new IllegalStateException("disciplina concluida ja registrada.");
        }

        disciplinasConcluidas.add(disciplina);
        

    }
    
    public List<Disciplina> getDisciplinasConcluidas(){
        return disciplinasConcluidas;
    }


}