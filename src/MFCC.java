
//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

/***********************/
//pasa las frecuencias a la escala Mel
//construye los bins
//construye los cepstrum
//reporte final :MFCC



public class MFCC{
	
	int Fs ; //frecuencia de sampleo
	double[] arreglo;
	int nBins;
	
	// lleva las frecuencias a la escala bin 
	//nBins es el numero de bins 
	//el arreglo que debe entrar es el modulo del FFT de tamaño N/2
	
	public MFCC(int Fs, double [] arreglo, int nBins){  
		
		this.Fs=Fs;
		this.arreglo=arreglo;
		this.nBins=nBins;
		}
	
	
	public static double []  coeficientesMFCC(int Fs, double [] arreglo, int nBins){
		int N=arreglo.length;
		double [] Bins= new double [nBins];
		double fMenor=0;      //menor frecuencia   0Hz
		double fMayor=(double)(N-1)*(double)Fs/((double)N*(double)2);    //mayor frecuencia  = 1/(2*T) o 1*fs/2 criterio nyquist
	
	 	/*******/		
/*		System.out.println(" fMayor = "+fMayor);
		double frecuencia,frecuenciaMel;
		
			frecuencia=(double)(N-1)*(double)16000/((double)N*(double)2);
			frecuenciaMel= 1127*(Math.log(1+(double)((frecuencia))/700));
			
		double recontraPaso=frecuenciaMel/24;
		double sumaRC=recontraPaso;
		System.out.println(" recontraPaso"+recontraPaso);
		
		for(int p=0;p<16000;p++){
			
			frecuencia=(double)(N-1)*(double)p/((double)N*(double)2);
			frecuenciaMel= 1127*(Math.log(1+(double)((frecuencia))/700));
			if(frecuenciaMel>sumaRC){
				System.out.println(""+p+"	"+frecuencia+"	"+frecuenciaMel);
				sumaRC=sumaRC+recontraPaso;
			}
			
			}
	*/	/******/
		double fMelMenor = 0;
		double fMelMayor= 1127*(Math.log(1+(double)((fMayor))/700));
		
//		System.out.println(" fMelMayor = "+fMelMayor);
		
		double paso=fMelMayor/(nBins+1);
		double k=0,H=0;  // H(Mel(F))   k=Mel(F)
//		System.out.println(" paso = "+paso);
		int i=0;int j=0; int ban=0; int pos=0; int b=0;
		double suma=0;
		double fMenos,fMedio,fMas;
		
		fMenos=0;fMedio=paso;fMas=2*paso;
		
	//	System.out.println("fMenos = "+fMenos+" fMedio = "+fMedio+" fMas = "+fMas);		
		
		for(i=0;i<N;i++){
		
			
			k= Mel((double)i*(double)Fs/((double)N*(double)2));  //por que es i*Fs/N, pero este vector solo tiene los N/2 samples de fourier, no tienen los conjugados
			
//			System.out.println("i = "+i+" frecuencia = "+(double)i*(double)Fs/((double)N*(double)2)+" Mel = "+k);
			
			if(k>fMas){
				Bins[b]=Math.log(suma);   //cepstrum logaritmo natural 
				i=pos;
				ban=0;
				suma=0;
				fMenos=fMenos+paso;
				fMedio=fMedio+paso;
				fMas=fMas+paso;
				k= Mel((double)i*(double)Fs/((double)N*(double)2));
				
	//		System.out.println(" Bins["+b+"] = "+Bins[b]+" i = "+i+" ban = "+ban+" suma = "+suma+" fMenos = "+fMenos+" fMedio = "+fMedio+" fMas = "+fMas);
	//		System.out.println(" k = "+k);
				b++;
				}
				
			if(k>=fMenos&&k<fMedio){
				
			H=(k-fMenos)/paso;
			
	//		System.out.println(" H ="+H );
			
			}
			
			if(k>=fMedio&&k<=fMas){
				
				if(ban==0){
					pos=i;
					ban=1;
	//		System.out.println(" pos = "+pos+" ban = "+ban);
					}
				
			H=(fMas-k)/paso;	
	//		System.out.println(" H ="+H );
						
			}
		
			suma =suma+ arreglo[i]*H;  
			/**********///
			//aca puede ser:
			// suma=suma+Math.pow(arreglo[i],2)*H; // (probar)
			/************/////
	//		System.out.println(" suma = "+suma);
	//		System.out.println(" b = "+b);
			}
	//	System.out.println(" b = "+b);
			Bins[b]=Math.log(suma);  //cepstrum 
	//		System.out.println(" Bins["+b+"] = "+Bins[b]+" i = "+i+" ban = "+ban+" suma = "+suma+" fMenos = "+fMenos+" fMedio = "+fMedio+" fMas = "+fMas);
	//		System.out.println(" k = "+k);		
			Bins=MFCC.DTCII(Bins);
			
		return Bins;
		}
	
	/****************/
	//trasnformada discreta del coseno para obtener el cepstrum
	//entrada ln[valor del bin]
	public static double [] DTCII(double [] arreglo){
		
		double [] DCT = new double [arreglo.length];
		double suma =0;
		double N =(double)arreglo.length;
		for(int i=0;i<N;i++){
			
			for(int j=0;j<N;j++){
		    		
			 	suma = suma +arreglo[j]*Math.cos((Math.PI*((double)i+1)*((double)j+0.5))/N);
	         //	suma = suma +arreglo[j]*Math.cos((Math.PI*(2*(double)i-1)*((double)j-1))/(2*(double)N));
				
				}
				DCT[i] = Math.sqrt(2/N)*suma;
				suma=0;
		
				
			}
		
		return DCT;
		}
		
	
	private static double Mel(double frecuencia){
		
		frecuencia=1127*Math.log(1+frecuencia/(double)700);
		return frecuencia;
		}
	
	}