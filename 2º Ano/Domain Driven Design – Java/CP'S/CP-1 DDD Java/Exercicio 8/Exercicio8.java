import java.util.Scanner;

//###############################################################################################
// Exercicio 8
// Objetivo: Calcular a média de 10 números, exibindo também o maior e o menor.
//###############################################################################################

public class Exercicio8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numero;
        int soma = 0;
        int maior = Integer.MIN_VALUE;
        int menor = Integer.MAX_VALUE;

        // Ler 10 números
        for (int i = 1; i <= 10; i++) {
            System.out.println("Digite o " + i + "º número: ");
            numero = sc.nextInt();

            soma += numero;

            if (numero > maior) {
                maior = numero;
            }
            if (numero < menor) {
                menor = numero;
            }
        }

        double media = soma / 10.0;

        System.out.println("Média: " + media);
        System.out.println("Maior número: " + maior);
        System.out.println("Menor número: " + menor);

        sc.close();
    }
}
