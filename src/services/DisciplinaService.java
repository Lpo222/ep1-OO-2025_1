package services;

import models.Disciplina;
import utils.ArquivosUtils; // Seu utilitário de arquivos

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap; // Se for carregar em um mapa para fácil acesso
import java.util.List;
import java.util.Map; // Se for carregar em um mapa

public class DisciplinaService {

    private static final String ARQUIVO_DISCIPLINAS = "data/disciplinas.txt";

    public void gravarDadosDisciplina(List<Disciplina> disciplinas) throws IOException {
        if(disciplinas == null) {
            disciplinas = new ArrayList<>();
        }

        List<String> linhasParaSalvar = new ArrayList<>();
        for(Disciplina disciplina : disciplinas){

            String preRequisitosStr = String.join(",", disciplina.getPreRequisitos());
            if(preRequisitosStr.isEmpty()){
                preRequisitosStr = "Nenhum";
            }

            //codigo;nome;cargaHoraria;preRequisitosStr
            String linha =  disciplina.getCodigo() + ";" +
                            disciplina.getNome() + ";" +
                            disciplina.getCargaHoraria() + ";" +
                            preRequisitosStr;
            
            linhasParaSalvar.add(linha);


        }


        try{
            ArquivosUtils.escreverArquivo(ARQUIVO_DISCIPLINAS, linhasParaSalvar);
            System.out.println("Dados da disciplina gravdos com sucesso em " + ARQUIVO_DISCIPLINAS);
        }catch(IOException e){
            System.err.println("Erro ao gravar dados da disciplina: " + e.getMessage());
        }

    }

    public Map<String, Disciplina> carregarDisciplinas(){

    Map<String, Disciplina> disciplinasCarregadas = new HashMap<>();
    List<String> linhasDoArquivo;

    try {
        linhasDoArquivo = ArquivosUtils.lerLinhasDoArquivo(ARQUIVO_DISCIPLINAS);
    } catch (IOException e) {
        System.err.println("Erro ao ler o arquivo " + ARQUIVO_DISCIPLINAS + ": " + e.getMessage());
        return disciplinasCarregadas;
    }

    for (String linha : linhasDoArquivo) {
        if (linha == null || linha.trim().isEmpty()) {
            continue;
        }

        String[] dados = linha.split(";", -1);

        //codigo;nome;cargaHoraria;preRequisitosStr
        if (dados.length == 4) {
            try {
                String codigo = dados[0].trim();
                String nome = dados[1].trim();
                int cargaHoraria = Integer.parseInt(dados[2].trim());
                String preRequisitosStr = dados[3].trim();

                List<String> preRequisitosList = new ArrayList<>();
                if (!"NENHUM".equalsIgnoreCase(preRequisitosStr) && !preRequisitosStr.isEmpty()) {
                    String[] codigosPreReq = preRequisitosStr.split(",");
                    for (String codPreReq : codigosPreReq) {
                        preRequisitosList.add(codPreReq.trim());
                    }
                }

                Disciplina disciplina = new Disciplina(codigo, nome, preRequisitosList, cargaHoraria);
                disciplinasCarregadas.put(codigo, disciplina);

            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter carga horária para número na linha: '" + linha + "'. Pulando disciplina.");
            } catch (Exception e) {
                System.err.println("Erro ao processar disciplina da linha: '" + linha + "': " + e.getMessage() + ". Pulando disciplina.");
            }
        } else {
            System.err.println("Formato de linha inválido para disciplina (esperava 4 campos): '" + linha + "'. Pulando linha.");
        }
    }

    System.out.println(disciplinasCarregadas.size() + " disciplinas carregadas de " + ARQUIVO_DISCIPLINAS);
    return disciplinasCarregadas;
}

}