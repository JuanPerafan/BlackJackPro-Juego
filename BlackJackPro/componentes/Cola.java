package componentes;

/**
 * Implementación de una estructura tipo Cola para gestionar turnos en el juego.
 * Aplica la lógica FIFO (Primero en Entrar, Primero en Salir).
 */
public class Cola<T> {

    private Nodo<T> inicio;
    private Nodo<T> fin;
    private int cantidadElementos;

    /**
     * Nodo interno que contiene el dato y referencia al siguiente nodo.
     */
    private static class Nodo<T> {
        private T valor;
        private Nodo<T> siguienteNodo;

        public Nodo(T valor) {
            this.valor = valor;
            this.siguienteNodo = null;
        }
    }

    /**
     * Inicializa una cola vacía.
     */
    public Cola() {
        this.inicio = null;
        this.fin = null;
        this.cantidadElementos = 0;
    }

    /**
     * Inserta un nuevo elemento al final de la cola.
     * 
     * @param valor Elemento que se añadirá.
     */
    public void agregar(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor);

        if (estaVacía()) {
            inicio = nuevoNodo;
        } else {
            fin.siguienteNodo = nuevoNodo;
        }

        fin = nuevoNodo;
        cantidadElementos++;
    }

    /**
     * Remueve y retorna el primer elemento de la cola.
     * 
     * @return El elemento al inicio de la cola, o null si está vacía.
     */
    public T eliminar() {
        if (estaVacía()) {
            return null;
        }

        T valor = inicio.valor;
        inicio = inicio.siguienteNodo;

        if (inicio == null) {
            fin = null;
        }

        cantidadElementos--;
        return valor;
    }

    /**
     * Consulta el primer elemento sin eliminarlo.
     * 
     * @return El primer elemento o null si la cola está vacía.
     */
    public T peek() {
        return inicio != null ? inicio.valor : null;
    }

    /**
     * Comprueba si la cola no contiene elementos.
     * 
     * @return true si la cola está vacía, false si tiene elementos.
     */
    public boolean estaVacía() {
        return inicio == null;
    }

    /**
     * Retorna el número actual de elementos en la cola.
     * 
     * @return Cantidad de elementos.
     */
    public int obtenerCantidad() {
        return cantidadElementos;
    }

    /**
     * Representa la cola como una cadena de texto.
     * 
     * @return Cadena que muestra el contenido de la cola.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Inicio -> ");

        Nodo<T> actual = inicio;
        while (actual != null) {
            builder.append("[")
                    .append(actual.valor)
                    .append("] -> ");
            actual = actual.siguienteNodo;
        }

        builder.append("Fin");
        return builder.toString();
    }

    /**
     * Método recursivo que construye la representación en cadena de la cola.
     * 
     * @return La cola representada como texto usando recursividad.
     */
    public String mostrarRecursivamente() {
        StringBuilder builder = new StringBuilder();
        builder.append("Inicio -> ");
        mostrarRecursivoHelper(inicio, builder);
        builder.append("Fin");
        return builder.toString();
    }

    private void mostrarRecursivoHelper(Nodo<T> nodo, StringBuilder builder) {
        if (nodo == null) {
            return;
        }

        builder.append("[")
                .append(nodo.valor)
                .append("] -> ");

        mostrarRecursivoHelper(nodo.siguienteNodo, builder);
    }
}
