f = open("C:/Users/Owner/Desktop/files/patients.txt", "r")
# omit empty lines and lines containing only whitespace
lines = [line.upper() for line in f if line.strip()]
f.close()

# now write the output file
out = open("C:/Users/Owner/Desktop/patients2.txt", "w");
out.writelines(lines);
out.close;

print "Finished"
