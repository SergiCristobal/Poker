package model;

public class Baraja {
    private char[] numero = {'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'};
    private char[] palo = {'h', 'c', 'd', 's'};
    private Carta[][] baraja = new Carta[13][4];
    
	public Baraja(){
        for(int i = 0; i < 13; i++){
            for(int j = 0; j < 4; j++){
                baraja[i][j] = new Carta(numero[i], palo[j]);
            }
        }
    }
    
    public Carta getCartaBaraja(int i, int j){
        return baraja[i][j];
    }
    
    public Carta[][] getBaraja() {
 		return baraja;
 	}
}
