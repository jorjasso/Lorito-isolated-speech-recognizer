//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

/*clase para grabar un nuevo objeto del tipo plantilla*/
import java.io.*;
import java.util.*;  

public class patronesPlantillasArchivo {
	
	
	
	public static void insertar(plantilla p,String nombre){
	//	String nombre = "patrones plantilla";
		try  {
        	ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(nombre+".lorito"));
            patronesPlantillas obj1=(patronesPlantillas)entrada.readObject();
            obj1.mostrarPatronesPlantillas();
            System.out.println("-----------------------------");
            entrada.close();
            
		obj1.insertarPlantilla(p);
        
        
        ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(obj1.nombre()+".lorito"));
 //           salida.writeObject("guardar este string y un objeto\n");
            salida.writeObject(obj1);
            salida.close();
            
            entrada=new ObjectInputStream(new FileInputStream(nombre+".lorito"));
   //       String str=(String)entrada.readObject();
            obj1=(patronesPlantillas)entrada.readObject();
            obj1.mostrarPatronesPlantillas();
            System.out.println("-----------------------------");
            entrada.close();    
            
        }catch (Exception e) {  }
		
		
		
		}
	
	public static plantilla[] leer(String nombre){
	//	String nombre = "patrones plantilla";
		try  {
        	ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(nombre+".lorito"));
            patronesPlantillas obj1=(patronesPlantillas)entrada.readObject();
            obj1.mostrarPatronesPlantillas();
            System.out.println("-----------------------------");
            entrada.close();
            plantilla[] p=obj1.obtenerPlantillas();
			return  p;
            
        }catch (Exception e) {  }
		
		return null;
		
		}
	
	
 /*   public static void main(String[] args) {
  
  		String nombre = "patrones plantilla";
  		
        try  {
        	ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(nombre+".lorito"));
            patronesPlantillas obj1=(patronesPlantillas)entrada.readObject();
            obj1.mostrarPatronesPlantillas();
            System.out.println("-----------------------------");
            entrada.close();
            
        double [][] matriz=new double[7][7];
    	String nombre1 ;
    	int i=0; 
    	int j=0;
    	int a=0;
    	for(i=0;i<7;i++){
    		for(j=0;j<7;j++){
    			
    			matriz[i][j]=a;
    			a++;
    		}
    	}
    	nombre1 = "plantilla uno";
        plantilla p1= new plantilla(nombre1,matriz);
        
        double [][] matriz1=new double[7][7];
    	String nombre2 ;
    	a=0;
    	for(i=0;i<7;i++){
    		for(j=0;j<7;j++){
    			
    			matriz1[i][j]=a+1;
    			a++;
    		}
    	}
    	nombre2 = "plantilla dos";
        plantilla p2= new plantilla(nombre2,matriz1);
        
        double [][] matriz2=new double[7][7];
    	String nombre3 ;
    	
    	a=0;
    	for(i=0;i<7;i++){
    		for(j=0;j<7;j++){
    			
    			matriz2[i][j]=a+2;
    			a++;
    		}
    	}
    	nombre3 = "plantilla tres";
        plantilla p3= new plantilla(nombre3,matriz2);
        
        obj1.insertarPlantilla(p1);
        obj1.insertarPlantilla(p2);
        obj1.insertarPlantilla(p3);
        
        ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(obj1.nombre()+".lorito"));
 //           salida.writeObject("guardar este string y un objeto\n");
            salida.writeObject(obj1);
            salida.close();
            
            entrada=new ObjectInputStream(new FileInputStream(nombre+".lorito"));
   //       String str=(String)entrada.readObject();
            obj1=(patronesPlantillas)entrada.readObject();
            obj1.mostrarPatronesPlantillas();
            System.out.println("-----------------------------");
            entrada.close();    
            
        }catch (Exception e) {  }
        
        
        
         
   }
  */ 
}