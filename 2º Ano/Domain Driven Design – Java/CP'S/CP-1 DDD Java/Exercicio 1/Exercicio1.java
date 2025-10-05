import java.util.Scanner;

//Professor eu comentei bastante para eu me recordar do que eu fiz numa futura revisão

//###############################################################################################
//Exercicio: 1
//Objetivo: Ler dois inteiros e imprimir a soma.
//###############################################################################################

public class Exercicio1 {
    //Método principal (Java sempre começa daqui)
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //Procurei tutoriais e vi o pessoal recomendando usar Scanner para ler dados do usuário

        //Depois do int ficam as variáveis
        int a, b, soma;

        //Pega o 1 numero
        System.out.println("Digite o primeiro número: ");
        a = sc.nextInt();

        //Pega o 2 numero
        System.out.println("Digite o segundo número: ");
        b = sc.nextInt();

        //Faz a soma
        soma = a + b;

        //Printa o resultado
        System.out.println("A soma é: " + soma);

        //Foi dito pelo cara de um video que isso são boas práticas
        sc.close(); 
    }
}