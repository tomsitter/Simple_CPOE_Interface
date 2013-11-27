
def ascii_lines(iterable):
    for line in iterable:
        if all(ord(ch) < 128 for ch in line):
            yield line



f = open("C:/Users/Owner/Desktop/files/instructions.txt", "r")
out = open("C:/Users/Owner/Desktop/files/asciiinstructions.txt", "w");

for line in ascii_lines(f):
    out.write(line);

f.close()
out.close()
