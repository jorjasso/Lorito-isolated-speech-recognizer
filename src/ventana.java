//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

//Permite hacer el ventanamiento de la señan con varios tipos de ventanas
//haming haning y rectangular

public class ventana{
	
	
	
	public ventana(){
		
		
		}
		
	public  static double[]hammingI(double [] arreglo){
		int i=0;
		for(i=0;i<arreglo.length;i++){
			arreglo[i]=(0.54-0.46*Math.cos(2*Math.PI*i/(arreglo.length-1)))*arreglo[i];		
		}
		
		return arreglo;
		}
		
		/*para optimizar como siempre trabajo con ventanas de tamaño 512*/
	public  static double[]hammingII(double [] arreglo, int tamano){
		int i=0;
		for(i=0;i<tamano;i++){
			arreglo[i]=(0.54-0.46*Math.cos(2*Math.PI*i/(tamano-1)))*arreglo[i];	
		}
		
		return arreglo;
		}	
		
		/*para optimizar aun mas esto me ahorra el paso de tener que pasar a un array temporal los valores */
	public  static double hammingIII(double valor, int tamano, int i){
			double dato=0;
			dato=(0.54-0.46*Math.cos(2*Math.PI*i/(tamano-1)))*valor;
		return dato;
		}		
		
	public static double rectangular(double valor){
		return valor;		
		}
			
		
	public static double [] hanning(double [] arreglo){
		int i=0;
		for(i=0;i<arreglo.length;i++){
			arreglo[i]=(0.5-0.5*Math.cos(2*Math.PI*i/(arreglo.length-1)))*arreglo[i];
			
		}
		
		return arreglo;
		
		}	
	
	
	}