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

Start this client first; otherwise, the server will think you are the sender and all heck will break loose!

```
java -jar build/jar/Receiver.jar
```

Sender
--------

Start this client second; otherwise, the apocalyptic scenario described above applies.
```
java -jar build/jar/Sender.jar
```
