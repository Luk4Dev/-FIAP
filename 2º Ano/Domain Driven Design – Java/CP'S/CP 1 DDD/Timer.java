public class Timer {
    public static void main(String[] args) {
        int tempo = 10; // segundos

        System.out.println("Contagem regressiva:");

        for (int i = tempo; i >= 0; i--) {
            System.out.println(i + "...");
            try {
                Thread.sleep(1000); // espera 1 segundo
            } catch (InterruptedException e) {
                System.out.println("O timer foi interrompido!");
                return;
            }
        }

        System.out.println("Tempo esgotado! ⏰");
    }
}
