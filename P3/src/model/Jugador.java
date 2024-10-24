package model;

import java.util.ArrayList;


public class Jugador {
	
	private String id;
	private ArrayList<Carta> mano;
	private CardAnalizator mejor;
	private ArrayList<Carta> mesa;
	private int numGanadas;
	


	public Jugador(String id, ArrayList<Carta> mano,ArrayList<Carta> mesa) {
		this.id = id;
		this.mano=mano;
		this.mesa=mesa;
		this.mejor=null;
		this.numGanadas = 0;
	}
	
	// Comprobamos cual de todas las posibles manos del jugador es la mejor y la guardamos
	public void calcular() {
		ArrayList<ArrayList<Carta>> posiblesCombis=Combinador.generarManosPosibles(mano, mesa);
		for(ArrayList<Carta> cartas:posiblesCombis) {
			CardAnalizator analizadas=new CardAnalizator(cartas);
			analizadas.bestHand();
			if(mejor==null)
				mejor=analizadas;
			else {
				if(mejor.compare(analizadas) == -1) {
					mejor=analizadas;
				}
			}
		}
	}
	/*// Metodo para calcular la mejor mano con la restriccion del modo de juego Omaha
	public void calcularOmaha() {
		ArrayList<ArrayList<Carta>> posiblesCombis=CombinadorOmaha.generarCombinaciones(mano, mesa);
		for(ArrayList<Carta> cartas:posiblesCombis) {
			CardAnalizator analizadas=new CardAnalizator(cartas);
			analizadas.bestHand();
			if(mejor==null)
				mejor=analizadas;
			else {
				if(mejor.compare(analizadas) == -1) {
					mejor=analizadas;
				}
			}
		}
	}*/
	public String getId() {
		return id;
	}
	public String getBestHand() {
		return mejor.getbestString();
	}
	public boolean hayGutshot() {
		return mejor.hayGutshot(mejor.getCartas());
	}
	public boolean hayOpenEnded() {
		return mejor.hayOpenEnded(mejor.getCartas());
	}
	public boolean drawFlush() {
		return mejor.drawFlush(mejor.getCartas());
	}
	public int compara(Jugador o2) {
		return mejor.compare(o2.mejor);
	}
	
	public String salida() {
		
	StringBuilder str=new StringBuilder();
	str.append(this.getId()).append(": ");
	
	for(Carta c:mejor.getCartas()) {
		if(mano.contains(c)) {
			str.append(c.toString());
		}
		else str.append(c.toString());
	}
	
	String jugada = (" (" + mejor.getBest() + ")");
	str.append(jugada);
	return str.toString();
	
	}
	
	public String salida2and4(){
		StringBuilder str=new StringBuilder();
		str.append("- Best hand: ");
		str.append(mejor.getBest());
		str.append(" with ");
		for(Carta c:mejor.getCartas()) {
			if(mano.contains(c)) {
				str.append(c.toString());
			}
			else str.append(c.toString());
		}
		return str.toString();
	}

	public ArrayList<Carta> getMano() {
		return mano;
	}
	
	public void setMesa(ArrayList<Carta> mesa) {
		this.mesa = mesa;
	}

	public void aumentarGanadas() {
		numGanadas++;
		
	}

	public int getNumGanadas() {
		return numGanadas;
	}
	
	public String toString() {
		return id + " " +  mano.toString();
	}
}
