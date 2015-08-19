//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;

class pantalla{
	
	JPanel panelPrincipal;
	JPanel panel;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;
	double [][]matriz;// matriz con los coeificientes wavelet a cada escala
	
	public pantalla(double [][]matriz){	
	
		panelPrincipal = new JPanel(new GridLayout(0,1));
		panel1=new JPanel();
		panel2=new JPanel();
		panel3=new JPanel();
		panel4=new JPanel();
	/*	agregarComponentes();
		panelPrincipal.add(panel1);
		panelPrincipal.add(panel2);
		panelPrincipal.add(panel3);
		panelPrincipal.add(panel4);		
	*/	this.matriz=matriz;
	
		for(int i=0;i<9;i++){
		panel = 	new JPanel();
		crearPanel(matriz[i]);
		panelPrincipal.add(panel);
			
			}
	
		}
	
	public void agregarComponentes(){
		crearPanel1();
		crearPanel2();
		crearPanel3();
		crearPanel4();
		}
		
		
	public void crearPanel(double []datos){
		Graficar canvas1= new Graficar(0);
		canvas1.tamano(750,70);
		canvas1.graficarDatos(datos,obtenerMayor(datos));
		panel.add(canvas1);
		
		}
			
	public void crearPanel1(){
		Graficar canvas1= new Graficar(0);
		canvas1.tamano(750,70);
		canvas1.graficarDatos(f1(),obtenerMayor(f1()));
		panel1.add(canvas1);
		
		}	
	public void crearPanel2(){
		Graficar canvas2= new Graficar(0);
		canvas2.tamano(750,70);
		canvas2.graficarDatos(f2(),obtenerMayor(f2()));
		panel2.add(canvas2);
				}	
		
 	public void crearPanel3(){
		Graficar canvas3= new Graficar(0);
		canvas3.tamano(750,70);
		canvas3.graficarDatos(f3(),obtenerMayor(f3()));
		panel3.add(canvas3);
		}		
		
	public void crearPanel4(){
		Graficar canvas4= new Graficar(0);
		canvas4.tamano(750,70);
		canvas4.graficarDatos(f4(),obtenerMayor(f4()));
		panel4.add(canvas4);
		}			

	public double[] f1(){
		
		double [] funcion=new double[10000];
		for(int i=0;i<10000;i++){
		//	funcion[i]=i*Math.cos(2*Math.PI*30*i/10000);
		funcion[i]=5*Math.cos(2*Math.PI*3*i/10000-Math.PI/2);
	//		System.out.println("f1"+funcion[i]);
		}
		return funcion;
		}
		
		
	public double[] f2(){
		
		double [] funcion=new double[10000];
		for(int i=0;i<10000;i++){
			funcion[i]=5*Math.cos(2*Math.PI*7*i/10000);
			//funcion[i]= 5*Math.cos(2*Math.PI*3*i/10000+Math.PI/2)+5*Math.cos(2*Math.PI*7*i/10000)+5*Math.cos(2*Math.PI*70*i/10000+Math.PI/2);
	//		System.out.println(funcion[i]);
		}
		return funcion;
		}
	
	public double[] f3(){
		
		double [] funcion=new double[10000];
		for(int i=0;i<10000;i++){
			funcion[i]=i*Math.cos(2*Math.PI*70*i/10000+Math.PI/2);
		//	funcion[i]= i*Math.cos(2*Math.PI*200*i/10000)+i*Math.cos(2*Math.PI*10*i/10000);
			
		}
		return funcion;
		}
		
	public double[] f4(){
		
		double [] funcion=new double[10000];
		for(int i=0;i<10000;i++){
			funcion[i]= 5*Math.cos(2*Math.PI*3*i/10000-Math.PI/2)+5*Math.cos(2*Math.PI*7*i/10000)+5*Math.cos(2*Math.PI*70*i/10000+Math.PI/2);
			//funcion[i]= 100*Math.cos(2*Math.PI*20*i/10000);
			
		}
		return funcion;
		}
	
	public double obtenerMayor(double[] datos){
		double mayor,menor;
		mayor = 0;
		menor =1000;
		for(int i=0;i<datos.length;i++){
			if(mayor<datos[i]){
				mayor=datos[i];
				}
			if(menor>datos[i]){
				menor=datos[i];
				}	
			
		}
		
		if(mayor>-menor){
			return mayor;
			
			}
		else {
			return -menor;
			}	
		
		}
	
	
/*	private static void crearMostrarGUI() {
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        pantalla ventana = new pantalla();
        JFrame frame = new JFrame("pantalla de prueba");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setContentPane(ventana.panelPrincipal);
        frame.pack();
        frame.setVisible(true);
    }
    
     public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                crearMostrarGUI();
            }
        });
    }
*/
	}