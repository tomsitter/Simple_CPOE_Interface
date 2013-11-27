
def ascii_lines(iterable):
    for line in iterable:
        if all(ord(ch) < 128 for ch in line):
            yield line



f = open("C:/Users/Owner/workspace/FinalProject_6HC3/files/drugs.txt", "r")
out = open("C:/Users/Owner/workspace/FinalProject_6HC3/files/asciidrugs.txt", "w");

for line in ascii_lines(f):
    out.write(line);

f.close()
out.close()
