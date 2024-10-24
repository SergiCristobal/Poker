package model;

import java.util.ArrayList;


public class EquityCalculator {

	private ArrayList<Jugador> jugadores;
	private ArrayList<Carta> board;
	private ArrayList<Carta> baraja = new ArrayList<Carta>();
	private double totalCombinaciones;
	

	public EquityCalculator(ArrayList<Jugador> jugadores, ArrayList<Carta> board){
		this.jugadores = jugadores;
		this.board = board;
		totalCombinaciones = 0;
		crearBaraja();
	}
	
	
	public void crearBaraja() {
		baraja.clear();
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j <= 14; j++) {
				if (i == 0) {
					baraja.add(new Carta(j, 'h'));
				} else if (i == 1) {
					baraja.add(new Carta(j, 'd'));
				} else if (i == 2) {
					baraja.add(new Carta(j, 'c'));
				} else {
					baraja.add(new Carta(j, 's'));
				}
			}
		}
	}
	
	 private void quitarRepetidas(ArrayList<Carta> board, ArrayList<Jugador> jugadores) {
		for(Carta c:board) { //Quitamos las cartas de la baraja que ya estan en el board
			if(baraja.contains(c))
				baraja.remove(c);
		 }

		
		for(Jugador j: jugadores) { // Quitamos las cartas que tengan los jugadores de la baraja
			for(Carta c: j.getMano()) {
				if(baraja.contains(c))
					baraja.remove(c);
			}
		}
	}
	
	private ArrayList<ArrayList<Carta>> generarPosiblesBoards() {
		quitarRepetidas(board,jugadores);
		return Combinador.generarBoardsPosibles(baraja, board);
	}
	
	
//	La idea sería crear combinaciones de tamaño : 
//	preflop -> board.size()										 
//	flop -> board.size() - 3										 
//	river -> board.size() - 4				
//  turn -> simplemente calcular quien gana por la puntuacion de esa besthand
//  entonces calculas todos los posibles boards dada la baraja y las cartas que tienes en el board.
//  Por ejemplo si estas en pre-flop tendrias 40 cartas en la baraja para 5 posiciones 658008 posibles boards
//	para cada una de esos boards tienes que generar las combinaciones con la mano del jugador y sacar la besthand de todas esas
//	y ahi ya vas sumando puntos.
	
	public void calcularEquityJugadores() {
		ArrayList<ArrayList<Carta>> boards = generarPosiblesBoards(); // Se generan los posibles boards
		totalCombinaciones = boards.size();
		int tam = baraja.size();
		ArrayList<Jugador> jugadoresPorMejorJugada = new ArrayList<>(); // Array para ordenador los jugadores
		for(ArrayList<Carta> b : boards) {
			for(Jugador j: jugadores) {
				j.setMesa(b); 
				j.calcular(); // Se calcula la mejor jugada para cada jugador con cada board
				jugadoresPorMejorJugada.add(j);
			}
			JugadorComparador jc=new JugadorComparador();
			jugadoresPorMejorJugada.sort(jc);
			jugadoresPorMejorJugada.get(0).aumentarGanadas();
			jugadoresPorMejorJugada.clear();
		}
	}
	
	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}
	
	
	public double getTotalCombinaciones() {
		return totalCombinaciones;
	}

}
