
//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

class morlet{
	
	
	public void morlet(){}
	
	
	public static double[][] waveletMorlet( int lb, int ub, int  Npuntos){
		
		
		double [][] WM=new double[2][Npuntos];
		double incremento = (double)((ub-lb)/(double)(ub-lb-1));
		

		double c=(2/(Math.sqrt(3)*Math.pow(Math.PI,1/4)));
		c=0.8673;// c=(2/(Math.sqrt(3)*Math.pow(Math.PI,1/4)));
		
		System.out.println("incremento"+incremento+" c= "+c);
		double paso=lb;
		for(int j=0;j<Npuntos;j++){
			
			WM[0][j]=c*(1-Math.pow(paso,2))*Math.exp(-Math.pow(paso,2)/2);                        //coeficientes wavelet
			WM[1][j]=paso;                        //posicion
			
			paso=paso+incremento;
			}
			
			return WM;
		
		}
	
	
	
	
	}