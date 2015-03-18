To build
========

```
ant compile
ant jar
```

To run
======

Network server
--------------

Start this server first; it will run while the Receiver and Sender connect to it.

```
java -jar build/jar/NetworkServer.jar
```

Receiver
--------

Start this server first; it will run while the Receiver and Sender connect to it.

```
java -jar build/jar/Receiver.jar
```

Sender
--------

```
java -jar build/jar/Sender.jar
```
