//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

//clase donde estaran todas las plantillas a ser procesadas
//por medio de la tecnica de fourier

class patronesPlantillas implements java.io.Serializable{
	
	public plantilla [] p;  //conjunto de plantillas que seran guardadas para la comparacion
	String nombre;    //nombre de patrones plantillas
	
	final static int MAX_NUMERO_PLANTILLAS =20; //maximo numero de plantillas
	//final static int MAX_NUMERO_PLANTILLAS =125; //maximo numero de plantillas
		
	int contador;
	
	public patronesPlantillas(String nombre){
		contador=0;
		this.nombre=nombre;
		crearPlantilla();
		}
	
	public void crearPlantilla(){
		 p=new plantilla[MAX_NUMERO_PLANTILLAS];
		}
	
	public plantilla[] obtenerPlantillas(){
		
		return p;
		}
	
	//inserta una plantilla
	public void insertarPlantilla(plantilla pl){
		
		if(contador<MAX_NUMERO_PLANTILLAS)
		{p[contador++]=pl;}
		else
		{
			System.out.println("no se pueden insertar mas plantillas");
			}
		}
		
	public String nombre(){
		return nombre;
		}
	
	public void mostrarPatronesPlantillas(){
		
	System.out.println("patrones plantillas"+nombre);
//	System.out.println("maximo numero de plantillas"+MAX_NUMERO_PLANTILLAS);
//	System.out.println("actual numero de plantillas"+contador);
//	System.out.println("plantillas actuales");
//	for(int i=0;i<contador;i++){
//		System.out.println("	"+p[i].obtenerNombre());
//		p[i].imprimirMatriz();
//		}
	
	}
	}