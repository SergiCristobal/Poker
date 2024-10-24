package model;

import java.util.Comparator;

public class CartaComparador implements Comparator<Carta> {
//compara dos cartas 
	@Override
	public int compare(Carta o1, Carta o2) {
		int sol=-1;
		if(o1.equals(o2))sol=0;
		else if(o1.getValor()>o2.getValor())sol=1;
		return sol;
	}
}
