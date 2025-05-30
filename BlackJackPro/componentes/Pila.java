package componentes;

/*
 * Implementación genérica de una Pila (Stack) para uso general.
 */
public class Pila<T> {

    private volatile Nodo<T> tope;
    private volatile int contador;

    /*
     * Nodo interno inmutable para mantener la integridad del dato.
     */
    private static class Nodo<T> {
        private T valor;
        private Nodo<T> siguiente;

        public Nodo(T valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

    /**
     * Inicializa una pila vacía.
     */
    public Pila() {
        this.tope = null;
        this.contador = 0;
    }

    /**
     * Inserta un elemento en la cima de la pila.
     * 
     * @param valor Elemento a agregar.
     */
    public void push(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor);
        nuevoNodo.siguiente = tope;
        tope = nuevoNodo;
        contador++;
    }

    /**
     * Remueve y retorna el elemento en la cima.
     * 
     * @return Elemento extraído o null si la pila está vacía.
     */
    public T pop() {
        if (tope == null) {
            return null;
        }

        T valor = tope.valor;
        tope = tope.siguiente;
        contador--;

        return valor;
    }

    /**
     * Retorna el elemento en la cima sin removerlo.
     * 
     * @return Elemento en la cima o null si no hay elementos.
     */
    public T peek() {
        return tope != null ? tope.valor : null;
    }

    /**
     * Verifica si la pila está vacía.
     * 
     * @return true si no contiene elementos, false de lo contrario.
     */
    public boolean estaVacia() {
        return tope == null;
    }

    /**
     * Devuelve la cantidad de elementos en la pila.
     * 
     * @return Número de elementos almacenados.
     */
    public int tamaño() {
        return contador;
    }

    /**
     * Representa la pila como cadena mostrando de arriba hacia abajo.
     * 
     * @return Cadena con el contenido actual de la pila.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tope -> ");

        Nodo<T> actual = tope;
        while (actual != null) {
            sb.append("[")
                    .append(actual.valor)
                    .append("] -> ");
            actual = actual.siguiente;
        }

        sb.append("null");
        return sb.toString();
    }

    /**
     * Representación recursiva de la pila desde la base hasta la cima.
     * 
     * @return Cadena con el contenido en orden inverso.
     */
    public String inverso() {
        StringBuilder sb = new StringBuilder();
        sb.append("Base -> ");
        inversoRecursivo(tope, sb);
        sb.append("Tope");
        return sb.toString();
    }

    private void inversoRecursivo(Nodo<T> nodo, StringBuilder sb) {
        if (nodo == null) {
            return;
        }

        inversoRecursivo(nodo.siguiente, sb);

        sb.append("[")
                .append(nodo.valor)
                .append("] -> ");
    }
}
