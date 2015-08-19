//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

import java.awt.*;
import java.awt.geom.*;
class Graficar extends Canvas{
	
	//factores escala
	double escalaX, escalaY;
	double xMax,xMin,yMax,yMin;
	double xMaxtem,xMintem,yMaxtem,yMintem; //para resetear
	int ancho, altura;
	double[] datos;
	double xEspacioMarcas ;
	double yEspacioMarcas ;
	double xIntervaloMarcas;
	double yIntervaloMarcas;
	
    double [][]matriz;
    int [][]matrizColores;
	
	int tipoGrafica;
	
	public Graficar(int opcion){ //entra una opcion de grafica y el voctor de grafica
		this.setBackground(Color.white);   
		tipoGrafica=opcion;
		}
		
	public Graficar(int opcion, double[][] matriz){ //entra una opcion de grafica y una matriz de grafica
	    
	//	this.setSize(100,50);
		this.setBackground(Color.white);
		this.matriz=matriz;
		tipoGrafica=opcion;
		}	
	
	public double obtenerxMax(){
		return xMax;
		}
	
	public void establecerxMax(double xMax){
		this.xMax=xMax;
		}
		
	public double obteneryMax(){
		return yMax;
		}
	
	public void estableceryMax(double yMax){
		this.yMax=yMax;
		}	
		
	public double obtenerxMin(){
		return xMin;
		}
	
	public void establecerxMin(double xMin){
		this.xMin=xMin;
		}
		
	public double obteneryMin(){
		return yMin;
		}
	
	public void estableceryMin(double yMin){
		this.yMin=yMin;
		}	
			
	public void graficarDatos(double[]datos,double mayor){
		this.datos=datos;
		xMin=0.0;
		xMax=datos.length;
		yMax=mayor;
		
		if(tipoGrafica==1) yMin=3;//para fourier
		else yMin=-mayor;
		ancho = this.getWidth();
        altura =this.getHeight();
        xEspacioMarcas = (yMax-yMin)/50;  
		yEspacioMarcas = (xMax-xMin)/50;
		xIntervaloMarcas=xMax/100;
		yIntervaloMarcas=2*mayor/10;
//        System.out.println("xMax = "+xMax+" YMax = "+yMax+" yMin = "+yMin+" ancho = "+ancho+" altura = "+altura);
        xMintem=xMin;
		xMaxtem=xMax;
		yMaxtem=mayor;
		yMintem=-mayor;
        
    	}
    	
    	//entrada matriz espectros y mayor valor presente en
    	//el espectograma
    public void graficarDatos(double[][]datos, double mayor){
		this.matriz=datos;
		xMin=0.0;
		xMax=datos.length;;  // filas tiempo 
		yMax=datos[0].length;// columnas frecuencia
		
		if(tipoGrafica==2) yMin=3;//para fourier
		else yMin=-mayor;
		ancho = this.getWidth();
        altura =this.getHeight();
        xEspacioMarcas = (yMax-yMin)/50;  
		yEspacioMarcas = (xMax-xMin)/50;
		xIntervaloMarcas=xMax/100;
		yIntervaloMarcas=2*yMax/10;
  //      System.out.println("xMax = "+xMax+" YMax = "+yMax+" yMin = "+yMin+" ancho = "+ancho+" altura = "+altura);
        xMintem=xMin;
		xMaxtem=xMax;
		yMaxtem=mayor;
		yMintem=-mayor;
		
		matrizColores= new int[datos.length][datos[0].length];
		int mayor1=0;
		int menor=100;
		int valor;
		for (int i=0; i<datos.length; i++) {
	    	for (int j=0; j<datos[0].length; j++) {
	    	// se asigna su equivalente en colores el espectograma
	    	// espectograma= cuadrado del modulo	
			valor = (int)(255*(Math.log(matriz[i][j]*matriz[i][j]))/(Math.log(mayor*mayor)));
			if(valor>0) matrizColores[i][j]=valor;
			if(valor<=0) matrizColores[i][j]=0;
			if (mayor1<matrizColores[i][j]) mayor1=matrizColores[i][j];
			if (menor>matrizColores[i][j]) menor=matrizColores[i][j];
			
//			System.out.println("matrizColores[i][j] "+matrizColores[i][j]);
	    		}
		}
//        System.out.println("menor y mayor "+menor+" "+mayor1);
    	}	
		
	public void resetear(){
		
		System.out.println("tipo grafica"+tipoGrafica);
		xMin=xMintem;
		xMax=xMaxtem;
		yMax=yMaxtem;
		yMin=yMintem;
		ancho = this.getWidth();
        altura =this.getHeight();
        xEspacioMarcas = (yMax-yMin)/50;  
		yEspacioMarcas = (xMax-xMin)/50;
		xIntervaloMarcas=xMax/100;
		yIntervaloMarcas=2*yMax/10;
		if (tipoGrafica==1){tipoGrafica=1;yMin=3;}
		
		}	
	public void tamano(int ancho, int altura){
		this.setSize(ancho,altura);
		}
		
	public int obtenerx(double x){
		int valx=(int)(x*escalaX);
		return valx;
		}	
		
	public int obtenery(double y){	
		double valory = (yMax+yMin)-y;
    	int valy = (int)(valory*escalaY);
    	return valy;		
	    }	
	    
	public void dibujarEjes(Graphics g){
		
		g.setColor(Color.LIGHT_GRAY);
    	g.drawString("" + (int)xMin, obtenerx(xMin),obtenery(xEspacioMarcas/2)-2);
    	g.drawString("" + (int)yMin,obtenerx(yEspacioMarcas/2)+2,obtenery(yMin));
    	int xMaxInt = (int)xMax;
    	String xMaxStr = "" + xMaxInt;
    	char[] array = xMaxStr.toCharArray();
	    FontMetrics fontMetrics =g.getFontMetrics();
    	Rectangle2D r2d =fontMetrics.getStringBounds(array,0,array.length,g);
    	int labWidth = (int)(r2d.getWidth());
    	int labHeight =(int)(r2d.getHeight());
	    g.drawString("" + (int)xMax,obtenerx(xMax)-labWidth,obtenery(xEspacioMarcas/2)-2);
	    g.drawString("" + (int)yMax,obtenerx(yEspacioMarcas/2)+2,obtenery(yMax)+labHeight);
		g.drawLine(obtenerx(xMin),obtenery(0.0),obtenerx(xMax),obtenery(0.0));
    	g.drawLine(obtenerx(0.0),obtenery(yMin),obtenerx(0.0),obtenery(yMax));

	xDivisiones(g);
    yDivisiones(g);
  }

  
  void xDivisiones(Graphics g){
    double xDoub = 0;
    int x = 0;
    int topEnd = obtenery(xEspacioMarcas/2);
    int bottomEnd = obtenery(-xEspacioMarcas/2);	   
	    if(topEnd < 5){
	      topEnd = 5;
	      bottomEnd = -5;
	    }
	    while(xDoub < xMax){
	      x = obtenerx(xDoub);
	      g.drawLine(x,topEnd,x,bottomEnd);
	      xDoub += xIntervaloMarcas;
	    }
	    xDoub = 0;
	    while(xDoub > xMin){
	      x = obtenerx(xDoub);
	      g.drawLine(x,topEnd,x,bottomEnd);
	      xDoub -= xIntervaloMarcas;
	    }

  }



  void yDivisiones(Graphics g){
    double yDoub = 0;
    int y = 0;
    int rightEnd = obtenerx(yEspacioMarcas/2);
    int leftEnd = obtenerx(-yEspacioMarcas/2);

	    while(yDoub < yMax){
	      y = obtenery(yDoub);
	      g.drawLine(rightEnd,y,leftEnd,y);
	      yDoub += yIntervaloMarcas;
	    }
	
	    yDoub = 0;
	    while(yDoub > yMin){
	      y = obtenery(yDoub);
	      g.drawLine(rightEnd,y,leftEnd,y);
	      yDoub -= yIntervaloMarcas;
	    }

  }  
	    
	public void paint(Graphics g){
		
	try{
		 int x1,y1,x2,y2;
		 double valorx;
		 escalaX = ancho/(xMax-xMin);
    	 escalaY = altura/(yMax-yMin);
    	 
    	 
    if(tipoGrafica==0||tipoGrafica==1){
    
    	//establece el origen de coordenadas
    	if(tipoGrafica==0)
    	  	g.translate((int)((0-xMin)*escalaX),(int)((0-yMin)*escalaY));
    	 if(tipoGrafica==1) //grafica para fourier
    	 	g.translate((int)((0-xMin)*escalaX),(int)((0-yMin)*escalaY));
    	  dibujarEjes(g);
   /* 	  if(tipoGrafica==0)	
    	  	g.setColor(Color.red);
    	  	if(tipoGrafica==1) //grafica para fourier
    	  	g.setColor(Color.yellow);
   	*/	 g.setColor(Color.blue);
   		 x2=(int)xMin;
   		 y2=0;
   		 x1=obtenerx(x2);
   		 y1=obtenery(datos[0]);
   		 valorx=x2;
   			 
   		while(valorx<xMax){
   			
   		 y2=obtenery(datos[(int)valorx]);
      	 x2=obtenerx(valorx);

      	 g.drawLine(x1,y1,x2,y2);
	      //incrementa a travez del eje de las x
	      valorx += 1;
	      //guarda el punto para usarlo como punto de inicio para dibujar el siguiente segmento de linea
	      x1 = x2;
	      y1 = y2;	
   			}
   		
		}//de if(tipoGrafica==0||tipoGrafica==1)
		
	 if(tipoGrafica==2){ //dibujar espectro
	 
	     g.translate((int)((0-xMin)*escalaX),(int)((0-yMin)*escalaY));
	     dibujarEjes(g);
         Dimension dim = this.getSize();
		 int ancho = dim.width;
		 int alto = dim.height;
		 
		 x2=(int)xMin;
   		 y2=0;
   		 x1=obtenerx(x2);
   		 y1=obtenery(0);
   		 valorx=x2;
		 
		 
	    Graphics2D g2 = (Graphics2D) g; 
	    for (int i=0; i<xMax; i++) {
//	    		System.out.println("antesssssss");
	    	x1=obtenerx(i);
//	    	System.out.println("x1 "+x1);
	    	for (int j=0; j<yMax; j++) {
	    		y1=obtenery(j);
//	    		System.out.println("y1 "+y1);
//	    		System.out.println("y1 "+y1);
//	    		System.out.println("dibujando "+i+" "+j+" "+x1+" "+y1);
//	    		System.out.println("matrizColores[i][j]] "+matrizColores[i][j]);
//	    		System.out.print("-"+tablaColor[matrizColores[i][j]]);
		 g2.setColor(tablaColor[matrizColores[i][j]]);
		 
		 
	if(ancho>xMax)g2.fillRect(x1,y1,(int)ancho/((int)xMax)+1,1);
	else          g2.fillRect(x1,y1, 1,1);
		 
 	     }		
   		}
	   }
		
	}
	
	catch(Exception e){
		
		}	
		
	}
	     
	     
//	 matrizEspectroFourier/
		
	
	
	final static Color tablaColor[] = {
	new Color(  0, 172, 252),
	new Color(  0 , 172,  252    ),
	new Color(  0, 172, 252	 ),
	new Color(  0, 168, 252	 ),
	new Color(  0, 164, 252	 ),
	new Color(  0, 160, 252	 ),
	new Color(  0, 152, 252	 ),
	new Color(  0, 152, 252	 ),
	new Color(  0, 148, 252	 ),
	new Color(  0, 148, 252	 ),
	new Color(  0, 144, 252	 ),
	new Color(  0, 140, 252	 ),
	new Color(  0, 136, 252	 ),
	new Color(  0, 132, 252	 ),
	new Color(  0, 132, 252	 ),
	new Color(  0, 128, 252	 ),
	new Color(  0, 124, 252	 ),
	new Color(  0, 120, 252	 ),
	new Color(  0, 116, 252	 ),
	new Color(  0, 112, 252	 ),
	new Color(  0, 112, 252	 ),
	new Color(  0, 108, 252	 ),
	new Color(  0, 104, 252	 ),
	new Color(  0, 100, 252	 ),
	new Color(  0,  96, 252	 ),
	new Color(  0, 92, 252	 ),
	new Color(  0, 92, 252	 ),
	new Color(  0, 88, 252	 ),
	new Color(  0, 84, 252	 ),
	new Color(  0, 80, 252	 ),
	new Color(  0, 8 , 252	 ),
	new Color(  0 , 4 , 252	 ),
	new Color(  0, 4 , 252	 ),
	new Color(  4,  4, 248	 ),
	new Color(  4,  4, 248	 ),
	new Color(  8,  4, 244	 ),
	new Color(  8,  8, 240	 ),
	new Color( 12,  8, 236	 ),
	new Color( 16,  8, 232	 ),
	new Color( 16, 12, 232	 ),
	new Color( 20, 12, 228	 ),
	new Color( 20, 12, 224	 ),
	new Color( 24, 12, 224	 ),
	new Color( 24, 16, 220	 ),
	new Color( 28, 16, 216	 ),
	new Color( 28, 16, 216	 ),
	new Color( 32, 16, 212	 ),
	new Color( 32 ,20, 212	 ),
	new Color( 36,20, 208	 ),
	new Color( 32,20, 204	 ),
	new Color( 40,20, 204	 ),
	new Color( 40,20, 200	 ),
	new Color(  44,24,196	 ),
	new Color(  44,24,196	 ),
	new Color(  48,24,192	 ),
	new Color(  48,24,188	 ),
	new Color(  52, 28, 188	 ),
	new Color(  52, 28, 184	 ),
	new Color(  56, 28, 180	 ),
	new Color(  56, 28, 180	 ),
	new Color(  60, 32, 176	 ),
	new Color(  60, 32, 172	 ),
	new Color(  64, 32, 172	 ),
	new Color(  64, 32, 168	 ),
	new Color(  68, 36, 168	 ),
	new Color(  68, 36, 164	 ),
	new Color(  72, 36, 160	 ),
	new Color(  72, 36, 160	 ),
	new Color(  76, 40, 156	 ),
	new Color(  76, 40, 152	 ),
	new Color(  80, 40, 152	 ),
	new Color(  80, 40, 148	 ),
	new Color(  84, 44, 144	 ),
	new Color(  84, 44, 144	 ),
	new Color(  88, 44, 140	 ),
	new Color(  88, 44, 136	 ),
	new Color(  92, 48, 136	 ),
	new Color(  92, 48, 132	 ),
	new Color(  96, 48, 128	 ),
	new Color(  100, 48, 128	 ),
	new Color(  100, 48, 128	 ),
	new Color(  104, 52, 124	 ),
	new Color(  104, 52, 120	 ),
	new Color(  108, 52, 116	 ),
	new Color(  108, 52, 116	 ),
	new Color(  112, 56, 112	 ),
	new Color(  112, 56, 108	 ),
	new Color(  116, 56, 108	 ),
	new Color(  116, 56, 104	 ),
	new Color(  120, 60, 100	 ),
	new Color(  120, 60, 100	 ),
	new Color(  124, 60, 96	 ),
	new Color(  124, 60, 92	 ),
	new Color(  128, 64, 92	 ),
	new Color(  128, 64, 88	 ),
	new Color(  132, 64, 88	 ),
	new Color(  132, 54, 84	 ),
	new Color(  136, 68, 80	 ),
	new Color(  136, 68, 80	 ),
	new Color(  140, 68, 76 	 ),
	new Color(  140, 68, 72 	 ),
	new Color(  144, 72, 72	 ),
	new Color(  144, 72 , 68	 ),
	new Color(  148, 72, 64	 ),
	new Color(  148, 72, 64	 ),
	new Color(  152, 72, 60	 ),
	new Color(  152, 76, 56	 ),
	new Color(  156, 76, 56	 ),
	new Color(  156, 76, 52	 ),
	new Color(  160, 76, 48	 ),
	new Color(  164, 80, 44	 ),
	new Color(  164, 80, 44	 ),
	new Color(  168, 80, 40	 ),
	new Color(  168, 84, 36	 ),
	new Color(  172, 84, 36	 ),
	new Color(  172, 84, 32	 ),
	new Color(  176, 84, 28	 ),
	new Color(  176, 88, 28	 ),
	new Color(  180, 88, 24	 ),
	new Color(  180, 88, 20	 ),
	new Color(  184, 88, 20	 ),
	new Color(  184, 92, 16	 ),
	new Color(  188, 92, 12	 ),
	new Color(  188, 92, 12	 ),
	new Color(  192, 92, 8	 ),
	new Color(  196, 96, 4	 ),
	new Color(  196, 96, 4	 ),
	new Color(  196, 100, 4	 ),
	new Color(  196, 100, 4	 ),
	new Color(  196, 104, 4	 ),
	new Color(  200, 108, 4	 ),
	new Color(  200, 108, 4	 ),
	new Color(  200, 112, 4	 ),
	new Color(  200, 112, 4	 ),
	new Color(  200, 116, 4	 ),
	new Color(  204, 120, 4	 ),
	new Color(  204, 120, 4	 ),
	new Color(  204, 124, 4	 ),
	new Color(  204, 124, 4	 ),
	new Color(  208, 128, 4	 ),
	new Color(  208, 132, 4	 ),
	new Color(  208, 132, 4	 ),
	new Color(  208, 136, 4	 ),
	new Color(  208, 136, 4 	 ),
	new Color(  212, 140, 4	 ),
	new Color(  212, 144, 4	 ),
	new Color(  212, 144, 4	 ),
	new Color(  212, 148, 4	 ),
	new Color(  216, 152, 4	 ),
	new Color(  216, 152, 4	 ),
	new Color(  216, 156, 4	 ),
	new Color(  216, 156, 4	 ),
	new Color(  216, 160, 4	 ),
	new Color(  200, 164, 4	 ),
	new Color(  200, 164, 4	 ),
	new Color(  200, 168, 4	 ),
	new Color(  200, 168, 4	 ),
	new Color(  224, 172, 4	 ),
	new Color(  224, 176, 4	 ),
	new Color(  224, 176, 4	 ),
	new Color(  224, 180, 4	 ),
	new Color(  224, 180, 4	 ),
	new Color(  228, 184, 4	 ),
	new Color(  228, 188, 4	 ),
	new Color(  228, 188, 4	 ),
	new Color(  228, 192, 4	 ),
	new Color(  228, 192, 4	 ),
	new Color(  232, 196, 4	 ),
	new Color(  232, 200, 4	 ),
	new Color(  232, 200, 4	 ),
	new Color(  232, 204, 4	 ),
	new Color(  236, 208, 4	 ),
	new Color(  236, 208, 4	 ),
	new Color(  236, 212, 4	 ),
	new Color(  236, 212, 4	 ),
	new Color(  236, 216, 4	 ),
	new Color(  240, 220, 4	 ),
	new Color(  240, 220, 4	 ),
	new Color(  240, 224, 4	 ),
	new Color(  240, 224, 4	 ),
	new Color(  244, 228, 4	 ),
	new Color(  244, 232, 4	 ),
	new Color(  244, 232, 4	 ),
	new Color(  244, 236, 4	 ),
	new Color(  244, 232, 4	 ),
	new Color(  248, 240, 4	 ),
	new Color(  248, 244, 4	 ),
	new Color(  248, 244, 4	 ),
	new Color(  248, 248, 4	 ),
	new Color(  252, 252, 0	 ),
	new Color(  252, 252, 104 ),
	new Color(  252, 252, 104 ),
	new Color(  252, 252, 108 ),
	new Color(  252, 252, 112 ),
	new Color(  252, 252, 116 ),
	new Color(  252, 252, 120 ),
	new Color(  252, 252, 120 ),
	new Color(  252, 252, 124 ),
	new Color(  252, 252, 128 ),
	new Color(  252, 252, 132 ),
	new Color(  252, 252, 136 ),
	new Color(  252, 252, 136 ),
	new Color(  252, 252, 140 ),
	new Color(  252, 252, 144 ),
	new Color(  252, 252, 148 ),
	new Color(  252, 252, 152 ),
	new Color(  252, 252, 152 ),
	new Color(  252, 252, 156 ),
	new Color(  252, 252, 160 ),
	new Color(  252, 252, 164 ),
	new Color(  252, 252, 168 ),
	new Color(  252, 252, 168 ),
	new Color(  252, 252, 176 ),
	new Color(  252, 252, 180 ),
	new Color(  252, 252, 184 ),
	new Color(  252, 252, 188 ),
	new Color(  252, 252, 192 ),
	new Color(  252, 252, 196 ),
	new Color(  252, 252, 200 ),
	new Color(  252, 252, 200 ),
	new Color(  252, 252, 204 ),
	new Color(  252, 252, 208 ),
	new Color(   252, 252, 212 ),
	new Color(   252, 252, 216 ),
	new Color(   252, 252, 216 ),
	new Color(  252, 252, 220 ),
	new Color(  252, 252, 224 ),
	new Color(  252, 252, 228 ),
	new Color(  252, 252, 232 ),
	new Color(  252, 252, 232 ),
	new Color(  252, 252, 236 ),
	new Color(  252, 252, 240 ),
	new Color(  252, 252, 244 ),
	new Color(  252, 252, 248 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252 ),
	new Color(  252, 252, 252  )};
    
		
		}
		
		
	