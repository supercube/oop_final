package mainframe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

public class Play implements Runnable
{
	private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;
	private boolean soundFlag;
	private Key k;
	private String strFilename;

    /**
     * @param filename the name of the file that is going to be played
     */
    public Play(String filename, Key _k ){

        strFilename = filename;
		k = _k;
		//String strFilename = "forest.wav";

        try {
            soundFile = new File(strFilename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
	}
	public void run()
	{
		int flag = 0;
        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
			synchronized( k ){
				if( k.getSoundFlag() == false ){
					flag = 1;
					k.setStopFlag( true );
					k.notifyAll();
					break;
				}
			}
            try {
                nBytesRead = audioStream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
		if( flag == 0 ){
			Thread t1;
			Play repeat = new Play( strFilename, k );
			t1 = new Thread( repeat );
			t1.start();
		}
    }
	
}