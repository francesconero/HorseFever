package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.swing.JTextField;

public class TextFieldStreamer extends InputStream implements ActionListener {

	 	private JTextField tf;
	    private String str = null;
	    private int pos = 0;
		private final PrintWriter pW;


	    public TextFieldStreamer(JTextField textField, PrintWriter pW) {
	    	tf = textField;
			this.pW = pW;
		}

		//gets triggered everytime that "Enter" is pressed on the textfield
	    public void actionPerformed(ActionEvent e) {
	        str = tf.getText() + System.lineSeparator();
	        pos = 0;
	        tf.setText("");
	        pW.println(str);
	        synchronized (this) {
	            //maybe this should only notify() as multiple threads may
	            //be waiting for input and they would now race for input
	            this.notifyAll();
	        }
	    }

	    @Override
	    public int read() {
	        //test if the available input has reached its end
	        //and the EOS should be returned 
	        if(str != null && pos == str.length()){
	            str =null;
	            //this is supposed to return -1 on "end of stream"
	            //but I'm having a hard time locating the constant
	            return java.io.StreamTokenizer.TT_EOF;
	        }
	        //no input available, block until more is available because that's
	        //the behavior specified in the Javadocs
	        while (str == null || pos >= str.length()) {
	            try {
	                //according to the docs read() should block until new input is available
	                synchronized (this) {
	                    this.wait();
	                }
	            } catch (InterruptedException ex) {
	                ex.printStackTrace();
	            }
	        }
	        //read an additional character, return it and increment the index
	        return str.charAt(pos++);
	    }

}
