package models;

import java.util.Objects;

//Classe que representa as turmas, contendo seus números, disciplinas horarios e professores correspondentes
public class Turma {

    //informações contidas em Turma
    private int numero;
    private Disciplina disciplina;
    private String horario;
    private String professor;
    private int vagas;

    public Turma(int numero, Disciplina disciplina, String horario, String professor, int vagas){
        this.numero = Objects.requireNonNull(numero, "Nome nao pode ser nulo.");
        this.disciplina = Objects.requireNonNull(disciplina, "Disciplina não pode ser nula.");
        this.horario = horario;
        this.professor = professor;
        this.vagas = vagas;
    }

    //setters para as variáveis de Turma
    public void setNumero(int numero){
        this.numero = Objects.requireNonNull(numero, "Número nao pode ser nulo.");
    }

    public void setDisciplina(Disciplina disciplina){
        this.disciplina = Objects.requireNonNull(disciplina, "Disciplina não pode ser nula.");
    }

    public void setHorario(String horario){
        this.horario = horario;
    }

    public void setProfessor(String professor){
        this.professor = professor;
    }

    public void setVagas(int vagas){
        this.vagas = vagas;
    }

    //getters

    public int getNumero(){
        return numero;
    }

    public Disciplina getDisciplina(){
        return disciplina;
    }

    public String getHorario(){
        return horario;
    }

    public String getProfessor(){
        return professor;
    }

    public int getVagas(){
        return vagas;
    }

    
}
