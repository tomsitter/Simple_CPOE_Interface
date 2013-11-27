f = open("C:/Users/Owner/workspace/FinalProject_6HC3/files/drugs.txt", "r")

lines = [line for line in f if line.strip()]
lines.sort()

f.close()


out = open("C:/Users/Owner/workspace/FinalProject_6HC3/files/asciidrugs.txt", "w");
out.writelines(lines);
out.close()
