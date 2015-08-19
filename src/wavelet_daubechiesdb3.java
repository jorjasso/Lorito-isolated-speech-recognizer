//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

public class wavelet_daubechiesdb3{
	
	double h1,h2,h3,h4,h5,h6,g1,g2,g3,g4,g5,g6;
	double []datos;	
	double []coeficientes;
	
	double []energia;
	
	double [][]matrizWavelet; //cada fila tiene los coeficientes de cada nivel de descompocicion
	
	int estado; //hasta que nivel de descompocicion se ha hecho
	int tamano;
	
	public wavelet_daubechiesdb3(double [] datos){
		
		//coeficietes de los filtros para la funcion escala
		h1=0.3327;
		h2=0.8069;
		h3=0.4599;
		h4=-0.1350;
		h5=-0.0854;
		h6=0.0352;
		
		//coeficientes de los filtros para la funcion wavelets
		g1=h6;
		g2=-h5;
		g3=h4;
		g4=-h3;
		g5=h2;
		g6=-h1;
		
		
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
		double a,b,c,d,e,f,a1,b1,c1,d1,e1,f1;
		double n1,n2,n3,n4;//coeficientes para extrapolacion
		int pos=0;
		int i,j,k;
		double [] temp=new double[datos.length];
		
			
		
		for(i=0;i<Math.log(N)-2;i++)
		{
			for(j=0;j<M;j=j+2)
			{	
			
				
				if(j<M-4){
				a=h1*coeficientes[j];
				b=h2*coeficientes[j+1];
				c=h3*coeficientes[j+2];
				d=h4*coeficientes[j+3];
				e=h5*coeficientes[j+4];
				f=h6*coeficientes[j+5];
				
				a1=g1*coeficientes[j];
				b1=g2*coeficientes[j+1];
				c1=g3*coeficientes[j+2];
				d1=g4*coeficientes[j+3];
				e1=g5*coeficientes[j+4];
				f1=g6*coeficientes[j+5];
				}
				
				else{
					if(j<M-2){
				//extrapolacion con primera derivada parte derecha
				n1=coeficientes[j+3]+(coeficientes[j+3]-coeficientes[j+2]);
				n2=n1+(n1-coeficientes[j+3]);
				
				a=h1*coeficientes[j];
				b=h2*coeficientes[j+1];
				c=h3*coeficientes[j+2];
				d=h4*coeficientes[j+3];
				e=h5*n1;
				f=h6*n2;
				
				a1=g1*coeficientes[j];
				b1=g2*coeficientes[j+1];
				c1=g3*coeficientes[j+2];
				d1=g4*coeficientes[j+3];
				e1=g5*n1;
				f1=g6*n2;
				
				
					}
					else{
						
				//extrapolacion con primera derivada parte derecha
				n1=coeficientes[j+1]+(coeficientes[j+1]-coeficientes[j]);
				n2=n1+(n1-coeficientes[j+1]);
				n3=n2+(n2-n1);
				n4=n3+(n3-n2);
				
				a=h1*coeficientes[j];
				b=h2*coeficientes[j+1];
				c=h3*n1;
				d=h4*n2;
				e=h5*n3;
				f=h6*n4;
				
				a1=g1*coeficientes[j];
				b1=g2*coeficientes[j+1];
				c1=g3*n1;
				d1=g4*n2;
				e1=g5*n3;
				f1=g6*n4;
						
						}
				
					}
					
				temp[pos]=a+b+c+d+e+f;
				temp[pos+M/2]=a1+b1+c1+d1+e1+f1;
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
		
	public void matrizWavelet(){
		int M=datos.length;
		int a=0;
		int b=0;
		
		int i,j;
		a=M/2;
		for(i=0;i<estado-2;i++){
			matrizWavelet[i]=new double[M/2];
			for( j=0;j<M/2;j++){
				
				matrizWavelet[i][j]=coeficientes[a+j];
				
				}
				M=M/2;
				a=M/2;
			}
		
	
		
		}	
		
	public double[][] obtenerMatrizWavelet(){
		return matrizWavelet;
		}	
		
	//retorna la energia de la matriz wavelet	
	public double[] obtenerEnergia(){
		return energia;
		}		

	public void energia(){
		
		energia=new double[matrizWavelet.length-2];
		double suma=0;
		
		
		for(int i=0;i<estado-2;i++){
			
			for(int j=0;j<matrizWavelet[i].length;j++){
				
				suma=suma+Math.pow(matrizWavelet[i][j],2);
				
				}
				energia[i]=suma;
				energia[i]=energia[i]/matrizWavelet[i].length;
				suma=0;
			}
		
	

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
		reconocerWaveletDaubechies3 reconocer= new reconocerWaveletDaubechies3(datos);
		
		matrizWavelet=reconocer.matrizCaracteristicas;
		
		System.out.println("matriz de caracteristicas");
		
		for(int i=0;i<matrizWavelet.length;i++){
			for(int j=0;j<matrizWavelet[i].length;j++){
				System.out.print(" "+matrizWavelet[i][j]+" ");
				}
			System.out.println("");
		}	

/*		
		double [] datos={4,6,10,12,8,6,5,5};
		wavelet_daubechiesdb3 daubechies=new wavelet_daubechiesdb3(datos);
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