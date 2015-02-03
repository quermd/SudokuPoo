package grafics;


import java.util.Arrays;
import java.util.Random;

import javax.swing.table.AbstractTableModel;

import sun.rmi.runtime.Log;

import java.util.logging.Logger;

public class GenerarSudoku{

	/**
	 * 
	 */
	private static final boolean DEBUG = false;
	private static final boolean SUPER_DEBUG = true;
	
	private final static int TAMANY_QUADRICULA=9;
	
	private int[][] cuadricula=new int[TAMANY_QUADRICULA][TAMANY_QUADRICULA];
	
	private Random r;
	
	public static void main(String[] args) {
		GenerarSudoku.generar();
	}
	
	public static void generar() {
		new GenerarSudoku();
		
	}
	
	public GenerarSudoku(){
		r= new Random();
		boolean totCorrecte=true;
		int intents=0;
		int cont=0;
		do {
			r= new Random();
			intents++;
			cont++;
			cuadricula=null;
			cuadricula=new int[TAMANY_QUADRICULA][TAMANY_QUADRICULA];
			if (SUPER_DEBUG && cont>1000){
				imprimir();
				System.out.println(" nºintents= " + intents);
				cont=0;
			}
			totCorrecte=generarTaulaSudoku();
		} while (!totCorrecte );
		imprimir();
		if (SUPER_DEBUG ){
			
			System.out.println(" nºintents= " + intents);
			
		}
		
	}
	
	
	
	/*
	 * 
	 * array[fila][columna]
	 * 
	 *  012  -> array[][columna]
	 * 
	 *0 123
	 *1 456
	 *2 789
	 * 
	 *^- array [fila][]
	 * 
	 * 
	 * 
	 * 
	 * array[1][2]=>5
	 * 
	 * 
	 */
	
	private boolean generarTaulaSudoku(){
		boolean totCorrecte=true;
		
		for (int i=0; i<TAMANY_QUADRICULA; i++ ){
			for (int j=0; j<TAMANY_QUADRICULA; j++){
				if (DEBUG){
					System.out.println("Buscant numero per posicio: [" + i + "] [" + j + "]: ...");
				}
			
				totCorrecte= colocarNumeroEn(i,j);
				if (!totCorrecte){
					if (DEBUG){
						System.out.println("MAL");
					}
					return totCorrecte;
				}
			}
		}
		return totCorrecte;
	}
	
	
	private boolean colocarNumeroEn(int fila, int columna) {
		
		int numeroAcolocar=0;
		int count=0;
		
		
		do {
			count++;
			if (count>90){
				if (DEBUG){
					System.out.println("MAL");
				}
				return false;
			}
			numeroAcolocar = generaNumeroAleatori();
			if (DEBUG){
				System.out.println("Numero aleatori generat: " + numeroAcolocar );
			}
			
			
		} while (comprobarRepetitEnColumna(columna,numeroAcolocar)||
				comprobarRepetitEnFila(fila,numeroAcolocar)||
				comprobarQuadrants(fila,columna,numeroAcolocar));
		
		if (DEBUG){
			System.out.println("COLOCAT numero " + numeroAcolocar + " en:  [" + fila + "] [" + columna +"]" );
		}
		cuadricula[fila][columna]=Integer.valueOf(numeroAcolocar);
		
		if (SUPER_DEBUG){
			imprimir();
		}
		return true;
		
	}
	private boolean comprobarQuadrants(int fila, int columna, int numeroAcolocar) {
		boolean trobat=false;
		
		int posicioIncialDelQuadrantY= determinaPosicioInicialQuadrant(fila);
		int posicioIncialDelQuadrantX= determinaPosicioInicialQuadrant(columna);
		
		for (int i=posicioIncialDelQuadrantX;i< posicioIncialDelQuadrantX+3;i++){
			for (int j=posicioIncialDelQuadrantY;j< posicioIncialDelQuadrantY+3;j++){
				
				if(comprobarEspaiEnCuadriculaAmbNumero(cuadricula[j][i],numeroAcolocar)){
					return true;
				}
			}
		}
		
		
		return trobat;
	}

	private int determinaPosicioInicialQuadrant(int fila) {
		switch (fila){
			case 0:
			case 1:
			case 2:
				return 0;
			case 3:
			case 4:
			case 5:
				return 3;
			case 6:
			case 7:
			case 8:
				return 6;	
		}
		return 0;
	}

	private void imprimir(){
		for (int i=0; i<TAMANY_QUADRICULA;i++){
			for (int j=0; j<TAMANY_QUADRICULA;j++){
				System.out.print((cuadricula[i][j]==0)?"n ":cuadricula[i][j] +" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
	private boolean comprobarRepetitEnFila (int posicioFila,
			int numeroAcolocar) {
		boolean trobat=false;
		
		if (DEBUG){
			System.out.println("comprobant no repetit en fila "+ posicioFila);
		}
		
		for (int i=0; i<TAMANY_QUADRICULA;i++){ 
			
			if(comprobarEspaiEnCuadriculaAmbNumero(cuadricula[posicioFila][i],numeroAcolocar)){
				trobat=true;
				break;
			}
			
		}
		if (DEBUG){
			System.out.println("repetit en linea: " + trobat);
		}
		return trobat;
	}
	
	private boolean comprobarRepetitEnColumna(int posicioColumna, int numeroAcolocar) {
		boolean trobat=false;
		
		if (DEBUG){
			System.out.println("comprobant no repetit en columna : " + posicioColumna);
		}
		for (int i=0;i<TAMANY_QUADRICULA;i++ ){
			//System.out.println("element en fila " + i + " :" + cuadricula[columna][i].toString());
			
			if(comprobarEspaiEnCuadriculaAmbNumero(cuadricula[i][posicioColumna],numeroAcolocar)){
				trobat=true;
				break;
			}
			
		}
		
		if (DEBUG){
			System.out.println("repetit en columna: " + trobat);
		}
		return trobat;
	}

	
	private boolean comprobarEspaiEnCuadriculaAmbNumero(Object element,int numeroAcolocar ){
		boolean iguals=false;
		if (element!=null){
			if(((Integer)element).intValue()==numeroAcolocar){
				iguals=true;
			}
		}
		return iguals;
	}

	

	private int generaNumeroAleatori() {
		return r.nextInt(TAMANY_QUADRICULA)+1;
	}

	

	

	
}
