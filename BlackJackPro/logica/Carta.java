package logica;

/**
 * Representa una carta individual en un juego de cartas como Blackjack.
 * Cada carta tiene un símbolo (valor) y un palo (categoría).
 */
public class Carta {

    // Constantes para los palos
    public static final String CORAZONES = "Corazones";
    public static final String DIAMANTES = "Diamantes";
    public static final String TREBOLES = "Tréboles";
    public static final String PICAS = "Picas";

    // Constantes para los valores especiales
    public static final String AS = "A";
    public static final String JOTA = "J";
    public static final String REINA = "Q";
    public static final String REY = "K";

    private final String simbolo;
    private final String categoria;

    /**
     * Crea una carta con un símbolo y un palo.
     *
     * @param simbolo   Valor de la carta (A, 2-10, J, Q, K).
     * @param categoria Palo de la carta (Corazones, Diamantes, Tréboles, Picas).
     */
    public Carta(String simbolo, String categoria) {
        this.simbolo = simbolo;
        this.categoria = categoria;
    }

    /**
     * Devuelve el símbolo de la carta (valor visual).
     *
     * @return El símbolo de la carta.
     */
    public String obtenerSimbolo() {
        return simbolo;
    }

    /**
     * Devuelve el palo o categoría de la carta.
     *
     * @return El palo de la carta.
     */
    public String obtenerCategoria() {
        return categoria;
    }

    /**
     * Obtiene el valor numérico de la carta de acuerdo a las reglas del Blackjack.
     * A = 1 (el valor 11 se trata externamente).
     * J, Q, K = 10
     * Números = su valor entero.
     *
     * @return Valor numérico de la carta.
     */
    public int obtenerValorJuego() {
        if (simbolo.equals(AS)) {
            return 1;
        } else if (simbolo.equals(JOTA) || simbolo.equals(REINA) || simbolo.equals(REY)) {
            return 10;
        } else {
            return Integer.parseInt(simbolo);
        }
    }

    /**
     * Indica si la carta es un As.
     *
     * @return true si es As, false en otro caso.
     */
    public boolean esAs() {
        return simbolo.equals(AS);
    }

    /**
     * Representación completa de la carta.
     *
     * @return Una cadena como "valor de palo".
     */
    @Override
    public String toString() {
        return simbolo + " de " + categoria;
    }

    /**
     * Representación abreviada de la carta.
     *
     * @return Cadena como "valor+palo_inicial".
     */
    public String representacionCorta() {
        char inicialPalo = categoria.charAt(0);
        return simbolo + inicialPalo;
    }
}
