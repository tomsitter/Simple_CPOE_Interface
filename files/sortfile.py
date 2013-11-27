f = open("C:/Users/Owner/Desktop/files/instructions.txt", "r")

lines = [line for line in f if line.strip()]
lines.sort()

f.close()


out = open("C:/Users/Owner/Desktop/files/instructions2.txt", "w");
out.writelines(lines);
out.close()
