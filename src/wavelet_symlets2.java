//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

public class wavelet_symlets2{
	
	double h1,h2,h3,h4,g1,g2,g3,g4;
	double []datos;	
	double []coeficientes;
	
	public wavelet_symlets2(double [] datos){
		
		//coeficietes de los filtros para la funcion escala
		h1=0.4830;
		h2=0.8365;
		h3=0.2241;
		h4=-0.1294;
		
		//coeficientes de los filtros para la funcion wavelets
		g1=h4;
		g2=-h3;
		g3=h2;
		g4=-h1;
		
		
		this.datos=datos;
		coeficientes = new double[datos.length];
		copiar();//incializa el vector coeficientes con los elementos del vector datos
		calcular();
		
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
	
		
		for(i=0;i<Math.log(N);i++)
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
				
		System.out.println(" coeficientes ");		
		for(j=0;j<datos.length;j++){
		
		System.out.print(" "+coeficientes[j]+" ");
		}	
		System.out.println(" ");
				
			
			}
		
		}
		
	public double[] coeficientes(){
		return coeficientes;
		}	
		
		
		
	public static void main(String args[]){
		
		double [] datos={4,6,10,12,8,6,5,5};
		wavelet_symlets2 symlet=new wavelet_symlets2(datos);
		int i;
		
		for(i=0;i<datos.length;i++){
		
		System.out.print(" "+datos[i]+" ");
		}	
		System.out.println(" ");
		
		datos=symlet.coeficientes();
		
		for(i=0;i<datos.length;i++){
		
		System.out.print(" "+datos[i]+" ");
		}	
	
	}
	}