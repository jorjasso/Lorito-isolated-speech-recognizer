//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

//para crear una nuevo archivo patronesPlantillas vacio
import java.io.*;
import java.util.*;  

public class nuevaPlantilla {
	
	//valores para nombre
	//plantillaMFCC
	//plantillaHaar
	//plantillaDb2
	//plantillaDb3
	//plantillaCoiflet
	//plantillaBSpline
	//plantillaWaveletPacketDb2
	//plantillaWaveletPacketWalsh
	//plantillaWaveletPacketDb3
	//plantillaWaveletPacketPSBC
	
    public static void main(String[] args) {
    	
    	String nombre ="plantillas";
        patronesPlantillas p= new patronesPlantillas(nombre);

        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
            salida.writeObject(p);
            salida.close();
            System.out.println("archivo de patronesplantillas creado");
            p.mostrarPatronesPlantillas();
        }catch (IOException ex) {
            System.out.println(ex);
         }
        /*******************/
    	
    	
/* 	    String nombre ="plantillaMFCC";
        patronesPlantillas p= new patronesPlantillas(nombre);

        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
            salida.writeObject(p);
            salida.close();
            System.out.println("archivo de patronesplantillas creado");
            p.mostrarPatronesPlantillas();
        }catch (IOException ex) {
            System.out.println(ex);
         }
         
         
         /*********/
/*         nombre ="plantillaHaar";
         p= new patronesPlantillas(nombre);

        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
            salida.writeObject(p);
            salida.close();
            System.out.println("archivo de patronesplantillas creado");
            p.mostrarPatronesPlantillas();
        }catch (IOException ex) {
            System.out.println(ex);
         }
         /*********/
         /*********/
/*         nombre ="plantillaDb2";
         p= new patronesPlantillas(nombre);

        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
            salida.writeObject(p);
            salida.close();
            System.out.println("archivo de patronesplantillas creado");
            p.mostrarPatronesPlantillas();
        }catch (IOException ex) {
            System.out.println(ex);
         }
         /*********/
         /*********/
/*         nombre ="plantillaDb3";
         p= new patronesPlantillas(nombre);

        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
            salida.writeObject(p);
            salida.close();
            System.out.println("archivo de patronesplantillas creado");
            p.mostrarPatronesPlantillas();
        }catch (IOException ex) {
            System.out.println(ex);
         }
         /*********//*********/
/*         nombre ="plantillaCoiflet";
         p= new patronesPlantillas(nombre);

        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
            salida.writeObject(p);
            salida.close();
            System.out.println("archivo de patronesplantillas creado");
            p.mostrarPatronesPlantillas();
        }catch (IOException ex) {
            System.out.println(ex);
         }
         /*********//*********/
/*         nombre ="plantillaBSpline";
         p= new patronesPlantillas(nombre);

        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
            salida.writeObject(p);
            salida.close();
            System.out.println("archivo de patronesplantillas creado");
            p.mostrarPatronesPlantillas();
        }catch (IOException ex) {
            System.out.println(ex);
         }
         /*********//*********/
/*         nombre ="plantillaWaveletPacketDb2";
         p= new patronesPlantillas(nombre);

        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
            salida.writeObject(p);
            salida.close();
            System.out.println("archivo de patronesplantillas creado");
            p.mostrarPatronesPlantillas();
        }catch (IOException ex) {
            System.out.println(ex);
         }
         /*********//*********/
/*         nombre ="plantillaWaveletPacketWalsh";
         p= new patronesPlantillas(nombre);

        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
            salida.writeObject(p);
            salida.close();
            System.out.println("archivo de patronesplantillas creado");
            p.mostrarPatronesPlantillas();
        }catch (IOException ex) {
            System.out.println(ex);
         }
         /*********//*********/
/*         nombre ="plantillaWaveletPacketDb3";
         p= new patronesPlantillas(nombre);

        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
            salida.writeObject(p);
            salida.close();
            System.out.println("archivo de patronesplantillas creado");
            p.mostrarPatronesPlantillas();
        }catch (IOException ex) {
            System.out.println(ex);
         }
         /*********//*********/
/*         nombre ="plantillaWaveletPacketPSBC";
         p= new patronesPlantillas(nombre);

        try  {
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(nombre+".lorito"));
            salida.writeObject(p);
            salida.close();
            System.out.println("archivo de patronesplantillas creado");
            p.mostrarPatronesPlantillas();
        }catch (IOException ex) {
            System.out.println(ex);
         }
         /*********/
         
         
         }
         }