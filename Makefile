.PHONY : compile server receiver sender

compile: */*.java
	javac */*.java -d build/

server: compile
	java -cp build/ NetworkServer

receiver: compile
	java -cp build/ Receiver

sender: compile
	java -cp build/ Sender
