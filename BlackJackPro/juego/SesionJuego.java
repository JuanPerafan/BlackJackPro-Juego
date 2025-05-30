package juego;

import componentes.Pila;
import logica.Carta;
import logica.Jugador;

/**
 * Representa una sesión de juego de Blackjack.
 * Gestiona las cartas jugadas, el resultado y el jugador vencedor.
 */
public class SesionJuego {

    private int identificador;
    private Jugador jugadorVencedor;
    private Pila<RegistroJugada> registroJugadas;
    private String estadoFinal;
    private static int totalSesiones = 0;

    /**
     * Representa una jugada individual con la carta jugada,
     * el nombre del jugador y el puntaje tras esa jugada.
     */
    private static class RegistroJugada {
        private Carta carta;
        private String nombreJugador;
        private int puntajeActual;

        public RegistroJugada(Carta carta, String nombreJugador, int puntajeActual) {
            this.carta = carta;
            this.nombreJugador = nombreJugador;
            this.puntajeActual = puntajeActual;
        }

        @Override
        public String toString() {
            return nombreJugador + " jugó " + carta + " (Puntaje: " + puntajeActual + ")";
        }
    }

    /**
     * Crea una nueva sesión de juego con un identificador único.
     */
    public SesionJuego() {
        this.identificador = ++totalSesiones;
        this.registroJugadas = new Pila<>();
        this.jugadorVencedor = null;
        this.estadoFinal = "En curso";
    }

    /**
     * Retorna el identificador único de esta sesión.
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * Registra una nueva jugada realizada por un jugador.
     *
     * @param carta   Carta que se jugó.
     * @param jugador Jugador que realizó la jugada.
     */
    public void agregarJugada(Carta carta, Jugador jugador) {
        RegistroJugada jugada = new RegistroJugada(carta, jugador.getNombre(), jugador.puntajeTotal());
        registroJugadas.push(jugada);
        System.out.println(jugada.toString());
    }

    /**
     * Establece el resultado de la sesión, indicando el ganador si lo hay.
     *
     * @param ganador     Jugador que ganó (null si fue empate).
     * @param descripcion Motivo del resultado.
     */
    public void definirGanador(Jugador ganador, String descripcion) {
        this.jugadorVencedor = ganador;
        this.estadoFinal = (ganador != null)
                ? ganador.getNombre() + " ganó: " + descripcion
                : "Empate: " + descripcion;

        if (ganador != null) {
            ganador.sumarVictoria();
        }
    }

    /**
     * Devuelve el jugador que ganó esta sesión, o null si hubo empate.
     */
    public Jugador getJugadorVencedor() {
        return jugadorVencedor;
    }

    /**
     * Retorna el estado final de la sesión.
     */
    public String getEstadoFinal() {
        return estadoFinal;
    }

    /**
     * Devuelve la pila con el registro de jugadas realizadas.
     */
    public Pila<RegistroJugada> getRegistroJugadas() {
        return registroJugadas;
    }

    /**
     * Muestra todas las jugadas realizadas en orden cronológico.
     */
    public String obtenerHistorialJugadas() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Registro de Jugadas ===\n");
        sb.append(registroJugadas.inverso());
        sb.append("\n=== Fin del Registro ===\n");
        return sb.toString();
    }

    /**
     * Devuelve un resumen general de esta sesión de juego.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n====================================\n");
        sb.append("       RESUMEN DE LA SESIÓN #").append(identificador).append("\n");
        sb.append("====================================\n");
        sb.append("Estado: ").append(estadoFinal).append("\n");
        sb.append("Jugadas realizadas: ").append(registroJugadas.tamaño()).append("\n");
        if (jugadorVencedor != null) {
            sb.append("Ganador: ").append(jugadorVencedor.getNombre())
                    .append(" (").append(jugadorVencedor.getVictorias()).append(" victorias)\n");
        } else {
            sb.append("Resultado: Empate\n");
        }
        sb.append("====================================\n");
        return sb.toString();
    }
}
