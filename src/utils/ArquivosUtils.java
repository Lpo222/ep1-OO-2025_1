package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

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


}