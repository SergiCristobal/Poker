package model;

import java.util.ArrayList;

public class Combinador {

	// Combinamos las cartas del jugador y las cartas de la mesa para generar todas las combinaciones posibles de 5 cartas
	public static ArrayList<ArrayList<Carta>> generarManosPosibles(ArrayList<Carta> manoJugador, ArrayList<Carta> cartasEnMesa) {
		ArrayList<ArrayList<Carta>> combinaciones = new ArrayList<>();
		ArrayList<Carta> cartasDisponibles = new ArrayList<>(manoJugador);
        cartasDisponibles.addAll(cartasEnMesa);

        generarCombinacionesRecursivamente(combinaciones, new ArrayList<>(), cartasDisponibles, 5, 0);

        return combinaciones;
    }

	// Utilizando vuelta atras genera las combinaciones posibles cuando llega a 5 cartas se añade la posible combinacion
    private static void generarCombinacionesRecursivamente(ArrayList<ArrayList<Carta>> combinaciones, ArrayList<Carta> combinacionActual, ArrayList<Carta> cartasDisponibles, int k, int startIndex) {
        if (combinacionActual.size() == k) { // Si llega a k que en este caso es 5 es combinacion valida
            combinaciones.add(new ArrayList<>(combinacionActual));
            return;
        }
        
        for (int i = startIndex; i < cartasDisponibles.size(); i++) {
            combinacionActual.add(cartasDisponibles.get(i));
            generarCombinacionesRecursivamente(combinaciones, combinacionActual, cartasDisponibles, k, i + 1);
            combinacionActual.remove(combinacionActual.size() - 1);
        }
    }
    
	 // Combinamos las cartas de la baraja y las cartas de la mesa para generar todas las combinaciones posibles de 5 cartas
    public static ArrayList<ArrayList<Carta>> generarBoardsPosibles(ArrayList<Carta> baraja, ArrayList<Carta> cartasEnMesa) {
        ArrayList<ArrayList<Carta>> combinaciones = new ArrayList<>();
        ArrayList<Carta> cartasDisponibles = new ArrayList<>(baraja);
        cartasDisponibles.addAll(cartasEnMesa);

        generarBoardsRecursivamente(combinaciones, new ArrayList<>(), cartasDisponibles, 5, 0, cartasEnMesa);

        return combinaciones;
    }

    // Utilizando vuelta atrás, genera las combinaciones posibles cuando llega a 5 cartas se añade la posible combinación
    private static void generarBoardsRecursivamente(ArrayList<ArrayList<Carta>> combinaciones, ArrayList<Carta> combinacionActual, ArrayList<Carta> cartasDisponibles, int k, int startIndex, ArrayList<Carta> cartasEnMesa) {
        if (combinacionActual.size() == k) { // Si llega a k que en este caso es 5 es combinación válida
            if (contieneTodasLasCartas(combinacionActual, cartasEnMesa)) {
                combinaciones.add(new ArrayList<>(combinacionActual));
            }
            return;
        }

        for (int i = startIndex; i < cartasDisponibles.size(); i++) {
            combinacionActual.add(cartasDisponibles.get(i));
            generarBoardsRecursivamente(combinaciones, combinacionActual, cartasDisponibles, k, i + 1, cartasEnMesa);
            combinacionActual.remove(combinacionActual.size() - 1);
        }
    }

    // Verifica si la combinación actual contiene todas las cartas de cartasEnMesa
    private static boolean contieneTodasLasCartas(ArrayList<Carta> combinacionActual, ArrayList<Carta> cartasEnMesa) {
        return combinacionActual.containsAll(cartasEnMesa);
    }


}
