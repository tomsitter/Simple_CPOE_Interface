f = open("C:/Users/Owner/Desktop/ePrescriber/files/ParticipantData.txt", "r")

data = f.readlines()

topline = data[0].split(":");

print topline

fname = topline[1].strip()

out = open("C:/Users/Owner/Desktop/ePrescriber/files/participantdata/" + fname + ".txt", "w");
out.writelines(data);
out.close()
f.close()


open("C:/Users/Owner/Desktop/ePrescriber/files/ParticipantData.txt", "w").close()
