package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa uma disciplina acadêmica com código, nome e pré-requisitos.
 */
public class Disciplina{
    
    //informações contidas em Disciplina
    protected String codigo;
    protected String nome;
    protected List<String> preRequisitos;

    public Disciplina(String codigo, String nome, List<String> preRequisitos){
        this.codigo = Objects.requireNonNull(codigo, "Codigo da disciplina não pode ser nulo.");
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo.");
        this.preRequisitos = new ArrayList<>(preRequisitos);
    }

    //setters para codigo, nome e pré requisitos

    public void setCodigo(String codigo){
        this.codigo = Objects.requireNonNull(codigo, "Codigo da disciplina não pode ser nulo.");
    }

    public void setNome(String nome){
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo.");
    }

    public void registraPreRequisitos(String codigoPreRequisito){
        Objects.requireNonNull(codigoPreRequisito, "Código de pré-requisito não pode ser nulo.");

        if(preRequisitos.contains(codigoPreRequisito)){
            throw new IllegalStateException("Pré-requisito já registrado");
        } else if(codigoPreRequisito.equals(this.codigo)){
            throw new IllegalArgumentException("Disciplina não pode ser pré-requisito de sí mesma.");
        }else{
            preRequisitos.add(codigoPreRequisito);
        }

    }

    public void removerPreRequisito(String codigoPreRequisito){
        preRequisitos.remove(codigoPreRequisito);
    }

    //getters

    public String getCodigo(){
        return codigo;
    }

    public String getNome(){
        return nome;
    }

    public List<String> getPreRequisitos(){
        return new ArrayList<>(preRequisitos);
    }
     
    // --- Identidade (equals/hashCode) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disciplina that = (Disciplina) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
    
}
