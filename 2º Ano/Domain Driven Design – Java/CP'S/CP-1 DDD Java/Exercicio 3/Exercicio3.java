import java.util.Scanner;

//###############################################################################################
// Exercicio 3
// Objetivo: Ler três números inteiros e mostrar o maior deles.
//###############################################################################################

public class Exercicio3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int a, b, c;

        System.out.println("Digite o primeiro número: ");
        a = sc.nextInt();

        System.out.println("Digite o segundo número: ");
        b = sc.nextInt();

        System.out.println("Digite o terceiro número: ");
        c = sc.nextInt();

        //Inicialmente assumimos que 'a' é o maior
        int maior = a;

        if (b > maior) {
            maior = b;
        }
        if (c > maior) {
            maior = c;
        }

        System.out.println("O maior número é: " + maior);

        sc.close();
    }
}
