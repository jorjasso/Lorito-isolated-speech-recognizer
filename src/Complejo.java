//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com
public class Complejo {
	
	double real;
	double imaginario;
	
	/***cosntructores**///
	
	public Complejo(){
		real=0.0;
		imaginario=0.0;
		
		}
		
	public Complejo(double real, double imaginario)	{
		this.real=real;
		this.imaginario=imaginario;		
		}
		
	public Complejo(double real){
		this.real=real;
		imaginario=0;
		}
		
	 /****metodos de instancia***///
		
		// modifica la esctructura interna del numero complejo
	public void conjugado(){
		
		
		double tem;
		tem= real;
		real=imaginario; 
		imaginario=tem;
		
		}
		
		// no modifica la esctructura interna del numero complejo
	public Complejo Cconjugado(){
		
		Complejo conj=new Complejo(imaginario,real);
		return conj;
		}
		
	public  void menosComplejo(){
		this.real=-this.real;
		this.imaginario=-this.imaginario;
				
	}
	
	public  Complejo menosC(){
		Complejo complejo = new Complejo(-this.real,-this.imaginario);
		return complejo;
				
	}
	
		
	public String cadena(){
		
		if (this.imaginario>=0){
			return ("( "+this.real+" + "+this.imaginario+"i"+" )");
		}
		else{
			return ("( "+this.real+" - "+-this.imaginario+"i"+" )");}
		
		}	
		
		/***metodos de clase***/
		
	public static Complejo sumar(Complejo c1, Complejo c2)	{
		Complejo resultado = new Complejo();
		resultado.real=c1.real+c2.real;
		resultado.imaginario=c1.imaginario+c2.imaginario;
		return resultado;
		
		}
		
	public static Complejo multiplicar(Complejo c1, Complejo c2) {
	
		Complejo resultado = new Complejo();
		resultado.real=c1.real*c2.real-c1.imaginario*c2.imaginario;
		resultado.imaginario=c1.real*c2.imaginario+c1.imaginario*c2.real;
		return resultado;
    
       }
       
   
}
