package componentes;

import java.util.Iterator;

/*
 * Implementación de una lista enlazada simple.
 */
public class ListaEnlazada<T> implements Iterable<T> {

    private Nodo<T> inicio;
    private int contador;

    /*
     * Clase interna Nodo que mantiene el dato y referencia al siguiente nodo.
     */
    private static class Nodo<T> {
        private final T valor;
        private Nodo<T> siguienteNodo;

        /*
         * Constructor para crear nodos con el dato dado.
         * 
         * @param valor Elemento almacenado en el nodo.
         */
        public Nodo(T valor) {
            this.valor = valor;
            this.siguienteNodo = null;
        }
    }

    /*
     * Constructor para inicializar la lista vacía.
     */
    public ListaEnlazada() {
        this.inicio = null;
        this.contador = 0;
    }

    /*
     * Añade un elemento al final de la lista.
     * Complejidad: O(n) ya que recorre hasta el último nodo.
     * 
     * @param valor Elemento a añadir.
     * 
     * @throws IllegalArgumentException si el valor es null.
     */
    public void insertar(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No se permiten valores null");
        }

        Nodo<T> nuevoNodo = new Nodo<>(valor);

        if (inicio == null) {
            inicio = nuevoNodo;
        } else {
            Nodo<T> actual = inicio;
            while (actual.siguienteNodo != null) {
                actual = actual.siguienteNodo;
            }
            actual.siguienteNodo = nuevoNodo;
        }

        contador++;
    }

    /*
     * Quita y devuelve el primer elemento de la lista.
     * Complejidad: O(1).
     * 
     * @return El dato eliminado o null si la lista está vacía.
     */
    public T removerPrimero() {
        if (estaVacía()) {
            return null;
        }

        T valor = inicio.valor;
        inicio = inicio.siguienteNodo;
        contador--;

        return valor;
    }

    /*
     * Comprueba si la lista está vacía.
     * Complejidad: O(1).
     * 
     * @return true si no hay elementos, false en caso contrario.
     */
    public boolean estaVacía() {
        return inicio == null;
    }

    /*
     * Devuelve la cantidad de elementos en la lista.
     * 
     * @return Número de elementos almacenados.
     */
    public int obtenerTamaño() {
        return contador;
    }

    /*
     * Representa la lista como cadena.
     * Complejidad: O(n).
     * Formato: [elem1] -> [elem2] -> ... -> null
     * 
     * @return Cadena que muestra los elementos de la lista.
     */
    @Override
    public String toString() {
        if (estaVacía()) {
            return "[]->null";
        }
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = inicio;
        sb.append("[").append(actual.valor).append("]->");
        while (actual.siguienteNodo != null) {
            actual = actual.siguienteNodo;
            sb.append("[").append(actual.valor).append("]->");
        }
        sb.append("null");
        return sb.toString();
    }

    /*
     * Muestra la lista de forma recursiva.
     * 
     * @return Representación en cadena de la lista usando recursividad.
     */
    public String mostrarRecursivamente() {
        return mostrarRecursivoAux(inicio, new StringBuilder()).toString();
    }

    /*
     * Método auxiliar recursivo que construye la cadena.
     */
    private StringBuilder mostrarRecursivoAux(Nodo<T> nodo, StringBuilder sb) {
        if (nodo == null) {
            return sb.append("null");
        }
        sb.append("[").append(nodo.valor).append("]->");
        return mostrarRecursivoAux(nodo.siguienteNodo, sb);
    }

    /*
     * Obtiene el elemento en la posición indicada.
     * 
     * @param indice Posición del elemento (0-based).
     * 
     * @return Elemento en la posición especificada.
     * 
     * @throws IndexOutOfBoundsException si el índice está fuera del rango válido.
     */
    public T obtenerElemento(int indice) {
        if (indice < 0 || indice >= contador) {
            throw new IndexOutOfBoundsException(
                    String.format("Índice %d fuera de rango [0,%d]", indice, contador - 1));
        }

        Nodo<T> actual = inicio;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguienteNodo;
        }
        return actual.valor;
    }

    /*
     * Implementación de Iterator para permitir recorrer la lista con for-each.
     * 
     * @return Iterador para la lista.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> nodoActual = inicio;

            @Override
            public boolean hasNext() {
                return nodoActual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                T valor = nodoActual.valor;
                nodoActual = nodoActual.siguienteNodo;
                return valor;
            }
        };
    }
}
