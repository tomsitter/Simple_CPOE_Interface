package create.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long startTime;
	
	//Menubar elements
	private JMenuBar menuBar;
	private JMenu optionMenu;
	private JMenu finishSession;
	private ButtonGroup optionGroup;
	private JRadioButtonMenuItem rdbtBoth;
	private JRadioButtonMenuItem rdbtDropdownOnly;
	private JRadioButtonMenuItem rdbtTextOnly;
	
	//Input and Output data
	private ArrayList<String> drugArrayList;
	private ArrayList<String> instructionArrayList;
	private ArrayList<String> patientArrayList;
	private Path drugs = Paths.get("files/drugs.txt");
	private Path instructions = Paths.get("files/instructions.txt");
	private Path patients = Paths.get("files/patients.txt");
	private Charset charset = Charset.forName("US-ASCII");
	private Path target = Paths.get("files/ParticipantData.txt");
	
	private JButton btnFinish;
	private JComboBox comboDrugDropDown;
	private JComboBox comboDrugBoth;
	private AutoTextField textOnlyDrug;
	private JComboBox comboPatientDropDown;
	private JComboBox comboPatientBoth;
	private AutoTextField textOnlyPatient;
	private JList listInstructions;
	private JScrollPane instructionScrollPane;	
	private JComboBox comboMethod;
	private JComboBox comboUnit;
	private JComboBox comboFrequency;
	private JComboBox comboLengthOfTime;
	private JComboBox comboTimesPerUnit;
	private JComboBox comboLengthUnits;
	private JLabel forLabel;
	private JLabel patientLabel;
	private JLabel drugLabel;
	private JLabel instrLabel;
	private MainFrame thisClass;
	
	private Object[] method = {"Apply", "Rub in", "Take"};
	private Object[] unit = {"Capsules", "Drops", "gm", "Inhalations",  "mg", "mL", "Patch", "Puffs", "Squirts", "Tabs", "Units"};
	private Object[] frequency = {"Everyday", "Immediately", "Two times daily", "Three times daily", "Four times daily", "Every other hour", 
			"Every other day","At morning","At night","When needed"};
	private Object[] lengthOfTime = {"1", "2", "3", "4", "5", "6", "7","8", "9", "10", "11", "12", "13", "14", "30", "60", "90"};
	private Object[] lengthUnits = {"Days", "Weeks", "Months"};
	
	public MainFrame() {
		
		//Instantiate menu components
		menuBar = new JMenuBar();
		optionGroup = new ButtonGroup();
		optionMenu = new JMenu("File");
		finishSession = new JMenu("Finish Session");
		rdbtBoth = new JRadioButtonMenuItem("Both", true);
		rdbtDropdownOnly = new JRadioButtonMenuItem("Dropdown only");
		rdbtTextOnly = new JRadioButtonMenuItem("Text only");
		
		//Components not requiring file reads
		btnFinish = new JButton("Finish");
		comboLengthOfTime = new JComboBox<> (lengthOfTime);
		comboTimesPerUnit = new JComboBox<> (lengthOfTime);
		comboFrequency = new JComboBox<> (frequency);
		comboLengthUnits = new JComboBox<> (lengthUnits);
		comboMethod = new JComboBox<>(method);
		thisClass = this;

		comboUnit = new JComboBox<>(unit);
		forLabel = new JLabel("for");
		patientLabel = new JLabel("Patient");
		drugLabel = new JLabel("Drug");
		instrLabel = new JLabel("Extra Instructions");
		
		//Vars for storing input data
		drugArrayList = new ArrayList<>();
		instructionArrayList = new ArrayList<>();
		patientArrayList = new ArrayList<>();
		
		//Read in stored data
		try (
			BufferedReader drug_reader = Files.newBufferedReader(drugs, charset);
			BufferedReader instruction_reader = Files.newBufferedReader(instructions, charset);
			BufferedReader patient_reader = Files.newBufferedReader(patients, charset)) 
		{
			String line = null;
			while ((line = drug_reader.readLine()) != null) {
				drugArrayList.add(line);
			}
			while ((line = instruction_reader.readLine()) != null) {
				instructionArrayList.add(line);
			}
			while ((line = patient_reader.readLine()) != null) {
				patientArrayList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Convert read data to array
		Object[] drugList = drugArrayList.toArray();
		Object[] instructionList = instructionArrayList.toArray();
		Object[] patientList = patientArrayList.toArray();

		
		comboDrugDropDown = new JComboBox<>(drugList);
		comboDrugBoth = new JComboBox<>(drugList);
		textOnlyDrug = new AutoTextField(drugArrayList);
		
		comboPatientDropDown = new JComboBox<>(patientList);
		comboPatientBoth = new JComboBox<>(patientList);
		textOnlyPatient = new AutoTextField(patientArrayList);
		
		listInstructions = new JList<>(instructionList);
		listInstructions = new JList<>(instructionList);
		instructionScrollPane = new JScrollPane(listInstructions);
				
		setupFrame();
		setupLayout();
		setupListeners();

		//Input dialog with a combo box 
		String participant = JOptionPane.showInputDialog(this, "Please enter your name");
		try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
	  		writer.append("Participant Name: " + participant);
	  		writer.newLine();
	  		writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		startTime = System.currentTimeMillis();
	}
	
	private void setupFrame(){
		
		//set up menubar
		menuBar.add(optionMenu);
		optionMenu.add(rdbtBoth);
		optionGroup.add(rdbtBoth);
		optionMenu.add(rdbtDropdownOnly);
		optionGroup.add(rdbtDropdownOnly);
		optionMenu.add(rdbtTextOnly);
		optionGroup.add(rdbtTextOnly);
		
		//add the menu bar
		this.setJMenuBar(menuBar);

		//current does nothing
		this.setBackground(Color.WHITE);
		
		//Set up absolute layout
		getContentPane().setLayout(null);
		
		//Add finish button
		getContentPane().add(btnFinish);
		getContentPane().add(comboLengthOfTime);
		getContentPane().add(comboTimesPerUnit);
		getContentPane().add(comboLengthUnits);
		getContentPane().add(comboFrequency);
		getContentPane().add(comboUnit);
		getContentPane().add(comboMethod);
		getContentPane().add(forLabel);
		getContentPane().add(drugLabel);
		getContentPane().add(patientLabel);
		getContentPane().add(instrLabel);
		
		//Add combo boxes and text boxes
		getContentPane().add(comboDrugDropDown);
		getContentPane().add(textOnlyDrug);
		getContentPane().add(comboDrugBoth);

		getContentPane().add(comboPatientDropDown);
		getContentPane().add(textOnlyPatient);
		getContentPane().add(comboPatientBoth);		
		
		comboDrugDropDown.setVisible(false);
		textOnlyDrug.setVisible(false);
		comboDrugDropDown.setSelectedIndex(-1);
		comboDrugBoth.setSelectedIndex(-1);
		AutoCompleteDecorator.decorate(comboDrugBoth);
		
		comboPatientDropDown.setVisible(false);
		comboPatientDropDown.setSelectedIndex(-1);
		comboPatientBoth.setSelectedIndex(-1);
		textOnlyPatient.setVisible(false);
		AutoCompleteDecorator.decorate(comboPatientBoth);
		
		instructionScrollPane.setViewportView(listInstructions);
		getContentPane().add(instructionScrollPane);
	}
	
	private void setupLayout() {

		//Insets insets = this.getInsets();
		Dimension size = btnFinish.getPreferredSize();
		btnFinish.setBounds(667, 381, size.width, size.height);
		comboMethod.setBounds(25 , 175, 172, 25);
		comboUnit.setBounds(325 , 175, 100, 25);
		comboLengthOfTime.setBounds(215 ,175 ,100, 25);
		comboTimesPerUnit.setBounds(215 ,223 ,100, 25);
		comboLengthUnits.setBounds(325 ,223 ,100, 25);
		comboFrequency.setBounds(25 ,223 ,125, 25);
		//tf.setBounds(25, 400, 125, 25);
		
		forLabel.setBounds(170, 225, 50, 25);
		forLabel.setFont(new Font("Arial", Font.BOLD, 14));
		
		drugLabel.setBounds(25, 85, 400, 25);
		size = comboDrugDropDown.getPreferredSize();
		comboDrugDropDown.setBounds(25, 110, 400, 25);
		comboDrugBoth.setBounds(25, 110, 400, 25);
		textOnlyDrug.setBounds(25, 110, 400, 25);
		textOnlyDrug.setFont(comboDrugBoth.getFont());
		
		patientLabel.setBounds(25, 25, 400, 25);
		comboPatientDropDown.setBounds(25, 50, 400, 25);
		comboPatientBoth.setBounds(25, 50, 400, 25);
		textOnlyPatient.setBounds(25, 50, 400, 25);
		textOnlyPatient.setFont(comboDrugBoth.getFont());
		
		instrLabel.setBounds(475, 25, 267, 25);
		instructionScrollPane.setBounds(471, 52, 267, 296);
		listInstructions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listInstructions.setSelectedIndex(0);
		//listInstructions.setBounds(381, 319, 188, -286);
		
//		Border b = BorderFactory.createLineBorder(Color.black, 2);
//		instructionScrollPane.setBorder(b);
//		textOnlyDrug.setBorder(b);
		comboDrugDropDown.setBackground(Color.white);
//		comboDrugDropDown.setBorder(b);
//		textOnlyPatient.setBorder(b);
		comboPatientDropDown.setBackground(Color.white);
//		comboPatientDropDown.setBorder(b);
		comboMethod.setBackground(Color.white);
		comboUnit.setBackground(Color.white);
		comboLengthOfTime.setBackground(Color.white);
		comboTimesPerUnit.setBackground(Color.white);
		comboLengthUnits.setBackground(Color.white);
		comboFrequency.setBackground(Color.white);	
	}
	
	private void setupListeners(){
		
		rdbtBoth.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				comboDrugBoth.setVisible(true);
				comboDrugDropDown.setVisible(false);
				textOnlyDrug.setVisible(false);
				
				comboPatientBoth.setVisible(true);
				comboPatientDropDown.setVisible(false);
				textOnlyPatient.setVisible(false);

				try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
			  		writer.append("\nBoth options enabled... Resetting Clock\n");
			  		writer.newLine();
			  		writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//JOptionPane.showConfirmDialog(thisClass, "Text entry disabled. Click OK to Begin");
				JOptionPane.showMessageDialog(thisClass, "Both entry methods enabled. Click OK to Begin");
				startTime = System.currentTimeMillis();
				resetMenus();
			}
		});
		rdbtDropdownOnly.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				comboDrugBoth.setVisible(false);
				comboDrugDropDown.setVisible(true);
				textOnlyDrug.setVisible(false);
				
				comboPatientBoth.setVisible(false);
				comboPatientDropDown.setVisible(true);
				textOnlyPatient.setVisible(false);
				
				try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
			  		writer.append("\nText entry disabled... Resetting Clock\n");
			  		writer.newLine();
			  		writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(thisClass, "Text entry disabled. Click OK to Begin");
				startTime = System.currentTimeMillis();
				resetMenus();
			}
		});
		rdbtTextOnly.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				comboDrugBoth.setVisible(false);
				comboDrugDropDown.setVisible(false);
				textOnlyDrug.setVisible(true);
				
				comboPatientBoth.setVisible(false);
				comboPatientDropDown.setVisible(false);
				textOnlyPatient.setVisible(true);
				
				try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
			  		writer.append("\nDropdown menu disabled... Resetting Clock\n");
			  		writer.newLine();
			  		writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//JOptionPane.showConfirmDialog(thisClass, "Text entry disabled. Click OK to Begin");
				JOptionPane.showMessageDialog(thisClass, "Dropdown entry disabled. Click OK to Begin");
				startTime = System.currentTimeMillis();
				resetMenus();
			}
		});
		
		comboMethod.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("comboBoxChanged".equals(e.getActionCommand())) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": Method changed to: " + 
				  				comboMethod.getSelectedItem().toString());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		textOnlyDrug.addKeyListener(new KeyListener() {
		
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": Drug changed to: " + 
				  				textOnlyDrug.getText());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		textOnlyPatient.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": Patient changed to: " + 
				  				textOnlyPatient.getText());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		comboFrequency.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("comboBoxChanged".equals(e.getActionCommand())) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": Frequency changed to: " + 
				  				comboFrequency.getSelectedItem().toString());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});

		comboLengthOfTime.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("comboBoxChanged".equals(e.getActionCommand())) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": LengthOfTime changed to: " + 
				  				comboLengthOfTime.getSelectedItem().toString());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});

		comboLengthUnits.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("comboBoxChanged".equals(e.getActionCommand())) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": LengthUnits changed to: " + 
				  				comboLengthUnits.getSelectedItem().toString());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});

		comboUnit.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("comboBoxChanged".equals(e.getActionCommand())) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": Units changed to: " + 
				  				comboUnit.getSelectedItem().toString());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});

		comboTimesPerUnit.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("comboBoxChanged".equals(e.getActionCommand())) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": TimesPerUnit changed to: " + 
				  				comboTimesPerUnit.getSelectedItem().toString());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});

		comboDrugBoth.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("comboBoxChanged".equals(e.getActionCommand())) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": Drug changed to: " + 
				  				comboDrugBoth.getSelectedItem().toString());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		comboDrugDropDown.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("comboBoxChanged".equals(e.getActionCommand())) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": Drug changed to: " + 
				  				comboDrugDropDown.getSelectedItem().toString());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		comboPatientBoth.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("comboBoxChanged".equals(e.getActionCommand())) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": Patient changed to: " + 
				  				comboPatientBoth.getSelectedItem().toString());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		comboPatientDropDown.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("comboBoxChanged".equals(e.getActionCommand())) {
					try (BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND})){
				  		writer.append((System.currentTimeMillis()-startTime) + ": Patient changed to: " + 
				  				comboPatientDropDown.getSelectedItem().toString());
				  		writer.newLine();
				  		writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)  {
			
				String drug = "NA";
				String patient = "NA";
				
				if (rdbtBoth.isSelected()) {
					if (comboDrugBoth.getSelectedIndex() != -1) {
						drug = comboDrugBoth.getSelectedItem().toString();
					}
					if (comboPatientBoth.getSelectedIndex() != -1) {
						patient = comboPatientBoth.getSelectedItem().toString();
					}
				} else if (rdbtDropdownOnly.isSelected()) {
					if (comboDrugDropDown.getSelectedIndex() != -1) {
						drug = comboDrugDropDown.getSelectedItem().toString();
					}
					if (comboPatientDropDown.getSelectedIndex() != -1) {
						patient = comboPatientDropDown.getSelectedItem().toString();
					}
				} else {
					drug = textOnlyDrug.getText();
					patient = textOnlyPatient.getText();
				}
				
				String instr = listInstructions.getSelectedValue().toString();
				
				String method = comboMethod.getSelectedItem().toString();
				String freq = comboFrequency.getSelectedItem().toString();
				String LoT = comboLengthOfTime.getSelectedItem().toString();
				String LoU = comboLengthUnits.getSelectedItem().toString();
				String TpU = comboTimesPerUnit.getSelectedItem().toString();
				String unit = comboUnit.getSelectedItem().toString();
				
				try ( BufferedWriter writer = Files.newBufferedWriter(target, charset, new OpenOption[] {StandardOpenOption.APPEND}) ){
					writer.append((System.currentTimeMillis()-startTime) + " Result: " + patient + " " + drug + " \n" +
							method + " " + TpU + " " + unit + " " + freq + " for " + LoT + " "  + LoU + " " + instr);
					writer.newLine();
					writer.newLine();
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				resetMenus();
				//comboDrugDropDown.setEditable(true);
			}
		});
	}
	
	
	public void resetMenus() {
	
		comboDrugBoth.setSelectedIndex(0);
		comboDrugDropDown.setSelectedIndex(0);
		textOnlyDrug.setText("");
		textOnlyPatient.setText("");
		comboPatientBoth.setSelectedIndex(0);
		comboPatientDropDown.setSelectedIndex(0);
		comboMethod.setSelectedIndex(0);
		comboUnit.setSelectedIndex(0);
		comboFrequency.setSelectedIndex(0);
		comboLengthOfTime.setSelectedIndex(0);
		comboLengthUnits.setSelectedIndex(0);
		comboTimesPerUnit.setSelectedIndex(0);
		listInstructions.setSelectedIndex(0);
	}
	
	public void start() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(850, 500);
		this.setResizable(false);
		this.setLocation(300, 200);
		ImageIcon img = new ImageIcon("res/pill_icon128.png");
		this.setIconImage(img.getImage());
		this.setVisible(true);
	}
}
