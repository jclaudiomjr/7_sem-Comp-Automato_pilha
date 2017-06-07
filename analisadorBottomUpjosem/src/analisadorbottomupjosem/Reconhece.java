package analisadorbottomupjosem;
import java.util.Stack;

/**
 * Classe verificadora da palavra.
 * @author José Claudio Jr, Gustavo Nikititz, Mateus Sulzbach
 */
public class Reconhece 
{
    private Gramatica gramatica;
    private Stack automato_pilha;
    private String fita_entrada;

    /**
     * Construtor da classe Reconhece.
     * @param gramatica - gramatica possui toda as regras, handle e alfabeto da linguagem.
     * @param fita_entrada - fita de entrada inserida pelo usuário.
     */
    public Reconhece(Gramatica gramatica, String fita_entrada) {
        this.gramatica = gramatica;
        this.automato_pilha = new Stack();  /* Pilha do automato. */
        this.automato_pilha.push("$");      /* A pilha já é inicializada com um $. */
        this.fita_entrada = fita_entrada;
    }
    
    /**
     * Método responsável pelo processo de reconhecimento da palavra.
     */
    public void reconhecimento()
    {
        char[] letras = null;                   /* Array do tipo char que recebe a fita de entrada, separado por caractere */
        int posicao_simbolo_lido = 0;           /* Controla a posição do simbolo lido da fita de entrada. */
        letras = fita_entrada.toCharArray();
        
        for (int i = 0 ; i < letras.length ; i++)
        {
            System.out.println("Print Teste - Array Letras: " + letras[i]);
        }
        
        /* Começo do reconhecimento... */
        while ( letras[posicao_simbolo_lido] != '$' )
        {
            System.out.println("Print Teste - Simbolo da fita: " + letras[posicao_simbolo_lido]);
            /* o primeiro simbolo lido sempre vai ser empilhado na pilha, pos isto é feita esta verificação. */
            if (posicao_simbolo_lido > 0)
            { 
                /* Este é o motivo de ter criado um alfabeto, caso o alfabeto CONTER o topo da pilha, então o topo da pilha é um TERMINAL. */
                if (gramatica.getAlfabeto().contains(automato_pilha.peek()))
                {
                    System.out.println("Comparação: TERMINAL COM TERMINAL. Ação: REDUZIR.");
                    /* Se não consegue reduzir, vai ser empilhado o simbolo lido da fita. */
                    if(tenta_reduzir(letras[posicao_simbolo_lido]) == 0)
                    {
                        automato_pilha.push(String.valueOf(letras[posicao_simbolo_lido]));
                        System.out.println("Print Teste - Não reduziu e Empilhou: " + letras[posicao_simbolo_lido]);
                        posicao_simbolo_lido++;
                    }
                    else
                    {
                        System.out.println("Print Teste - Reduziu, o topo da pilha é: " + automato_pilha.peek());
                    }
                }
                /* Se não tem, é porque o topo da pilha é um NÃO TERMINAL. Comparação de não terminal com terminal a ação é EMPILHAR. */
                else
                {
                    System.out.println("Comparação: NÃO TERMINAL COM TERMINAL. Ação: EMPILHAR.");
                    automato_pilha.push(String.valueOf(letras[posicao_simbolo_lido]));
                    System.out.println("Print Teste - Não reduziu. Comparação de NÃO TERMINAL COM TERMINAL. Empilhou: " + letras[posicao_simbolo_lido]);
                    posicao_simbolo_lido++;
                }
            /* O programa só cai aqui quando o simbolo lido for 0 (primeiro simbolo lido sempre será empilhado) */    
            } 
            else 
            {
                automato_pilha.push(String.valueOf(letras[posicao_simbolo_lido]));
                System.out.println("Print Teste -automato pilha vazio, Empilhou " + letras[posicao_simbolo_lido]);
                posicao_simbolo_lido++;
            }   
        }
        System.out.println("Print Teste - Simbolo da fita: " + letras[posicao_simbolo_lido]);
        /* Quando o simbolo da fita é $, acontece mais uma tentativa de reduzir a pilha. */
        if (letras[posicao_simbolo_lido] == '$')
        {
            /* Se o retorno for 3, aceita a palavra */
            if(tenta_reduzir(letras[posicao_simbolo_lido]) == 3)
            {
                System.out.println("Palavra " + "< " + fita_entrada + " >" + " ACEITA!");
            }
            /* Se não, REJEITA. */
            else
            {
                System.out.println("Palavra " + "< " + fita_entrada + " >" + " REJEITADA!");
            }
        }
    }
    
    /**
     * Método que faz a tentativa de REDUÇÃO.
     * @param simbolo_lido - Recebe o simbolo lido da fita, para realizar a comparação de igualdade com o automato.
     * @return reduziu - Retorna 0 se não conseguiu reduzir, 1 se conseguiu reduzir ou 3 se aceitou a palavra.
     */
    public int tenta_reduzir(char simbolo_lido)
    {
        int reduziu = 0;                    /* Variável usada para o retorno deste método. */
        Stack pilha_aux = new Stack();      /* Pilha auxiliar do automato. */
        String concatenar = "#";            /* Variavel recebe a concatenação dos topos da pilha para tentar reduzir, esta String que é usada para verificar se existe nas regras da gramática. */
        StringBuilder constroi_palavra = new StringBuilder(concatenar);     /* Esta variavel que montar a concatenação e enviar para a variavel 'concatenar'. */
        
        /* A pilha auxiliar recebe uma cópia do automato. */
        pilha_aux = (Stack)automato_pilha.clone();
        
        /* deleta o # usado na inicialização de concatenar. */
        constroi_palavra.deleteCharAt(0);
        
        /* Inicio da tentativa de redução. */
        while(automato_pilha.peek() != "$")
        {
            /* Concatena o topo da pilha. */
            constroi_palavra.append((String)automato_pilha.peek()).substring(1);
            /* Inverte a String. */
            constroi_palavra.reverse();
            /* concatenar recebe o conteúdo de constroi_palavra. */
            concatenar = concatenar.replace(concatenar, constroi_palavra.toString());
            System.out.println("Print Teste - Tenta Reduzir: " + constroi_palavra);
            /* Verifica se existe regra para a palavra concatenar. */
            if (gramatica.getRegras().contains(concatenar))
            {
                System.out.println("Print Teste - Achou Handle de: " + automato_pilha.peek());
                automato_pilha.pop();
                automato_pilha.push(gramatica.getHandle().get(gramatica.getRegras().indexOf(constroi_palavra.toString())));
                System.out.println("Print Teste - Achou Handle e Empilhou " + automato_pilha.peek());
                reduziu = 1;
                /* limpa o constroi_palavra, para receber novo conteúdo futuramente */
                constroi_palavra.delete(0, constroi_palavra.length());
                /* Limpa também a variavel concatenar. */
                concatenar = constroi_palavra.toString();
                /* pilha auxiliar recebe uma cópia do automato, atualizado. */
                pilha_aux = (Stack)automato_pilha.clone();
                //System.out.println("Print Teste - Estado de concatenar e constroi_palavra: " + concatenar + " # " + constroi_palavra);
            }
            /* Caso não achou regras... */
            else
            {
                /* Reverte a string novamente. */
                constroi_palavra.reverse();
                System.out.println("Print Teste - Não achou regras para: " + concatenar);
                /* Desempilha o automato, e o novo topo será concatenado no constroi_palavra. */
                System.out.println("Print Teste - Desempilhou:  " + automato_pilha.peek());
                automato_pilha.pop();
                /* Compara se o topo do automato é igual ao simbolo da fita (que só será verdadeiro no $) e o tamanho da fita for 2 (ou seja, $E) */
                if (automato_pilha.peek().equals(String.valueOf(simbolo_lido)) && pilha_aux.size() == 2)
                {
                    reduziu = 3;
                    return reduziu;
                }
            }    
        }
        /* O automato recebe de volta o conteúdo da pilha auxiliar. */
        automato_pilha = (Stack)pilha_aux.clone();
        System.out.println("Print Teste - Tamanho da pilha:  " + pilha_aux.size());
        System.out.println("Print Teste - Conteudo do automato:  ");
        while (!pilha_aux.empty())
        {
            System.out.println(pilha_aux.peek() + "  ");
            pilha_aux.pop();
        }
        pilha_aux.clear();
        return reduziu;
    }
    
}
