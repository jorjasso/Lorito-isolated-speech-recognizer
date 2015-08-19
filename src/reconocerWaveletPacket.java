//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

public class reconocerWaveletPacket{

	//entrada voz normalizada
	Ventaneamiento ventaneamiento;  //obtiene la matriz de ventaneamiento	
	double[][]matrizWavelet; //almacena valores computados despues de ventanamiento 
	double[][]matrizEnergia;//almacena la mastriz con los valores de Energia
	double[][]matrizCaracteristicas;
	
	
	
	double mayorEspectro;
	
	
	public reconocerWaveletPacket(double [] arreglo){
		//entra el arreglo de voz normalizada
		//sale una matriz de ventaneamiento
		 ventaneamiento=new Ventaneamiento(arreglo,384,160,0);//obtiene la matriz de ventaneamiento
		 //ventaneamiento.imprimirMatriz();
		 
		 calcular();
		}
	
	//calcula la energia de los niveles en la descompocicion wavelet
	//calcula la transformada discreta del coseno que da lugar a la matriz de caracteristicas
	public void calcular(){
		int filas=0;		
		matrizWavelet=ventaneamiento.obtenerMatriz();//obtiene la matriz de ventanamiento
//		ventaneamiento.imprimirMatriz();
		filas = ventaneamiento.devolverFilas();//devuelve las filas de la matriz de ventanamietno
		matrizEnergia=new double[filas][];
		matrizCaracteristicas=new double[filas][];
		//aplicar la descompocicion wavelet a cada fila de la matriz de ventaneamiento
		/*******/
		
			for(int i=0;i<filas;i++){ //computa iterativamente la matriz wavelet (Energia)
				waveletPacket PacketDb4=new waveletPacket(matrizWavelet[i]);
		//		matrizWavelet[i]= daubechies.coeficientes;
		//		datos=daubechies.coeficientes();	
				matrizEnergia[i]=PacketDb4.obtenerEnergia();
				matrizEnergia[i]=logaritmo(matrizEnergia[i]);
				matrizCaracteristicas[i]=MFCC.DTCII(matrizEnergia[i]);	
					}
					
			}
		
	public double[]	logaritmo(double []arreglo)	{
		
		for(int i=0;i<arreglo.length;i++)
		{
			arreglo[i]=Math.log(arreglo[i]);
			
			}
		return arreglo;
		}
	
	public double[][] matrizCaracteristicas(){
		return matrizCaracteristicas;
		}	
		
	}

		
		
	
	
	