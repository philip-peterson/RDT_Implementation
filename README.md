To build
========

```
make
```

To run
======

Network server
--------------

Start this server first; it will run while the Receiver and Sender connect to it.
First connect to thunder.cise.ufl.edu before running.

```
./network 65003
```

Receiver
--------

Start this client first before the other; otherwise, the server will think you are the sender and all heck will break loose!
This should be run on sand.cise.ufl.edu

```
./receiver thunder.cise.ufl.edu 65003
```

Sender
--------

Start this client second; otherwise, the apocalyptic scenario described above applies.
This should be run on storm.cise.ufl.edu
```
./sender thunder.cise.ufl.edu 65003 test_poem.txt
```
