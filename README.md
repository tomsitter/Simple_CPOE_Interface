A simple Java interface for electronic prescription designed for an HCI experiment

It is designed to measure both the speed and accuracy of electronic prescriptions.
All user interaction with the system is recorded in a text file for later analysis of accuracy and speed.

This software was created for a Human-Computer Interaction class at McMaster University in 2013.

SETUP

1) Git clone the repository to your computer.
2) Compile with java compiler, import into IDE, or run the ePrescriber.jar file directly
3) It is recommended to create a shortcut to the .jar because the .har relies on relative path to resource files


USAGE

-A participant identified will be requested at program startup.
-All results will be stored in a temporary file "ParticipantData.txt" for the extent of the experiment.
-After each participant has completed the experiment, run prepareNextParticipant.py, or copy the information from the temporary file into a separate text file. ParticipantData.txt will be overwritten for each participant!
-Input method can be changed using the "File" dropdown menu. This will create a popup box and reset the timer when "OK" is pressed.


CUSTOMIZATION

Drugs are stored files/drugs.txt and are read when the program starts. This text file can be edited to modify the drugs in the CPOE database.
Patients names are similarily stored in files/patients.txt
Extra instructions (such as 'with food', 'at bedtime', etc.) are stored in files/instructions.txt
