//Jorge Guevara Diaz
//bach Ciencias de la Computacion
//speech recognition usando wavelets 
//jorge.jorjasso@gmail.com  . jorjasso@hotmail.com

//package capturar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

public class CapturarAudio extends JFrame{

  boolean stopCapture = false;
  ByteArrayOutputStream byteArrayOutputStream;
  AudioFormat audioFormat;
  TargetDataLine targetDataLine;
  AudioInputStream audioInputStream;
  SourceDataLine sourceDataLine;

/*  public static void main(String args[]){
    new CapturarAudio();
  }//fin del main
*/
  public CapturarAudio(){//constructor
    final JButton captureBtn = new JButton("Capturar");                        
    final JButton stopBtn = new JButton("parar");
    final JButton playBtn = new JButton("Reproducir");
                         

    captureBtn.setEnabled(true);
    stopBtn.setEnabled(false);
    playBtn.setEnabled(false);

    
    captureBtn.addActionListener(
      new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	                 
          captureBtn.setEnabled(false);
          stopBtn.setEnabled(true);
          playBtn.setEnabled(false);
          //Captura los datos del micrófono 
          captureAudio();
        }//actionPerformed
      }//ActionListener
    );//addActionListener()
    getContentPane().add(captureBtn);

    stopBtn.addActionListener(
      new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	                 
          captureBtn.setEnabled(true);
          stopBtn.setEnabled(false);
          playBtn.setEnabled(true);
          //Para de capturar datos del micrófono
          stopCapture = true;
        }//actionPerformed
      }//ActionListener
    );//addActionListener()
    getContentPane().add(stopBtn);

    playBtn.addActionListener(
      new ActionListener(){
        public void actionPerformed(
        	                 ActionEvent e){
          //Reproduce los datos capturados
          playAudio();
        }//actionPerformed
      }//ActionListener
    );//addActionListener()
    getContentPane().add(playBtn);

    getContentPane().setLayout(new FlowLayout());
    setTitle("Capturar Voz");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(250,350);
    setVisible(true);
  }//constructor

  //Captura audio en un micrófono
  // y los almacena en un objeto ByteArrayOutputStream
  private void captureAudio(){
    try{
      //Obtiene todos los mixers disponibles
      Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
                      
      System.out.println("Mixers Disponibles:");
      
      for(int cnt = 0; cnt < mixerInfo.length; cnt++){
                                         
      	System.out.println(mixerInfo[cnt].getName());
      	                              
      }

      
      audioFormat = getAudioFormat();

      DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class,audioFormat);
      

      Mixer mixer = AudioSystem.getMixer(mixerInfo[3]);
                          
      
      //Obtiene un TargetDataLine en el mixer seleccionado
      
      targetDataLine = (TargetDataLine)mixer.getLine(dataLineInfo);
                     
      //Prepara la linea para usarla
      targetDataLine.open(audioFormat);
      targetDataLine.start();

      //Crea un hilo que captura los datos del micrófono
      
      Thread captureThread = new CaptureThread();
      captureThread.start();
    } catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }//
  }//captureAudio

  //Reproduce los datos salvados en 
  // ByteArrayOutputStream
  private void playAudio() {
    try{
      //Get everything set up for playback.
      //Get the previously-saved data into a byte
      // array object.
      byte audioData[] = byteArrayOutputStream.
                                   toByteArray();
      //Get an input stream on the byte array
      // containing the data
      InputStream byteArrayInputStream =
             new ByteArrayInputStream(audioData);
      AudioFormat audioFormat = getAudioFormat();
      audioInputStream = new AudioInputStream(
                    byteArrayInputStream,
                    audioFormat,
                    audioData.length/audioFormat.
                                 getFrameSize());
      DataLine.Info dataLineInfo = 
                            new DataLine.Info(
                            SourceDataLine.class,
                            audioFormat);
      sourceDataLine = (SourceDataLine)
               AudioSystem.getLine(dataLineInfo);
      sourceDataLine.open(audioFormat);
      sourceDataLine.start();

      
      Thread playThread = new PlayThread();
      playThread.start();
    } catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }//catch
  }//playAudio

  
  private AudioFormat getAudioFormat(){
    float sampleRate = 8000.0F;
    //8000,11025,16000,22050,44100
    int sampleSizeInBits = 16;
    //8,16
    int channels = 1;
    //1,2
    boolean signed = true;
    //true,false
    boolean bigEndian = false;
    //true,false
    return new AudioFormat(
                      sampleRate,
                      sampleSizeInBits,
                      channels,
                      signed,
                      bigEndian);
  }// getAudioFormat
//=============================================//


class CaptureThread extends Thread{
 
  byte tempBuffer[] = new byte[10000];
  public void run(){
    byteArrayOutputStream =new ByteArrayOutputStream();
    stopCapture = false;
    try{//Loop until stopCapture is set by
        // another thread that services the Stop
        // button.
      while(!stopCapture){
        //Read data from the internal buffer of
        // the data line.
        int cnt = targetDataLine.read(tempBuffer,0,tempBuffer.length);
                              
                              
        if(cnt > 0){
          //Save data in output stream object.
          byteArrayOutputStream.write(tempBuffer,
                                      0,
                                      cnt);
        }//if
      }//while
      byteArrayOutputStream.close();
    }catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }//catch
  }//run
}//inner class CaptureThread
//===================================//
//Inner class to play back the data
// that was saved.
class PlayThread extends Thread{
  byte tempBuffer[] = new byte[10000];

  public void run(){
    try{
      int cnt;
      //Keep looping until the input read method
      // returns -1 for empty stream.
      while((cnt = audioInputStream.read(
      	              tempBuffer, 0,
                      tempBuffer.length)) != -1){
        if(cnt > 0){
          //Write data to the internal buffer of
          // the data line where it will be
          // delivered to the speaker.
          sourceDataLine.write(tempBuffer,0,cnt);
        }//end if
      }//end while
      //Block and wait for internal buffer of the
      // data line to empty.
      sourceDataLine.drain();
      sourceDataLine.close();
    }catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }//end catch
  }//end run
}//end inner class PlayThread
//=============================================//

}//
	
	
	/*Notas:
	 un targetDataLine es un tipo de DataLine, del cual los datos, pueden es leidos
	 esos datos leidos los almaceno en un byteArrayOutputStream , que es un tipo de datos
	 en el cual puedo write array de datos
	 
	 un audioInputStream es un Input stream (flujo de datos de entrada y por tanto solo pueden ser leidos)
	 con un espècifico formato de audio y una longitud (longitud expresda en frames sampled, no em bytes)
	 leo los datos del Udio Input Stream y los pongo el el SourceDataLine que tiene un buffer interno, es decir estopy escribiendo
	 los datos al mixer via al sourceDataLine
	 
	 */