package model;


public class Carta {
	
	private char numero;
	private char palo;
	private int  valor;
	
	public Carta(char valor2, char palo) {
		this.palo=palo;
		this.numero=valor2;
		this.valor=calcular(valor2);
	}
	
	public Carta(int valor, char palo) {
		this.valor = valor;
		this.palo = palo;
		this.numero = calcularChar(valor);
	}
	
	private char calcularChar(int n) {
		if(n==14)return 'A';
		else if(n==13)return 'K';
		else if(n==12)return 'Q';
		else if(n==11)return 'J';
		else if(n==10)return 'T';
		else return Character.forDigit(n, 10);
	}
	
	private int calcular(char n) {
		if(n=='A')return 14;
		else if(n=='K')return 13;
		else if(n=='Q')return 12;
		else if(n=='J')return 11;
		else if(n=='T')return 10;
		else return n-'0';
	}

	public char getNumero() {
		return numero;
	}
	public int getValor() {
		return valor;
	}
	public void setNumero(char numero) {
		this.numero = numero;
	}

	public char getPalo() {
		return palo;
	}

	public void setPalo(char palo) {
		this.palo = palo;
	}
	@Override
	public boolean equals (Object obj) {
		Carta b=(Carta) obj;
		if(b.palo==(this.palo)&&b.numero==(this.numero))
			return true;
		else 
			return false;
	}
	public String toString() {
		StringBuilder str=new StringBuilder();
		str.append(numero).append(palo);
		return str.toString();
	}
}
