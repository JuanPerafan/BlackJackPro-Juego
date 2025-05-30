/**
 * Clase principal para iniciar la aplicación.
 */
public class Main {
    public static void main(String[] args) {
        // Crear y arrancar la partida de Blackjack
        juego.JuegoBlackjack partidaBlackjack = new juego.JuegoBlackjack();
        partidaBlackjack.comenzarPartida();
    }
}

