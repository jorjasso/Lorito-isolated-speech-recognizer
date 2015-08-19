//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

//Permite guardar los valores de caracteristicas para 
//una plantilla por me dio de la tecnica de fourier
class plantilla implements java.io.Serializable{
	
	String nombre;
	double[][] Matriz;
	
	public plantilla(){}
	
	//entrada nombre y valores de caracteristicas
	public plantilla(String nombre, double [][]Matriz){
		this.nombre=nombre;
		this.Matriz=Matriz;
		}
	
	public String obtenerNombre(){
		return nombre;
		}
		
	public double[][] obtnerMatriz(){
		return Matriz;
		}	
	
	public void imprimirMatriz(){
		int i=0;
		int j=0;
		for(i=0;i<Matriz.length;i++){
    		for(j=0;j<Matriz[0].length;j++){
    			System.out.print(" "+Matriz[i][j]);
    	
    		}
    		System.out.println("");
    	}
		
		
		}
	
	
	
	}