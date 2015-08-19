
//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.io.*;
import javax.swing.filechooser.*;

import javax.sound.sampled.*;

import java.awt.geom.*;
import javax.swing.border.*;


public class MenuRAH implements ActionListener, ItemListener {
	
    static private final String nuevaLinea = "\n";
    final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	
	String Nombrearchivoaudio;
	
	JFrame frame;
    
    JPanel panelPrincipal;
    JPanel panelCarga;
    JPanel panelVoz,panelEliminacionSegmentosInutiles,panelVozNormalizada; 
    JPanel panelCombox;
    JPanel PanelProcesamientoFourier,PanelProcesamientoFourierEspectro,PanelProcesamientoMFCC;
    JPanel panelWaveletHaar,panelDWT,panelWaveletDaubechies2,panelWaveletDaubechies3,panelWaveletCoiflet,panelWaveletBSpline,panelWaveletPacket,panelWaveletWalsh,panelWaveletPacketDb3,panelWaveletPacketPSBC;
    JPanel ProcesamientoSenal;//contiene 2 paneles uno con Fourier y Wavelets y otro con el comboBox
    JPanel esFourieroWavelets;//contiene los paneles Fourier y Wavelets;
    
    JTabbedPane tabbedPaneVoz,tabbedPaneFourier,tabbedPaneWavelet;
    
    
    JButton graficarBtn, graficarVNBtn,graficarESIBtn,graficarPSFBtn,graficarEspectroBtn;
    JButton resetearBtn,resetearVNBtn,resetearESIBtn,resetearPSFBtn;
    JTextArea log,salida,salidaHaar,salidaDaubechies2,salidaDaubechies3,salidaCoiflet,salidaBSpline,salidaWaveletPacket,salidaWalsh,salidaPacketDb3,salidaWaveletPacketPSBC;
    JFileChooser fc;
    Graficar canvas1,canvas2,canvas3,canvas4,canvas5,canvas6,canvas7,canvas8,canvas9,canvas10,canvas11,canvas12,canvas13,canvas14,canvas15;
  
//importante  : descomentar para agregar entradad de linea
    CapturarAudio capturarAudioVentana;

    ImageIcon icon;
    
    final static String FOURIER = "Procesamiento de la se�al con Fourier";
    final static String WAVELETS   = "Procesamiento de la se�al con Wavelets";
    
    int ban=0;  
    int banE=0;  
    
    Datos datos;
    Fourier trasformadaFourier;
    reconocerFourier reconocer;
    reconocerWaveletHaar reconocerHaar;
    reconocerWaveletDaubechies reconocerDb2;
    reconocerWaveletDaubechies3 reconocerDb3;
    reconocerWaveletCoiflet reconocerCoiflet;
    reconocerWaveletPacket reconocerPacketDb2;
    reconocerWaveletPacketWalsh reconocerPacketWalsh;
    reconocerWaveletPacketDb3 reconocerPacketDb3;
    reconocerWaveletPacketPSBC reconocerPacketPSBC;
    
    int FrecuenciaDeSampleo;
    double  datosVoz[]; //datos de la se�al de voz
    double  datosESI[]; //datos con segmentos inutiles de voz eliminados
    double  datosVozNormalizada[];	//datos normalizados
    double  datosFourier[];	//datos de la trasformada de Fourier

		  JTextField xMinTxt = new JTextField("" );
		  JTextField xMaxTxt = new JTextField("" );
		  JTextField yMinTxt = new JTextField("" );
		  JTextField yMaxTxt = new JTextField("" );
		  
		  JTextField xMinTxtESI = new JTextField("" );
		  JTextField xMaxTxtESI = new JTextField("" );
		  JTextField yMinTxtESI = new JTextField("" );
		  JTextField yMaxTxtESI = new JTextField("" );
		  
		  JTextField xMinTxtVN = new JTextField("" );
		  JTextField xMaxTxtVN = new JTextField("" );
		  JTextField yMinTxtVN = new JTextField("" );
		  JTextField yMaxTxtVN = new JTextField("" );
		  
		  JTextField xMinTxtF = new JTextField("" );
		  JTextField xMaxTxtF = new JTextField("" );
		  JTextField yMinTxtF = new JTextField("" );
		  JTextField yMaxTxtF = new JTextField("" );
	
	/*constructor*/
    public MenuRAH(JFrame frame) {
    	
    	Nombrearchivoaudio="";
    	this.frame=frame;
       
    	tabbedPaneVoz = new JTabbedPane();
    	
        panelCarga = new JPanel();
        panelVoz = new JPanel();
        panelCombox =new JPanel();
	    PanelProcesamientoFourier = new JPanel();
	    PanelProcesamientoFourierEspectro=new JPanel();
	    PanelProcesamientoMFCC=new JPanel();
		panelWaveletHaar = new JPanel();
		panelWaveletDaubechies2 = new JPanel();
		panelWaveletDaubechies3 = new JPanel();
		panelWaveletCoiflet = new JPanel();
		panelWaveletBSpline = new JPanel();
		panelWaveletPacket = new JPanel();
		panelWaveletWalsh = new JPanel();
		panelWaveletPacketDb3= new JPanel();
		panelWaveletPacketPSBC= new JPanel();
			    
		esFourieroWavelets= new JPanel(new CardLayout());
	    tabbedPaneFourier = new JTabbedPane();
	    tabbedPaneWavelet = new JTabbedPane();
		ProcesamientoSenal= new JPanel(new GridBagLayout());
			
		panelDWT = new JPanel();
		
       
        addWidgets();
   
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.PAGE_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

       
        panelPrincipal.add(panelCarga);
        panelPrincipal.add(tabbedPaneVoz);
    	panelPrincipal.add(ProcesamientoSenal);
        panelPrincipal.add(panelDWT);
    }
 	
 	 public void crearTabPaneVoz(){
 	  	
 	  			panelVoz=crearPanelVoz();
 	  			panelEliminacionSegmentosInutiles=crearPanelEliminarSegmentosInutiles();
			    panelVozNormalizada=crearPanelVozNormalizada();
			    
		    tabbedPaneVoz.addTab("voz",panelVoz);
		    tabbedPaneVoz.addTab("eliminacion segmentos",panelEliminacionSegmentosInutiles);
			tabbedPaneVoz.addTab("voz Normalizada",panelVozNormalizada);	
 	 	
 	 	}
 	 	
 	 public JPanel crearEsFourierWavelets(){
 	  	
 	  	    PanelProcesamientoFourier=crearPanelProcesamientoFourier();
			PanelProcesamientoFourierEspectro=crearPanelProcesamientoFourierEspectro();
			PanelProcesamientoMFCC=crearPanelMFCC();
		        
			panelWaveletHaar=crearpanelWaveletHaar();
			panelWaveletDaubechies2=crearpanelWaveletDaubechies2();
			panelWaveletDaubechies3=crearpanelWaveletDaubechies3();
			panelWaveletCoiflet=crearpanelWaveletCoiflet();
//			panelWaveletBSpline=crearpanelWaveletBSpline();
			panelWaveletPacket=crearpanelWaveletPacket();    
			panelWaveletWalsh=crearpanelWalsh();
			panelWaveletPacketDb3= crearpanelWaveletPacketDb3();
			panelWaveletPacketPSBC= crearpanelWaveletPacketPSBC();
			
	   			
			tabbedPaneFourier.addTab("FFT",PanelProcesamientoFourier);
			tabbedPaneFourier.addTab("Espectograma",PanelProcesamientoFourierEspectro);
			tabbedPaneFourier.addTab("MFCC",PanelProcesamientoMFCC);
			
			tabbedPaneWavelet.addTab(" Haar",panelWaveletHaar);//aca agragar mas paneles de wavelets
			tabbedPaneWavelet.addTab(" Daubechies4",panelWaveletDaubechies2);
			tabbedPaneWavelet.addTab(" Daubechies6",panelWaveletDaubechies3);
			tabbedPaneWavelet.addTab(" Coiflet6",panelWaveletCoiflet);
//			tabbedPaneWavelet.addTab(" T. Fourier",panelWaveletBSpline);
			tabbedPaneWavelet.addTab(" Walsh",panelWaveletWalsh);
			tabbedPaneWavelet.addTab(" Packet Db4",panelWaveletPacket);
			tabbedPaneWavelet.addTab(" Packet Db6",panelWaveletPacketDb3);
			tabbedPaneWavelet.addTab(" Packet PSBC",panelWaveletPacketPSBC);
		
		esFourieroWavelets.add(tabbedPaneFourier, FOURIER);
		esFourieroWavelets.add(tabbedPaneWavelet, WAVELETS);
		
			tabbedPaneWavelet.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Procesamiento de la se�al :  Analisis Wavelet"), 
            BorderFactory.createEmptyBorder(5,5,5,5)));
            
            tabbedPaneFourier.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Procesamiento de la se�al :  Analisis Fourier"), 
            BorderFactory.createEmptyBorder(5,5,5,5)));
            
 	 	return esFourieroWavelets;
 	 	}
     
     
     public void crearProcesamientoSenal(){
 	  	
 	  	//ProcesamientoSenal = new JPanel(new GridBagLayout()); 
        GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	ProcesamientoSenal.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
         	c.ipady = 0;      
            c.gridx = 0;
			c.gridy = 1;
        ProcesamientoSenal.add(crearEsFourierWavelets(), c);   
        
 	 	}
 	 	
 	/*agrega los dispositivos*/   
    private void addWidgets() {
    	
        
        panelCarga=crearPanelCarga();
        crearTabPaneVoz();
        crearProcesamientoSenal();
        
            panelCarga.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Menu"), 
            BorderFactory.createEmptyBorder(0,5,5,5)));
            
        	tabbedPaneVoz.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Voz"), 
            BorderFactory.createEmptyBorder(5,5,5,5)));
               
             panelDWT.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("DWT"), 
            BorderFactory.createEmptyBorder(5,5,5,5)));

    }
    
    
    /*crea el panel para ubicar el combox*/
     public JPanel crearComboxPane(){
     
        JPanel comboBoxPane    = new JPanel(); //FlowLayout
        String comboBoxItems[] = { FOURIER, WAVELETS };
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
    	return comboBoxPane;
    	
    	}
   
     /*crea el panel carga que sera agragado al panelCarga*/
    public JPanel crearPanelCarga(){
     
    
        log = new JTextArea(4,25);
        log.setMargin(new Insets(0,0,0,0));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        JPanel panel =new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
        
        fc = new JFileChooser();
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

       JToolBar toolBar = new JToolBar("Barra de Herramientas");
    	
       JButton button = null;
        button = hacerBotonNavegable("Abrir", "Abrir",
                                      "Abrir",
                                      "Abrir");
        toolBar.add(button);
        button = hacerBotonNavegable("Guardar", "Guardar",
                                     "Guardar",
                                     "Guardar");
        toolBar.add(button);
        button = hacerBotonNavegable("Nuevo", "Nuevo",
                                      "Nuevo",
                                      "Nuevo");
        toolBar.add(button);
		button = hacerBotonNavegable("Limpiar","Limpiar",
                                      "Limpiar",
                                      "Limpiar");
        toolBar.add(button);
        
        button = hacerBotonNavegable("Salir","Salir",
                                      "Salir",
                                      "Salir");
        toolBar.add(button);
        
        panelCombox=crearComboxPane();
		toolBar.add(panelCombox);
    
        JPanel panelBtn = new JPanel(new GridLayout(1, 0)); 

   		panelBtn.add(toolBar);
            panelBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(""), 
            BorderFactory.createEmptyBorder(0,0,0,0)));
            
            c.fill = GridBagConstraints.HORIZONTAL;
        	c.ipady = 0;      //
			c.weightx = 1.0;
			c.weighty = 1.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
        panel.add(panelBtn,c);
         	c.gridwidth = 1;
       		c.weightx = 1.0;
			c.weighty = 1.0;
        	c.ipady = 5;
        //	c.ipadx = 40;
        	c.gridx = 0;
			c.gridy = 1;
        panel.add(logScrollPane,c);	
    	
    	return panel;
    	
    	}
    
    protected JButton hacerBotonNavegable(String imageName,
    									  String actionCommand,
    									  String toolTipText,
                                          String altText) {
        //Busca la Imagen
        String imgLocation = "/imagenes/"+ imageName+ ".gif";
        URL imageURL = MenuRAH.class.getResource(imgLocation);

        //Crea e inicializa el Boton
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        if (imageURL != null) {                      //imagen encontrada
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {                                     //imagen no encontrada
            button.setText(altText);
        
        }

        return button;
    }
    
    
    /*crea el panel wavelet que sera agregado al panelWaveletHaar*/
    public JPanel crearpanelWaveletHaar(){
    	

    	salidaHaar = new JTextArea(7,25);
        salidaHaar.setMargin(new Insets(0,0,0,0));
        salidaHaar.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(salidaHaar);
        
 	    canvas8= new Graficar(1); //graficar DWT
		canvas8.tamano(375,130);
		//radio buttons para plantilla y reconocer
		
		JRadioButton graficarHaar = new JRadioButton("graficarHaar");
    	graficarHaar.setMnemonic(KeyEvent.VK_D);
    	graficarHaar.setActionCommand("graficarHaar");
			
		JRadioButton plantilla = new JRadioButton("PlantillaHaar");
    	plantilla.setMnemonic(KeyEvent.VK_D);
    	plantilla.setActionCommand("PlantillaHaar");
    	
    	JRadioButton reconocer = new JRadioButton("ReconocerHaar");
    	reconocer.setMnemonic(KeyEvent.VK_D);
    	reconocer.setActionCommand("ReconocerHaar");
    	
    	ButtonGroup group = new ButtonGroup();
    	group.add(graficarHaar);
    	group.add(plantilla);
    	group.add(reconocer);
    	
    	graficarHaar.addActionListener(this);
    	plantilla.addActionListener(this);
    	reconocer.addActionListener(this);
	
        JPanel panel = new JPanel(new GridLayout(1,2)); 
         GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelSalida =new JPanel(new GridBagLayout());   
        JPanel panelDWT = new JPanel(new GridBagLayout()); 
	        panelSalida.setBorder(new EtchedBorder());        
     	    panelDWT.setBorder(new EtchedBorder());        
         
        
		panelDWT.add(canvas8);  
			c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //
        c.ipadx=0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		panelSalida.add(plantilla,c);
		c.gridx = 1;
		c.gridy = 0;
		panelSalida.add(reconocer,c);
		c.gridx = 2;
		c.gridy = 0;
		panelSalida.add(graficarHaar,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;

	//      c.gridwidth = 1;
       		c.weightx = 1.0;
			c.weighty = 1.0;
        	c.ipady = 5;
        //	c.ipadx = 40;
        //	c.gridx = 0;
		//	c.gridy = 1;
		
		panelSalida.add(logScrollPane,c);
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		
        panel.add(panelSalida,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelDWT,c);	
   	    	canvas8.repaint();
   	   


	  
	   return panel; 	
    	}
    	
    public JPanel crearpanelWaveletDaubechies2(){
    	

 /**/   	salidaDaubechies2 = new JTextArea(7,25);
 /**/       salidaDaubechies2.setMargin(new Insets(0,0,0,0));
/**/        salidaDaubechies2.setEditable(false);
/**/        JScrollPane logScrollPane = new JScrollPane(salidaDaubechies2);
        
/**/ 	    canvas9= new Graficar(1); //graficar DWT
/**/		canvas9.tamano(375,130);
		//radio buttons para plantilla y reconocer
		
/**/		JRadioButton graficarDb2 = new JRadioButton("graficarDb2");
/**/    	graficarDb2.setMnemonic(KeyEvent.VK_D);
/**/    	graficarDb2.setActionCommand("graficarDb2");
			
		JRadioButton plantilla = new JRadioButton("PlantillaDb2");
    	plantilla.setMnemonic(KeyEvent.VK_D);
    	plantilla.setActionCommand("PlantillaDb2");
    	
    	JRadioButton reconocer = new JRadioButton("ReconocerDb2");
    	reconocer.setMnemonic(KeyEvent.VK_D);
    	reconocer.setActionCommand("ReconocerDb2");
    	
    	ButtonGroup group = new ButtonGroup();
/**/    	group.add(graficarDb2);
    	group.add(plantilla);
    	group.add(reconocer);
    	
/**/    	graficarDb2.addActionListener(this);
    	plantilla.addActionListener(this);
    	reconocer.addActionListener(this);
	
        JPanel panel = new JPanel(new GridLayout(1,2)); 
         GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelSalida =new JPanel(new GridBagLayout());   
        JPanel panelDWT = new JPanel(new GridBagLayout()); 
	        panelSalida.setBorder(new EtchedBorder());        
     	    panelDWT.setBorder(new EtchedBorder());        
         
        
/**/		panelDWT.add(canvas9);  
			c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //
        c.ipadx=0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		panelSalida.add(plantilla,c);
		c.gridx = 1;
		c.gridy = 0;
		panelSalida.add(reconocer,c);
		c.gridx = 2;
		c.gridy = 0;
/**/		panelSalida.add(graficarDb2,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;

	//      c.gridwidth = 1;
       		c.weightx = 1.0;
			c.weighty = 1.0;
        	c.ipady = 5;
        //	c.ipadx = 40;
        //	c.gridx = 0;
		//	c.gridy = 1;
		
		panelSalida.add(logScrollPane,c);
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		
        panel.add(panelSalida,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelDWT,c);	
/**/   	    	canvas9.repaint();
   	   


	  
	   return panel; 	

    	}	
    	
    	
    public JPanel crearpanelWaveletDaubechies3(){
    	
     	salidaDaubechies3 = new JTextArea(7,25);
        salidaDaubechies3.setMargin(new Insets(0,0,0,0));
        salidaDaubechies3.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(salidaDaubechies3);
        
 	    canvas10= new Graficar(1); //graficar DWT
		canvas10.tamano(375,130);
		//radio buttons para plantilla y reconocer
		
		JRadioButton graficarDb3 = new JRadioButton("graficarDb3");
    	graficarDb3.setMnemonic(KeyEvent.VK_D);
    	graficarDb3.setActionCommand("graficarDb3");
			
		JRadioButton plantilla = new JRadioButton("PlantillaDb3");
    	plantilla.setMnemonic(KeyEvent.VK_D);
    	plantilla.setActionCommand("PlantillaDb3");
    	
    	JRadioButton reconocer = new JRadioButton("ReconocerDb3");
    	reconocer.setMnemonic(KeyEvent.VK_D);
    	reconocer.setActionCommand("ReconocerDb3");
    	
    	ButtonGroup group = new ButtonGroup();
    	group.add(graficarDb3);
    	group.add(plantilla);
    	group.add(reconocer);
    	
    	graficarDb3.addActionListener(this);
    	plantilla.addActionListener(this);
    	reconocer.addActionListener(this);
	
        JPanel panel = new JPanel(new GridLayout(1,2)); 
         GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelSalida =new JPanel(new GridBagLayout());   
        JPanel panelDWT = new JPanel(new GridBagLayout()); 
	        panelSalida.setBorder(new EtchedBorder());        
     	    panelDWT.setBorder(new EtchedBorder());        
         
        
		panelDWT.add(canvas10);  
			c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //
        c.ipadx=0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		panelSalida.add(plantilla,c);
		c.gridx = 1;
		c.gridy = 0;
		panelSalida.add(reconocer,c);
		c.gridx = 2;
		c.gridy = 0;
		panelSalida.add(graficarDb3,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;

	//      c.gridwidth = 1;
       		c.weightx = 1.0;
			c.weighty = 1.0;
        	c.ipady = 5;
        //	c.ipadx = 40;
        //	c.gridx = 0;
		//	c.gridy = 1;
		
		panelSalida.add(logScrollPane,c);
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		
        panel.add(panelSalida,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelDWT,c);	
   	    	canvas10.repaint();
   	   
	  
	   return panel; 	
    	}		
	
	public JPanel crearpanelWaveletCoiflet(){
    	
    	salidaCoiflet = new JTextArea(7,25);
        salidaCoiflet.setMargin(new Insets(0,0,0,0));/**/        salidaCoiflet.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(salidaCoiflet);
        
 	    canvas11= new Graficar(1); //graficar DWT
		canvas11.tamano(375,130);
		//radio buttons para plantilla y reconocer
		
		JRadioButton graficarCoiflet = new JRadioButton("graficarCoiflet");
    	graficarCoiflet.setMnemonic(KeyEvent.VK_D);
    	graficarCoiflet.setActionCommand("graficarCoiflet");
			
		JRadioButton plantilla = new JRadioButton("PlantillaCoiflet");
    	plantilla.setMnemonic(KeyEvent.VK_D);
    	plantilla.setActionCommand("PlantillaCoiflet");
    	
    	JRadioButton reconocer = new JRadioButton("ReconocerCoiflet");
    	reconocer.setMnemonic(KeyEvent.VK_D);
    	reconocer.setActionCommand("ReconocerCoiflet");
    	
    	ButtonGroup group = new ButtonGroup();
    	group.add(graficarCoiflet);
    	group.add(plantilla);
    	group.add(reconocer);
    	
    	graficarCoiflet.addActionListener(this);
    	plantilla.addActionListener(this);
    	reconocer.addActionListener(this);
	
        JPanel panel = new JPanel(new GridLayout(1,2)); 
         GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelSalida =new JPanel(new GridBagLayout());   
        JPanel panelDWT = new JPanel(new GridBagLayout()); 
	        panelSalida.setBorder(new EtchedBorder());        
     	    panelDWT.setBorder(new EtchedBorder());        
         
        
		panelDWT.add(canvas11);  
			c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //
        c.ipadx=0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		panelSalida.add(plantilla,c);
		c.gridx = 1;
		c.gridy = 0;
		panelSalida.add(reconocer,c);
		c.gridx = 2;
		c.gridy = 0;
		panelSalida.add(graficarCoiflet,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;

	//      c.gridwidth = 1;
       		c.weightx = 1.0;
			c.weighty = 1.0;
        	c.ipady = 5;
        //	c.ipadx = 40;
        //	c.gridx = 0;
		//	c.gridy = 1;
		
		panelSalida.add(logScrollPane,c);
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		
        panel.add(panelSalida,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelDWT,c);	
   	    	canvas11.repaint();
   	   


	  
	   return panel; 	
    	}	
    	
    public JPanel crearpanelWaveletBSpline(){
    	
    
    	salidaBSpline = new JTextArea(7,25);
        salidaBSpline.setMargin(new Insets(0,0,0,0));/**/   
        salidaBSpline.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(salidaBSpline);
        
 	    canvas4= new Graficar(1); //graficar DWT
		canvas4.tamano(375,130);
		//radio buttons para plantilla y reconocer
		
		JRadioButton graficarBSpline = new JRadioButton("graficarTFourier");
    	graficarBSpline.setMnemonic(KeyEvent.VK_D);
    	graficarBSpline.setActionCommand("graficarTFourier");
			
		JRadioButton plantilla = new JRadioButton("PlantillaTFourier");
    	plantilla.setMnemonic(KeyEvent.VK_D);
    	plantilla.setActionCommand("PlantillaTFourier");
    	
    	JRadioButton reconocer = new JRadioButton("ReconocerTFourier");
    	reconocer.setMnemonic(KeyEvent.VK_D);
    	reconocer.setActionCommand("ReconocerTFourier");
    	
    	ButtonGroup group = new ButtonGroup();
    	group.add(graficarBSpline);
    	group.add(plantilla);
    	group.add(reconocer);
    	
    	graficarBSpline.addActionListener(this);
    	plantilla.addActionListener(this);
    	reconocer.addActionListener(this);
	
        JPanel panel = new JPanel(new GridLayout(1,2)); 
         GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelSalida =new JPanel(new GridBagLayout());   
        JPanel panelDWT = new JPanel(new GridBagLayout()); 
	        panelSalida.setBorder(new EtchedBorder());        
     	    panelDWT.setBorder(new EtchedBorder());        
         
        
		panelDWT.add(canvas4);  
			c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //
        c.ipadx=0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		panelSalida.add(plantilla,c);
		c.gridx = 1;
		c.gridy = 0;
		panelSalida.add(reconocer,c);
		c.gridx = 2;
		c.gridy = 0;
		panelSalida.add(graficarBSpline,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;

	//      c.gridwidth = 1;
       		c.weightx = 1.0;
			c.weighty = 1.0;
        	c.ipady = 5;
        //	c.ipadx = 40;
        //	c.gridx = 0;
		//	c.gridy = 1;
		
		panelSalida.add(logScrollPane,c);
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		
        panel.add(panelSalida,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelDWT,c);	
   	    	canvas4.repaint();
   	   


	  
	   return panel; 	
    	}		
    	
    	
    public JPanel crearpanelWaveletPacket(){
    	
    	salidaWaveletPacket = new JTextArea(7,25);
        salidaWaveletPacket.setMargin(new Insets(0,0,0,0));/**/   
        salidaWaveletPacket.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(salidaWaveletPacket);
        
 	    canvas12= new Graficar(1); //graficar DWT
		canvas12.tamano(375,130);
		//radio buttons para plantilla y reconocer
		
		JRadioButton graficarWaveletPacket = new JRadioButton("graficarWPDb4");
    	graficarWaveletPacket.setMnemonic(KeyEvent.VK_D);
    	graficarWaveletPacket.setActionCommand("graficarWPDb4");
			
		JRadioButton plantilla = new JRadioButton("PlantillaWPDb4");
    	plantilla.setMnemonic(KeyEvent.VK_D);
    	plantilla.setActionCommand("PlantillaWPDb4");
    	
    	JRadioButton reconocer = new JRadioButton("ReconocerWPDb4");
    	reconocer.setMnemonic(KeyEvent.VK_D);
    	reconocer.setActionCommand("ReconocerWPDb4");
    	
    	ButtonGroup group = new ButtonGroup();
    	group.add(graficarWaveletPacket);
    	group.add(plantilla);
    	group.add(reconocer);
    	
    	graficarWaveletPacket.addActionListener(this);
    	plantilla.addActionListener(this);
    	reconocer.addActionListener(this);
	
        JPanel panel = new JPanel(new GridLayout(1,2)); 
         GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelSalida =new JPanel(new GridBagLayout());   
        JPanel panelDWT = new JPanel(new GridBagLayout()); 
	        panelSalida.setBorder(new EtchedBorder());        
     	    panelDWT.setBorder(new EtchedBorder());        
         
        
		panelDWT.add(canvas12);  
			c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //
        c.ipadx=0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		panelSalida.add(plantilla,c);
		c.gridx = 1;
		c.gridy = 0;
		panelSalida.add(reconocer,c);
		c.gridx = 2;
		c.gridy = 0;
		panelSalida.add(graficarWaveletPacket,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;

	//      c.gridwidth = 1;
       		c.weightx = 1.0;
			c.weighty = 1.0;
        	c.ipady = 5;
        //	c.ipadx = 40;
        //	c.gridx = 0;
		//	c.gridy = 1;
		
		panelSalida.add(logScrollPane,c);
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		
        panel.add(panelSalida,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelDWT,c);	
   	    	canvas12.repaint();
   	   


	  
	   return panel; 	
    	}		
    	
      public JPanel crearpanelWalsh(){
    	

    	salidaWalsh = new JTextArea(7,25);
        salidaWalsh.setMargin(new Insets(0,0,0,0));
        salidaWalsh.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(salidaWalsh);
        
 	    canvas13= new Graficar(1); //graficar DWT
		canvas13.tamano(375,130);
		//radio buttons para plantilla y reconocer
		
		JRadioButton graficarWalsh = new JRadioButton("graficarWalsh");
    	graficarWalsh.setMnemonic(KeyEvent.VK_D);
    	graficarWalsh.setActionCommand("graficarWalsh");
			
		JRadioButton plantilla = new JRadioButton("PlantillaWalsh");
    	plantilla.setMnemonic(KeyEvent.VK_D);
    	plantilla.setActionCommand("PlantillaWalsh");
    	
    	JRadioButton reconocer = new JRadioButton("ReconocerWalsh");
    	reconocer.setMnemonic(KeyEvent.VK_D);
    	reconocer.setActionCommand("ReconocerWalsh");
    	
    	ButtonGroup group = new ButtonGroup();
    	group.add(graficarWalsh);
    	group.add(plantilla);
    	group.add(reconocer);
    	
        graficarWalsh.addActionListener(this);
    	plantilla.addActionListener(this);
    	reconocer.addActionListener(this);
	
        JPanel panel = new JPanel(new GridLayout(1,2)); 
         GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelSalida =new JPanel(new GridBagLayout());   
        JPanel panelDWT = new JPanel(new GridBagLayout()); 
	        panelSalida.setBorder(new EtchedBorder());        
     	    panelDWT.setBorder(new EtchedBorder());        
         
        
		panelDWT.add(canvas13);  
			c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //
        c.ipadx=0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		panelSalida.add(plantilla,c);
		c.gridx = 1;
		c.gridy = 0;
		panelSalida.add(reconocer,c);
		c.gridx = 2;
		c.gridy = 0;
		panelSalida.add(graficarWalsh,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;

	//      c.gridwidth = 1;
       		c.weightx = 1.0;
			c.weighty = 1.0;
        	c.ipady = 5;
        //	c.ipadx = 40;
        //	c.gridx = 0;
		//	c.gridy = 1;
		
		panelSalida.add(logScrollPane,c);
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		
        panel.add(panelSalida,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelDWT,c);	
   	    	canvas13.repaint();
   	   


	  
	   return panel; 	

    	}	
    	
     public JPanel crearpanelWaveletPacketDb3(){
    	

    	salidaPacketDb3 = new JTextArea(7,25);
        salidaPacketDb3.setMargin(new Insets(0,0,0,0));
        salidaPacketDb3.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(salidaPacketDb3);
        
 	    canvas14= new Graficar(1); //graficar DWT
		canvas14.tamano(375,130);
		//radio buttons para plantilla y reconocer
		
		JRadioButton graficarWPDb3 = new JRadioButton("graficarWPDb3");
    	graficarWPDb3.setMnemonic(KeyEvent.VK_D);
    	graficarWPDb3.setActionCommand("graficarWPDb3");
			
		JRadioButton plantilla = new JRadioButton("PlantillaWPDb3");
    	plantilla.setMnemonic(KeyEvent.VK_D);
    	plantilla.setActionCommand("PlantillaWPDb3");
    	
    	JRadioButton reconocer = new JRadioButton("ReconocerWPDb3");
    	reconocer.setMnemonic(KeyEvent.VK_D);
    	reconocer.setActionCommand("ReconocerWPDb3");
    	
    	ButtonGroup group = new ButtonGroup();
	    group.add(graficarWPDb3);
    	group.add(plantilla);
    	group.add(reconocer);
    	
	    graficarWPDb3.addActionListener(this);
    	plantilla.addActionListener(this);
    	reconocer.addActionListener(this);
	
        JPanel panel = new JPanel(new GridLayout(1,2)); 
         GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelSalida =new JPanel(new GridBagLayout());   
        JPanel panelDWT = new JPanel(new GridBagLayout()); 
	        panelSalida.setBorder(new EtchedBorder());        
     	    panelDWT.setBorder(new EtchedBorder());        
         
        
			panelDWT.add(canvas14);  
			c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //
        c.ipadx=0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		panelSalida.add(plantilla,c);
		c.gridx = 1;
		c.gridy = 0;
		panelSalida.add(reconocer,c);
		c.gridx = 2;
		c.gridy = 0;
		panelSalida.add(graficarWPDb3,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;

	//      c.gridwidth = 1;
       		c.weightx = 1.0;
			c.weighty = 1.0;
        	c.ipady = 5;
        //	c.ipadx = 40;
        //	c.gridx = 0;
		//	c.gridy = 1;
		
		panelSalida.add(logScrollPane,c);
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		
        panel.add(panelSalida,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelDWT,c);	
	   	canvas14.repaint();
   	   


	  
	   return panel; 	

    	}		
    	
      public JPanel crearpanelWaveletPacketPSBC(){
    	

 /**/   	salidaWaveletPacketPSBC = new JTextArea(7,25);
 /**/       salidaWaveletPacketPSBC.setMargin(new Insets(0,0,0,0));
/**/        salidaWaveletPacketPSBC.setEditable(false);
/**/        JScrollPane logScrollPane = new JScrollPane(salidaWaveletPacketPSBC);
        
/**/ 	    canvas15= new Graficar(1); //graficar DWT
/**/		canvas15.tamano(375,130);
		//radio buttons para plantilla y reconocer
		
/**/		JRadioButton graficarPSBC = new JRadioButton("graficarPSBC");
/**/    	graficarPSBC.setMnemonic(KeyEvent.VK_D);
/**/    	graficarPSBC.setActionCommand("graficarPSBC");
			
		JRadioButton plantilla = new JRadioButton("PlantillaPSBC");
    	plantilla.setMnemonic(KeyEvent.VK_D);
    	plantilla.setActionCommand("PlantillaPSBC");
    	
    	JRadioButton reconocer = new JRadioButton("ReconocerPSBC");
    	reconocer.setMnemonic(KeyEvent.VK_D);
    	reconocer.setActionCommand("ReconocerPSBC");
    	
    	ButtonGroup group = new ButtonGroup();
/**/    	group.add(graficarPSBC);
    	group.add(plantilla);
    	group.add(reconocer);
    	
/**/    	graficarPSBC.addActionListener(this);
    	plantilla.addActionListener(this);
    	reconocer.addActionListener(this);
	
        JPanel panel = new JPanel(new GridLayout(1,2)); 
         GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelSalida =new JPanel(new GridBagLayout());   
        JPanel panelDWT = new JPanel(new GridBagLayout()); 
	        panelSalida.setBorder(new EtchedBorder());        
     	    panelDWT.setBorder(new EtchedBorder());        
         
        
/**/		panelDWT.add(canvas15);  
			c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //
        c.ipadx=0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		panelSalida.add(plantilla,c);
		c.gridx = 1;
		c.gridy = 0;
		panelSalida.add(reconocer,c);
		c.gridx = 2;
		c.gridy = 0;
/**/		panelSalida.add(graficarPSBC,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;

	//      c.gridwidth = 1;
       		c.weightx = 1.0;
			c.weighty = 1.0;
        	c.ipady = 5;
        //	c.ipadx = 40;
        //	c.gridx = 0;
		//	c.gridy = 1;
		
		panelSalida.add(logScrollPane,c);
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		
        panel.add(panelSalida,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelDWT,c);	
/**/   	    	canvas15.repaint();
   	   


	  
	   return panel; 	

    	}	
    	
 	
 
	/*panel Voz*/
	public JPanel crearPanelVoz(){
 			
 			
 		canvas1= new Graficar(0);
		canvas1.tamano(750,70);
	//	canvas1.setBackground(Color.black);
		
 		graficarBtn = new JButton("Graficar");
    	graficarBtn.addActionListener(this);
    	
    	resetearBtn = new JButton("Resetear");
    	resetearBtn.addActionListener(this);
   
        JPanel panel = new JPanel(new GridBagLayout()); 
        GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelGrafica =new JPanel(new GridLayout(0,1));   
        JPanel panelComandos = new JPanel(new GridBagLayout()); 
	        panelGrafica.setBorder(new EtchedBorder());        
     	    panelComandos.setBorder(new EtchedBorder());        
         
          //Paneles que contienen un label y 
		  // un campo de texto
		  JPanel pan0 = new JPanel();
		  JPanel pan1 = new JPanel();
		  JPanel pan2 = new JPanel();
		  JPanel pan3 = new JPanel();
		 
		  
		   xMinTxt = new JTextField("0.000" + canvas1.xMin);
		   xMaxTxt = new JTextField("0.000" + canvas1.xMax);
		   yMinTxt = new JTextField("0.000" + canvas1.yMin);
		   yMaxTxt = new JTextField("0.000" + canvas1.yMax);
			
			
		  	pan0.add(new JLabel("xMin"));
		    pan0.add(xMinTxt);
		
		    pan1.add(new JLabel("xMax"));
		    pan1.add(xMaxTxt);
		
		    pan2.add(new JLabel("yMin"));
		    pan2.add(yMinTxt);
		
		    pan3.add(new JLabel("yMax"));
		    pan3.add(yMaxTxt);
		
	        c.ipady = 0;      //
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		    panelComandos.add(pan0,c);
		    c.gridx = 1;
			c.gridy = 0;
		    panelComandos.add(pan1,c);
		    c.gridx = 2;
			c.gridy = 0;
		    panelComandos.add(pan2,c);
		    c.gridx = 3;
			c.gridy = 0;
		    panelComandos.add(pan3,c);
		    c.gridx = 4;
			c.gridy = 0;
		   
		    panelComandos.add(graficarBtn,c);
		    c.gridx = 6;
			c.gridy = 0;
		  	panelComandos.add(resetearBtn,c);	
 		
		panelGrafica.add(canvas1);  
			c.ipady = 0;     
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
        panel.add(panelGrafica,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;
        panel.add(panelComandos,c);	
  
   	   	canvas1.repaint();
    	return panel;
    	
    	}
    	
    	/*panel eliminarSegmentosInutiles*/
    	public JPanel crearPanelEliminarSegmentosInutiles(){
 			
 		canvas5= new Graficar(0);
		canvas5.tamano(750,70);
	
		
 		graficarESIBtn = new JButton("Graficar");
    	graficarESIBtn.addActionListener(this);
    	
    	resetearESIBtn = new JButton("Resetear");
    	resetearESIBtn.addActionListener(this);
    	
        JPanel panel = new JPanel(new GridBagLayout()); 
        GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelGrafica =new JPanel(new GridLayout(0,1));   
        JPanel panelComandos = new JPanel(new GridBagLayout()); 
	        panelGrafica.setBorder(new EtchedBorder());        
     	    panelComandos.setBorder(new EtchedBorder());        
         
          //Paneles que contienen un label y 
		  // un campo de texto
		  JPanel pan0 = new JPanel();
		  JPanel pan1 = new JPanel();
		  JPanel pan2 = new JPanel();
		  JPanel pan3 = new JPanel();
		 
		    
	//		xMin=0;yMin=-1;yMax=1; yIntervaloMarcas=0.1;
		   xMinTxtESI = new JTextField("0.000" + canvas5.xMin);
		   xMaxTxtESI = new JTextField("0.000" + canvas5.xMax);
		   yMinTxtESI = new JTextField("0.000" + canvas5.yMin);
		   yMaxTxtESI = new JTextField("0.000" + canvas5.yMax);
			   
		  	pan0.add(new JLabel("xMin"));
		    pan0.add(xMinTxtESI);
		
		    pan1.add(new JLabel("xMax"));
		    pan1.add(xMaxTxtESI);
		
		    pan2.add(new JLabel("yMin"));
		    pan2.add(yMinTxtESI);
		
		    pan3.add(new JLabel("yMax"));
		    pan3.add(yMaxTxtESI);
		
	        c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		    panelComandos.add(pan0);
		    c.gridx = 1;
			c.gridy = 0;
		    panelComandos.add(pan1);
		    c.gridx = 2;
			c.gridy = 0;
		    panelComandos.add(pan2);
		    c.gridx = 3;
			c.gridy = 0;
		    panelComandos.add(pan3);
		    
		    panelComandos.add(graficarESIBtn);
		    panelComandos.add(resetearESIBtn);
		panelGrafica.add(canvas5);  
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
        panel.add(panelGrafica,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelComandos,c);	
   	    	canvas5.repaint();
   		return panel;
    	
    	}

		/*crear panelVozNormalizada*/
	public JPanel crearPanelVozNormalizada(){
 			
 		canvas2= new Graficar(0);
		canvas2.tamano(750,70);
	
		
 		graficarVNBtn = new JButton("Graficar");
    	graficarVNBtn.addActionListener(this);
    	
    	resetearVNBtn = new JButton("Resetear");
    	resetearVNBtn.addActionListener(this);
    	
        JPanel panel = new JPanel(new GridBagLayout()); 
        GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelGrafica =new JPanel(new GridLayout(0,1));   
        JPanel panelComandos = new JPanel(new GridBagLayout()); 
	        panelGrafica.setBorder(new EtchedBorder());        
     	    panelComandos.setBorder(new EtchedBorder());        
         
          //Paneles que contienen un label y 
		  // un campo de texto
		  JPanel pan0 = new JPanel();
		  JPanel pan1 = new JPanel();
		  JPanel pan2 = new JPanel();
		  JPanel pan3 = new JPanel();
		 
		    
	//		xMin=0;yMin=-1;yMax=1; yIntervaloMarcas=0.1;
		   xMinTxtVN = new JTextField("0.000" + canvas2.xMin);
		   xMaxTxtVN = new JTextField("0.000" + canvas2.xMax);
		   yMinTxtVN = new JTextField("0.000" + canvas2.yMin);
		   yMaxTxtVN = new JTextField("0.000" + canvas2.yMax);
			   
		  	pan0.add(new JLabel("xMin"));
		    pan0.add(xMinTxtVN);
		
		    pan1.add(new JLabel("xMax"));
		    pan1.add(xMaxTxtVN);
		
		    pan2.add(new JLabel("yMin"));
		    pan2.add(yMinTxtVN);
		
		    pan3.add(new JLabel("yMax"));
		    pan3.add(yMaxTxtVN);
		
	        c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		    panelComandos.add(pan0);
		    c.gridx = 1;
			c.gridy = 0;
		    panelComandos.add(pan1);
		    c.gridx = 2;
			c.gridy = 0;
		    panelComandos.add(pan2);
		    c.gridx = 3;
			c.gridy = 0;
		    panelComandos.add(pan3);
		    
		    panelComandos.add(graficarVNBtn);
		    panelComandos.add(resetearVNBtn);
		panelGrafica.add(canvas2);  
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
        panel.add(panelGrafica,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelComandos,c);	
   	    	canvas2.repaint();
   		return panel;
    	
    	}
	
	public JPanel crearPanelProcesamientoFourier(){
 			
 		canvas3= new Graficar(1);
		canvas3.tamano(750,100);
	//	canvas3.setBackground(Color.black);
    	resetearPSFBtn = new JButton("Resetear");
    	resetearPSFBtn.addActionListener(this);	
 	
 		
 		graficarPSFBtn = new JButton("Graficar");
    	graficarPSFBtn.addActionListener(this);
    	
        JPanel panel = new JPanel(new GridBagLayout()); 
        GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelGrafica =new JPanel(new GridLayout(0,1));   
        JPanel panelComandos = new JPanel(new GridBagLayout()); 
	        panelGrafica.setBorder(new EtchedBorder());        
     	    panelComandos.setBorder(new EtchedBorder());        
         
          //Paneles que contienen un label y 
		  // un campo de texto
		  JPanel pan0 = new JPanel();
		  JPanel pan1 = new JPanel();
		  JPanel pan2 = new JPanel();
		  JPanel pan3 = new JPanel();
		  
		   xMinTxtF = new JTextField("0.000" + canvas3.xMin);
		   xMaxTxtF = new JTextField("0.000" + canvas3.xMax);
		   yMinTxtF = new JTextField("0.000" + canvas3.yMin);
		   yMaxTxtF = new JTextField("0.000" + canvas3.yMax);
			   
		  	pan0.add(new JLabel("xMin"));
		    pan0.add(xMinTxtF);
		
		    pan1.add(new JLabel("xMax"));
		    pan1.add(xMaxTxtF);
		
		    pan2.add(new JLabel("yMin"));
		    pan2.add(yMinTxtF);
		
		    pan3.add(new JLabel("yMax"));
		    pan3.add(yMaxTxtF);
		
	        c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		    panelComandos.add(pan0);
		    c.gridx = 1;
			c.gridy = 0;
		    panelComandos.add(pan1);
		    c.gridx = 2;
			c.gridy = 0;
		    panelComandos.add(pan2);
		    c.gridx = 3;
			c.gridy = 0;
		    panelComandos.add(pan3);
		    
		    panelComandos.add(graficarPSFBtn);
		    panelComandos.add(resetearPSFBtn);
		    
		    //panelComandos.add(resetearVNBtn);
		panelGrafica.add(canvas3);  
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
        panel.add(panelGrafica,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelComandos,c);	
   	    	canvas3.repaint();
   		return panel;
    	}

	public JPanel crearPanelMFCC(){
		
	    
		salida = new JTextArea(7,25);
        salida.setMargin(new Insets(0,0,0,0));
        salida.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(salida);
        
 	    canvas6= new Graficar(1); //graficar DWT
		canvas6.tamano(375,130);
		//radio buttons para plantilla y reconocer
			
		JRadioButton plantilla = new JRadioButton("Plantilla");
    	plantilla.setMnemonic(KeyEvent.VK_D);
    	plantilla.setActionCommand("Plantilla");
    	
    	JRadioButton reconocer = new JRadioButton("Reconocer");
    	reconocer.setMnemonic(KeyEvent.VK_D);
    	reconocer.setActionCommand("Reconocer");
    	
    	ButtonGroup group = new ButtonGroup();
    	group.add(plantilla);
    	group.add(reconocer);
    	
    	plantilla.addActionListener(this);
    	reconocer.addActionListener(this);
	
        JPanel panel = new JPanel(new GridLayout(1,2)); 
         GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelSalida =new JPanel(new GridBagLayout());   
        JPanel panelDWT = new JPanel(new GridBagLayout()); 
	        panelSalida.setBorder(new EtchedBorder());        
     	    panelDWT.setBorder(new EtchedBorder());        
         
        
		panelDWT.add(canvas6);  
			c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;      //
        c.ipadx=0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		panelSalida.add(plantilla,c);
		c.gridx = 1;
		c.gridy = 0;
		panelSalida.add(reconocer,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;
	
	//      c.gridwidth = 1;
       		c.weightx = 1.0;
			c.weighty = 1.0;
        	c.ipady = 5;
        //	c.ipadx = 40;
        //	c.gridx = 0;
		//	c.gridy = 1;
		
		panelSalida.add(logScrollPane,c);
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		
        panel.add(panelSalida,c);
  		  	c.gridx = 0;
		  	c.gridy = 1;

        panel.add(panelDWT,c);	
   	    	canvas6.repaint();
   	   
   		return panel;
    	}

public JPanel crearPanelProcesamientoFourierEspectro(){
 			
 	    canvas7= new Graficar(2);
		canvas7.tamano(750,100);
	//	canvas4.graficarDatos(datosVoz,datos.obtenerMayor());
 		graficarEspectroBtn = new JButton("Graficar Espectograma");
    	graficarEspectroBtn.addActionListener(this);
    
        JPanel panel = new JPanel(new GridBagLayout()); 
        GridBagConstraints c = new GridBagConstraints();
        	c.fill = GridBagConstraints.HORIZONTAL;
        
        	if (RIGHT_TO_LEFT) {
            	panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        	}
    
        JPanel panelGrafica =new JPanel(new GridLayout(0,1));   
        JPanel panelComandos = new JPanel(new GridBagLayout()); 
	        panelGrafica.setBorder(new EtchedBorder());        
     	    panelComandos.setBorder(new EtchedBorder());        
         
          //Paneles que contienen un label y 
	/*	  // un campo de texto
		  JPanel pan0 = new JPanel();
		  JPanel pan1 = new JPanel();
		  JPanel pan2 = new JPanel();
		  JPanel pan3 = new JPanel();
		  JPanel pan4 = new JPanel();
		  JPanel pan5 = new JPanel();
		  JPanel pan6 = new JPanel();
		  
		   xMinTxt = new JTextField("" + xMin);
		   xMaxTxt = new JTextField("" + xMax);
		   yMinTxt = new JTextField("" + yMin);
		   yMaxTxt = new JTextField("" + yMax);
		   xTicIntTxt = new JTextField("" + xIntervaloMarcas);
		   yTicIntTxt = new JTextField("" + yIntervaloMarcas);
		   
		  
			
		  	pan0.add(new JLabel("xMin"));
		    pan0.add(xMinTxt);
		
		    pan1.add(new JLabel("xMax"));
		    pan1.add(xMaxTxt);
		
		    pan2.add(new JLabel("yMin"));
		    pan2.add(yMinTxt);
		
		    pan3.add(new JLabel("yMax"));
		    pan3.add(yMaxTxt);
		
		    pan4.add(new JLabel("xIntervaloMarcas"));
		    pan4.add(xTicIntTxt);
		
		    pan5.add(new JLabel("yIntervaloMarcas"));
		    pan5.add(yTicIntTxt);
	        c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
		    panelComandos.add(pan0);
		    c.gridx = 1;
			c.gridy = 0;
		    panelComandos.add(pan1);
		    c.gridx = 2;
			c.gridy = 0;
		    panelComandos.add(pan2);
		    c.gridx = 3;
			c.gridy = 0;
		    panelComandos.add(pan3);
		    c.gridx = 4;
			c.gridy = 0;
		    panelComandos.add(pan4);
		    c.gridx = 5;
			c.gridy = 0;
		    panelComandos.add(pan5);
		    c.gridx = 6;
			c.gridy = 0;
		    panelComandos.add(pan6);
		    c.gridx = 7;
		*/	c.gridy = 0;
		    panelComandos.add(graficarEspectroBtn);
		  		
 		 /*   canvas3=new graficarCanvas(2); //opcion 2 para dibujar la transformada de fourier
 			canvas3.setBackground(Color.WHITE);
 			canvas3.setSize(750,70);
 		*/
		panelGrafica.add(canvas7);  
			c.ipady = 0;      //make this component tall
			c.weightx = 0.0;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 0;
        panel.add(panelGrafica,c);
            c.anchor = GridBagConstraints.LAST_LINE_START;
          	c.ipady = 0;
          	c.weightx = 0;
  		  	c.gridx = 0;
		  	c.gridy = 1;
		  	c.insets = new Insets(0,1,0,1);//insertar espaciado arriba izquierda abajo derecha 
        panel.add(panelComandos,c);	
        //	ancho = canvas3.getWidth();
       // 	altura = canvas3.getHeight();
   	    	canvas7.repaint();
    //System.out.println("en voz "+yMin+" "+yMax);
    	return panel;
    	
    	}




	
	 public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(esFourieroWavelets.getLayout());
        cl.show(esFourieroWavelets, (String)evt.getItem());
    }
 

    /**
	/*action performed*/   
    public void actionPerformed(ActionEvent event) {
		
		System.out.println(event.getActionCommand());
	  //int ban=0;//para control de los canvas
	  //manejar accion abrir
	  if (event.getActionCommand() == "Abrir") {
		  int returnVal = fc.showOpenDialog(null);
				
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File fileIn = fc.getSelectedFile();
                int totalFramesRead = 0;
                int totalBytesRead = 0;
				
				try {
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
  					int bytesPerFrame = audioInputStream.getFormat().getFrameSize();	
  					int numBytes = 1024 * bytesPerFrame; 
  		        	byte[] audioBytes = new byte[numBytes];
  				// obtengo el valor de la longitud del archivo en bytes			
  				// y construyo mi objeto datos pasandole esa longitud de archivo
  				//longitud del archivo en bytes
  					int longitudArchivoBytes=(int)audioInputStream.getFormat().getFrameSize()*(int)audioInputStream.getFrameLength();
  						datos = new Datos(longitudArchivoBytes,audioInputStream.getFormat().isBigEndian());
  					byte[] datosTemporal=new byte[longitudArchivoBytes];     //arreglo de bytes temporal
  				int pos=0; 			
  				try {
    				int numBytesRead = 0;
    				int numFramesRead = 0;
    				
    				while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
      				
      					numFramesRead = numBytesRead / bytesPerFrame;
      					totalFramesRead += numFramesRead;
      				/***/
      					System.arraycopy(audioBytes, 0, datosTemporal, pos, numBytesRead);
      					pos=pos+numBytesRead;
      				
    				}
    		
      				   datos.llenarByte(datosTemporal);
      /*revisarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr*/
      				datosVoz=new double[longitudArchivoBytes/bytesPerFrame];
      				datosVoz=datos.convertirByteADouble();
      				/*valores para graficar la ventana*/
      				
      				Nombrearchivoaudio=fileIn.getName();
      				
      			    log.append("Abriendo:   "+fileIn.getName()+"."+nuevaLinea);
      				log.append("   NroBytes	Frames	BigEndian	Mayor valor	Formato"+ nuevaLinea);
    		        log.append("  "+longitudArchivoBytes+"	"+audioInputStream.getFrameLength()+"	"+audioInputStream.getFormat().isBigEndian()+"	"+datos.obtenerMayor()+"	"+audioInputStream.getFormat().toString()+nuevaLinea);
    		        
    		        //obtiene la frecuencia de sampleo
    		        FrecuenciaDeSampleo=(int)audioInputStream.getFormat().getSampleRate();
					
			/*eliminacion de segmentos inutiles*/
			 	
					datosESI=preprocesamiento.eliminarSegmentosInutiles(datosVoz);
					System.out.println("datos VN"+datosESI.length);
					
					
   			/*datos normalizados de [-1,1]*/
   					datosVozNormalizada=preprocesamiento.normalizacion(datosESI,datos.obtenerMayor());
   					
   					 ban=0;
   					 banE=0;
   				/*	trasformadaFourier=new Fourier(datosVozNormalizada);
   					trasformadaFourier.FFTradix2DIF();
   					datosFourier=trasformadaFourier.moduloII();
   					System.out.println("datos VN"+datosVozNormalizada.length);
   					System.out.println("datos Fourier"+datosFourier.length);
   					System.out.println("datos Fourier"+trasformadaFourier.obtenerMayor());	
    			*/	canvas1.graficarDatos(datosVoz,datos.obtenerMayor());
    				canvas2.graficarDatos(datosVozNormalizada,1);
    				canvas5.graficarDatos(datosESI,datos.obtenerMayor());
    			//	canvas3.graficarDatos(datosFourier,trasformadaFourier.obtenerMayor());
    		/*actualizar cajas de texto*/		
    				xMinTxt.setText(""+canvas1.xMin);
    				xMaxTxt.setText(""+canvas1.xMax);
    				yMinTxt.setText(""+canvas1.yMin);
    				yMaxTxt.setText(""+canvas1.yMax);
    				
    				xMinTxtESI.setText(""+canvas5.xMin);
    				xMaxTxtESI.setText(""+canvas5.xMax);
    				yMinTxtESI.setText(""+canvas5.yMin);
    				yMaxTxtESI.setText(""+canvas5.yMax);
    				
    				xMinTxtVN.setText(""+canvas2.xMin);
    				xMaxTxtVN.setText(""+canvas2.xMax);
    				yMinTxtVN.setText(""+canvas2.yMin);
    				yMaxTxtVN.setText(""+canvas2.yMax);
    				
    		/*		xMinTxtF.setText(""+canvas3.xMin);
    				xMaxTxtF.setText(""+canvas3.xMax);
    				yMinTxtF.setText(""+canvas3.yMin);
    				yMaxTxtF.setText(""+canvas3.yMax);
  			*/			} 
  						catch (Exception ex) { 
    			   	log.append("No se pudo leer " + nuevaLinea);
  							}
					} catch (Exception e) {
  					log.append("Tipo incompatible" + nuevaLinea);
					}
                	
                /*********/
           	 	} else {
                	log.append("abrir ha sido cancelado por el usuario" + nuevaLinea);
            	}
            	log.setCaretPosition(log.getDocument().getLength());
        	}
        //manejar accion nuevo
           if (event.getActionCommand() == "Nuevo") {           	
           CapturarAudio capturarAudioVentana= new CapturarAudio();
        }
        // manejar accion limpiar
        if (event.getActionCommand() == "Limpiar") {
           log.setText("");
           salida.setText("");
        }
        //manejar accion salir
        if (event.getActionCommand() == "Salir") {
            System.exit(0);
        }
        
         /*else if (event.getSource() == guardarBtn) {
           // int returnVal = fc.showSaveDialog(MenuRAH.this);
             int returnVal = fc.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would save the file.
                log.append("Saving: " + file.getName() + "." + nuevaLinea);
            } else {
                log.append("Save command cancelled by user." + nuevaLinea);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }*/
        
        /**/
        if (event.getSource() == graficarBtn) {
        	      
                  canvas1.establecerxMax(Double.parseDouble(xMaxTxt.getText()));
                  canvas1.establecerxMin(Double.parseDouble(xMinTxt.getText()));
                  canvas1.estableceryMax(Double.parseDouble(yMaxTxt.getText()));
                  canvas1.estableceryMin(Double.parseDouble(yMinTxt.getText()));
			      canvas1.repaint();
			  
	       
			
			}
		if (event.getSource() == graficarVNBtn) {
         		  canvas2.establecerxMax(Double.parseDouble(xMaxTxtVN.getText()));
                  canvas2.establecerxMin(Double.parseDouble(xMinTxtVN.getText()));
                  canvas2.estableceryMax(Double.parseDouble(yMaxTxtVN.getText()));
                  canvas2.estableceryMin(Double.parseDouble(yMinTxtVN.getText()));
			      canvas2.repaint();
		   	
			}	
			
		if (event.getSource() == graficarESIBtn) {
         		  canvas5.establecerxMax(Double.parseDouble(xMaxTxtESI.getText()));
                  canvas5.establecerxMin(Double.parseDouble(xMinTxtESI.getText()));
                  canvas5.estableceryMax(Double.parseDouble(yMaxTxtESI.getText()));
                  canvas5.estableceryMin(Double.parseDouble(yMinTxtESI.getText()));
			      canvas5.repaint();
		   	
			}		
			
		
			
		if (event.getSource() == graficarPSFBtn) {
				if(ban==0){
					trasformadaFourier=new Fourier(datosVozNormalizada);
   					trasformadaFourier.FFTradix2DIF();
   					datosFourier=trasformadaFourier.moduloII();
   					canvas3.graficarDatos(datosFourier,trasformadaFourier.obtenerMayor());
   					xMinTxtF.setText(""+canvas3.xMin);
    				xMaxTxtF.setText(""+canvas3.xMax);
    				yMinTxtF.setText(""+canvas3.yMin);
    				yMaxTxtF.setText(""+canvas3.yMax);
					ban=1;
					canvas3.repaint();
			
					}
				else{
         		  canvas3.establecerxMax(Double.parseDouble(xMaxTxtF.getText()));
                  canvas3.establecerxMin(Double.parseDouble(xMinTxtF.getText()));
                  canvas3.estableceryMax(Double.parseDouble(yMaxTxtF.getText()));
                  canvas3.estableceryMin(Double.parseDouble(yMinTxtF.getText()));
			      canvas3.repaint();
		   		}
			}
				
			if (event.getSource() == graficarEspectroBtn) {
				if(banE==0){
/*********************************************************************/					
//aca probar con voces normalizadas y sin normalizarrrrrrr					
	//				reconocer=new reconocerFourier(datosVozNormalizada,16000);
				// me salen valores en el calculo del espectro negativos	
	  	          reconocer=new reconocerFourier(datosESI,16000);
					System.out.println("obtenerMayorValorEspectograma() "+reconocer.obtenerMayorEspectro());
   	               canvas7.graficarDatos(reconocer.obtenerEspectro(),reconocer.obtenerMayorEspectro());
   					
   					
		     		banE=1;
					canvas7.repaint();
					}
				else{
        
		           canvas7.repaint();
		   		}
			}
			
		if (event.getSource() == resetearBtn) {
         		  canvas1.resetear();                  
			      canvas1.repaint();
			}		
			
		if (event.getSource() == resetearESIBtn) {
         		  canvas5.resetear();
			      canvas5.repaint();
			}				
			
		if (event.getSource() == resetearVNBtn) {
         		  canvas2.resetear();
			      canvas2.repaint();
			}			
			
		if (event.getSource() == resetearPSFBtn) {
         		  canvas3.resetear();
			      canvas3.repaint();
		   	
			}			
			        /**/
		
		if(event.getActionCommand()=="Plantilla"){
			
				String s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    				
        	    plantilla p=new plantilla(s,reconocer.obtenerMatrizMFCC());					
        	    patronesPlantillasArchivo.insertar(p,"plantillaMFCC");
        	    }
/*descoemntar y borrar el parentesis       	    
        	    /****************/
        	  // s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);
/*descomentar
			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerHaar = new reconocerWaveletHaar(datosESI);				;
        	    p=new plantilla(s,reconocerHaar.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaHaar");
        	    /****************/
/***************************************************************************/

			
				 //s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

/*descomentar			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerDb2 = new reconocerWaveletDaubechies(datosESI);				;
        	     p=new plantilla(s,reconocerDb2.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaDb2");
			
	
			
				 //s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerDb3 = new reconocerWaveletDaubechies3(datosESI);				;
        	     p=new plantilla(s,reconocerDb3.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaDb3");
			
	
			
//				 s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerCoiflet = new reconocerWaveletCoiflet(datosESI);				;
        	     p=new plantilla(s,reconocerCoiflet.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaCoiflet");
			
	
			
//				 s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerPacketDb2 = new reconocerWaveletPacket(datosESI);				;
        	     p=new plantilla(s,reconocerPacketDb2.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaWaveletPacketDb2");
			
	
			
//				 s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerPacketWalsh = new reconocerWaveletPacketWalsh(datosESI);				;
        	     p=new plantilla(s,reconocerPacketWalsh.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaWaveletPacketWalsh");
			
		
//				 s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerPacketDb3 = new reconocerWaveletPacketDb3(datosESI);				;
        	     p=new plantilla(s,reconocerPacketDb3.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaWaveletPacketDb3");
			
	
			
//				 s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerPacketPSBC = new reconocerWaveletPacketPSBC(datosESI);				;
        	     p=new plantilla(s,reconocerPacketPSBC.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaWaveletPacketPSBC");
			
		}
/***************************************************************************/
			
		
		
		if(event.getActionCommand()=="PlantillaHaar"){
			
				String s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerHaar = new reconocerWaveletHaar(datosESI);				;
        	    plantilla p=new plantilla(s,reconocerHaar.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaHaar");
			
		}
		
		if(event.getActionCommand()=="PlantillaDb2"){
			
				String s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerDb2 = new reconocerWaveletDaubechies(datosESI);				;
        	    plantilla p=new plantilla(s,reconocerDb2.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaDb2");
			
		}
		
		if(event.getActionCommand()=="PlantillaDb3"){
			
				String s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerDb3 = new reconocerWaveletDaubechies3(datosESI);				;
        	    plantilla p=new plantilla(s,reconocerDb3.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaDb3");
			
		}
		
		if(event.getActionCommand()=="PlantillaCoiflet"){
			
				String s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerCoiflet = new reconocerWaveletCoiflet(datosESI);				;
        	    plantilla p=new plantilla(s,reconocerCoiflet.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaCoiflet");
			
		}
		if(event.getActionCommand()=="PlantillaWPDb4"){
			
				String s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerPacketDb2 = new reconocerWaveletPacket(datosESI);				;
        	    plantilla p=new plantilla(s,reconocerPacketDb2.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaWaveletPacketDb2");
			
		}
		
		if(event.getActionCommand()=="PlantillaWalsh"){
			
				String s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerPacketWalsh = new reconocerWaveletPacketWalsh(datosESI);				;
        	    plantilla p=new plantilla(s,reconocerPacketWalsh.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaWaveletPacketWalsh");
			
		}
		if(event.getActionCommand()=="PlantillaWPDb3"){
			
				String s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerPacketDb3 = new reconocerWaveletPacketDb3(datosESI);				;
        	    plantilla p=new plantilla(s,reconocerPacketDb3.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaWaveletPacketDb3");
			
		}
		
		if(event.getActionCommand()=="PlantillaPSBC"){
			
				String s = (String)JOptionPane.showInputDialog(this.frame,"Nombre : ","Plantilla",JOptionPane.PLAIN_MESSAGE,icon,null,null);

			                    if ((s != null) && (s.length() > 0)) {
			                    	
			  
        	    					}	
        	    reconocerPacketPSBC = new reconocerWaveletPacketPSBC(datosESI);				;
        	    plantilla p=new plantilla(s,reconocerPacketPSBC.matrizCaracteristicas);					
        	    patronesPlantillasArchivo.insertar(p,"plantillaWaveletPacketPSBC");
			
		}
		
		
       /*****************************************************/
       //DWT para los MFCC 
        if(event.getActionCommand()=="Reconocer"){
        	
            DynamicTypeWarping DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	plantilla [] p=patronesPlantillasArchivo.leer("plantillaMFCC");
        	double minimaDistancia1=100000;
        	double minimaDistancia2=100000;
        	double minimaDistancia3=100000;
        	double minimaDistancia4=100000;
        	double distancia1,distancia2,distancia3,distancia4;
        	
        	String ganador1=" ",ganador2=" ",ganador3=" ",ganador4=" ";
        	
        	salida.append("������������������������������������������������"+nuevaLinea);
        	salida.append("�������"+Nombrearchivoaudio+"������������������"+nuevaLinea);
        	
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
//        			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocer.obtenerMatrizMFCC(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocer.obtenerMatrizMFCC(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocer.obtenerMatrizMFCC(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocer.obtenerMatrizMFCC(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salida.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salida.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
					salida.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salida.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salida.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salida.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salida.append("������������������������������������������������"+nuevaLinea);
			
			/************************/
/*descomentar			
			 DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	 p=patronesPlantillasArchivo.leer("plantillaHaar");
        	 minimaDistancia1=100000;
        	 minimaDistancia2=100000;
        	 minimaDistancia3=100000;
        	 minimaDistancia4=100000;
        	//double distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerHaar = new reconocerWaveletHaar(datosESI);
        	
        	 ganador1=" ";ganador2=" ";ganador3=" ";ganador4=" ";
        	
        	salidaHaar.append("������������������������������������������������"+nuevaLinea);
        	salidaHaar.append("�������"+Nombrearchivoaudio+"������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
//        			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerHaar.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerHaar.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerHaar.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerHaar.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaHaar.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaHaar.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
/*descomentar								salidaHaar.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaHaar.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaHaar.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaHaar.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaHaar.append("������������������������������������������������"+nuevaLinea);
	
			
			/************************/
			/************************/
			/**************************************************************/
		//DWT para coeficientes basados en los wavelets de daubechies2
	 
        	
/*descomentar			             DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	 p=patronesPlantillasArchivo.leer("plantillaDb2");
        	 minimaDistancia1=100000;
        	 minimaDistancia2=100000;
        	 minimaDistancia3=100000;
        	 minimaDistancia4=100000;
        //	 distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerDb2 = new reconocerWaveletDaubechies(datosESI);
        	
        	 ganador1=" ";ganador2=" ";ganador3=" ";ganador4=" ";
        	
        	salidaDaubechies2.append("������������������������������������������������"+nuevaLinea);
        	salidaDaubechies2.append("�������"+Nombrearchivoaudio+"������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
//        			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaDaubechies2.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaDaubechies2.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
/*descomentar								salidaDaubechies2.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaDaubechies2.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaDaubechies2.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaDaubechies2.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaDaubechies2.append("������������������������������������������������"+nuevaLinea);
			
	/**************************************************************/
	
		//DWT para coeficientes basados en los wavelets de daubechies3
	 
/*descomentar			        	
             DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	 p=patronesPlantillasArchivo.leer("plantillaDb3");
        	 minimaDistancia1=100000;
        	 minimaDistancia2=100000;
        	 minimaDistancia3=100000;
        	 minimaDistancia4=100000;
        //	 distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerDb3 = new reconocerWaveletDaubechies3(datosESI);
        	
        	ganador1=" ";ganador2=" ";ganador3=" ";ganador4=" ";
        	
        	salidaDaubechies3.append("������������������������������������������������"+nuevaLinea);
        	salidaDaubechies3.append("�������"+Nombrearchivoaudio+"������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
//        			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaDaubechies3.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaDaubechies3.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
/*descomentar								salidaDaubechies3.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaDaubechies3.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaDaubechies3.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaDaubechies3.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaDaubechies3.append("������������������������������������������������"+nuevaLinea);
			
	/**************************************************************/
	
		//DWT para coeficientes basados en los wavelets de Coiflet
	
/*descomentar			        	
             DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	 p=patronesPlantillasArchivo.leer("plantillaCoiflet");
        	 minimaDistancia1=100000;
        	 minimaDistancia2=100000;
        	 minimaDistancia3=100000;
        	 minimaDistancia4=100000;
        //	 distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerCoiflet = new reconocerWaveletCoiflet(datosESI);
        	
        ganador1=" ";ganador2=" ";ganador3=" ";ganador4=" ";
        	
        	salidaCoiflet.append("������������������������������������������������"+nuevaLinea);
        	salidaCoiflet.append("�������"+Nombrearchivoaudio+"������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
//        			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerCoiflet.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerCoiflet.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerCoiflet.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerCoiflet.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaCoiflet.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaCoiflet.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
/*descomentar								salidaCoiflet.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaCoiflet.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaCoiflet.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaCoiflet.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaCoiflet.append("������������������������������������������������"+nuevaLinea);
		
	/**************************************************************/
	
		//DWT para coeficientes basados en los wavelets packet perceptuales daubechies 2
/*descomentar				 
        	
             DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	 p=patronesPlantillasArchivo.leer("plantillaWaveletPacketDb2");
        	 minimaDistancia1=100000;
        	 minimaDistancia2=100000;
        	 minimaDistancia3=100000;
        	 minimaDistancia4=100000;
    //    	 distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerPacketDb2 = new reconocerWaveletPacket(datosESI);
        	
        	ganador1=" ";ganador2=" ";ganador3=" ";ganador4=" ";
        	
        	salidaWaveletPacket.append("������������������������������������������������"+nuevaLinea);
        	salidaWaveletPacket.append("�������"+Nombrearchivoaudio+"������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
//        			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerPacketDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerPacketDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerPacketDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerPacketDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaWaveletPacket.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaWaveletPacket.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
/*descomentar								salidaWaveletPacket.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaWaveletPacket.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaWaveletPacket.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaWaveletPacket.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaWaveletPacket.append("������������������������������������������������"+nuevaLinea);
		
	/**************************************************************/	
	//DWT para coeficientes basados en los wavelets packet perceptuales walsh
	 
/*descomentar			        	
             DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
         p=patronesPlantillasArchivo.leer("plantillaWaveletPacketWalsh");
        	 minimaDistancia1=100000;
        	 minimaDistancia2=100000;
        	 minimaDistancia3=100000;
        	 minimaDistancia4=100000;
        	 //distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerPacketWalsh = new reconocerWaveletPacketWalsh(datosESI);
        	
        ganador1=" ";ganador2=" ";ganador3=" ";ganador4=" ";
        	
        	salidaWalsh.append("������������������������������������������������"+nuevaLinea);
        	salidaWalsh.append("�������"+Nombrearchivoaudio+"������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
//        			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerPacketWalsh.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerPacketWalsh.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerPacketWalsh.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerPacketWalsh.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaWalsh.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaWalsh.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
/*descomentar								salidaWalsh.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaWalsh.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaWalsh.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaWalsh.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaWalsh.append("������������������������������������������������"+nuevaLinea);
			
	/**************************************************************/	
	//DWT para coeficientes basados en los wavelets packet perceptuales Db3
	 
/*descomentar			        	
             DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	 p=patronesPlantillasArchivo.leer("plantillaWaveletPacketDb3");
        	 minimaDistancia1=100000;
        	 minimaDistancia2=100000;
        	 minimaDistancia3=100000;
        	 minimaDistancia4=100000;
        	 //distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerPacketDb3 = new reconocerWaveletPacketDb3(datosESI);
        	
        	ganador1=" ";ganador2=" ";ganador3=" ";ganador4=" ";
        	
        	salidaPacketDb3.append("������������������������������������������������"+nuevaLinea);
        	salidaPacketDb3.append("�������"+Nombrearchivoaudio+"������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
//        			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerPacketDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerPacketDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerPacketDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerPacketDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaPacketDb3.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaPacketDb3.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
/*descomentar								salidaPacketDb3.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaPacketDb3.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaPacketDb3.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaPacketDb3.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaPacketDb3.append("������������������������������������������������"+nuevaLinea);
			
	/**************************************************************/	
	//DWT para coeficientes basados en los wavelets packet perceptuales PSBC
	 
/*descomentar			        	
             DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	 p=patronesPlantillasArchivo.leer("plantillaWaveletPacketPSBC");
        	 minimaDistancia1=100000;
        	 minimaDistancia2=100000;
        	 minimaDistancia3=100000;
        	 minimaDistancia4=100000;
        	 //distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerPacketPSBC = new reconocerWaveletPacketPSBC(datosESI);
        	
        	ganador1=" ";ganador2=" ";ganador3=" ";ganador4=" ";
        	
        	salidaWaveletPacketPSBC.append("������������������������������������������������"+nuevaLinea);
        	salidaWaveletPacketPSBC.append("�������"+Nombrearchivoaudio+"������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
 //       			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerPacketPSBC.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerPacketPSBC.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerPacketPSBC.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerPacketPSBC.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaWaveletPacketPSBC.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaWaveletPacketPSBC.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
/*descomentar								salidaWaveletPacketPSBC.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaWaveletPacketPSBC.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaWaveletPacketPSBC.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaWaveletPacketPSBC.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaWaveletPacketPSBC.append("������������������������������������������������"+nuevaLinea);
descomentar	*/		}
			/************************/
/*******************************************************************************************************/			
		
	/**************************************************************/
	//DWT para coeficientes basados en los wavelets de haar
	 if(event.getActionCommand()=="ReconocerHaar"){
        	
            DynamicTypeWarping DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	plantilla [] p=patronesPlantillasArchivo.leer("plantillaHaar");
        	double minimaDistancia1=100000;
        	double minimaDistancia2=100000;
        	double minimaDistancia3=100000;
        	double minimaDistancia4=100000;
        	double distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerHaar = new reconocerWaveletHaar(datosESI);
        	
        	String ganador1=" ",ganador2=" ",ganador3=" ",ganador4=" ";
        	
        	salidaHaar.append("������������������������������������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
     //   			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerHaar.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerHaar.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerHaar.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerHaar.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaHaar.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaHaar.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
					salidaHaar.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaHaar.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaHaar.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaHaar.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaHaar.append("������������������������������������������������"+nuevaLinea);
		}	
	/**************************************************************/
		//DWT para coeficientes basados en los wavelets de daubechies2
	 if(event.getActionCommand()=="ReconocerDb2"){
        	
            DynamicTypeWarping DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	plantilla [] p=patronesPlantillasArchivo.leer("plantillaDb2");
        	double minimaDistancia1=100000;
        	double minimaDistancia2=100000;
        	double minimaDistancia3=100000;
        	double minimaDistancia4=100000;
        	double distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerDb2 = new reconocerWaveletDaubechies(datosESI);
        	
        	String ganador1=" ",ganador2=" ",ganador3=" ",ganador4=" ";
        	
        	salidaDaubechies2.append("������������������������������������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
       // 			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaDaubechies2.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaDaubechies2.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
					salidaDaubechies2.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaDaubechies2.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaDaubechies2.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaDaubechies2.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaDaubechies2.append("������������������������������������������������"+nuevaLinea);
		}	
	/**************************************************************/
	
	/**************************************************************/
	/**************************************************************/
	
		//DWT para coeficientes basados en los wavelets de daubechies3
	 if(event.getActionCommand()=="ReconocerDb3"){
        	
            DynamicTypeWarping DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	plantilla [] p=patronesPlantillasArchivo.leer("plantillaDb3");
        	double minimaDistancia1=100000;
        	double minimaDistancia2=100000;
        	double minimaDistancia3=100000;
        	double minimaDistancia4=100000;
        	double distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerDb3 = new reconocerWaveletDaubechies3(datosESI);
        	
        	String ganador1=" ",ganador2=" ",ganador3=" ",ganador4=" ";
        	
        	salidaDaubechies3.append("������������������������������������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
        //			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaDaubechies3.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaDaubechies3.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
					salidaDaubechies3.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaDaubechies3.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaDaubechies3.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaDaubechies3.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaDaubechies3.append("������������������������������������������������"+nuevaLinea);
		}	
	/**************************************************************/
	
		//DWT para coeficientes basados en los wavelets de Coiflet
	 if(event.getActionCommand()=="ReconocerCoiflet"){
        	
            DynamicTypeWarping DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	plantilla [] p=patronesPlantillasArchivo.leer("plantillaCoiflet");
        	double minimaDistancia1=100000;
        	double minimaDistancia2=100000;
        	double minimaDistancia3=100000;
        	double minimaDistancia4=100000;
        	double distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerCoiflet = new reconocerWaveletCoiflet(datosESI);
        	
        	String ganador1=" ",ganador2=" ",ganador3=" ",ganador4=" ";
        	
        	salidaCoiflet.append("������������������������������������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
        //			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerCoiflet.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerCoiflet.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerCoiflet.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerCoiflet.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaCoiflet.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaCoiflet.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
					salidaCoiflet.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaCoiflet.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaCoiflet.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaCoiflet.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaCoiflet.append("������������������������������������������������"+nuevaLinea);
		}	
	/**************************************************************/
	
		//DWT para coeficientes basados en los wavelets packet perceptuales daubechies 2
	 if(event.getActionCommand()=="ReconocerWPDb4"){
        	
            DynamicTypeWarping DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	plantilla [] p=patronesPlantillasArchivo.leer("plantillaWaveletPacketDb2");
        	double minimaDistancia1=100000;
        	double minimaDistancia2=100000;
        	double minimaDistancia3=100000;
        	double minimaDistancia4=100000;
        	double distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerPacketDb2 = new reconocerWaveletPacket(datosESI);
        	
        	String ganador1=" ",ganador2=" ",ganador3=" ",ganador4=" ";
        	
        	salidaWaveletPacket.append("������������������������������������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
        //			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerPacketDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerPacketDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerPacketDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerPacketDb2.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaWaveletPacket.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaWaveletPacket.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
					salidaWaveletPacket.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaWaveletPacket.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaWaveletPacket.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaWaveletPacket.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaWaveletPacket.append("������������������������������������������������"+nuevaLinea);
		}	
	/**************************************************************/	
	//DWT para coeficientes basados en los wavelets packet perceptuales walsh
	 if(event.getActionCommand()=="ReconocerWalsh"){
        	
            DynamicTypeWarping DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	plantilla [] p=patronesPlantillasArchivo.leer("plantillaWaveletPacketWalsh");
        	double minimaDistancia1=100000;
        	double minimaDistancia2=100000;
        	double minimaDistancia3=100000;
        	double minimaDistancia4=100000;
        	double distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerPacketWalsh = new reconocerWaveletPacketWalsh(datosESI);
        	
        	String ganador1=" ",ganador2=" ",ganador3=" ",ganador4=" ";
        	
        	salidaWalsh.append("������������������������������������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
        //			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerPacketWalsh.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerPacketWalsh.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerPacketWalsh.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerPacketWalsh.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaWalsh.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaWalsh.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
					salidaWalsh.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaWalsh.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaWalsh.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaWalsh.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaWalsh.append("������������������������������������������������"+nuevaLinea);
		}	
	/**************************************************************/	
	//DWT para coeficientes basados en los wavelets packet perceptuales Db3
	 if(event.getActionCommand()=="ReconocerWPDb3"){
        	
            DynamicTypeWarping DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	plantilla [] p=patronesPlantillasArchivo.leer("plantillaWaveletPacketDb3");
        	double minimaDistancia1=100000;
        	double minimaDistancia2=100000;
        	double minimaDistancia3=100000;
        	double minimaDistancia4=100000;
        	double distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerPacketDb3 = new reconocerWaveletPacketDb3(datosESI);
        	
        	String ganador1=" ",ganador2=" ",ganador3=" ",ganador4=" ";
        	
        	salidaPacketDb3.append("������������������������������������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
        //			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerPacketDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerPacketDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerPacketDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerPacketDb3.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaPacketDb3.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaPacketDb3.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
					salidaPacketDb3.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaPacketDb3.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaPacketDb3.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaPacketDb3.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaPacketDb3.append("������������������������������������������������"+nuevaLinea);
	}		
	/**************************************************************/	
	//DWT para coeficientes basados en los wavelets packet perceptuales PSBC
	 if(event.getActionCommand()=="ReconocerPSBC"){
        	
            DynamicTypeWarping DWT=new DynamicTypeWarping(8); //tama�o de ventana de ajuste=10
        	plantilla [] p=patronesPlantillasArchivo.leer("plantillaWaveletPacketPSBC");
        	double minimaDistancia1=100000;
        	double minimaDistancia2=100000;
        	double minimaDistancia3=100000;
        	double minimaDistancia4=100000;
        	double distancia1,distancia2,distancia3,distancia4;
        	
        	reconocerPacketPSBC = new reconocerWaveletPacketPSBC(datosESI);
        	
        	String ganador1=" ",ganador2=" ",ganador3=" ",ganador4=" ";
        	
        	salidaWaveletPacketPSBC.append("������������������������������������������������"+nuevaLinea);
        	
        	for(int opcion=1;opcion<=2;opcion++)  //para las opciones de las distancias
        	{
        		DWT.opcionDistancia(opcion);
        		
        		for(int i=0;i<p.length;i++){
        		
        		if(p[i]!=null){
        //			System.out.println(p[i].obtenerNombre());
        			
        			distancia1=DWT.matching(reconocerPacketPSBC.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia1<minimaDistancia1){
        				minimaDistancia1=distancia1;
        				ganador1=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia2=DWT.matching_p_1(reconocerPacketPSBC.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia2<minimaDistancia2){
        				minimaDistancia2=distancia2;
        				ganador2=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia3=DWT.simetrico_p_0(reconocerPacketPSBC.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia3<minimaDistancia3){
        				minimaDistancia3=distancia3;
        				ganador3=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        			
        			distancia4=DWT.simetrico_p_1(reconocerPacketPSBC.matrizCaracteristicas(),p[i].obtnerMatriz());
        			if (distancia4<minimaDistancia4){
        				minimaDistancia4=distancia4;
        				ganador4=p[i].obtenerNombre();
        				}
        //			System.out.println("distancia matching p=0 "+distancia);
        		}
        		
        		}
        			
        			if(opcion==1){
        	//			System.out.println("Distancia euclidea");
        				
        				salidaWaveletPacketPSBC.append("     ���������Distancia euclidea���������"+nuevaLinea);
        				}
        			else{
        	//			System.out.println("	Distancia Chebyshev");
        				salidaWaveletPacketPSBC.append("     ���������Distancia Chebyshev���������"+nuevaLinea);
        				}	
        			
        	/*		System.out.println(" Distancia white and neely               : "+minimaDistancia1+" Palabra reconocida :"+ganador1);
					System.out.println(" Distancia white and neely p=1           : "+minimaDistancia2+" Palabra reconocida :"+ganador2);
					System.out.println(" Distancia sakoe and chiba simetrico p=0 : "+minimaDistancia3+" Palabra reconocida :"+ganador3);
					System.out.println(" Distancia sakoe and chiba simetrico p=1 : "+minimaDistancia4+" Palabra reconocida :"+ganador4);
			*/		
					salidaWaveletPacketPSBC.append("� white and neely  : "+minimaDistancia1+nuevaLinea+"	Palabra reconocida :"+ganador1+nuevaLinea);
					salidaWaveletPacketPSBC.append("� white and neely p=1   : "+minimaDistancia2+nuevaLinea+"	Palabra reconocida :"+ganador2+nuevaLinea);
					salidaWaveletPacketPSBC.append("� sakoe and chiba simetrico p=0 : "+minimaDistancia3+nuevaLinea+"	Palabra reconocida :"+ganador3+nuevaLinea);
					salidaWaveletPacketPSBC.append("� sakoe and chiba simetrico p=1 : "+minimaDistancia4+nuevaLinea+"	Palabra reconocida :"+ganador4+nuevaLinea);
					
        			
        		}
        
			salidaWaveletPacketPSBC.append("������������������������������������������������"+nuevaLinea);
		}	
	
		if(event.getActionCommand()=="graficarHaar"){
			
		wavelet_haar haar=new wavelet_haar(datosESI);
		
		//matriz con los valores de los coeficientes en cada escala util para graficar
		double [][]matriz=haar.obtenerMatrizWavelet();
			
		JFrame.setDefaultLookAndFeelDecorated(true);
        pantalla ventana = new pantalla(matriz);
        JFrame frame = new JFrame("Coeficientes Wavelet Haar");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        frame.setContentPane(ventana.panelPrincipal);
        frame.pack();
        frame.setVisible(true);
			/**/
			
			
			} 
        if(event.getActionCommand()=="graficarDb2"){
			
		wavelet_daubechies Db2=new wavelet_daubechies(datosESI);
		
		//matriz con los valores de los coeficientes en cada escala util para graficar
		double [][]matriz=Db2.obtenerMatrizWavelet();
			
		JFrame.setDefaultLookAndFeelDecorated(true);
        pantalla ventana = new pantalla(matriz);
        JFrame frame = new JFrame("Coeficientes Wavelet Daubechies 2");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        frame.setContentPane(ventana.panelPrincipal);
        frame.pack();
        frame.setVisible(true);
			/**/
			
			}
			
		if(event.getActionCommand()=="graficarDb3"){
			
		wavelet_daubechiesdb3 Db3=new wavelet_daubechiesdb3(datosESI);
		
		//matriz con los valores de los coeficientes en cada escala util para graficar
		double [][]matriz=Db3.obtenerMatrizWavelet();
			
		JFrame.setDefaultLookAndFeelDecorated(true);
        pantalla ventana = new pantalla(matriz);
        JFrame frame = new JFrame("Coeficientes Wavelet Daubechies 3");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        frame.setContentPane(ventana.panelPrincipal);
        frame.pack();
        frame.setVisible(true);
			/**/
			
			
			}	
			
		if(event.getActionCommand()=="graficarCoiflet"){
			
		wavelet_coiflet coiflet=new wavelet_coiflet(datosESI);
		
		//matriz con los valores de los coeficientes en cada escala util para graficar
		double [][]matriz=coiflet.obtenerMatrizWavelet();
			
		JFrame.setDefaultLookAndFeelDecorated(true);
        pantalla ventana = new pantalla(matriz);
        JFrame frame = new JFrame("Coeficientes Wavelet Coiflet");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        frame.setContentPane(ventana.panelPrincipal);
        frame.pack();
        frame.setVisible(true);
			/**/
			
			
			}		
        			
    }

         /*pone los iconos en la imagen*/
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MenuRAH.class.getResource(path);
        	if (imgURL != null) {
            return new ImageIcon(imgURL);
        	}
        	else {
            	System.err.println("Couldn't find file: " + path);
            return null;
        	}
    }


	/*crea y muestra el GUI*/
    private static void createAndShowGUI() {
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Reconocedor de palabras :     Lorito          jorjasso@hotmail.com  & josc_orlando@hotmail.com");
        MenuRAH ventana = new MenuRAH(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setContentPane(ventana.panelPrincipal);
        frame.pack();
        frame.setVisible(true);
    }

    /*principal*/
    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
  
  }
  /*acomodar*/
/*  
1.	???????
2.	???????
3.	????????????
4.	?????????????
5.	?
6.	?
7.	o
8.	?
9.	?
10.	?
11.	?
12.	?
13.	?
14.	?
15.	�
16.	?
17.	?
18.	?
19.	?
20.	
21.	�
22.	?
23.	?
24.	?
25.	?
26.	?
27.	?
28.	?
29.	?
30.	?
31.	?
32.	 
33.	�
34.	"
35.	#
36.	$
37.	%
38.	&
39.	'
40.	(
41.	)
42.	*
43.	+
44.	,
45.	-
46.	.
47.	/
48.	0
49.	1
50.	2
51.	3
52.	4
53.	5
54.	6
55.	7
56.	8
57.	9
58.	:
59.	;
60.	<
61.	=
62.	>
63.	�
64.	@
65.	A
66.	B
67.	C
68.	D
69.	E
70.	F
71.	G
72.	H
73.	I
74.	J
75.	K
76.	L
77.	M
78.	N
79.	O
80.	P
81.	Q
82.	R
83.	S
84.	T
85.	U
86.	V
87.	W
88.	X
89.	Y
90.	Z
91.	[
92.	\
93.	]
94.	^
95.	_
96.	`
97.	a
98.	b
99.	c
100.	d
101.	e
102.	f
103.	g
104.	h
105.	i
106.	j
107.	k
108.	l
109.	m
110.	n
111.	o
112.	p
113.	q
114.	r
115.	s
116.	t
117.	u
118.	v
119.	w
120.	?
121.	y
122.	z
123.	{
124.	|
125.	}
126.	~
127.	�
128.	�
129.	�
130.	�
131.	�
132.	�
133.	�
134.	�
135.	�
136.	�
137.	�
138.	�
139.	�
140.	�
141.	�
142.	�
143.	�
144.	�
145.	�
146.	�
147.	�
148.	�
149.	�
150.	�
151.	�
152.	�
153.	�
154.	�
155.	�
156.	�
157.	�
158.	�
159.	�
160.	�
161.	�
162.	�
163.	�
164.	�
165.	�
166.	�
167.	�
168.	�
169.	�
170.	
171.	�
172.	�
173.	�
174.	"
175.	"
176.	�
177.	�
178.	� 
179.	�
180.	�
181.	�
182.	�
183.	�
184.	�
185.	�
186.	�
187.	+
188.	+
189.	�
190.	�
191.	+
192.	+
193.	-
194.	-
195.	+
196.	-
197.	+
198.	�
199.	�
200.	+
201.	+
202.	-
203.	-
204.	�
205.	-
206.	+
207.	�
208.	�
209.	�
210.	�
211.	�
212.	�
213.	i
214.	�
215.	�
216.	�
217.	+
218.	+
219.	�
220.	_
221.	�
222.	�
223.	�
224.	�
225.	�
226.	�
227.	�
228.	�
229.	�
230.	�
231.	�
232.	�
233.	�
234.	�
235.	�
236.	�
237.	�
238.	�
239.	�
240.	�
241.	�
242.	=
243.	�
244.	
245.	�
246.	�
247.	�
248.	�
249.	�
250.	�
251.	�
252.	�
253.	�
254.	�
255.	 
256.	 






*/