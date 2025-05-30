package componentes;
/**
 * Implementación de una Tabla Hash para almacenar el estado de los jugadores.
 * Cada entrada almacena: nombre, puntaje, cartas en mano, estado actual.
 */
public class TablaHash<Clave, Valor> {

    private static final int CAPACIDAD_INICIAL = 16;
    private static final double FACTOR_CARGA = 0.75;

    // Array que almacena las listas enlazadas para manejar colisiones
    private Nodo<Clave, Valor>[] tabla;
    // Número actual de elementos en la tabla
    private int cantidad;
    // Umbral para hacer rehash cuando se alcanza la carga máxima permitida
    private int umbralRehash;

    /**
     * Nodo que almacena cada par clave-valor y la referencia al siguiente nodo en
     * la lista enlazada.
     */
    private static class Nodo<Clave, Valor> {
        private final Clave clave;
        private Valor valor;
        private Nodo<Clave, Valor> siguiente;

        public Nodo(Clave clave, Valor valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = null;
        }
    }

    /**
     * Constructor que inicializa la tabla con capacidad inicial y umbral de carga.
     */
    @SuppressWarnings("unchecked")
    public TablaHash() {
        this.tabla = new Nodo[CAPACIDAD_INICIAL];
        this.cantidad = 0;
        this.umbralRehash = (int) (CAPACIDAD_INICIAL * FACTOR_CARGA);
    }

    /**
     * Calcula el índice correspondiente en la tabla para una clave dada.
     * 
     * @param clave La clave a ubicar en la tabla.
     * @return El índice calculado dentro del rango de la tabla.
     */
    private int indiceParaClave(Clave clave) {
        int hash = clave.hashCode();
        return (hash & 0x7FFFFFFF) % tabla.length;
    }

    /**
     * Inserta un nuevo par clave-valor o actualiza el valor si la clave ya existe.
     * Realiza rehash si la tabla supera el factor de carga.
     * 
     * @param clave Clave a insertar o actualizar.
     * @param valor Valor asociado a la clave.
     */
    public void insertar(Clave clave, Valor valor) {
        if (clave == null) {
            throw new IllegalArgumentException("Clave no puede ser null");
        }

        if (cantidad >= umbralRehash) {
            expandirTabla();
        }

        int indice = indiceParaClave(clave);
        Nodo<Clave, Valor> actual = tabla[indice];

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                actual.valor = valor;
                return;
            }
            actual = actual.siguiente;
        }

        Nodo<Clave, Valor> nuevoNodo = new Nodo<>(clave, valor);
        nuevoNodo.siguiente = tabla[indice];
        tabla[indice] = nuevoNodo;
        cantidad++;
    }

    /**
     * Busca y devuelve el valor asociado a una clave dada.
     * 
     * @param clave Clave a buscar.
     * @return Valor asociado o null si no se encuentra la clave.
     */
    public Valor obtener(Clave clave) {
        if (clave == null) {
            return null;
        }

        int indice = indiceParaClave(clave);
        Nodo<Clave, Valor> actual = tabla[indice];

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }

        return null;
    }

    /**
     * Elimina el par clave-valor asociado a la clave dada.
     * 
     * @param clave Clave a eliminar.
     * @return Valor eliminado o null si la clave no existe.
     */
    public Valor eliminar(Clave clave) {
        if (clave == null) {
            return null;
        }

        int indice = indiceParaClave(clave);
        Nodo<Clave, Valor> actual = tabla[indice];
        Nodo<Clave, Valor> previo = null;

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                if (previo == null) {
                    tabla[indice] = actual.siguiente;
                } else {
                    previo.siguiente = actual.siguiente;
                }
                cantidad--;
                return actual.valor;
            }
            previo = actual;
            actual = actual.siguiente;
        }

        return null;
    }

    /**
     * Verifica si una clave existe en la tabla.
     * 
     * @param clave Clave a verificar.
     * @return true si la clave está presente, false si no.
     */
    public boolean contieneClave(Clave clave) {
        return obtener(clave) != null;
    }

    /**
     * Devuelve el número de pares clave-valor almacenados.
     * 
     * @return Número de elementos en la tabla.
     */
    public int tamaño() {
        return cantidad;
    }

    /**
     * Verifica si la tabla está vacía.
     * 
     * @return true si no hay elementos, false en caso contrario.
     */
    public boolean estaVacia() {
        return cantidad == 0;
    }

    /**
     * Duplica la capacidad de la tabla y redistribuye todos los elementos para
     * mantener eficiencia.
     */
    @SuppressWarnings("unchecked")
    private void expandirTabla() {
        Nodo<Clave, Valor>[] viejoTabla = tabla;
        int nuevaCapacidad = tabla.length * 2;
        tabla = new Nodo[nuevaCapacidad];
        umbralRehash = (int) (nuevaCapacidad * FACTOR_CARGA);

        for (Nodo<Clave, Valor> nodo : viejoTabla) {
            while (nodo != null) {
                int indice = indiceParaClave(nodo.clave);
                Nodo<Clave, Valor> siguienteNodo = nodo.siguiente;
                nodo.siguiente = tabla[indice];
                tabla[indice] = nodo;
                nodo = siguienteNodo;
            }
        }
    }

    /**
     * Representación en cadena del contenido actual de la tabla.
     * 
     * @return Cadena con los pares clave-valor de cada índice.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TablaHash[tamaño=").append(cantidad).append("]\n");

        for (int i = 0; i < tabla.length; i++) {
            if (tabla[i] != null) {
                sb.append(i).append(": ");
                Nodo<Clave, Valor> nodo = tabla[i];
                while (nodo != null) {
                    sb.append("[")
                            .append(nodo.clave)
                            .append("=>").append(nodo.valor)
                            .append("]").append(nodo.siguiente != null ? " -> " : "");
                    nodo = nodo.siguiente;
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Muestra todas las claves almacenadas en la tabla usando recursividad.
     * 
     * @return Cadena con todas las claves separadas por coma.
     */
    public String listarClaves() {
        StringBuilder sb = new StringBuilder();
        sb.append("Claves: [");
        listarClavesRecursivo(0, sb);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Método recursivo auxiliar para listar las claves.
     */
    private void listarClavesRecursivo(int indice, StringBuilder sb) {
        if (indice >= tabla.length) {
            return;
        }

        Nodo<Clave, Valor> nodo = tabla[indice];
        while (nodo != null) {
            sb.append(nodo.clave);
            nodo = nodo.siguiente;
            if (nodo != null || indice < tabla.length - 1) {
                sb.append(", ");
            }
        }

        listarClavesRecursivo(indice + 1, sb);
    }
}
