package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


	public class CardAnalizator {
		
		private ArrayList<Carta> poker;
		private ArrayList<Carta> par2;
		private ArrayList<Carta> par1;
		private ArrayList<Carta> trio;
		private ArrayList<Carta> escalera;
		private ArrayList<Carta> cartas;
		
		private Carta alta=null;
		private char palo;
		private String bestString;
		private String best;
		private boolean hayEscal=false;
		private boolean hayEscalcol=false;
		private boolean haytrio=false;
		private int puntuacion=0;
		private boolean hayPareja;
		
		
		public CardAnalizator(ArrayList<Carta> cartas) {
			 par2=new ArrayList<Carta>();
			 par1=new ArrayList<Carta>();
			 trio=new ArrayList<Carta>();
			 escalera=new ArrayList<Carta>();
			 poker=new ArrayList<Carta>();
			 this.cartas=cartas;
		}
	
		
		public void ordenar(ArrayList<Carta> cartas) {
			CartaComparador comparador=new CartaComparador();
			cartas.sort(comparador);
		}
		/*
		 * Esta funcion comprueba si se esta dibujando un flush de forma muy parecida a como se comprueba si hay poker 
		 */
		public boolean drawFlush(ArrayList<Carta> cartas) {
			boolean draw=false;
			Map<Character,Integer> numPalos=new HashMap<Character,Integer>();
			for(Carta c:cartas) {
				if(numPalos.containsKey(c.getPalo())) {
					int aux=numPalos.get(c.getPalo());
					aux++;
					numPalos.replace(c.getPalo(), aux);
					if(aux==4)draw=true;
					if(aux==5)draw=false;
				}
				else {
					numPalos.put(c.getPalo(), 1);
				}
			}
			return draw;
		}
		
		/*
		 * Esta funcion recorre las cartas y determina si hay gutshot
		 */
		public Boolean hayGutshot(ArrayList<Carta> cartas) {
			ArrayList<Carta> temp=new ArrayList<Carta>();
			
			for(Carta c: cartas) {
				temp.add(c);
			}
			
			ordenar(temp);
			
			int i=0;
			int cont=1;
			int valor = 0;
			boolean gut=false;
			boolean salto=false;
			for(Carta c:temp) {
				if(i==0)valor=c.getValor();
				else {
					if(valor==5&&salto&&cont==3&&temp.get(temp.size()-1).getValor()==14) {
						gut=true;
						break;
						
					}
					if(valor == 5 && !salto && temp.get(temp.size() - 1).equals(c) && valor + 1 != c.getValor()) { // Si he encontrado 4 seguidas y la ultima no es la siguiente
						gut = true;
						break;
					}
					if(valor+1==c.getValor()) {
						cont++;
						valor=c.getValor();
						
					}
					else if(!salto&&valor+2==c.getValor()) {
						salto=true;
						cont++;
						valor=c.getValor();
					}
					else {
						cont=1;
						valor=c.getValor();
					}
				}
				if(cont==4&&salto)gut=true;
				i++;
			}
			return gut;
		}
		/*
		 * Esta funcion recorre las cartas y determina si hay openended usando un metodo parecido a la funcion que comprueba si hay escalera 
		 */
		public Boolean hayOpenEnded(ArrayList<Carta> cartas) {
			ArrayList<Carta> temp=new ArrayList<Carta>();
			
			for(Carta c: cartas) {
				temp.add(c);
			}
			
			ordenar(temp);
			int cont=0;
			for(int i=0;i<4;i++) {
				if(temp.get(i).getValor()+1==temp.get(i+1).getValor()) {
					cont++;
				}
				else if(cont==3&&temp.get(i).getValor()==5) {
					if(temp.get(4).getValor()==14) {
						cont++;
					}
				}
			}
			
			return cont==3;
		}/*
		 * Esta funcion recorre las cartas y determina si hay poker teniendo un mapa por valores de carta que si llega a 4 hay poker
		 */
		
		public Boolean esPoker(ArrayList<Carta> cartas) {
			int iguales=0;
			int i=0;
			ordenar(cartas);
			// Llevamos un mapa que guarda las veces que aparecen cartas con el mismo valor
			 Map<Integer,Integer> palosPorCarta=new HashMap<Integer,Integer>();
			for(Carta c:cartas) {
				if(palosPorCarta.containsKey(c.getValor())) {
					iguales=palosPorCarta.get(c.getValor());
					iguales++;
					palosPorCarta.replace(c.getValor(), iguales);
					i++;
					poker.add(c);
					if(iguales==4) {
						return true;
					}
					
				}
				else if(i<=1){// Si la primera o la segunda falla si pero si ya falla la tercera significa que no hay forma de que en las restantes haya poker y con esto lo hacemos mas eficiente
					palosPorCarta.put(c.getValor(), 1);
					i++;
					poker.clear();
					poker.add(c);
				}
				else break;
			}
			return false;
			
		}
		/*
		 * Esta funcion recorre las cartas y determina si hay full
		 */
		public Boolean esFull(ArrayList<Carta> cartas) {
			// Comprobamos si hay trio y si lo hay comprobamos si hay pareja en las cartas que no som el trio
			if(esTrio(cartas)) {
				ArrayList<Carta> temporal=new ArrayList<Carta> ();
				for(Carta c:cartas) {
					if(!trio.contains(c))temporal.add(c);
				}
				if(esPareja(temporal)) {
					return true;
				}
				
			}
			return false;
		}
		/*
		 * Esta funcion recorre las cartas y determina si hay escalera
		 */
		public Boolean esEscalera(ArrayList<Carta> cartas) {	
			// Ordenamos la mano y comprobamos si la siguiente de la carta actual es su valor + 1 con el caso particular del A
			ArrayList<Carta> temp=new ArrayList<Carta>();
			
			for(Carta c: cartas) {
				temp.add(c);
			}
			
			ordenar(temp);
			int cont=0;
			for(int i=0;i < 4;i++) {
				if(temp.get(i).getValor()+1==temp.get(i+1).getValor()) {
					cont++;
					escalera.add(temp.get(i));
				}
				else if(cont==3&&temp.get(i).getValor()==5) {
					if(temp.get(i+1).getValor()==14) {
						cont++;
						escalera.add(temp.get(i));
						
					}
				}
			}
			if(cont==4) {
				hayEscal = true;
				escalera.add(temp.get(cont));
			}
			return hayEscal;
		}
		
		/*
		 * Esta funcion recorre las cartas y determina si hay escalera de color 
		 */
		public Boolean esEscaleraColor (ArrayList<Carta> cartas) {
			// Comprobamos si hay escalera y si hay color
			if(hayEscalcol)return true;
			if(esEscalera(cartas)&&esColor(cartas)) {
				hayEscalcol=true;
				return true;
			}
			return false;
		}
		/*
		 * Esta funcion recorre las cartas y determina si hay color
		 */
		public Boolean esColor(ArrayList<Carta> cartas) {
			// Comprobamos que el palo sea el mismo en toda la mano
			for(int i=0;i<4;i++) {
				if(cartas.get(i).getPalo()!=cartas.get(i+1).getPalo()) {
					return false;
				}
			}
			
			palo=cartas.get(0).getPalo();
			return true;
		}
		/*
		 * Esta funcion recorre las cartas y determina si hay escalera real
		 */
		public Boolean esEscaleraReal (ArrayList<Carta> cartas) {
			// Comprobamos si hay escalera de color y chequeamos el primer valor y si no es un 10 ya sabemos que no es escalera real
			if(esEscaleraColor(cartas)) {
				if(escalera.get(0).getValor()==10)return true;
			}
			return false;
		}
		/*
		 * Esta funcion recorre las cartas y determina si hay trio
		 */
		public Boolean esTrio(ArrayList<Carta> cartas) {
			// Llevamos un mapa que guarda las veces que aparecen cartas con el mismo valor
			boolean t=false;
			int iguales=1;
			Map<Integer,ArrayList<Carta>> palosPorCarta=new HashMap<Integer,ArrayList<Carta>>();
			
			for(Carta c:cartas) {
				if(palosPorCarta.containsKey(c.getValor())) {
					
					palosPorCarta.get(c.getValor()).add(c);
					iguales=palosPorCarta.get(c.getValor()).size();
					
					if(iguales==3) {
						t=true;
						for(Carta x:palosPorCarta.get(c.getValor())) trio.add(x);
						haytrio=true;
						break;
					}
					
				}
				else {
					palosPorCarta.put(c.getValor(),new ArrayList<Carta>() );
					palosPorCarta.get(c.getValor()).add(c);
				}
			}
			return t;
			
		}
		/*
		 * Esta funcion recorre las cartas y determina si hay doble pareja 
		 */
		public Boolean esDoblePareja(ArrayList<Carta> cartas) {
			// Llevamos un mapa que guarda las veces que aparecen cartas con el mismo valor
			int parejas=0;
			int iguales=1;
			Map<Integer,ArrayList<Carta>> palosPorCarta=new HashMap<Integer,ArrayList<Carta>>();
			
			// Lo ordenamos para saber que la pareja pequenia esta en par 1 y la grande en par 2
			ArrayList<Carta> temp=new ArrayList<Carta>();
			for(Carta c: cartas) {
				temp.add(c);
			}
			ordenar(temp);
			
			for(Carta c:temp) {
				if(palosPorCarta.containsKey(c.getValor())) {
					
					palosPorCarta.get(c.getValor()).add(c);
					iguales=palosPorCarta.get(c.getValor()).size();
					
					if(iguales==2) {
						parejas++;
						if(parejas==1) {
							for(Carta x:palosPorCarta.get(c.getValor())) par1.add(x);
							hayPareja=true;
						}
						else {
							for(Carta x:palosPorCarta.get(c.getValor())) par2.add(x);
						}
						
						if(parejas==2)break;
					}
					
				}
				else  {
					palosPorCarta.put(c.getValor(),new ArrayList<Carta>() );
					palosPorCarta.get(c.getValor()).add(c);
	
				}
			}
			return parejas==2;
			
			 
		}
		/*
		 * Esta funcion recorre las cartas y determina si hay pareja
		 */
		public Boolean esPareja(ArrayList<Carta> cartas) {
			// Llevamos un mapa que guarda las veces que aparecen cartas con el mismo valor
			boolean p=false;
			int iguales=1;
			Map<Integer,ArrayList<Carta>> palosPorCarta=new HashMap<Integer,ArrayList<Carta>>();
			for(Carta c:cartas) {
				if(palosPorCarta.containsKey(c.getValor())) {
					
					palosPorCarta.get(c.getValor()).add(c);
					iguales=palosPorCarta.get(c.getValor()).size();
					
					if(iguales==2) {
						p=true;
						for(Carta x:palosPorCarta.get(c.getValor())) par1.add(x);
						break;
					}
					
				}
				else {
					palosPorCarta.put(c.getValor(),new ArrayList<Carta>() );
					palosPorCarta.get(c.getValor()).add(c);
					
				}
			}
			if(p)hayPareja=true;
			return p;
			
		}
		/*
		 * Esta funcion recorre las cartas y determina si hay doble pareja 
		 */
		public void cartaAlta(ArrayList<Carta> cartas) {
			// Sacamos la carta mas alta con un bucle
			int max=0;
			for(Carta c: cartas) {
				if(c.getValor()>max) {
					max=c.getValor();
					alta=c;
				}
			}
		}
		
		// Formamos el string con la mejor jugada comprobando en orden de mejor a peor jugada
		public void bestHand() {
			StringBuilder str=new StringBuilder();
			
			if(esEscaleraReal(cartas)) {
				str.append( "Royal Flush of ").append(paloToString());
				best=str.toString();
				str.append(" ");
				for(Carta c:escalera)str.append(c.toString());
				puntuacion=10;
			}
			else if(hayEscalcol) {
				str.append( "Straight Flush of ").append(paloToString());
				best=str.toString();
				str.append(" ");
				for(Carta c:escalera)str.append(c.toString());
				puntuacion=9;
			}
			else if(esPoker(cartas))
			{
				str.append("Poker of ").append(cartaToString(poker.get(0).getValor()));
				best=str.toString();
				str.append(" ");
				for(Carta c:cartas)str.append(c.toString());
				puntuacion=8;
			}
			else if(esFull(cartas))
			{
				str.append(cartaToString(trio.get(0).getValor())).append( " Full of ").append(cartaToString(par1.get(0).getValor()));
				best=str.toString();
				str.append(" ");
				for(Carta c:trio)str.append(c.toString());
				for(Carta c:par1)str.append(c.toString());
				puntuacion=7;
			}
			else if(esColor(cartas)) {
				str.append( "Flush of ").append(paloToString());
				best=str.toString();
				str.append(" ");
				for(Carta c:cartas)str.append(c.toString());
				puntuacion=6;
			}
			else if(hayEscal) {
				str.append( "Straight");
				best=str.toString();
				str.append(" ");
				for(Carta c:escalera)str.append(c.toString());
				puntuacion=5;
			}
			else if(haytrio)
			{
				str.append( "Three of a kind ");
				str.append(cartaToString(trio.get(0).getValor()));
				best=str.toString();
				str.append(" ");
				for(Carta c:trio)str.append(c.toString());
				puntuacion=4;
				
			}
			else if(esDoblePareja(cartas))
			{
				str.append( "Two Pair of ");
				str.append(cartaToString(par1.get(0).getValor())).append(" and ").append(cartaToString(par2.get(0).getValor()));
				best=str.toString();
				str.append(" ");
				for(Carta c:par1) {
					str.append(c.toString());
				}
				for(Carta c:par2) {
					str.append(c.toString());
				}
				puntuacion=3;
			}
			else if(hayPareja||esPareja(cartas))
			{
				str.append( "Pair of ");
				str.append(cartaToString(par1.get(0).getValor()));
				best=str.toString();
				str.append(" ");
				for(Carta c:par1)str.append(c.toString());
				puntuacion=2;
				
			}
			else{
				cartaAlta(cartas);
				str.append("High Card ");
				best=str.toString();
				str.append(alta.toString());
				puntuacion=1;
			}
			bestString=str.toString();
			
		}

		public ArrayList<Carta> getCartas(){
			return cartas;
		}
		private String cartaToString(int valor) {
			
			 switch(valor){
	            case 2:
	            	return"2's";
	               
	            case 3:
	            	return "3's";
	               
	            case 4:
	            	return "4's";
	                
	            case 5:
	            	return "5's";
	               
	            case 6:
	            	return "6's";
	                
	            case 7:
	            	return "7's";
	               
	            case 8:
	            	return "8's";
	               
	            case 9:
	            	return "9's";
	               
	            case 10:
	            	return "Tens";
	               
	            case 11:
	            	return "Jacks";
	               
	            case 12:
	            	return "Queens";
	                
	            case 13:
	            	return"Kings";
	               
	            case 14:  
	            	return "Aces";
	               
	        }
			 return"";
		}


		private String paloToString() {
			if(palo=='h')return"Hearts";
			else if(palo=='d')return"Diamonds";
			else if(palo=='s')return"Spades";
			else return "Clubs";
		}


		public String getbestString() {
			return bestString;
		}

		
		public int getPuntuacion() {
			return puntuacion;
		}
		
		public String getBest() {
			return best;
		}
		/*
		 * Esta funcion se encarga de que dado otra jugada analizada se determine cual es mejor
		 * 1=mejor
		 * 0=igual
		 * -1=peor
		 */
		public int compare(CardAnalizator analizadas) {
			if(puntuacion>analizadas.getPuntuacion()) {
				return 1;
			}
			else if(puntuacion==analizadas.getPuntuacion()){
				
				switch(puntuacion){
	            case 1://carta alta 
	            	
	            	return kicker(cartas, analizadas.cartas);//compara las cartas hasta encomtrar cual tiene la mejor
	               
	            case 2://pareja 
	            	return compararPareja(analizadas);
 
	            case 3://doble pareja
	            	return compararDoblePareja(analizadas) ;
	                
	            case 4://trio
	            	return compararTrio(analizadas) ;
	               
	            case 5://straight
	            	return compararStraight(analizadas);
	            	
	            case 6: //color
	            	return kicker(cartas, analizadas.cartas);
	                
	            case 7://full
	            	return comparaFull(analizadas);
	            case 8://poker
	            	return compararPoker(analizadas);
	            	
	            case 9://escalera de color
	            	 return compararStraight(analizadas);
	            case 10://escalera real
	            	return 0;// si o si hay empate 
				}
	                
			}
			
			return -1;
		}
		
// Las funciones que siguen sirven para comparar dos jugadas sabiendo que jugada es
		
		
		
		private int compararPoker(CardAnalizator analizadas) {
			if(poker.get(0).getValor()>analizadas.poker.get(0).getValor()) {
        		return 1;
        	}
        	else if(poker.get(0).getValor()==analizadas.poker.get(0).getValor()) {
        		//Como tienen el mismo poker comprobamos la suma total
        		if(suma(cartas)>suma(analizadas.cartas)) {
        			return 1;
        		}
        		else if(suma(cartas)<suma(analizadas.cartas)) {
        			return-1;
        		}
        		else return 0;
        	}
        	else return -1;
		}


		private int compararStraight(CardAnalizator analizadas) {
			//compara el ultimo valor de la escalera 
			if(escalera.get(4).getValor()>analizadas.escalera.get(4).getValor()) {
        		if(escalera.get(4).getValor()==14) return -1;//si la ultima es A significa que la escalera es 2 3 4 5 A que es la mas baja 
        		return 1;
        	}
        	else if(escalera.get(4).getValor()==analizadas.escalera.get(4).getValor()) {
        		return 0;
        	}
        	else if(analizadas.escalera.get(4).getValor()==14)return 1;
        	else return -1;
		}


		// Comprobamos el valor de las cartas del trio y si no lo hacemos en el kicker
		private int compararTrio(CardAnalizator analizadas) {
			if(trio.get(0).getValor()>analizadas.trio.get(0).getValor()) {
        		return 1;
        	}
        	else if (trio.get(0).getValor()<analizadas.trio.get(0).getValor()) {
        		return -1;
        	}
        	else {
        		ArrayList<Carta> desempate1=new ArrayList<>();
        		for(Carta c:cartas) {
        			if(!trio.contains(c)) {
        				desempate1.add(c);
        			}
        		}
        		ArrayList<Carta> desempate2=new ArrayList<>();
        		for(Carta c:analizadas.cartas) {
        			if(!analizadas.trio.contains(c)) {
        				desempate2.add(c);
        			}
        		}
        		return kicker(desempate1,desempate2);
        	}
		}

		// Comprobamos el valor de las cartas de ambas parejas y si son iguales miramos la suma de la mano entera
		private int compararDoblePareja(CardAnalizator analizadas) {
			if(par2.get(0).getValor()>analizadas.par2.get(0).getValor()) {
        		return 1;
        	}
        	else if (par2.get(0).getValor()<analizadas.par2.get(0).getValor()) {
        		return -1;
        	}
        	else {
        		if(par1.get(0).getValor()>analizadas.par1.get(0).getValor()) {
            		return 1;
            	}
            	else if (par1.get(0).getValor()<analizadas.par1.get(0).getValor()) {
            		return -1;
            	}
            	else {
            		if(suma(cartas)>suma(analizadas.cartas)) {
	            		return 1;
	            	}
	            	else if(suma(cartas)==suma(analizadas.cartas)) {
	            		return 0;
	            	}
	            	else return -1;
            	}
        		
        	}
		}


		// Comprobamos el valor de las cartas de la pareja y si no lo hacemos en el kicker
		private int compararPareja(CardAnalizator analizadas) {
			if(par1.get(0).getValor()>analizadas.par1.get(0).getValor()) {
        		return 1;
        	}
        	else if (par1.get(0).getValor()<analizadas.par1.get(0).getValor()) {
        		return -1;
        	}
        	else {
        		ArrayList<Carta> desempate1=new ArrayList<>();
        		for(Carta c:cartas) {
        			if(!par1.contains(c)) {
        				desempate1.add(c);
        			}
        		}
        		ArrayList<Carta> desempate2=new ArrayList<>();
        		for(Carta c:analizadas.cartas) {
        			if(!analizadas.par1.contains(c)) {
        				desempate2.add(c);
        			}
        		}
        		return kicker(desempate1,desempate2);
        	}
		}
		// Cogemos ambas manos y comparamos las cartas mas altas para saber que mano es ganadora
		public int kicker(ArrayList<Carta> cartas1,ArrayList<Carta> cartas2) {
			ArrayList<Carta> temp1=new ArrayList<Carta>();
			for(Carta c: cartas1) {
				temp1.add(c);
			}
			ordenar(temp1);
			
			ArrayList<Carta> temp2=new ArrayList<Carta>();
			for(Carta c: cartas2) {
				temp2.add(c);
			}
			ordenar(temp2);
			
			for(int i=temp1.size() - 1;i>=0;i--) {
				if(temp1.get(i).getValor() > temp2.get(i).getValor()) {
					return 1;
				}
				else if (temp1.get(i).getValor()< temp2.get(i).getValor()) {
					return -1;
				}
			}
			return 0;
		}
		// Comparamos primero el trio y luego las parejas
		private int comparaFull(CardAnalizator analizadas) {
			if(trio.get(0).getValor()>analizadas.trio.get(0).getValor()) {
				return 1;
			}
			else if(trio.get(0).getValor()==analizadas.trio.get(0).getValor()) {
				
				if(par1.get(0).getValor()>analizadas.par1.get(0).getValor()) {
					return 1;
				}
				else if(par1.get(0).getValor()==analizadas.par1.get(0).getValor()) {
					return 0;
				}
				else return -1;
			}
			else return -1;
		}
		public int suma(ArrayList<Carta> cartas) {
			int suma=0;
			for(Carta c:cartas) {
				suma+=c.getValor();
			}
			return suma;
		}
	}

