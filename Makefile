.PHONY : default compile makelinks clean

default: compile makelinks

compile: src/*/*.java
	mkdir -p build
	javac src/*/*.java -d build/

makelinks: compile
	@echo Making links
	@echo "#!/bin/bash" > network
	@echo "#!/bin/bash" > receiver
	@echo "#!/bin/bash" > sender
	@echo java -cp build/ NetworkServer '$$@' >> network
	@echo java -cp build/ Receiver '$$@' >> receiver
	@echo java -cp build/ Sender '$$@' >> sender
	chmod +x network receiver sender 

clean:
	rm -r build
