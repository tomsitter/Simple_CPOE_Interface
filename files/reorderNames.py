f = open("C:/Users/Owner/workspace/FinalProject_6HC3/files/patients.txt", "r")
out = open("C:/Users/Owner/workspace/FinalProject_6HC3/files/patients2.txt", "w")

data = f.readlines();
f.close()
reordered_data = [];

for i in range(len(data)):
    name = data[i].split()
    reordered_data.append(name[1] + ", " + name[0])
#lines = [(line[1], line[0]) for val.split() in data]
#lines.sort()

#print lines

reordered_data.sort()

for i in range(len(reordered_data)):
    out.write(reordered_data[i] + "\n");
    print reordered_data[i] + "\n";

print "---Finished---"
out.close()

#out = open("C:/Users/Owner/workspace/FinalProject_6HC3/files/asciidrugs.txt", "w");
#out.writelines(lines);
#out.close()
