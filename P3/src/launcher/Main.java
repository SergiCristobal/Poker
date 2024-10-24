package launcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Carta;
import model.EquityCalculator;
import model.Jugador;

public class Main {

	
	private static String _inFile = null;
	
	public static void main(String[] args) {
		// Ejemplo pre-flop practica 3 a mano
		/*ArrayList<Carta> mano = new ArrayList<>();
		mano.add(new Carta('A','d'));
		mano.add(new Carta('A','c'));
		Jugador j1 = new Jugador("J1",mano,null);
		mano.clear();
		
		mano.add(new Carta('Q','d'));
		mano.add(new Carta('Q','h'));
		Jugador j2 = new Jugador("J2",mano,null);
		mano.clear();
		
		mano.add(new Carta('A','s'));
		mano.add(new Carta('K','s'));
		Jugador j3 = new Jugador("J3",mano,null);
		mano.clear();
		
		mano.add(new Carta('K','c'));
		mano.add(new Carta('Q','s'));
		Jugador j4 = new Jugador("J4",mano,null);
		mano.clear();
		
		mano.add(new Carta('6','d'));
		mano.add(new Carta('7','c'));
		Jugador j5 = new Jugador("J5",mano,null);
		mano.clear();
		
		mano.add(new Carta('8','d'));
		mano.add(new Carta('8','h'));
		Jugador j6 = new Jugador("J6",mano,null);
		mano.clear();
		
		ArrayList<Jugador> jugadores = new ArrayList<>();
		jugadores.add(j1);
		jugadores.add(j2);
		jugadores.add(j3);
		jugadores.add(j4);
		jugadores.add(j5);
		jugadores.add(j6);*/
		
		
			 
		 _inFile=args[0];
		 File file=new File(_inFile);
		try(Scanner scanner= new Scanner(file)){
			ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
			while(scanner.hasNextLine()) {
			
				String linea=scanner.next();
				String datos[] = linea.split(";");
				
				
				for(int i = 0 ; i < Integer.parseInt(datos[0]);i++) {
					
					String cartasJug = datos[i + 1].substring(2,6);
					ArrayList<Carta> mano = crearCartas(cartasJug);
					
					
					String cartasMesa = datos[datos.length-1];
					ArrayList<Carta> mesa = crearCartas(cartasMesa);
					
					String idJug = datos[i + 1].substring(0, 2);
					Jugador j =  new Jugador(idJug,mano,mesa);
					jugadores.add(j);
				}
			}
			ArrayList<Carta> board = new ArrayList<Carta>();
			
			//board.add(new Carta('Q', 'c'));
			//board.add(new Carta('6','s'));
			//board.add(new Carta('8','c'));
			//board.add(new Carta('K','d'));
			EquityCalculator eqc = new EquityCalculator(jugadores,board);
			eqc.calcularEquityJugadores();
			jugadores = eqc.getJugadores();
			for(Jugador j : jugadores) {
			  double equity = (j.getNumGanadas() / eqc.getTotalCombinaciones()) * 100;
			  String res =String.format("%.3f", equity);
			  System.out.println(j.getId() + ":" + res + "%");
			}
		
		}catch(IOException e){
			e.printStackTrace();
		}
	
	}
	
	static ArrayList<Carta> crearCartas (String cartasStr){
		char valor='0';
		ArrayList<Carta> mano = new ArrayList<Carta> ();
		for(int i = 0;i<cartasStr.length();i++) {
			if(valor!='0') {
				Carta carta=new Carta(valor,cartasStr.charAt(i));
				mano.add(carta);
				valor='0';
			}
			else {
				valor=cartasStr.charAt(i);
			}
		
		}
		return mano;
	}

}
