package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class ArquivosUtils{

    public static void escreverArquivo(String caminhoArquivo, List<String> linhas) throws IOException{

        Paths.get(caminhoArquivo).getParent().toFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, false))){
            for (String linha : linhas){
                writer.write(linha);
                writer.newLine();
            }
        }
    }

    public static List<String> lerLinhasDoArquivo(String caminhoArquivo) throws IOException {
        Paths.get(caminhoArquivo).getParent().toFile().mkdirs();

        if(!Files.exists(Paths.get(caminhoArquivo))){

            Files.createFile(Paths.get(caminhoArquivo));
            return new ArrayList<>();
        
        }

        List<String> linhas = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))){
            String linhaAtual;

            while((linhaAtual = reader.readLine()) != null){
                linhas.add(linhaAtual);
            }
        }
        return linhas;
    }

}