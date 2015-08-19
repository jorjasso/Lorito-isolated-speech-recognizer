//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

public class wavelet_daubechies{
	
	double h1,h2,h3,h4,g1,g2,g3,g4;
	double []datos;	
	double []coeficientes;
	
	double []energia;
	
	double [][]matrizWavelet; //cada fila tiene los coeficientes de cada nivel de descompocicion
	
	int estado; //hasta que nivel de descompocicion se ha hecho
	int tamano;
	
	public wavelet_daubechies(double [] datos){
		
		//coeficietes de los filtros para la funcion escala
		h1=(1+Math.sqrt(3))/(4*Math.sqrt(2));
		h2=(3+Math.sqrt(3))/(4*Math.sqrt(2));
		h3=(3-Math.sqrt(3))/(4*Math.sqrt(2));
		h4=(1-Math.sqrt(3))/(4*Math.sqrt(2));
		
		//coeficientes de los filtros para la funcion wavelets
		g1=h4;
		g2=-h3;
		g3=h2;
		g4=-h1;
		
		
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
		double a,b,c,d,e,f,g,h;		
		double n1,n2;//coeficientes para extrapolacion
		int pos=0;
		int i,j,k;
		double [] temp=new double[datos.length];
	
		
		for(i=0;i<Math.log(N)-1;i++)
		{
			for(j=0;j<M;j=j+2)
			{	
				if(j<M-2){
				a=h1*coeficientes[j];
				b=h2*coeficientes[j+1];
				c=h3*coeficientes[j+2];
				d=h4*coeficientes[j+3];
				
				e=g1*coeficientes[j];
				f=g2*coeficientes[j+1];
				g=g3*coeficientes[j+2];
				h=g4*coeficientes[j+3];
				}
				
				else{
				//extrapolacion con primera derivada	
				n1=coeficientes[j+1]+(coeficientes[j+1]-coeficientes[j]);
				n2=n1+(n1-coeficientes[j+1]);
				
				a=h1*coeficientes[j];
				b=h2*coeficientes[j+1];
				c=h3*n1;
				d=h4*n2;
				
				e=g1*coeficientes[j];
				f=g2*coeficientes[j+1];
				g=g3*n1;
				h=g4*n2;
					}
				temp[pos]=a+b+c+d;
				temp[pos+M/2]=e+f+g+h;
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
				
/*		System.out.println(" coeficientes ");		
		for(j=0;j<datos.length;j++){
		
		System.out.print(" "+coeficientes[j]+" ");
		}	
		System.out.println(" ");
				
*/			
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
		for(i=0;i<estado-1;i++){
			matrizWavelet[i]=new double[M/2];
			for( j=0;j<M/2;j++){
				
				matrizWavelet[i][j]=coeficientes[a+j];
				
				}
				M=M/2;
				a=M/2;
			}
		
/*		System.out.println("matriz wavelet");
		
		for(i=0;i<matrizWavelet.length-1;i++){
			
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
		
	public void energia(){
		
		energia=new double[matrizWavelet.length-1];
		double suma=0;
		
		
		for(int i=0;i<estado-1;i++){
			
			for(int j=0;j<matrizWavelet[i].length;j++){
				
				suma=suma+Math.pow(matrizWavelet[i][j],2);
				
				}
				energia[i]=suma;
				energia[i]=energia[i]/matrizWavelet[i].length;
				suma=0;
			}
		
/*		System.out.println(" energia ");
		for(int k=0;k<energia.length;k++){
			
			System.out.print(" "+energia[k]);
			}
		System.out.println("");	
*/			
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
		reconocerWaveletDaubechies reconocer= new reconocerWaveletDaubechies(datos);
		
		matrizWavelet=reconocer.matrizCaracteristicas;
		
		System.out.println("matriz de caracteristicas");
		
		for(int i=0;i<matrizWavelet.length;i++){
			for(int j=0;j<matrizWavelet[i].length;j++){
				System.out.print(" "+matrizWavelet[i][j]+" ");
				}
			System.out.println("");
		}	

/*		double [] datos={4,6,10,12,8,6,5,5};
		wavelet_daubechies daubechies=new wavelet_daubechies(datos);
		int i;
		
		for(i=0;i<datos.length;i++){
		
		System.out.print(" "+datos[i]+" ");
		}	
		System.out.println(" ");
		
		datos=daubechies.coeficientes();
		
		for(i=0;i<datos.length;i++){
		
		System.out.print(" "+datos[i]+" ");
		}	
*/
	}
	}