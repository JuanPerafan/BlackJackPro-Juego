package juego;

import componentes.Cola;
import logica.Jugador;

/**
 * Clase que administra la secuencia de participación de los jugadores mediante
 * una estructura FIFO.
 */
public class AdministradorTurnos {

    private Cola<Jugador> filaJugadores;

    /**
     * Inicializa el administrador de turnos con una cola vacía.
     */
    public AdministradorTurnos() {
        this.filaJugadores = new Cola<>();
    }

    /**
     * Inserta un jugador al final de la fila de turnos.
     *
     * @param jugador Jugador que se añadirá a la fila.
     */
    public void encolarJugador(Jugador jugador) {
        filaJugadores.agregar(jugador);
    }

    /**
     * Consulta al jugador que tiene el siguiente turno sin retirarlo de la fila.
     *
     * @return Jugador al frente de la fila, o null si está vacía.
     */
    public Jugador obtenerJugadorActual() {
        return filaJugadores.peek();
    }

    /**
     * Retira al jugador que tiene el turno actual de la fila.
     *
     * @return Jugador que tenía el turno, o null si no hay jugadores.
     */
    public Jugador avanzarTurno() {
        return filaJugadores.eliminar();
    }

    /**
     * Indica si aún hay jugadores en espera de turno.
     *
     * @return true si no hay jugadores en la fila, false si aún quedan.
     */
    public boolean sinTurnosPendientes() {
        return filaJugadores.estaVacía();
    }

    /**
     * Limpia la fila de turnos y agrega nuevamente a los jugadores dados en el
     * orden proporcionado.
     *
     * @param jugadores Lista de jugadores para reiniciar el orden de juego.
     */
    public void reiniciarFilaJugadores(Jugador... jugadores) {
        while (!filaJugadores.estaVacía()) {
            filaJugadores.eliminar();
        }
        for (Jugador jugador : jugadores) {
            filaJugadores.agregar(jugador);
        }
    }

    /**
     * Devuelve una cadena que representa el orden actual de los jugadores en turno.
     *
     * @return Representación textual del estado de la fila.
     */
    @Override
    public String toString() {
        return "Fila de jugadores: " + filaJugadores.toString();
    }
}
