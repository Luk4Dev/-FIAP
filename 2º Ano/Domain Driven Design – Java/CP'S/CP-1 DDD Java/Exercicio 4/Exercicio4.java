import java.util.Scanner;

//###############################################################################################
// Exercicio 4
// Objetivo: Ler um número inteiro e imprimir sua tabuada de 1 a 10.
//###############################################################################################

public class Exercicio4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numero;

        System.out.println("Digite um número para ver sua tabuada: ");
        numero = sc.nextInt();

        //vai de 1 até 10
        for (int i = 1; i <= 10; i++) {
            System.out.println(numero + " x " + i + " = " + (numero * i));
        }

        sc.close();
    }
}
