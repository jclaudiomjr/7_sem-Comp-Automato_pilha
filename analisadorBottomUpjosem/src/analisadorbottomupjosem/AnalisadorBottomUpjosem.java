package analisadorbottomupjosem;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * ANALISADOR BOTTOM-UP
 * @author José Claudio Jr, Gustavo Nikititz, Mateus Sulzbach
 */
public class AnalisadorBottomUpjosem 
{
    private static Gramatica g;
    private static String fita_entrada;
    private static Reconhece verifica;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        recebe_fita_entrada();
        insere_gramatica();
        verifica_palavra();
    }
    
    /**
     * Recebe a palavra do usuário e insere o $ no final da fita.
     */
    public static void recebe_fita_entrada()
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Informe a palavra a ser reconhecida:  ");
        fita_entrada = sc.nextLine();
        fita_entrada = fita_entrada.concat("$");
        System.out.println("\nPrint Teste - Fita de Entrada: " + fita_entrada);   
    }
     
    /**
     * Insere a gramatica (regras/produções).
     */
    public static void insere_gramatica()
    {
        ArrayList<String> regras = new ArrayList();
        regras.add("E->E+E");
        regras.add("E->E*E");
        regras.add("E->(E)");
        regras.add("E->i");
        g = new Gramatica(regras, fita_entrada);  
    }
    
    /**
     * Este metodo chama a classe que irá realizar o reconhecimento da palavra.
     */
    public static void verifica_palavra()
    {
        System.out.println("Print Teste - Entrou no verifica_palavra()");
        verifica = new Reconhece(g, fita_entrada);
        verifica.reconhecimento();   
    }
}
