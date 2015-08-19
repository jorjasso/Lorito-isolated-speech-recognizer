//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

public class waveletPacketWalsh{
	
	double h1,h2,g1,g2;
	double []datos;	
	double []coeficientes;
	
	double []energia;
	
	double [][]matrizWavelet; //para graficar los coeficientes
	
//	int estado; //hasta que nivel de descompocicion se ha hecho
	int tamano;
	
	
	//entrara un tamaño de 384 datos correspondiente a 
	//24mS para 16Khz de frecuencia de sampleo
	public waveletPacketWalsh(double [] datos){
		
	
		//coeficietes de los filtros para la funcion escala
		h1=1/Math.sqrt(2);
		h2=1/Math.sqrt(2);
		
		//coeficientes de los filtros para la funcion wavelets
		g1=h1;
		g2=-h2;
		
		matrizWavelet=new double[14][96];
		
		this.datos=datos;
		
		tamano=datos.length;

		coeficientes = new double[datos.length];
		copiar();//incializa el vector coeficientes con los elementos del vector datos
		//calculo del arbol de descompocicion wavelet packet para tomar los 
		//valores frecuenciales aproximados a la escala mel
		energia=obtencionEnergiaWaveletPacket();
		
		}
		
	private void copiar	(){
		
		for(int i=0;i<datos.length;i++)
		coeficientes[i]=datos[i];
		}
	
	
	
	//entra los coeficientes en su primer nivel de descompocicion (señal original)
	//salen las energias de los distintos coeficientes aproximados a la escala mel
	
	public double[] obtencionEnergiaWaveletPacket(){
		
		//primer estado de descompocicion coeficientes 2,3 (tomar fila 5,4)
//		System.out.println("primer nivel de descomposicion");
		double[][] matriz1;
		matriz1=calcular(coeficientes,7);
		//segundo estado de descompocicion coeficientes 4,5 (tomar fila 1,0)
//		System.out.println(" ");
//		System.out.println("segundo nivel de descomposicion");
		double[][] matriz2;
		matriz2=calcular(matriz1[3],2);
		//tercer nivel de descompocicion coeficientes 6,7,8 (tomar fila 2,1,0)
//		System.out.println(" ");
//		System.out.println("tercer nivel de descomposicion");
		double[][] matriz3;
		matriz3=calcular(matriz1[2],3);
		//cuarto nivel de descompocicion coeficientes 9,10,11 (tomar fila 2,1,0)
//		System.out.println(" ");
//		System.out.println("cuarto nivel de descomposicion");
		double[][] matriz4;
		matriz4=calcular(matriz1[1],3);
		//quinto nivel de descompocicion coeficientes 12,13,14 (tomar fila 2,1,0)
//		System.out.println(" ");
//		System.out.println("quinto nivel de descomposicion");
		double[][] matriz5;
		matriz5=calcular(matriz1[0],3);
		
		//calculo de las energias no se toma el primer grupo de coeficientes
		//pues corresponden al ruido del canal de transmision
		double [] energia=new double[13];
		
		energia[12]=energia(matriz1[5]); //coeficiente 2
		energia[11]=energia(matriz1[4]); //coeficiente 3
		
		energia[10]=energia(matriz2[1]); //coeficiente 4
		energia[9]=energia(matriz2[0]); //coeficiente 5
		
		energia[8]=energia(matriz3[2]); //coeficiente 6
		energia[7]=energia(matriz3[1]); //coeficiente 7
		energia[6]=energia(matriz3[0]); //coeficiente 8
		
		energia[5]=energia(matriz4[2]); //coeficiente 9
		energia[4]=energia(matriz4[1]); //coeficiente 10
		energia[3]=energia(matriz4[0]); //coeficiente 11
		
		energia[2]=energia(matriz5[2]); //coeficiente 12
		energia[1]=energia(matriz5[1]); //coeficiente 13
		energia[0]=energia(matriz5[0]); //coeficiente 14
		
/*		System.out.println("tamaño matriz1[6].length "+matriz1[6].length);
		System.out.println("tamaño matriz1[5].length "+matriz1[5].length);
		System.out.println("tamaño matriz1[4].length "+matriz1[4].length);
		System.out.println("tamaño matriz2[1].length "+matriz2[1].length);
		System.out.println("tamaño matriz2[0].length "+matriz2[0].length);
		System.out.println("tamaño matriz3[2].length "+matriz3[2].length);
		System.out.println("tamaño matriz3[1].length "+matriz3[1].length);
		System.out.println("tamaño matriz3[0].length "+matriz3[0].length);
		System.out.println("tamaño matriz4[2].length "+matriz4[2].length);
		System.out.println("tamaño matriz4[1].length "+matriz4[1].length);
		System.out.println("tamaño matriz4[0].length "+matriz4[0].length);
		System.out.println("tamaño matriz5[2].length "+matriz5[2].length);
		System.out.println("tamaño matriz5[1].length "+matriz5[1].length);
		System.out.println("tamaño matriz5[0].length "+matriz5[0].length);
		
*/
		
		llenarMatrizWavelet(matriz1[6] , 13);
		llenarMatrizWavelet(matriz1[5] , 12);
		llenarMatrizWavelet(matriz1[4] , 11);
		llenarMatrizWavelet(matriz2[1] , 10);
		llenarMatrizWavelet(matriz2[0] , 9);
		llenarMatrizWavelet(matriz3[2] , 8);
		llenarMatrizWavelet(matriz3[1] , 7);
		llenarMatrizWavelet(matriz3[0] , 6);
		llenarMatrizWavelet(matriz4[2] , 5);
		llenarMatrizWavelet(matriz4[1] , 4);
		llenarMatrizWavelet(matriz4[0] , 3);
		llenarMatrizWavelet(matriz5[2] , 2);
		llenarMatrizWavelet(matriz5[1] , 1);
		llenarMatrizWavelet(matriz5[0] , 0);
		
		return energia;
		
		
		}
	
	//metodo para llenar una matriz con fines de graficar los datos
	public void llenarMatrizWavelet(double []datos, int pos){
		
		int N=datos.length;
/*cambiar*/	int paso=96/N;
		int lim=paso;
		int suma=paso;
		int cont=1;
		

		for(int i=0;i<96;i++){
			
			if(i<lim){
			matrizWavelet[pos][i]=datos[cont-1];
			}
			else{
				
				cont++;
				suma=suma+paso;
				lim=suma;
				i--;

				}
			}
			
		}
	public double[][] calcular(double[] coeficientes, int estado){
		
		int N=coeficientes.length;
		int M=N;
		double a,b,c,d,e,f,g,h;		
		double n1,n2;//coeficientes para extrapolacion
		int pos=0;
		int i,j,k;
		double [] temp=new double[datos.length];
	
		
		for(i=0;i<estado-1;i++)
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
		double [][]matriz;	
			
		matriz=matrizWavelet(coeficientes,estado);	
		return matriz;
		}
		
	public double[] coeficientes(){
		return coeficientes;
		}	
		
		/*alamcena en una matriz los coeficientes wavelets por escala*/	

	public double[][] matrizWavelet(double[] coeficientes, int estado){
		
		double[][] matriz=new double[estado][];
		int M=coeficientes.length;
		int a=0;
		int b=0;
		
		int i,j;
		a=M/2;
		for(i=0;i<estado-1;i++){
			matriz[i]=new double[M/2];
			for( j=0;j<M/2;j++){
				
				matriz[i][j]=coeficientes[a+j];
				
				}
				M=M/2;
				a=M/2;
			}
			
			//ultimo estado
			
			matriz[i]=new double[M];
			for( j=0;j<M;j++){
				matriz[i][j]=coeficientes[j];
				
				}
				
		
/*		System.out.println("matriz wavelet");
		
		for(i=0;i<estado;i++){
			System.out.println("-------------fila "+i+"-----tamaño "+matriz[i].length+"----");	
			for( j=0;j<matriz[i].length;j++){
			System.out.print(" "+matriz[i][j]);	
				}
			System.out.println(" ");	
			
			}
*/			
		return 	matriz;
		
		}	

	public double[][] obtenerMatrizWavelet(){
		return matrizWavelet;
		}	
		
	//retorna la energia de la matriz wavelet	
	public double[] obtenerEnergia(){
		return energia;
		}		
		
	public double energia(double [] nivelWavelet){
		
		double energia;
		double suma=0;
		
			for(int i=0;i<nivelWavelet.length;i++){
				
				suma=suma+Math.pow(nivelWavelet[i],2);
				
				}
				energia=suma;
				energia=energia/nivelWavelet.length;
			
/*		System.out.println(" energia ");
		for(int k=0;k<energia.length;k++){
			
			System.out.print(" "+energia[k]);
			}
		System.out.println("");	
*/		return energia;	
		}
		
		
		
	public static void main(String args[]){
		
		double [] datos=new double[384];
		double [][] matrizWavelet;
		double [] energia;
/*		reconocerWaveletDaubechies reconocer= new reconocerWaveletDaubechies(datos);
		
		matrizWavelet=reconocer.matrizCaracteristicas;
		
		System.out.println("matriz de caracteristicas");
		
		for(int i=0;i<matrizWavelet.length;i++){
			for(int j=0;j<matrizWavelet[i].length;j++){
				System.out.print(" "+matrizWavelet[i][j]+" ");
				}
			System.out.println("");
		}	
*/
//		double [] datos={4,6,10,12,8,6,5,5};
		int i;
		for(i=0;i<384;i++){
			datos[i]=i+1;
			}
		
		for(i=100;i<105;i++){
			datos[i]=0;
			}
		
		waveletPacketWalsh packetDb2=new waveletPacketWalsh(datos);
		
		double[][] matriz;
		matriz=packetDb2.obtenerMatrizWavelet();
	
		System.out.println("    matriz para grafica    ");
		for(i=0;i<matriz.length;i++){
			for(int j=0;j<matriz[1].length;j++){
				System.out.print(" "+matriz[i][j]+" ");
				}
			System.out.println("");
			System.out.println("nueva fila");
			System.out.println("");
		}	

	
	
	
	}
	
}