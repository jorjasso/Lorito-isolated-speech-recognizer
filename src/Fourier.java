//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

// Trasformada Rapida de Fourier




public class Fourier {
	
	int estado;
	double[] arreglo;
	int tamano=0;
	Complejo [] W;
	Complejo [] Datos;
	double mayor=0; //para valor mayo en transformada de fourier
	double mayorValorEspectograma=0;//para mayor valor en espectograma;


	
	//Algoritmo radix 2 decimacion en frecuencia
	
	public Fourier(double []arreglo, int estado){ //
		this.estado=estado;
		this.arreglo=arreglo;
		tamano=this.arreglo.length;
		W= new Complejo[arreglo.length/2];  //arreglo de complejos para los factores mariposa
		Datos=new Complejo[arreglo.length];
		mayor=0;
//			System.out.println("tamaño en fourier()="+tamano);
		}
	
	public Fourier( double [] arreglo){		
		this.arreglo=arreglo;
		tamano=this.arreglo.length;
		W= new Complejo[arreglo.length/2];  //arreglo de complejos para los factores mariposa
		Datos=new Complejo[arreglo.length];
		estado=Potencia();
		mayor=0;
//		System.out.println("tamaño en fourier()="+tamano);
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
			   tem=this.arreglo;
			   this.arreglo= new double[i];
			    this.W= new Complejo[nPotencia/2];
			    this.Datos=new Complejo[nPotencia];
			   this.arreglo=modificarArreglo(tem,i);
			   this.tamano=this.arreglo.length;
                this.estado=i-1;  			  
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
	public double [] modificarArreglo(double [] arreglo, int n){
		
		double [] arregloModificado= new double [(int)java.lang.Math.pow((double)2,(double)n)];
		int i;
		double promedio=0;
		for( i=arreglo.length;i< (int)java.lang.Math.pow((double)2,(double)n);i++){
			arregloModificado[i]=promedio;
		}
		
		for(i =0;i<arreglo.length;i++){
		
		arregloModificado[i]=arreglo[i];
		}
		
		return arregloModificado;
		}
	
	
	
	public void FFTradix2DIF(){
		int i=0,j=0;
		Complejo [] temporal=new Complejo [tamano];
		Complejo resultado=new Complejo();
	   	
	   	//paso del arreglo de entrada a un arreglo de complejos
	   
        for(i=0;i<tamano;i++){
        	Datos[i]=new Complejo(this.arreglo[i]);
        //	System.out.print(Datos[i].cadena());
        	}	   
	
		//llenado de los factores mariposa
		int N=tamano;
		
	
		for(i=0;i<tamano/2;i++){
			W[i]=new Complejo(Math.cos(2*Math.PI*i/N),-Math.sin(2*Math.PI*i/N));	
		
			}
		
		//computa radix 2 decimacion en frecuencia FFT,The Gentleman-Sande butterfly. 
		int mitad=tamano/2;
		int a=0;
		int division=N/2;
		int contador=1;
	    int k=0;   //contador para los factores mariposa
	    int factor=1;  // para accesar a los factores mariposa
		int paso=division;
		
		for(i=0;i<estado;i++){
			
			k=0;
			
			for(j=0;j<mitad;j++){
				
			if(j==division){
				//contadores
	//			Tem=N;
                a=N*contador;
                division=division+paso;
                contador++;
                
				k=0;
				}		
		
			temporal[a]       = resultado.sumar(Datos[a],Datos[a+N/2]);	
			temporal[a+N/2] = resultado.multiplicar(resultado.sumar(Datos[a],(Datos[a+N/2].menosC())),W[k*factor]); 	
			
	  	    Datos[a].real=temporal[a].real;
        	Datos[a].imaginario=temporal[a].imaginario;
        	Datos[a+N/2].real=temporal[a+N/2].real;
        	Datos[a+N/2].imaginario=temporal[a+N/2].imaginario;
		/* 	//Complejo Prueba = new Complejo();
			//Prueba=Datos[a+N/2].menosC();
		//	System.out.println("datos[a] ="+Datos[a].cadena()+"datos[a+N/2] ="+Datos[a+N/2].cadena()+"temporal[a]"+temporal[a].cadena()+"temporal[a+N/2]"+temporal[a+N/2].cadena());
		//	System.out.println("resta = "+temporal[a+N/2].cadena());
			System.out.println("k ="+k+" factor ="+factor+" W["+k*factor+"] a = "+a);
			*/
			a++;	
			k++;
				
			}
			a=0;
			N=N/2;
			division=N/2;
			paso=division;
			contador=1;
			factor=factor*2;
			
			//Datos=temporal; 
			
		/*	for(int m=0;m<tamano;m++){
        	
        	Datos[m].real=temporal[m].real;
        	Datos[m].imaginario=temporal[m].imaginario;
		
		}*/
			
		/*	 for(int m=0;m<tamano;m++){
        	
        	System.out.println(Datos[m].cadena());
		
		}*/
		//		System.out.println("");
		}
		
		this.Datos=temporal;
		
	/******reordenamieto de bits: decodificando**************/////////
	
	    j = 0; i=0; k=0; double xt=0;
	 	for (i = 0; i < tamano- 1; i++) {
			if (i < j) {
				xt = this.Datos[j].real;
				this.Datos[j].real = this.Datos[i].real;
				this.Datos[i].real = xt;
				xt = this.Datos[j].imaginario;
				this.Datos[j].imaginario = this.Datos[i].imaginario;
				this.Datos[i].imaginario = xt;
			}
				k = tamano / 2;
				
		while (k <= j) {
				j -= k;
				k /= 2;
			}
			j += k;
		}
	/**********************//////
	/*	for(int m=0;m<tamano;m++){
        	
        	System.out.println(Datos[m].cadena());
		
		}
		
	*/	
	
		}
		
			//devuelve el modulo de solo la mitad de los valores
	
	  public double [] moduloI(){
//			System.out.println("tamaño="+tamano);
			double [] arreglo =new double [tamano/2];

			int i=0;
			mayorValorEspectograma=0;
			
			for(i=0;i<tamano/2;i++){
				
				arreglo[i]=Math.sqrt(Math.pow(Datos[i].real,2)+Math.pow((Datos[i].imaginario),2));
				

				if(mayor<arreglo[i]){
		   			mayor=arreglo[i];
		   			}
		   			
				
				}
				
				return arreglo;
	}	
	
	
	
	
	/*lo mismo que el anterior pero sin el primer elemento pues es solo el promedio*/
	public double [] moduloII(){  
//			System.out.println("tamaño="+tamano);
			double [] arreglo =new double [tamano/2-1];
			int i=0;
			
			for(i=0;i<tamano/2-1;i++){
				
				arreglo[i]=Math.sqrt(Math.pow(Datos[i+1].real,2)+Math.pow((Datos[i+1].imaginario),2));
				
				if(mayor<arreglo[i]){
		   			mayor=arreglo[i];
		   			}
				
				}
				
				return arreglo;
	}	
	
	
	
	public double obtenerMayor(){
		return mayor;
		}	
		
	
			
			
	public void limpiar (Fourier fourier)	{
		fourier =null;
		}	
		/*  */
	
		
		
		}
		/////*******************////
		
	
