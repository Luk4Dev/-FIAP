import java.util.Scanner;

//###############################################################################################
// Exercicio 6
// Objetivo: Verificar se um número é primo.
// Um número primo só é divisível por 1 e por ele mesmo.
//###############################################################################################

public class Exercicio6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n;

        System.out.println("Digite um número: ");
        n = sc.nextInt();

        boolean primo = true;

        if (n <= 1) {
            primo = false;
        } else {
            for (int i = 2; i * i <= n; i++) {
                if (n % i == 0) {
                    primo = false;
                    break;
                }
            }
        }

        if (primo) {
            System.out.println(n + " é primo.");
        } else {
            System.out.println(n + " não é primo.");
        }

        sc.close();
    }
}
