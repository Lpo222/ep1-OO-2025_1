package models;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Classe que representa as turmas, contendo seus números, disciplinas horarios e professores correspondentes
public class Turma {

    //informações contidas em Turma
    private int numero;
    private Disciplina disciplina;
    private String horario;
    private String professor;
    private int vagas;
    private String sala;
    private String avaliacao;
    private int aulasMinistradas;
    private Map<Aluno, Float> alunosMatriculados;
    private Map<Aluno, Integer> presenca;

    public Turma(int numero, Disciplina disciplina, String horario, String professor, int vagas, String sala, String avaliacao){
        this.numero = Objects.requireNonNull(numero, "Nome nao pode ser nulo.");
        this.disciplina = Objects.requireNonNull(disciplina, "Disciplina não pode ser nula.");
        this.horario = horario;
        this.professor = professor;
        this.vagas = vagas;
        this.sala = sala;
        this.avaliacao = avaliacao;
        this.aulasMinistradas = 0;
        this.alunosMatriculados = new HashMap();
        this.presenca = new HashMap();
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

    public void setSala(String sala){
        this.sala = sala;
    }

    public void setAvaliacao(String avaliacao){
        this.avaliacao = avaliacao;
    }

    public void matricularAluno(Aluno aluno){
        Objects.requireNonNull(aluno);

        if(vagas <= 0){
            throw new IllegalStateException("Turma cheia");
        }
        if(alunosMatriculados.containsKey(aluno)){
            throw new IllegalStateException("Aluno já matriculado nesta turma.");
        }

        alunosMatriculados.put(aluno, 0f);

        vagas--;
        
        aluno.adicionarTurma(this);

    }

    public void registrarNota(Aluno aluno, float nota){

        if(!alunosMatriculados.containsKey(aluno)){
            throw new IllegalStateException("Aluno não está matriculado nesta turma.");
        }

        if(nota < 0){
            throw new IllegalStateException("nota não pode ser negativa.");
        }

        alunosMatriculados.put(aluno, nota);

    }

    public void aulaMinistrada(){
        aulasMinistradas++;
    }

    public void setAulasMinistradas(int aulasMinistradas){
        this.aulasMinistradas = aulasMinistradas;
    }

    public int getAulasMinistradas(){
        return aulasMinistradas;
    }


    public void registrarPresenca(Aluno aluno, boolean presente){
    
        if(!presenca.containsKey(aluno)){
            throw new IllegalStateException("Aluno não está matriculado nesta turma.");
        }

        presenca.put(aluno, presente ? 1 : 0);
        aulaMinistrada();

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

    public String getSala(){
        return sala;
    }

    public String getAvaliacao(){
        return avaliacao;
    }
    
    public float getNota(Aluno aluno){
        return alunosMatriculados.getOrDefault(aluno, -1f);
    }

    public int getPresenca(Aluno aluno){
        return presenca.getOrDefault(aluno, -1) / aulasMinistradas;
    }

    public Map<Aluno, Float> getAlunosMatriculados(){
        return alunosMatriculados;
    }

    public Map<Aluno, Integer> getPresenca(){
        return presenca;
    }

}
