//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

public class wavelet_haar{
	
	double h1,h2,g1,g2;
	double []datos;	
	double []coeficientes;
	double [][]matrizWavelet; //cada fila tiene los coeficientes de cada nivel de descompocicion
	
	double []energia; //	devuelve un vector con la energa de cada nivel de descompocicion
	
	int estado; //hasta que nivel de descompocicion se ha hecho
	int tamano;
	
	public wavelet_haar(double [] datos){
		
		//coeficietes de los filtros para la funcion escala
		h1=1/Math.sqrt(2);
		h2=1/Math.sqrt(2);
		
		//coeficientes de los filtros para la funcion wavelets
		g1=h1;
		g2=-h2;
		
		this.datos=datos;
		/*para modificar el arreglo para que sea potencia de 2*/
		tamano=datos.length;
		estado=Potencia();
		
		datos=modificardatos(datos,estado);
		/**/
		
		matrizWavelet = new double[estado][];
		coeficientes = new double[datos.length];
		copiar();//incializa el vector coeficientes con los elementos del vector datos
		calcular();
		matrizWavelet();
		energia();
		}
		
	private void copiar	(){
		
		for(int i=0;i<datos.length;i++)
		coeficientes[i]=datos[i];
		}
	
	//entra señal y sale matriz con los coeficientes a diferentes escalas
	//se supone que la señal de entrada es una potencia de dos
	//en todo caso modificar el programa con zero padding por ejemplo
	
	public void calcular(){
		
		int N=datos.length;
		int M=N;
		double a,b,c,d;		
		int pos=0;
		int i,j,k;
		double [] temp=new double[datos.length];
	
		
		for(i=0;i<Math.log(N);i++)
		{
			for(j=0;j<M;j=j+2)
			{
				a=h1*coeficientes[j];
				b=h2*coeficientes[j+1];
				c=g1*coeficientes[j];
				d=g2*coeficientes[j+1];
				
				temp[pos]=a+b;
				temp[pos+M/2]=c+d;
				int medio=pos+M/2;
				pos++;
				
				}
				pos=0;
			for(j=0;j<M;j=j+2)
			{
				coeficientes[pos]=temp[pos];
				coeficientes[pos+M/2]=temp[pos+M/2];
				pos++;
				}
				
				M=M/2;
				pos=0;
				
			
			}
		
		}
		
	public double[] coeficientes(){
		return coeficientes;
		}	
		
		
	/*alamcena en una matriz los coeficientes wavelets por escala*/	
	public void matrizWavelet(){
		int M=datos.length;
		int a=0;
		int b=0;
		
		int i,j;
		a=M/2;
		for(i=0;i<estado;i++){
			matrizWavelet[i]=new double[M/2];
			for( j=0;j<M/2;j++){
				
				matrizWavelet[i][j]=coeficientes[a+j];
				
				}
				M=M/2;
				a=M/2;
			}
		
/*		System.out.println("");	
		System.out.println(" Matriz ");	
		for(i=0;i<matrizWavelet.length;i++){
			
			for( j=0;j<matrizWavelet[i].length;j++){
			System.out.print(" "+matrizWavelet[i][j]);	
				}
			System.out.println("");	
			}
	*/	
		
		}
		
	public double[][] obtenerMatrizWavelet(){
		return matrizWavelet;
		}	
		
	//retorna la energia de la matriz wavelet	
	public double[] obtenerEnergia(){
		return energia;
		}		
		
	//calcula la energia de la matriz wavelet			
	public void energia(){
		
		energia=new double[matrizWavelet.length];
		double suma=0;
		
		
		for(int i=0;i<matrizWavelet.length;i++){
			
			for(int j=0;j<matrizWavelet[i].length;j++){
				
				suma=suma+Math.pow(matrizWavelet[i][j],2);
				
				}
				energia[i]=suma;
				energia[i]=energia[i]/matrizWavelet[i].length;
				suma=0;
			}
		
		/**/
		
/*		System.out.print(" energia ");
		
		for(int i=0;i<energia.length;i++){
			
		System.out.print(" "+energia[i]+" ");
			
			}
*/		/**/
		
		
		}
		
		//funcion que devuelve el "n" potencia de 2 mas cercana al tamaño del vector
	//si el arreglo de datos no coincide con una potencia de dos , entonces
	//rellena el arreglo con el valor promedio de este hasta 
  	//igualar una potencia de dos cercana
	public int	Potencia(){
		double [] tem;
		int nPotencia=0;
		
		for(int i=0;i<tamano;i++){
			
			nPotencia=(int)java.lang.Math.pow((double)2,(double)i);
			if(tamano<nPotencia){
			   //modifica el arreglo
			   tem=this.datos;
			   tamano=this.datos.length;
               estado=i;  			  
//                System.out.println("tamaño en potencia()="+tamano); 
				return estado;
			}
			
			if(tamano==nPotencia){
			
			this.estado=i;	
			return estado;	
			}
		}
		return 0;
		}
	
	
	//rellena un arreglo que no es potencia de dos hasta hacerlo
	//coincidir con una potencia de dos, con el ultimo
	//del arregloa modificar
	public double [] modificardatos(double [] datos, int n){
		
		double [] datosModificado= new double [(int)java.lang.Math.pow((double)2,(double)n)];
		int i;
		double promedio=0;
		for( i=datos.length;i< (int)java.lang.Math.pow((double)2,(double)n);i++){
			datosModificado[i]=promedio;
		}
		
		for(i =0;i<datos.length;i++){
		
		datosModificado[i]=datos[i];
		}
		
		return datosModificado;
		}
	
	
		
	public static void main(String args[]){
		
		double [] datos={4,6,10,12,8,6,5,5};
		double [][] matrizWavelet;
		double [] energia;
		reconocerWaveletHaar reconocer= new reconocerWaveletHaar(datos);
		
		matrizWavelet=reconocer.matrizCaracteristicas;
		
		System.out.println("matriz de caracteristicas");
		
		for(int i=0;i<matrizWavelet.length;i++){
			for(int j=0;j<matrizWavelet[i].length;j++){
				System.out.print(" "+matrizWavelet[i][j]+" ");
				}
			System.out.println("");
		}	
		
/*		wavelet_haar haar=new wavelet_haar(datos);
		int i;
		
		for(i=0;i<datos.length;i++){
		
		System.out.print(" "+datos[i]+" ");
		}	
		System.out.println(" ");
		
		datos=haar.coeficientes();
		
		for(i=0;i<datos.length;i++){
		
		System.out.print(" "+datos[i]+" ");
		}	
		
		
		energia=haar.obtenerEnergia();
		
		System.out.print(" energia ");
		
		for(i=0;i<energia.length;i++){
			
		System.out.print(" "+energia[i]+" ");
			
			}
*/

	}
	}