
//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

//hace el procedimiento para reconocer una palabra
//por medio de fourier

class reconocerFourier{
		
		//entrada voz normalizada
	Ventaneamiento ventaneamiento;  //obtiene la matriz de ventaneamiento	
	double[][]matrizFourier; //almacena valores computados despues de ventanamiento -fft
	double[][]matrizMFCC;//almacena la mastriz con los valores MFCC
	Fourier fft;
	int frecuenciaDeSampleo;
	
	double mayorEspectro;
	
	
		
	public reconocerFourier(double [] arreglo, int frecuenciaDeSampleo){
		//entra el arreglo de voz normalizada
		//sale una matriz de ventaneamiento
		 ventaneamiento=new Ventaneamiento(arreglo);//obtiene la matriz de ventaneamiento
		 //ventaneamiento.imprimirMatriz();
		 this.frecuenciaDeSampleo=frecuenciaDeSampleo;
		 obtenerMatrizFourier();	 				//obtiene el espectograma (matriz de espectros)
		 calcularMatrizMFCC();
	
		}
	
	//computa en la matriz de ventaneamiento la matriz de fourier	
	// es decir la short fourier transform, o transformada de Gabor
	public void obtenerMatrizFourier(){
		int filas=0;
		matrizFourier=ventaneamiento.obtenerMatriz();//obtiene la matriz de ventanamiento
		filas = ventaneamiento.devolverFilas();//devuelve las filas de la matriz de ventanamietno
			mayorEspectro=0;
			for(int i=0;i<filas;i++){ //computa iterativamente la matriz de fourier
				
				fft=new Fourier(matrizFourier[i]); //construye el objeto Fourier
		        fft.FFTradix2DIF();					//computa el fft
		        double [] arreglo1 ;
		        matrizFourier[i]=fft.moduloI();				//devuelve el modulo de los valores computados incluyendo el primer elemento

				if(mayorEspectro<fft.obtenerMayor()){
					mayorEspectro=fft.obtenerMayor();
//					System.out.println("mayorEspectro "+mayorEspectro);
					}
						        
		      //matrizFourier[i]=fft.moduloII();  			//devuelve el modulo de los valores computados	
			}	
		}	
	
	
	//retorna la matriz de los espectros
	public double[][] obtenerEspectro(){	
		return matrizFourier;  //matriz de los espectros
		
		}

    public double obtenerMayorEspectro(){
    	return mayorEspectro;
    	}
    
    
	public void imprimirMatrizEspectro(){
		
		for(int i=0;i<matrizFourier.length;i++)	{
			for(int j=0;j<matrizFourier[0].length;j++)	{
			
			System.out.print(matrizFourier[i][j] + " ");
		}
		System.out.println();	
		}
		
	}		
	
	/*obtencion del vector de caracteristicas*/
	public void calcularMatrizMFCC(){
		int filas=0;
		int nBins=13;
		filas = ventaneamiento.devolverFilas();//devuelve las filas de la matriz de ventanamietno
		matrizMFCC=new double[filas][nBins];//crea matriz MFCC
		
			for(int i=0;i<filas;i++){ //computa iterativamente la matriz MFCC
		        matrizMFCC[i]=MFCC.coeficientesMFCC(frecuenciaDeSampleo,matrizFourier[i],nBins); 
			}		
		
		
		}
	public double[][] obtenerMatrizMFCC(){	
		return matrizMFCC;  //Matriz MFCCs
		
		}
		
	public void imprimirMatrizMFCC(){
		
		for(int i=0;i<matrizMFCC.length;i++)	{
			for(int j=0;j<matrizMFCC[0].length;j++)	{
			
			System.out.print(matrizMFCC[i][j] + " ");
		}
		System.out.println();	
		}
		
	}			
	
	}