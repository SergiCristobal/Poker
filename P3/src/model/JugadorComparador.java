package model;

import java.util.Comparator;

	public class JugadorComparador implements Comparator<Jugador> {

		@Override
		public int compare(Jugador o1, Jugador o2) {
			
			int res=o1.compara(o2);
			if(res==1)return -1;
			else if(res==-1)return 1;
			else return 0;
		}

	}
