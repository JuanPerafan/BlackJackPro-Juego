package logica;

import componentes.ArbolBinario;

/**
 * Representa al repartidor (la casa) en el juego de Blackjack.
 * Extiende la clase Participante y utiliza un Árbol Binario para decidir si
 * seguir jugando.
 */
public class Dealer extends Jugador {

    private static final int LIMITE_PARA_DETENERSE = 17;
    private ArbolBinario<String> arbolEstrategia;

    /**
     * Crea una instancia del repartidor (Dealer).
     */
    public Dealer() {
        super("Dealer");
        this.arbolEstrategia = new ArbolBinario<>();
    }

    /**
     * Evalúa automáticamente si el Dealer debe tomar una nueva carta según el árbol
     * de estrategia.
     *
     * @return true si debe tomar carta, false si debe detenerse.
     */
    public boolean necesitaOtraCarta() {
        int puntosActuales = puntajeTotal();
        ArbolBinario.NodoBinario<String> nodoDecision = arbolEstrategia.decidir(puntosActuales);
        return nodoDecision != null && nodoDecision.getValor().equals("Pedir carta");
    }

    /**
     * Ejecuta el turno automático del Dealer.
     * El Dealer seguirá tomando cartas hasta alcanzar al menos 17 puntos.
     *
     * @param baraja Fuente de donde tomar nuevas cartas.
     */
    public void ejecutarTurno(MazoCartas baraja) {
        System.out.println("\nTurno del Dealer:");
        System.out.println("Cartas visibles: " + getCartas().obtenerElemento(0) + " y [Carta oculta]");

        System.out.println("Revelando carta oculta: " + getCartas().obtenerElemento(1));
        System.out.println("Puntaje inicial: " + puntajeTotal());

        while (necesitaOtraCarta() && !baraja.sinCartas()) {
            Carta cartaNueva = baraja.extraerCarta();
            añadirCarta(cartaNueva);
            System.out.println("El Dealer recibe: " + cartaNueva);
            System.out.println("Puntaje actual: " + puntajeTotal());

            if (seExcedio()) {
                System.out.println("¡El Dealer se pasó de 21!");
                break;
            }
        }

        if (!seExcedio()) {
            plantado();
            System.out.println("El Dealer se planta con " + puntajeTotal() + " puntos.");
        }
    }

    /**
     * Devuelve el estado del Dealer en formato de texto.
     * Puede ocultar la segunda carta si el juego está en curso.
     *
     * @param mostrarTodo true para revelar todas las cartas, false para ocultar la
     *                    segunda.
     * @return Cadena representando el estado actual del Dealer.
     */
    public String descripcion(boolean mostrarTodo) {
        if (mostrarTodo || estaPlantado() || seExcedio() || getCartas().obtenerTamaño() <= 1) {
            return super.toString();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(getNombre()).append(" (Puntaje: ?)");
        sb.append("\nMano: ").append(getCartas().obtenerElemento(0)).append(", [Carta oculta]");
        sb.append("\nEstado: Jugando");

        return sb.toString();
    }

    @Override
    public String toString() {
        return descripcion(false);
    }
}
