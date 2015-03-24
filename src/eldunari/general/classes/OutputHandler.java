package eldunari.general.classes;

import eldunari.general.enumeration.OutputType;

import java.awt.Component;

import javax.swing.JOptionPane;

public class OutputHandler {

	public static void Message(OutputType type,String message){
		if(type != null){
			if(type.equals(OutputType.Warning)){
				JOptionPane.showMessageDialog(null, message,"Warnung",JOptionPane.WARNING_MESSAGE);
			}else if(type.equals(OutputType.Error)){
				JOptionPane.showMessageDialog(null, message,"Fehler",JOptionPane.ERROR_MESSAGE);
			}else if(type.equals(OutputType.Info)){
				JOptionPane.showMessageDialog(null, message,"Information",JOptionPane.INFORMATION_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, message);	
		}
	}

	public static void Message(Component com,OutputType type,String message){
		if(type != null){
			if(type.equals(OutputType.Warning)){
				JOptionPane.showMessageDialog(com, message,"Warnung",JOptionPane.WARNING_MESSAGE);
			}else if(type.equals(OutputType.Error)){
				JOptionPane.showMessageDialog(com, message,"Fehler",JOptionPane.ERROR_MESSAGE);
			}else if(type.equals(OutputType.Info)){
				JOptionPane.showMessageDialog(com, message,"Information",JOptionPane.INFORMATION_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(com, message);	
		}
	}
	
	

}
