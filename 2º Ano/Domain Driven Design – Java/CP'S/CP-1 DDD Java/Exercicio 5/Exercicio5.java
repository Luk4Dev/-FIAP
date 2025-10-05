import java.util.Scanner;

//###############################################################################################
// Exercicio 5
// Objetivo: Calcular a soma de 1 até N.
//###############################################################################################

public class Exercicio5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n, soma = 0;

        System.out.println("Digite um número N: ");
        n = sc.nextInt();

        //Soma todos os valores de 1 até N
        for (int i = 1; i <= n; i++) {
            soma += i;
        }

        System.out.println("A soma de 1 até " + n + " é: " + soma);

        sc.close();
    }
}
