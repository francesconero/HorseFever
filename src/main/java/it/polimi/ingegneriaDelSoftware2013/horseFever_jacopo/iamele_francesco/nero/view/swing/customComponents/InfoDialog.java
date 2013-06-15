package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class InfoDialog {
	private JFrame main;
	private String nome = null;
	private String ip = null;
	private JTextField textField;
	private JTextField textField_1;
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition acceptCondition  = lock.newCondition();
	private boolean inputValid = false; 
	private final Action action = new SwingAction();
	
	public InfoDialog(){
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				main = new JFrame("Inserisci il tuo nome e l'indirizzo ip della macchina che hosta la partita");
				main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				main.setResizable(false);
				
				JLabel lblInserisciIlTuo = new JLabel("Nome giocatore:");
				lblInserisciIlTuo.setHorizontalAlignment(SwingConstants.CENTER);
				
				textField = new JTextField();
				textField.setHorizontalAlignment(SwingConstants.CENTER);
				textField.setColumns(10);
				
				JLabel lblIndirizzoIp = new JLabel("Indirizzo IP:");
				lblIndirizzoIp.setHorizontalAlignment(SwingConstants.CENTER);
				
				JButton btnNewButton = new JButton("Connetti!");
				btnNewButton.setAction(action);
				
				textField_1 = new JTextField();
				textField_1.setHorizontalAlignment(SwingConstants.CENTER);
				textField_1.setColumns(10);
				GroupLayout groupLayout = new GroupLayout(main.getContentPane());
				groupLayout.setHorizontalGroup(
					groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
								.addComponent(lblInserisciIlTuo, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
								.addComponent(lblIndirizzoIp, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
							.addContainerGap())
				);
				groupLayout.setVerticalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblInserisciIlTuo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblIndirizzoIp)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnNewButton)
							.addContainerGap(12, Short.MAX_VALUE))
				);
				main.getContentPane().setLayout(groupLayout);
				
				main.pack();
				
				main.setLocationRelativeTo(null);
				main.setVisible(true);
		        
			}
		});
		
		
        while(!inputValid ){	
			lock.lock();
			try {
				acceptCondition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
        
        MetodiDiSupporto.swingInvokeAndWait(new Runnable() {
			public void run() {
				 main.dispose();
			}				
		});
       
	}
	
	public static void main(String[] args){
		new InfoDialog();
	}
	
	public String getNome(){
		return nome;
	}
	
	public String getIndirizzoIP(){
		return ip;
	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(MNEMONIC_KEY, KeyEvent.VK_ENTER);
			putValue(NAME, "Connetti");
			putValue(SHORT_DESCRIPTION, "Connettiti al server");
		}
		public void actionPerformed(ActionEvent e) {
			nome = textField.getText();
			ip = textField_1.getText();
			
			if(!(nome==null || ip == null)){
				if(nome.isEmpty()||ip.isEmpty()){
					JOptionPane.showMessageDialog(main, "Inserisci tutti i campi!", "Attenzione!", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						ip = InetAddress.getByName(ip).getHostAddress();
						inputValid = true;
					} catch (UnknownHostException ex) {
						JOptionPane.showMessageDialog(main, "L'indirizzo ip immesso non è valido!", "Attenzione!", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
			lock.lock();
			try{
				acceptCondition.signalAll();
			} finally {
				lock.unlock();
			}
		}
	}
}
