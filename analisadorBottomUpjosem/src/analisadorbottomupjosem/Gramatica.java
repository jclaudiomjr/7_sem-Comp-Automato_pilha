package analisadorbottomupjosem;
import java.util.ArrayList;

/**
 *
 * @author José Claudio Jr, Gustavo Nikititz, Mateus Sulzbach
 */
public class Gramatica 
{
    private final ArrayList<String> regras = new ArrayList();       /* ArrayList que conterá as regras (produções) da gramática. */
    private final ArrayList<String> handle = new ArrayList();       /* ArrayList que armazena o(s) handle(s) da gramática. */
    private final ArrayList<String> alfabeto = new ArrayList();     /* O programa identifica o alfabeto automaticamente. */
    
    
    /**
     * Construtor da gramática.
     * @param producoes - recebe as regras inseridas na classe main.
     * @param fita_entrada - recebe a palavra inserida pelo usuário.
     */
    public Gramatica(ArrayList<String> producoes, String fita_entrada)
    {
        int conta_regra, conta_letra, qtd_letras_alfabeto;     
        String aux[] = new String[2];
        char letras[] = null;
        
        /* Este FOR adiciona as regras e o(s) handle(s) nos arraylists. */
        for (conta_regra = 0 ; conta_regra < producoes.size() ; conta_regra++)
        {
            aux = producoes.get(conta_regra).split("->");

            this.handle.add(aux[0]);
            System.out.println("Print Teste - Handle adicionado: " + handle.get(handle.size() - 1));
            this.regras.add(aux[1]);
            System.out.println("Print Teste - Regra adicionada: " + regras.get(conta_regra));
        }
        
        letras = fita_entrada.toCharArray();
        qtd_letras_alfabeto = letras.length;
        
        /* Este FOR identifica o alfabeto e adiciona no arraylist 'alfabeto'. */
        for (conta_letra = 0 ; conta_letra < qtd_letras_alfabeto ; conta_letra++)
        {
            alfabeto.add(String.valueOf(letras[conta_letra]));
        }
    }

    /**
     * Retorna o Arraylist das REGRAS.
     * @return regras - ArrayList de regras (produções).
     */
    public ArrayList<String> getRegras() 
    {
        return regras;
    }

    /**
     * Retorna o ArrayList de Handle.
     * @return - handle - ArrayList de handle.
     */
    public ArrayList<String> getHandle() 
    {
        return handle;
    }

    /**
     * Retorna o ArrayList de Alfabeto.
     * @return alfabeto - ArrayList do Alfabeto.
     */
    public ArrayList<String> getAlfabeto() 
    {
        return alfabeto;
    }
}
