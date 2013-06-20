package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.other;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console.TextFieldStreamer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public final class GraphicalConsole extends PrintWriter {

	private static final Color outColor = Color.black;
	private static final Color errColor = Color.red;
	private static OutputStream outputStream = new OutputStream() {
		
		@Override
		public void write(int b) throws IOException {
			// TODO Auto-generated method stub
			
		}
	};
	private JFrame frmHorseFever;
	private JTextPane textPane;
	private JPanel panel;
	private JTextField textField;
	private JButton btnNewButton;

	private TextFieldStreamer inStream;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	}

	/**
	 * Create the application.
	 */
	public GraphicalConsole() {
		super(outputStream);
		initialize();
		inStream = new TextFieldStreamer(textField, this);
		textField.addActionListener(inStream);
		btnNewButton.addActionListener(inStream);
	}

	public void show() {
		frmHorseFever.setLocationRelativeTo(null);
		frmHorseFever.setVisible(true);
	}

	public void hide() {
		frmHorseFever.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frmHorseFever = new JFrame();
		frmHorseFever.setTitle("Horse Fever");
		frmHorseFever.setMinimumSize(new Dimension(300, 200));
		frmHorseFever.getContentPane().setBackground(Color.DARK_GRAY);
		frmHorseFever.setBounds(100, 100, 497, 344);
		frmHorseFever.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHorseFever.getContentPane().setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();

		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		frmHorseFever.getContentPane().add(scrollPane);

		panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		frmHorseFever.getContentPane().add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{86, 89, 0};
		gbl_panel.rowHeights = new int[]{23, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);

		btnNewButton = new JButton("OK");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		panel.add(btnNewButton, gbc_btnNewButton);
	}

	private void append(Color c, String s) { 
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
		textPane.revalidate();
		textPane.repaint();
	}

	public void writeOut(String string) {
		append(outColor, string);		
	}

	public void writeErr(String string){
		append(errColor, string);
	}

	protected JTextPane getTextPane() {
		return textPane;
	}
	
	public InputStream getInputStream() {
		return inStream;
	}
	
	@Override
	public void println(String x) {
		println((Object)x);
	}
	
	@Override
	public void println() {
		println((Object)"");
	};

	@Override
	public void print(final Object obj){
		SwingUtilities.invokeLater(new Runnable() {			
			public void run() {
				writeOut(obj.toString());
			}
		});
	}
	
	@Override
	public void print(String string){
		print((Object)string);
	}
	
	@Override
	public void println(final Object obj) {
		print(obj);
		print((Object)System.lineSeparator());
	}

	public JFrame getFrame() {
		return frmHorseFever;
	}
}
