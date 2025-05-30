package juego;

import logica.MazoCartas;
import logica.Carta;
import logica.Dealer;
import logica.Jugador;

import java.util.Scanner;

import componentes.ListaEnlazada;
import componentes.TablaHash;

/**
 * Controlador principal para la gestión y dinámica del juego Blackjack.
 * Hace uso de estructuras de datos y clases del modelo para el desarrollo del
 * juego.
 */
public class JuegoBlackjack {

    private MazoCartas mazo;
    private Jugador participante;
    private Dealer repartidor;
    private AdministradorTurnos gestionTurnos;
    private TablaHash<String, Jugador> mapaJugadores;
    private Scanner entradaUsuario;
    private boolean partidaActiva;

    /**
     * Inicializa los componentes esenciales para la partida de Blackjack.
     */
    public JuegoBlackjack() {
        this.mazo = new MazoCartas();
        this.entradaUsuario = new Scanner(System.in);
        this.gestionTurnos = new AdministradorTurnos();
        this.mapaJugadores = new TablaHash<>();
        this.partidaActiva = false;
    }

    /**
     * Método principal que pone en marcha el flujo completo del juego.
     */
    public void comenzarPartida() {
        System.out.println("====================================");
        System.out.println("          BLACKJACK GAME :P             ");
        System.out.println("====================================");

        // Pedir nombre del jugador
        System.out.print("\n Tu nombre: ");
        String nombre = entradaUsuario.nextLine().trim();
        if (nombre.isEmpty()) {
            nombre = "Jugador";
        }

        // Crear instancias de jugador y dealer
        participante = new Jugador(nombre);
        repartidor = new Dealer();

        // Registrar jugadores en la estructura de control
        mapaJugadores.insertar(participante.getNombre(), participante);
        mapaJugadores.insertar(repartidor.getNombre(), repartidor);

        // Activar el ciclo principal de la partida
        partidaActiva = true;
        while (partidaActiva) {
            jugarMano();
            solicitarNuevaMano();
        }

        // Presentar resumen final del juego
        mostrarResumenFinal();
        System.out.println("\nGracias por jugar. Vuelve pronto!");
        entradaUsuario.close();
    }

    /**
     * Ejecuta una mano completa, desde reparto hasta definición del ganador.
     */
    private void jugarMano() {
        System.out.println("\n====================================");
        System.out.println("         UNA NUEVA MANO EN LA MESA    ");
        System.out.println("======================================");

        // Reiniciar el mazo y barajar
        mazo = new MazoCartas();
        mazo.barajar();

        // Limpiar las cartas de ambos jugadores
        participante.reiniciarMano();
        repartidor.reiniciarMano();

        // Repartir dos cartas a cada jugador
        for (int i = 0; i < 2; i++) {
            participante.añadirCarta(mazo.extraerCarta());
            repartidor.añadirCarta(mazo.extraerCarta());
        }

        // Establecer el orden de turno
        gestionTurnos.reiniciarFilaJugadores(participante, repartidor);

        // Mostrar estado actual de la partida
        mostrarEstadoActual();

        // Verificar si existe Blackjack inicial
        if (detectarBlackjack()) {
            return;
        }

        // Turno del jugador
        ejecutarTurnoJugador();

        // Turno automático del dealer si jugador no se pasó
        if (!participante.seExcedio()) {
            repartidor.ejecutarTurno(mazo);
        }

        // Determinar ganador de la mano
        declararGanador();
    }

    /**
     * Controla las acciones durante el turno del jugador.
     */
    private void ejecutarTurnoJugador() {
        System.out.println("\n--- Es el turno de " + participante.getNombre() + " ---");

        boolean terminoTurno = false;
        while (!terminoTurno) {
            System.out.println("\nTu mano actual: ");
            ListaEnlazada<Carta> mano = participante.getCartas();
            for (int i = 0; i < mano.obtenerTamaño(); i++) {
                System.out.println("- " + mano.obtenerElemento(i));
            }
            System.out.println("Puntaje actual: " + participante.puntajeTotal());

            // Opciones para el jugador
            System.out.println("\n¿Ahora qué vas a hacer?");
            System.out.println("1. Pides carta");
            System.out.println("2. Te plantas");
            System.out.print("Elige opción (1-2): ");

            int eleccion;
            try {
                eleccion = Integer.parseInt(entradaUsuario.nextLine().trim());
            } catch (NumberFormatException e) {
                eleccion = 0; // Opción inválida
            }

            switch (eleccion) {
                case 1: // Solicitar carta
                    if (!mazo.sinCartas()) {
                        Carta cartaNueva = mazo.extraerCarta();
                        participante.añadirCarta(cartaNueva);
                        System.out.println("\nHas recibido: " + cartaNueva);

                        if (participante.seExcedio()) {
                            System.out.println("Te pasaste de los 21. Puntaje final: " + participante.puntajeTotal());
                            terminoTurno = true;
                        }
                    } else {
                        System.out.println("Las cartas se agotaron ;(.");
                        terminoTurno = true;
                    }
                    break;

                case 2: // Plantarse
                    participante.plantado();
                    System.out.println("Te has plantado con " + participante.puntajeTotal() + " puntos.");
                    terminoTurno = true;
                    break;

                default:
                    System.out.println("Opción no valida. Intenta de nuevo");
            }
        }
    }

    /**
     * Revisa si alguno tiene Blackjack al inicio y maneja resultado inmediato.
     * 
     * @return true si hay Blackjack inicial, false en caso contrario.
     */
    private boolean detectarBlackjack() {
        boolean jugadorBlackjack = participante.tieneBlackjack();
        boolean dealerBlackjack = repartidor.tieneBlackjack();

        if (jugadorBlackjack || dealerBlackjack) {
            System.out.println("\n¡Blackjack inicial detectado!");

            // Mostrar cartas del jugador
            System.out.println("\nCartas de " + participante.getNombre() + ":");
            ListaEnlazada<Carta> mano = participante.getCartas();
            for (int i = 0; i < mano.obtenerTamaño(); i++) {
                System.out.println("- " + mano.obtenerElemento(i));
            }

            // Mostrar cartas del dealer
            System.out.println("\nCartas del Dealer:");
            ListaEnlazada<Carta> manoDealer = repartidor.getCartas();
            for (int i = 0; i < manoDealer.obtenerTamaño(); i++) {
                System.out.println("- " + manoDealer.obtenerElemento(i));
            }

            // Evaluar ganador según Blackjack
            if (jugadorBlackjack && dealerBlackjack) {
                System.out.println("\nEmpate, ambos jugadores han terminado con Blackjack.");
            } else if (jugadorBlackjack) {
                System.out.println("\n¡" + participante.getNombre() + " gana con un estupendo Blackjack!");
                participante.sumarVictoria();
            } else {
                System.out.println("\nEl Dealer gana con un maravilloso Blackjack!");
                repartidor.sumarVictoria();
            }

            return true;
        }
        return false;
    }

    /**
     * Determina el ganador según las reglas después de los turnos.
     */
    private void declararGanador() {
        System.out.println("\n====================================");
        System.out.println("         RESULTADO DE LA MANO        ");
        System.out.println("====================================");

        int puntosJugador = participante.puntajeTotal();
        int puntosDealer = repartidor.puntajeTotal();

        System.out.println(participante.getNombre() + ": " + puntosJugador + " puntos");
        System.out.println("Dealer: " + puntosDealer + " puntos");

        if (participante.seExcedio()) {
            System.out.println("\nEl Dealer ha ganado " + participante.getNombre() + " excedió los 21 puntos.");
            repartidor.sumarVictoria();
        } else if (repartidor.seExcedio()) {
            System.out.println("\n¡" + participante.getNombre() + " ha ganado! El Dealer excedió 21 puntos.");
            participante.sumarVictoria();
        } else if (puntosJugador > puntosDealer) {
            System.out.println("\n¡" + participante.getNombre() + " gana por un puntaje mayor!");
            participante.sumarVictoria();
        } else if (puntosDealer > puntosJugador) {
            System.out.println("\nEl Dealer gana por un puntaje mayor!");
            repartidor.sumarVictoria();
        } else {
            System.out.println("\nHa habido un empate.");
        }
    }

    /**
     * Imprime el estado actual del juego: cartas y puntajes visibles.
     */
    private void mostrarEstadoActual() {
        System.out.println("\n--- Estado Actual ---");
        System.out.println(participante);
        System.out.println(repartidor);
        System.out.println("Cartas restantes en el mazo: " + mazo.totalCartas());
    }

    /**
     * Consulta al usuario si desea jugar otra mano y actualiza la variable de
     * control.
     */
    private void solicitarNuevaMano() {
        System.out.print("\n¿Quieres jugar otra mano? (s/n): ");
        String respuesta = entradaUsuario.nextLine().trim().toLowerCase();

        while (!respuesta.equals("s") && !respuesta.equals("n")) {
            System.out.print("Por favor, escribe 's' para sí o 'n' para no: ");
            respuesta = entradaUsuario.nextLine().trim().toLowerCase();
        }

        partidaActiva = respuesta.equals("s");
    }

    /**
     * Presenta el resumen final con las partidas ganadas de cada participante.
     */
    private void mostrarResumenFinal() {
        System.out.println("\n====================================");
        System.out.println("        ESTADÍSTICAS FINALES       ");
        System.out.println("====================================");
        System.out.println(participante.getNombre() + ": " + participante.getVictorias() + " victorias");
        System.out.println("Dealer: " + repartidor.getVictorias() + " victorias");

        if (participante.getVictorias() > repartidor.getVictorias()) {
            System.out.println("\n¡" + participante.getNombre() + " es el campeón universal!");
        } else if (repartidor.getVictorias() > participante.getVictorias()) {
            System.out.println("\nEl Dealer es el campeón!");
        } else {
            System.out.println("\nEmpate general. Todos son ganadores!");
        }
    }
}
