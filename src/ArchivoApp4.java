/*clase para grabar un nuevo objeto del tipo plantilla*/
import java.io.*;
import java.util.*;  

public class ArchivoApp4 {
    public static void main(String[] args) {
    	
   /* 	
        

        
        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
 //           salida.writeObject("guardar este string y un objeto\n");
            salida.writeObject(p1);
            salida.close();

            ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(nombre+".lorito"));
   //         String str=(String)entrada.readObject();
            plantilla obj1=(plantilla)entrada.readObject();
            System.out.println("nombre"+obj1.nombre);
            System.out.println("-----------------------------");
            System.out.println("matriz");
            obj1.imprimirMatriz();
            System.out.println("-----------------------------");
            System.out.println(obj1);
            System.out.println("-----------------------------");
            entrada.close();
//se puede fundir en una catch Exception
        }catch (IOException ex) {
            System.out.println(ex);
         }catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }

        try  {
//espera la pulsaci�n de una tecla y luego RETORNO
            System.in.read();
        }catch (Exception e) {  }
   }
  */ 
  
  		String nombre ="patrones plantilla";
        patronesPlantillas p= new patronesPlantillas(nombre);
        

        
        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
 //           salida.writeObject("guardar este string y un objeto\n");
            salida.writeObject(p);
            salida.close();

 /*           ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(nombre+".lorito"));
   //         String str=(String)entrada.readObject();
            plantilla obj1=(plantilla)entrada.readObject();
            System.out.println("nombre"+obj1.nombre);
            System.out.println("-----------------------------");
            System.out.println("matriz");
            obj1.imprimirMatriz();
            System.out.println("-----------------------------");
            System.out.println(obj1);
            System.out.println("-----------------------------");
            entrada.close();
*///se puede fundir en una catch Exception
        }catch (IOException ex) {
            System.out.println(ex);
         }
   /*      catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
*/
		
        



        try  {
        	ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(nombre+".lorito"));
   //       String str=(String)entrada.readObject();
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
    			
    			matriz[i][j]=a+1;
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
    			
    			matriz[i][j]=a+1;
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
}