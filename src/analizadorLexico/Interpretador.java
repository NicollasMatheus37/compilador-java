package analizadorLexico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Interpretador {

    private String path = "src\\arquivos\\EXEMPLOS_VALIDACAO_LMS.teste";
    private String texto = "";

    public void interpretar() throws FileNotFoundException, IOException {
        this.GravaArquivo();
    }

    public void GravaArquivo() throws FileNotFoundException, IOException {
        FileOutputStream arq = null;
        PrintStream ps = null;
        try {
            File f = new File(path);
            arq = new FileOutputStream(f);
            try {
                ps = new PrintStream(arq);
                ps.println(texto);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
        } finally {
            if (arq != null) {
                arq.close();
            }
        }
    }

    public StringBuilder lerArquivo() throws FileNotFoundException, IOException {
        StringBuilder result = new StringBuilder();
        FileInputStream arq = null;
        try {
            File f = new File(path);
            arq = new FileInputStream(f);
            int caracterLido = arq.read();

            while (caracterLido != -1) {
                result.append((char) caracterLido);
                caracterLido = arq.read();
            }
        } finally {
            if (arq != null) {
                arq.close();
            }
        }
        return result;
    }
}
