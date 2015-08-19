import java.io.*;
public class principal{
	
	 public static void main(String[] args) {
        // Create application frame.
    /*    PrincipalFrame frame = new PrincipalFrame();
        
        // Show frame
        frame.setVisible(true);
     */  
    /*  Complejo c1= new Complejo(3,4);
        Complejo c3= new Complejo(2,1);
        Complejo resultado=sumar(c1,c3);
        System.out.println(resultado.cadena());
        resultado=multiplicar(c1,c3);
        System.out.println(resultado.cadena());
        
        c1.conjugado();
        c3.conjugado();
     
        resultado=sumar(c1,c3);
        System.out.println(resultado.cadena());
        resultado=multiplicar(c1,c3);
        System.out.println(resultado.cadena());
        resultado.conjugado();
        System.out.println(resultado.cadena());
       
        
        double [] arreglo =new double[5];
        for(int i=0;i<5;i++){
        	arreglo[i]=i+1;
        	
        }
        
        arreglo=ventana.hamming(arreglo);
        
        
        for(int i=0;i<arreglo.length;i++){
        	
        	System.out.println(arreglo[i]);
        }
        
        Fourier f =new Fourier(arreglo);
        int potencia =f.Potencia();
        System.out.println(potencia);
        for(int i=0;i<f.arreglo.length;i++){
        	
        	System.out.println(f.arreglo[i]);
        }
        
        System.out.println("estado "+f.estado);
        System.out.println("tamaño "+f.tamano);
        
        f.FFTradix2DIF();
        
        double [] arreglo1= new double [4];
        arreglo1=f.modulo();
        
        for(int i=0;i<arreglo1.length;i++){
        	System.out.println(arreglo1[i]);
        	
        }
        */
   		/*para obtner los MFCC*/
   /*			int i=0;
   			int j=0;
        double [] arreglo =new double[7];
        for( i=0;i<7;i++){
        	arreglo[i]=i+1;
        	
        }
        for(  i=0;i<arreglo.length;i++){
        	System.out.println(arreglo[i]);
       	
        }
        
        arreglo = MFCC.coeficientesMFCC(32,arreglo,3);      
    */    /*para probar guardar y recuperar objetos en un disco*/
     /*   plantilla prueba=new plantilla();
        double [][] matriz=new double[7][7];
    	String nombre ;
    	 i=0; 
    	 j=0;
    	double a=0;
    	for(i=0;i<7;i++){
    		for(j=0;j<7;j++){
    			
    			matriz[i][j]=a+0.0075;
    			a++;
    		}
    	}
    	nombre = "plantilla uno";
        plantilla p1= new plantilla(nombre,matriz);
        Datos.archivarPlantilla(p1);
  //      prueba=Datos.leerPlantilla(nombre);
        
         a=0;
    	for(i=0;i<7;i++){
    		for(j=0;j<7;j++){
    			
    			matriz[i][j]=a+0.00001;
    			a++;
    		}
    	}
    	nombre = "plantilla dos";
        
        plantilla p2= new plantilla(nombre,matriz);
        Datos.archivarPlantilla(p2);
    //    prueba=Datos.leerPlantilla(nombre);
        
        a=0;
    	for(i=0;i<7;i++){
    		for(j=0;j<7;j++){
    			
    			matriz[i][j]=a-15.0007;
    			a++;
    		}
    	}
    	nombre = "plantilla tres";
        
        plantilla p3= new plantilla(nombre,matriz);
        Datos.archivarPlantilla(p3);
        
        /*lista los archivos de un directorio*/
     /*   File fichero=new File(".");
         String[] listaArchivos=fichero.list();
       /* 
       
        for( i=0; i<listaArchivos.length; i++){
            System.out.println(listaArchivos[i]);
        }
        */
        
        /*lista los archivos de un directorio*/
    /*    System.out.println(" ******* lista de los archivos con filtro *******");
        listaArchivos=fichero.list(new Filtro(".lorito"));
        for( i=0; i<listaArchivos.length; i++){
            System.out.println(listaArchivos[i]);
        }
        
        for( i=0; i<listaArchivos.length; i++){
            
            prueba=Datos.leerPlantilla(listaArchivos[i]);
        }
        
      */  
        
    /*eliminacion de segmentos inutiles*/
    
 /*   double [] datos =new double[30];
    int i;
    for( i=0; i<datos.length; i++){
            
            if(i<15)
            datos[i]=2*i;
            else
            datos[i]=datos.length;
        }
        
       for( i=0; i<datos.length; i++){
            
        System.out.print(" "+datos[i]);
        }
         
        
     System.out.println("datos1");
    double [] datos1;
    
        datos1=preprocesamiento.eliminarSegmentosInutiles(datos);
        
    //   datos1=preprocesamiento.recorte(datos,10,15) ;
        for( i=0; i<datos1.length; i++){
            
        System.out.print(" "+datos1[i]);
        }
   */     
       /*arreglo = MFCC.DTCII(arreglo);
        for(int i=0;i<arreglo.length;i++){
        	System.out.println(arreglo[i]);
        	
        }
 //***********prueba de DWT*************/       
      ///double [][] A={{20,20},{30,30}};
  //      double [][] A={{20,20,20,20},{20,20,30,40},{30,30,23,45}};
  //      double [][] B={{20,20,20,20},{20,20,30,40},{30,30,23,45}};
        
        
   //     double C =DynamicTypeWarping.matching(A,B);
  //      	   C =DynamicTypeWarping.matching_p_1(A,B);	
  //   double   	   C =DynamicTypeWarping.simetrico_p_0(A,B);	
 //       	   C =DynamicTypeWarping.simetrico_p_1(A,B);	
        
       /* 
        /**************/
        int i=0,j=0;
        int I=15,J=15;
        int r=5;
        
        double [][]matriz=new double[I][J];
        
        for(i=0;i<matriz.length;i++){
        	for(j=0;j<matriz[0].length;j++){
        		matriz[i][j]=0;
        		}
        	}
       i=0;j=0;
       
        do{
			i=i+1;
			if(i>=j+r){
				j=j+1;
				if(j>=J){
					
				System.out.println(	"return suma/(I+J);");
				break;
					}
				else{
					i=j-r;
					}	
				
				}
			
			else{
				if(i>=0){
					if(i<I){
					matriz[i][j]=1;	
					//	calcular
						
						}//del if
					
				 }//de if
				
				}	//del else
			
			
			}while(true);
			
        for(i=0;i<matriz.length;i++){
        	for(j=0;j<matriz[0].length;j++){
        		System.out.print(" "+matriz[i][j]);
        		}
        		System.out.println(" ");
        	}
        
        
        /************/
        
   // }
   
  /* double [] arreglo =new double[5];
        for(int i=0;i<5;i++){
        	arreglo[i]=i+1;
        	System.out.print(arreglo[i]+" ");
        }
        System.out.println(" ");
       arreglo=ventana.hammingI(arreglo);
       for(int i=0;i<5;i++){
        
        	System.out.print(arreglo[i]+" ");
        }
    */    
    /*    Fourier f =new Fourier(arreglo);
        
       
        f.FFTradix2DIF();
        
        double [] arreglo1 ;
        arreglo1=f.moduloII();
        
        for(int i=0;i<arreglo1.length;i++){
        	System.out.println(arreglo1[i]);
        }	
      */ /*************//////
     /* 	System.out.println(" ");
        double [] arreglo1 =new double[16];
        for(int i=0;i<16;i++){
        	arreglo1[i]=i+1;
        	System.out.print(arreglo1[i]+" ");
       }*/ 
   /*   Ventaneamiento e=new Ventaneamiento(arreglo1);
      e.imprimirMatriz();  
    
     
     	reconocerFourier reconocedor=new reconocerFourier(arreglo1);
     	reconocedor.imprimirMatriz();
     	
    */  	
      
    /*  double [] fila=new double[5];
      double [] fila1=new double[5];
      double [] fila2=new double[5];
      double [] fila3=new double[5];
      double [] fila4=new double[5];
      double [] fila5=new double[5];
      double [] fila6=new double[5];
      
      
      fila=e.obtenerFilaMatriz(0);  
      fila1=e.obtenerFilaMatriz(1);  
      fila2=e.obtenerFilaMatriz(2);  
      fila3=e.obtenerFilaMatriz(3);  
      fila4=e.obtenerFilaMatriz(4);  
      fila5=e.obtenerFilaMatriz(5);  
      fila6=e.obtenerFilaMatriz(6);  
        System.out.println("imprimiemdo filas ");
        int i;
      for(i=0;i<fila.length;i++){
        	System.out.print(fila[i]+" ");
        }
        System.out.println(" ");
        
       for( i=0;i<fila1.length;i++){
        	System.out.print(fila1[i]+" ");
        }
        System.out.println(" ");
        
        for( i=0;i<fila2.length;i++){
        	System.out.print(fila2[i]+" ");
        }
        System.out.println(" ");
        
        for( i=0;i<fila3.length;i++){
        	System.out.print(fila3[i]+" ");
        }
        System.out.println(" ");
        
        for( i=0;i<fila4.length;i++){
        	System.out.print(fila4[i]+" ");
        }
        System.out.println(" "); 
        for( i=0;i<fila5.length;i++){
        	System.out.print(fila5[i]+" ");
        }
        System.out.println(" "); 
        for( i=0;i<fila6.length;i++){
        	System.out.print(fila6[i]+" ");
        }
        System.out.println(" "); 
    */  /**********/ 
	/*  double aa=3.0;
		double a=3.1;
		double b=3.5;
		double c=3.9;
		double cc=4.0;
		int tam=16000;
			
		System.out.println(aa);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(cc);
		System.out.println("");
		System.out.println(Math.ceil(aa));
		System.out.println(Math.ceil(a));
		System.out.println(Math.ceil(b));
		System.out.println(Math.ceil(c));
		System.out.println(Math.ceil(cc));
		System.out.println((((tam-512+1)/(double)100)+1));
		System.out.println((int)Math.ceil(((tam-512+1)/(double)100)+1));
	*/
	}
	}