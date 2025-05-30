package logica;

import java.util.Random;

import componentes.ListaEnlazada;

/**
 * Clase que representa un mazo de cartas para juegos de cartas como Blackjack.
 * Utiliza una lista enlazada para almacenar las cartas.
 */
public class MazoCartas {

    private ListaEnlazada<Carta> pilaCartas;
    private Random generador;

    /**
     * Crea un mazo con las 52 cartas estándar.
     */
    public MazoCartas() {
        this.pilaCartas = new ListaEnlazada<>();
        this.generador = new Random();
        cargarCartasIniciales();
    }

    /**
     * Agrega las 52 cartas tradicionales al mazo.
     */
    private void cargarCartasIniciales() {
        String[] palos = { Carta.CORAZONES, Carta.DIAMANTES, Carta.TREBOLES, Carta.PICAS };
        String[] valores = { Carta.AS, "2", "3", "4", "5", "6", "7", "8", "9", "10",
                Carta.JOTA, Carta.REINA, Carta.REY };

        for (String palo : palos) {
            for (String valor : valores) {
                pilaCartas.insertar(new Carta(valor, palo));
            }
        }
    }

    /**
     * Reordena aleatoriamente las cartas del mazo usando el algoritmo de
     * Fisher-Yates.
     */
    public void barajar() {
        Carta[] cartasArray = new Carta[pilaCartas.obtenerTamaño()];
        int i = 0;

        while (!pilaCartas.estaVacía()) {
            cartasArray[i++] = pilaCartas.removerPrimero();
        }

        for (int j = cartasArray.length - 1; j > 0; j--) {
            int k = generador.nextInt(j + 1);
            Carta temp = cartasArray[j];
            cartasArray[j] = cartasArray[k];
            cartasArray[k] = temp;
        }

        for (Carta carta : cartasArray) {
            pilaCartas.insertar(carta);
        }
    }

    /**
     * Extrae la primera carta del mazo.
     *
     * @return La carta superior, o null si el mazo está vacío.
     */
    public Carta extraerCarta() {
        return pilaCartas.removerPrimero();
    }

    /**
     * Determina si ya no hay cartas en el mazo.
     *
     * @return true si el mazo está vacío, de lo contrario false.
     */
    public boolean sinCartas() {
        return pilaCartas.estaVacía();
    }

    /**
     * Informa cuántas cartas quedan en el mazo.
     *
     * @return Cantidad de cartas restantes.
     */
    public int totalCartas() {
        return pilaCartas.obtenerTamaño();
    }

    /**
     * Retorna una descripción del mazo y su estado actual.
     *
     * @return Representación textual del mazo.
     */
    @Override
    public String toString() {
        return "Mazo con " + totalCartas() + " cartas:\n" + pilaCartas.toString();
    }
}
