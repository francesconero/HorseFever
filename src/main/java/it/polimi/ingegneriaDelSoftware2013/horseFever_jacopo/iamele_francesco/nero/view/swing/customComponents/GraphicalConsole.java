package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.Console;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class GraphicalConsole implements Console {

	private static final Color outColor = Color.black;
	private static final Color errColor = Color.red;
	private static GraphicalConsole CONSOLE;
	private JFrame frame;
	private JTextPane textPane;
	private PipedInputStream pipeIn;
	private PipedInputStream pipeInErr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GraphicalConsole.getInstance().writeOut("ciao");
	}

	protected void startThreads() {
		final PipedOutputStream pipeOut = new PipedOutputStream();
		final PipedOutputStream pipeErr = new PipedOutputStream();

		new Thread("System.out.listener"){
			public void run() {
				try {
					pipeIn = new PipedInputStream(pipeOut);
					BufferedReader reader = new BufferedReader(new InputStreamReader(pipeIn));
					do{
						final String toWrite = reader.readLine();						
						EventQueue.invokeLater(new Runnable() {							
							public void run() {
								writeOut(toWrite);
							}
						});
					} while(true);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			};
		}.start();

		new Thread("System.err.listener"){
			public void run() {
				try {
					pipeInErr = new PipedInputStream(pipeErr);
					BufferedReader reader = new BufferedReader(new InputStreamReader(pipeInErr));
					do{
						final String toWrite = reader.readLine();						
						EventQueue.invokeLater(new Runnable() {							
							public void run() {
								writeErr(toWrite);
							}
						});
					} while(true);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			};
		}.start();

		System.setOut(new PrintStream(pipeOut));
		System.setErr(new PrintStream(pipeErr));
	}

	/**
	 * Create the application.
	 */
	private GraphicalConsole() {
		initialize();
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 497, 344);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
				);

		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		frame.getContentPane().setLayout(groupLayout);
	}

	public static GraphicalConsole getInstance(){

		if(CONSOLE==null){
			try {
				EventQueue.invokeAndWait(new Runnable() {
					public void run() {
						try {
							CONSOLE = new GraphicalConsole();
							CONSOLE.show();
							CONSOLE.startThreads();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

		}
		return CONSOLE;
	}

	private void append(Color c, String s) { // better implementation--uses
		// StyleContext
		StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = getTextPane().getDocument().getLength();
        getTextPane().setCaretPosition(len);
        getTextPane().setCharacterAttributes(aset, false);
        StyledDocument doc = textPane.getStyledDocument();
        try {
			doc.insertString(doc.getLength(), s, aset);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}

	public void writeOut(String string) {
		append(outColor, string);
		append(outColor, "\n");
	}

	public void writeErr(String string){
		append(errColor, string);
		append(errColor, "\n");
	}

	protected JTextPane getTextPane() {
		return textPane;
	}
}
