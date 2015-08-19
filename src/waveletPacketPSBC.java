//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

//wavelet packet que obtiene los parametros
//subbanda basados en el cepstro PSBC



public class waveletPacketPSBC{
	
	double h1,h2,h3,h4,g1,g2,g3,g4;
	double []datos;	
	double []coeficientes;
	
	double []energia;
	
	double [][]matrizWavelet; //para graficar los coeficientes
	
//	int estado; //hasta que nivel de descompocicion se ha hecho
	int tamano;
	
	
	//entrara un tamaño de 384 datos correspondiente a 
	//24mS para 16Khz de frecuencia de sampleo
	public waveletPacketPSBC(double [] datos){
		
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
		
		matrizWavelet=new double[23][96];
		
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
/*************************************************************/		
		//primer nivel de descompocicion coeficientes 3 
		//no se toma los coeficientes 1 y 2 tomar fila 5 para coeficiente 3
//		System.out.println("primer nivel de descomposicion");
		double[][] matriz1;
		matriz1=calcular(coeficientes,7);
		
/*************************************************************/				
		//segundo nivel de descompocicion coeficientes 4,5 tomar fila 1,0
//		System.out.println(" ");
//		System.out.println("segundo nivel de descomposicion");
		double[][] matriz2;
		matriz2=calcular(matriz1[4] ,2);
		
/*************************************************************/				
		//tercer nivel de descompocicion coeficientes 6,7,8,9 
		//tomar fila 2,1 para coeficientes 6,7
//		System.out.println(" ");
//		System.out.println("tercer nivel de descomposicion");
		double[][] matriz3;
		matriz3=calcular(matriz1[3],3);
		
		//tomar fila 1,0 para coeficiente 8,9
		double[][] matriz3a;
		matriz3a=calcular(matriz3[0],2)	;
		
/*************************************************************/				
		//cuarto nivel de descompocicion coeficientes ,10,11,12,13
		//tomar fila 2,1 para coeficientes 10,11
//		System.out.println(" ");
//		System.out.println("cuarto nivel de descomposicion");
		double[][] matriz4;
		matriz4=calcular(matriz1[2],3);
		
		//tomar fila 1,0 para coeficientes 12,13
		double[][] matriz4a;
		matriz4a=calcular(matriz4[0],2);
		
/*************************************************************/						
	   //quinto nivel de descompocicion coeficientes 14,15,16,17,18 
	   //tomar fila 3,2,1 para coeficientes 14,15,16
//		System.out.println(" ");
//		System.out.println("quinto nivel de descomposicion");
		double[][] matriz5;
		matriz5=calcular(matriz1[1],4);
		
		//tomar fila 1,0 para coeficientes 17,18
		double[][]matriz5a;
		matriz5a=calcular(matriz5[0],2);
/*************************************************************/						
		//sexto nivel de descompocicion coeficientes 19,20,21,22,23,24
		//tomar coeficientes 3,2 para coeficientes 19,20
		double[][] matriz6;
		matriz6=calcular(matriz1[0],4);		
		
		//tomar filas 1,0 para coeficientes 21,22
		double [][] matriz6a;
		matriz6a=calcular(matriz6[1],2);
		
		//tomar filas 1,0 para coeficientes 23,24
		double [][]matriz6b;
		matriz6b=calcular(matriz6[0],2);

/*************************************************************/								
		//calculo de las energias no se toma el primer grupo de coeficientes
		//pues corresponden al ruido del canal de transmision
		double [] energia=new double[22];
		
		energia[21]=energia(matriz1[5]); //coeficiente 3
		
		energia[20]=energia(matriz2[1]); //coeficiente 4
		energia[19]=energia(matriz2[0]); //coeficiente 5
		
		energia[18]=energia(matriz3[2]); //coeficiente 6
		energia[17]=energia(matriz3[1]); //coeficiente 7	
		energia[16]=energia(matriz3a[1]); //coeficiente 8
		energia[15]=energia(matriz3a[0]); //coeficiente 9
		
		energia[14]=energia(matriz4[2]); //coeficiente 10
		energia[13]=energia(matriz4[1]); //coeficiente 11
		energia[12]=energia(matriz4a[1]); //coeficiente 12
		energia[11]=energia(matriz4a[0]); //coeficiente 13
		
		energia[10]=energia(matriz5[3]); //coeficiente 14
		energia[9]=energia(matriz5[2]); //coeficiente 15
		energia[8]=energia(matriz5[1]); //coeficiente 16
		energia[7]=energia(matriz5a[1]); //coeficiente 17
		energia[6]=energia(matriz5a[0]); //coeficiente 18
		
		energia[5]=energia(matriz6[3]); //coeficiente 19
		energia[4]=energia(matriz6[2]); //coeficiente 20
		energia[3]=energia(matriz6a[1]); //coeficiente 21
		energia[2]=energia(matriz6a[0]); //coeficiente 22
		energia[1]=energia(matriz6b[1]); //coeficiente 23
		energia[0]=energia(matriz6b[0]); //coeficiente 24
		
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
		llenarMatrizWavelet(matriz1[6],22);
		llenarMatrizWavelet(matriz1[5],21);
		llenarMatrizWavelet(matriz2[1],20);
		llenarMatrizWavelet(matriz2[0],19);
		
		llenarMatrizWavelet(matriz3[2],18);
 	    llenarMatrizWavelet(matriz3[1],17);	
		llenarMatrizWavelet(matriz3a[1],16);
		llenarMatrizWavelet(matriz3a[0],15);
		
		llenarMatrizWavelet(matriz4[2],14);
  	    llenarMatrizWavelet(matriz4[1],13);
		llenarMatrizWavelet(matriz4a[1],12);
		llenarMatrizWavelet(matriz4a[0],11);
		
		llenarMatrizWavelet(matriz5[3],10);
		llenarMatrizWavelet(matriz5[2],9);
		llenarMatrizWavelet(matriz5[1],8);
		llenarMatrizWavelet(matriz5a[1],7);
		llenarMatrizWavelet(matriz5a[0],6);
		
		llenarMatrizWavelet(matriz6[3],5);
		llenarMatrizWavelet(matriz6[2],4);
		llenarMatrizWavelet(matriz6a[1],3);
		llenarMatrizWavelet(matriz6a[0],2);
		llenarMatrizWavelet(matriz6b[1],1);
		llenarMatrizWavelet(matriz6b[0],0);
		
		
		
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
		for(j=0;j<coeficientes.length;j++){
		
		System.out.print(" "+coeficientes[j]+" ");
		}	
		System.out.println(" ");
				
*/			
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
		
		waveletPacketPSBC packetDb2=new waveletPacketPSBC(datos);
		
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