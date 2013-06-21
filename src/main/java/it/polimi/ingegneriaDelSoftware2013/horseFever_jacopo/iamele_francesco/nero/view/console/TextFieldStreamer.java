package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import javax.swing.JTextField;

public class TextFieldStreamer extends InputStream implements ActionListener {

	private JTextField tf;
	private String str = null;
	private int pos = 0;
	private final PrintStream pW;
	private boolean streamClosed = false;


	public TextFieldStreamer(JTextField textField, PrintStream pW) {
		tf = textField;
		this.pW = pW;
	}

	//gets triggered everytime that "Enter" is pressed on the textfield
	public void actionPerformed(ActionEvent e) {
		synchronized (this) {
			str = tf.getText() + System.lineSeparator();
			pos = 0;
			tf.setText("");
			pW.println(str);
			this.notifyAll();
		}
	}

	@Override
	public int read() throws IOException {
		synchronized (this) {
			if(streamClosed){
				throw new IOException();
			}			
		}
		//test if the available input has reached its end
		//and the EOS should be returned 
		if((str != null && pos == str.length())){
			str =null;
			return java.io.StreamTokenizer.TT_EOF;
		}
		//no input available, block until more is available because that's
		//the behavior specified in the Javadocs
		while (str == null || pos >= str.length()) {
			try {
				//according to the docs read() should block until new input is available
				synchronized (this) {
					if(str == null || pos >= str.length()){
						this.wait();
					}
				}
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
		}
		//read an additional character, return it and increment the index
		return str.charAt(pos++);
	}
	
	@Override
	public void close() throws IOException {
		synchronized (this) {
			str = "FINE";
			pos = 0;
			streamClosed  = true;
			this.notifyAll();
		}
	}

}
