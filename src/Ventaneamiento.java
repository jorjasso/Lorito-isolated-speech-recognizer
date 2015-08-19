
//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

//clase que computa la matriz de ventaneamiento
//utilizando alguna funcion ventana
//formula para calcular el numero de filas en la matriz
//  (tamanoArreglo-tamVentana+1)/pasoVentana + 1
//  (tamanoArreglo-512+1)/100 + 1 si tamVentana=512 y pasoVentana=100

class Ventaneamiento
{
    double [][] matriz; // para capturar las diferentes espectros por fila, segun el ventanamiento
    int tam;  //tama�o del arreglo
    int TV;   //tama�o de la ventana
    int F;    // tama�o de paso de la ventana
    int piezas; //numero de filas de la matriz

    int tipo;// tipo de ventana

    public Ventaneamiento(double [] datos)
    {
        tam=datos.length;
        TV= 512;   //512
        F = 100; //100
        tipo=0;//ventana hamming
        //piezas=(int)Math.ceil(((tam-512+1)/(double)100)+1)
        piezas=(int)Math.ceil(((tam-TV+1)/(double)F)+1);
        matriz = new double[piezas][];
        System.out.println("piezas"+piezas);
        matriz=obtenerMatrizVentanamiento(datos);
    }

    //constructor de la clase ventaneamiento
    //entran los datos, el tama�o de la ventana, el tama�o de paso
    //y el tipo de ventana, por default hamming
    public Ventaneamiento(double [] datos, int TV, int F,int tipo)
    {
        tam=datos.length;
        this.TV= TV;   //512
        this.F = F; //100
        this.tipo=tipo;
        //piezas=(int)Math.ceil(((tam-512+1)/(double)100)+1)
        piezas=(int)Math.ceil(((tam-TV+1)/(double)F)+1);
        matriz = new double[piezas][];
        System.out.println("piezas"+piezas);
        matriz=obtenerMatrizVentanamiento( datos);
    }

    public double [][] obtenerMatrizVentanamiento( double [] datos)
    {
        int a,b,j,k;
	j=0;k=0;
	a=0;b=TV;

        while(b<=tam)
        {
            matriz[j] = new double[TV];
            for (int i=a;i<b;i++)
            {
                //ventana hamming
		if(tipo==0)
                    matriz[j][k]=ventana.hammingIII(datos[i],TV,k);
		//ventana rectangular
                if(tipo==1)
                    matriz[j][k]=ventana.rectangular(datos[i]);
                k++;
            }
            a=a+F;
            b=b+F;
            j++;
            k=0;
        }
        if(b==tam)
            return matriz;
        if (b>tam)
        {
            matriz[j] = new double[TV];
            for (int i=a;i<b;i++)
            {
                if(i<tam)
                {
                    //ventana hamming
                    if(tipo==0)
                        matriz[j][k]=ventana.hammingIII(datos[i],TV,k);

                    //ventana rectangular
                    if(tipo==1)
                        matriz[j][k]=ventana.rectangular(datos[i]);
                }
                else
                {
                    //extrapolacion
                    matriz[j][k]=2*matriz[j][k-1]-matriz[j][k-2];
                }
                k++;
            }
        }
        return matriz;
    }
		
		
	public void imprimirMatriz(){
		
		for(int i=0;i<matriz.length;i++)	{
			for(int j=0;j<matriz[0].length;j++)	{
			
			System.out.print(matriz[i][j] + " ");
		}
		System.out.println();	
		}
		
	}
	
	public double [][] obtenerMatriz(){
		return matriz;
		}
	public double [] obtenerFilaMatriz(int fila){
		return matriz[fila];
		}
	public int devolverFilas(){
		return piezas;
		}	
		
	}
	
	
	