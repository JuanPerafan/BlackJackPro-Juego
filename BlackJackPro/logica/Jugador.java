package logica;

import componentes.ListaEnlazada;
import componentes.Pila;

/**
 * Representa un jugador en el juego de Blackjack.
 * Contiene la información de su nombre, cartas actuales, historial de cartas
 * jugadas,
 * estado (si se ha plantado) y cantidad de partidas ganadas.
 */
public class Jugador {

    private String nombre;
    private ListaEnlazada<Carta> cartasEnMano;
    private Pila<Carta> historial;
    private boolean estaPlantado;
    private int victorias;

    /**
     * Crea un jugador con el nombre dado.
     * 
     * @param nombre Nombre del jugador.
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.cartasEnMano = new ListaEnlazada<>();
        this.historial = new Pila<>();
        this.estaPlantado = false;
        this.victorias = 0;
    }

    /**
     * Devuelve el nombre del jugador.
     * 
     * @return Nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Añade una carta a la mano actual y la guarda en el historial.
     * 
     * @param carta Carta recibida.
     */
    public void añadirCarta(Carta carta) {
        cartasEnMano.insertar(carta);
        historial.push(carta);
    }

    /**
     * Calcula el total de puntos en la mano actual del jugador.
     * Los ases pueden contar como 1 u 11, eligiendo el valor más conveniente.
     * 
     * @return Total de puntos.
     */
    public int puntajeTotal() {
        int puntaje = 0;
        int ases = 0;

        for (int i = 0; i < cartasEnMano.obtenerTamaño(); i++) {
            Carta carta = cartasEnMano.obtenerElemento(i);
            if (carta.esAs()) {
                ases++;
            } else {
                puntaje += carta.obtenerValorJuego();
            }
        }

        for (int i = 0; i < ases; i++) {
            puntaje += (puntaje + 11 <= 21) ? 11 : 1;
        }

        return puntaje;
    }

    /**
     * Indica si el jugador se pasó de 21 puntos.
     * 
     * @return true si se pasó, false en caso contrario.
     */
    public boolean seExcedio() {
        return puntajeTotal() > 21;
    }

    /**
     * Verifica si el jugador tiene un Blackjack (21 puntos con dos cartas).
     * 
     * @return true si tiene Blackjack, false en caso contrario.
     */
    public boolean tieneBlackjack() {
        return cartasEnMano.obtenerTamaño() == 2 && puntajeTotal() == 21;
    }

    /**
     * Marca al jugador como plantado (no desea recibir más cartas).
     */
    public void plantado() {
        this.estaPlantado = true;
    }

    /**
     * Retorna si el jugador ya se ha plantado.
     * 
     * @return true si se ha plantado, false si aún juega.
     */
    public boolean estaPlantado() {
        return estaPlantado;
    }

    /**
     * Devuelve la lista de cartas que tiene el jugador actualmente.
     * 
     * @return Mano de cartas.
     */
    public ListaEnlazada<Carta> getCartas() {
        return cartasEnMano;
    }

    /**
     * Devuelve el historial de cartas que ha recibido el jugador.
     * 
     * @return Historial de cartas.
     */
    public Pila<Carta> obtenerHistorial() {
        return historial;
    }

    /**
     * Suma una victoria al contador del jugador.
     */
    public void sumarVictoria() {
        victorias++;
    }

    /**
     * Devuelve el número de partidas ganadas por el jugador.
     * 
     * @return Total de victorias.
     */
    public int getVictorias() {
        return victorias;
    }

    /**
     * Limpia la mano actual del jugador y lo reinicia para una nueva ronda.
     */
    public void reiniciarMano() {
        while (!cartasEnMano.estaVacía()) {
            cartasEnMano.removerPrimero();
        }
        estaPlantado = false;
    }

    /**
     * Representa el estado actual del jugador en forma de cadena.
     * 
     * @return Información del jugador (nombre, puntaje, cartas, estado y
     *         victorias).
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(" (Puntaje: ").append(puntajeTotal()).append(")\n");
        sb.append("Cartas: ");

        for (int i = 0; i < cartasEnMano.obtenerTamaño(); i++) {
            Carta carta = cartasEnMano.obtenerElemento(i);
            sb.append(carta);
            if (i < cartasEnMano.obtenerTamaño() - 1) {
                sb.append(", ");
            }
        }

        sb.append("\nEstado: ").append(estaPlantado ? "Plantado" : "Jugando");
        sb.append("\nVictorias: ").append(victorias);

        return sb.toString();
    }
}
