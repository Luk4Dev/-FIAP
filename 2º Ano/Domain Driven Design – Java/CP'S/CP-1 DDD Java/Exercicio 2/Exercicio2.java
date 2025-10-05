import java.util.Scanner;

//###############################################################################################
// Exercicio 2
// Objetivo: Ler um número inteiro e verificar se é par ou ímpar.
//###############################################################################################

public class Exercicio2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numero;

        System.out.println("Digite um número: ");
        numero = sc.nextInt();

        // O % retorna o resto da divisão
        if (numero % 2 == 0) {
            System.out.println("O número é PAR");
        } else {
            System.out.println("O número é ÍMPAR");
        }

        sc.close();
    }
}