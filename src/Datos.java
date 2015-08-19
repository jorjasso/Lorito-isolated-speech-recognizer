
//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com
import java.awt.*;
import java.io.*;
import java.io.IOException;

import java.util.*;

public class Datos {
	
	  byte[] bits ;	    
	  
	  boolean formato;  // para el formato bigEndian y littleEndian
	  
	  double mayor, menor;
	
	public  Datos(int tamano, boolean formato){
		
		bits = new byte[tamano];
		this.formato=formato;
		mayor=0;
		menor=0;
		}
	
	public void llenarByte(){
		try{
		
		for (int i = 0; i < bits.length; i++) {
            bits[i] = (byte)-i;
          
       }
		}catch (Exception e){
				System.out.println(e);
				System.out.println("error al llenar byte");
				System.exit(0);
			
				
				}
		
			}
			
	public void llenarByte(byte[] bits)	{
		
		this.bits = bits;
		
	}	
			
	public void mostrarByte(){
		try{
			for (int i = 0; i < bits.length; i++) {
        
            System.out.print((byte)bits[i] + " ");
        }
			}
			catch(Exception e){
				System.out.println(e);
				System.out.println("error al mostrar byte");
				System.exit(0);
				}
		
		
		}		
		/*****/
		/* ejemplo bits[2]=2 (00000010) bits[3]=3 (00000011)
		  se aplica bits[2]<<8 o sea 10 00000000 , luego 11111111(0x000000FF) & bits[3]|bits[2]
		  en total da 10 00000011  que es el numero 515 , este es un short de 16 bits , han entrado 
		  dos bytes en uno (short[i]=contacenar byte[i]+byte[i+1])
		  los valores negativos estan en complemento a 2
		 */
	public short[] convertirByteAShort(byte[] bytes){
		
		short[] arrayShort = new short[bytes.length/2]; 
		
		if(formato==true){  // si el formato es BigEndian
		
		int temp = 0x00000000;
		for (int i = 0, j = 0; j < arrayShort.length ; j++, temp = 0x00000000) {
		//	System.out.print("i = "+ i);System.out.print("j = "+ j);System.out.println("temp = "+ temp);
			temp=(int)bytes[i++]<<8;//System.out.println("temp = "+ temp);
			temp |= (int) (0x000000FF & bytes[i++]);
			//System.out.println("temp despues de las operaciones"+temp);	
		  arrayShort[j]=(short)temp;
	//	  System.out.println("short"+j+" "+arrayShort[j]);	
		}
		return arrayShort;
		
		}
		if(formato==false){  // si el formato es littleEndian	
		int temp = 0x00000000;
		for (int i = 0, j = 0; j < arrayShort.length ; j++, temp = 0x00000000) {
		//	System.out.print("i = "+ i);System.out.print("j = "+ j);System.out.println("temp = "+ temp);
			temp=(int)bits[i+1]<<8;//System.out.println("temp = "+ temp);
			temp |= (int) (0x000000FF & bits[(i)]);
		   i=i+2;
			System.out.println("i"+i);
		}
		return arrayShort;
		
		}
		
		else{
			System.out.println("orden de Bytes desconocido o no soportado");
			}
	    
	    return arrayShort;
		}	
 
 	/**********/
 	public double[] convertirByteADouble(){
		
		double[] arrayDouble = new double[bits.length/2]; 
		
		if (formato==true){
		int temp = 0x00000000;
		for (int i = 0, j = 0; j < arrayDouble.length ; j++, temp = 0x00000000) {
		//	System.out.print("i = "+ i);System.out.print("j = "+ j);System.out.println("temp = "+ temp);
			temp=(int)bits[i++]<<8;//System.out.println("temp = "+ temp);
			temp |= (int) (0x000000FF & bits[i++]);
			//System.out.println("temp despues de las operaciones"+temp);	
		  arrayDouble[j]=(double)temp;
		//  System.out.println("Double"+j+" "+arrayDouble[j]);	
		}
		return arrayDouble;
		}
		/**/
		if(formato==false){  // si el formato es littleEndian
		int temp = 0x00000000;
		for (int i = 0, j = 0; j < arrayDouble.length ; j++, temp = 0x00000000) {
		//	System.out.print("i = "+ i);System.out.print("j = "+ j);System.out.println("temp = "+ temp);
			temp=(int)bits[i+1]<<8;//System.out.println("temp = "+ temp);
			temp |= (int) (0x000000FF & bits[(i)]);
		   i=i+2;
		//	System.out.println("i"+i);	
		  arrayDouble[j]=(double)temp;
		//  System.out.println("Double"+j+" "+arrayDouble[j]);	
		//calcular mayor y menor esto me servira para establecer
		//los parametros en el eje y para la grafica
		   if(mayor<arrayDouble[j]){
		   	mayor=arrayDouble[j];
		   	}
		   	if(menor>arrayDouble[j]){
		   	menor=arrayDouble[j];
		   	}
		
		}
		return arrayDouble;
		}
		
		else{
			System.out.println("orden de Bytes desconocido o no soportado");
			
			}
		
		return arrayDouble;
		/**/
		}	
		
	public double obtenerMayor(){
		
		System.out.println("mayor = "+mayor+"  menor = "+menor);
		if(mayor>=-menor){
		
		return mayor;
		}
		else{
			return -menor;
			}
		}	
	
	//graba las plantillas en disco , las plantillas contienen 
	//la matriz de coeficientes 
	public static void archivarPlantilla(plantilla p){
		try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(p.nombre+".lorito"));
 //           salida.writeObject("guardar este string y un objeto\n");
            salida.writeObject(p);
            salida.close();
			System.out.println("el archivo "+p.nombre+" ha sido guardado con exito...");
//se puede fundir en una catch Exception
        }catch (IOException ex) {
            System.out.println(ex);
         }
       
		}
	
	public static plantilla leerPlantilla(String Nombre){
			
			plantilla obj1=new plantilla();
			try  {
           ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(Nombre));
   //         String str=(String)entrada.readObject();
            obj1=(plantilla)entrada.readObject();
            System.out.println("nombre "+obj1.nombre);
            System.out.println("-----------------------------");
            System.out.println("matriz");
            obj1.imprimirMatriz();
            System.out.println("-----------------------------");
            System.out.println(obj1);
            System.out.println("-----------------------------");
            entrada.close();
            
            return obj1;
            
//se puede fundir en una catch Exception
        }catch (IOException ex) {
            System.out.println(ex);
         }catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }	
				return obj1;
		}
		
    public void archivarBytes(byte[] bits, String nombreArchivo) {
    	
    	try{
    	System.out.println("archivando bytes...");	
    	FileOutputStream archivo = new FileOutputStream(nombreArchivo);
    	archivo.write(bits);
    	archivo.close();
    	
    	}catch(Exception e){
				System.out.println(e);
				System.out.println("error al archivar byte");
				System.exit(0);
				}
    	
    	}

	
	
	
	public byte[] leerBytes(String nombreArchivo){
		try{
			
			System.out.println("leyendo bytes...");	
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			FileInputStream deArchivo =new FileInputStream(nombreArchivo);
			
			System.out.println("tamaño disponible..."+deArchivo.available());	
			byte []buffer =new byte[deArchivo.available()/2];
			
			while((deArchivo.read(buffer,0,buffer.length))!=-1){
	
					byteArrayOutputStream.write(buffer);
				}
				
			byteArrayOutputStream.close();
			deArchivo.close();
			
			return byteArrayOutputStream.toByteArray();
			
			}catch(Exception e){
				System.out.println(e);
				System.out.println("error al mostrar byte");
				System.exit(0);
				}
			return null;
		}
		
		

/*	public static void main (String args[]){
		
		
		Datos dato =new Datos(10);
		dato.llenarByte();
		dato.mostrarByte();
		short[] corto= dato.convertirByteAShort(dato.bits);
		dato.archivarBytes(dato.bits,"prueba");
		byte[] bit =dato.leerBytes("prueba");
		
		Datos bits = new Datos(bit.length);
		bits.llenarByte(bit);
		bits.mostrarByte();
	
	for (int i = 0; i < bit.length; i++) {
        
            System.out.print((byte)bit[i] + " ");
        }
	
    }
	*/
		}
		
	
	
	
	