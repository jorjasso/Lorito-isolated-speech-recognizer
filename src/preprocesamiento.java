//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

//normalizacion 
//eliminacion de ruido, 
//eliminacion de segmentos inutiles
//recorte

class preprocesamiento{
	
	
	public preprocesamiento(){}
	
	public static double[] normalizacion(double[] datos, double max){
		
		//T(y)=Ay+B
		//T(c)=m
		//T(d)=n
		//A=(m-n)/(c-d)  B=m-(m-n)/(c-d)
	    //valores para [m,n]=[-1,1]
		//valores para [c,d]=[-max,max]
		double m,n,c,d,A,B;
		double[]temporal=new double[datos.length];
			m=-1;
			n=1;
			c=-max;
			d=max;

			A=(m-n)/(c-d);
			B=m-((m-n)/(c-d))*c;

		for (int i=0;i<temporal.length;i++){
						
    			temporal[i]=A*datos[i]+B;
		}
		return temporal;
		}
		
	public static double[] eliminacionRuido(double[] datos){
		
		return datos;
		}
		
	public static double[] eliminarSegmentosInutiles(double[] datos){
		double umbral=50;
		int radio=5;
		double valor=0;
		double suma=0;
		int inicio=0;
		int fin=0;
		int lim=radio;
		
		System.out.println("valor = "+valor+" suma = "+suma+" inicio = "+inicio+" fin = "+fin+" lim = "+lim);
		for(int i=0;i<datos.length;i++){
		
				if(i<lim){			
					suma=suma+Math.abs(datos[i]);
	//	System.out.println("valor = "+valor+" suma = "+suma+" inicio = "+inicio+" fin = "+fin+" lim = "+lim+" i ="+i);				
				}
				else{
					
					valor=suma/radio;
			//		System.out.println("valor = "+valor);
					if(valor>umbral){
						inicio=i;
						break;	
						}
					else{
						valor=0;
						lim=lim+radio;
						suma=Math.abs(datos[i]);
						
						}	
					}
				
			}
	//	System.out.println("valor = "+valor+" suma = "+suma+" inicio = "+inicio+" fin = "+fin+" lim = "+lim);	
			int cont=0;
			lim=radio;
			valor=0;
			suma=0;		
			for(int i=datos.length-1;i>=0;i--){
			
				if(cont<lim){			
					suma=suma+Math.abs(datos[i]);
	//					System.out.println("valor = "+valor+" suma = "+suma+" inicio = "+inicio+" fin = "+fin+" lim = "+lim+" i = "+i);				
				}
				else{
					
					valor=suma/radio;
	//				System.out.println("valor = "+valor);
					if(valor>umbral){
						fin=i;
						break;	
						}
					else{
						valor=0;
						lim=lim+radio;
						suma=Math.abs(datos[i]);
						
						}	
					}
				cont++;		
			}		
		
		System.out.println(" inicio = "+inicio+"  fin = "+fin);
		
		double [] datosRecorte;
		datosRecorte=preprocesamiento.recorte(datos,inicio,fin);
		return datosRecorte;
		}		
		
		//recorta un arreglo desde la posicion inicio-fin
	public static double[] recorte(double[] datos, int inicio, int fin){
		
		double [] datosRecorte=new double[fin-inicio+1];
		
		for(int i=inicio;i<=fin;i++){
		datosRecorte[i-inicio]=datos[i];
			}
		
		return datosRecorte;
		}	
	
	
	}