package componentes;

/*
 * Árbol binario especializado en decisiones del Dealer para Blackjack.
 * Aplica una estrategia fija y directa basada en el puntaje del Dealer.
 * 
 * Ventajas:
 * - Espacio: constante, ya que no cambia su estructura.
 * - Tiempo: evaluación inmediata O(1).
 * 
 * Versión ajustada con nombres neutros para reutilización general.
 * @version 2.1
 */
public class ArbolBinario<T> {

    // Valor límite para que el Dealer decida detenerse
    private static final int LIMITE_ESTRATEGIA = 17;

    // Nodo raíz del árbol
    private NodoBinario<T> nodoRaiz;

    /*
     * Nodo individual del árbol binario.
     * Contiene un valor, dos hijos (izquierdo y derecho), y metadatos de la
     * decisión.
     */
    public static class NodoBinario<T> {
        private T valor;
        private NodoBinario<T> izquierdo;
        private NodoBinario<T> derecho;
        private String descripcion;
        private String condicion;

        public NodoBinario(T valor, String descripcion, String condicion) {
            this.valor = valor;
            this.descripcion = descripcion;
            this.condicion = condicion;
            this.izquierdo = null;
            this.derecho = null;
        }

        public T getValor() {
            return valor;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getCondicion() {
            return condicion;
        }

        public NodoBinario<T> getIzquierdo() {
            return izquierdo;
        }

        public void setIzquierdo(NodoBinario<T> nodo) {
            this.izquierdo = nodo;
        }

        public NodoBinario<T> getDerecho() {
            return derecho;
        }

        public void setDerecho(NodoBinario<T> nodo) {
            this.derecho = nodo;
        }
    }

    /*
     * Constructor que crea un árbol de decisiones básicas para el Dealer.
     */
    @SuppressWarnings("unchecked")
    public ArbolBinario() {
        nodoRaiz = new NodoBinario<>((T) "Inicio",
                "Evaluar si seguir o detenerse según el puntaje",
                String.format("puntaje >= %d", LIMITE_ESTRATEGIA));

        NodoBinario<T> pedir = new NodoBinario<>((T) "Pedir carta",
                "El Dealer intenta mejorar su mano",
                String.format("puntaje < %d", LIMITE_ESTRATEGIA));

        NodoBinario<T> plantarse = new NodoBinario<>((T) "Plantarse",
                "El Dealer mantiene su mano actual",
                String.format("puntaje >= %d", LIMITE_ESTRATEGIA));

        nodoRaiz.setIzquierdo(pedir);
        nodoRaiz.setDerecho(plantarse);
    }

    /*
     * Devuelve el nodo correspondiente a la acción según el puntaje del Dealer.
     * 
     * @param puntosActuales Puntaje actual del Dealer
     */
    public NodoBinario<T> decidir(int puntosActuales) {
        if (estaVacio())
            return null;

        NodoBinario<T> resultado = (puntosActuales < LIMITE_ESTRATEGIA)
                ? nodoRaiz.getIzquierdo()
                : nodoRaiz.getDerecho();

        System.out.println(String.format(
                "\nDealer Con %d puntos → %s",
                puntosActuales, resultado.getDescripcion()));

        return resultado;
    }

    /*
     * Verifica si el árbol está vacío.
     */
    public boolean estaVacio() {
        return nodoRaiz == null;
    }

    /*
     * Muestra el árbol completo por consola.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nÁrbol Binario de Decisiones");
        sb.append("\n===========================\n");
        imprimir(nodoRaiz, "", true);
        return sb.toString();
    }

    // Lógica auxiliar para impresión estructurada
    private void imprimir(NodoBinario<T> nodo, String prefijo, String rama, boolean ultimo) {
        if (nodo == null)
            return;

        System.out.println(prefijo + rama + nodo.getValor() +
                " [" + nodo.getCondicion() + "]" +
                "\n" + prefijo + "   └── " + nodo.getDescripcion());

        String nuevoPrefijo = prefijo + (ultimo ? "    " : "│   ");

        if (nodo.getIzquierdo() != null) {
            imprimir(nodo.getIzquierdo(), nuevoPrefijo, "├── ", nodo.getDerecho() == null);
        }
        if (nodo.getDerecho() != null) {
            imprimir(nodo.getDerecho(), nuevoPrefijo, "└── ", true);
        }
    }

    private void imprimir(NodoBinario<T> nodo, String prefijo, boolean ultimo) {
        imprimir(nodo, prefijo, ultimo ? "└── " : "├── ", ultimo);
    }

    /*
     * Retorna el nodo raíz del árbol.
     */
    public NodoBinario<T> getRaiz() {
        return nodoRaiz;
    }
}
