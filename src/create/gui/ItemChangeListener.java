package create.gui;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JComboBox;


public class ItemChangeListener implements ItemListener {

	private Charset charset = Charset.forName("US-ASCII");
	private Path target = Paths.get("files/ParticipantData.txt");
	
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			System.out.println(event.getItem().toString());
			System.out.println(event.getSource().toString());
	        JComboBox item = (JComboBox) event.getItem();
	  		try {
				BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND});
		  		writer.append(System.currentTimeMillis() + ": Method changed to: " + 
		  				item.getSelectedItem().toString());
		  		writer.newLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	   }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
