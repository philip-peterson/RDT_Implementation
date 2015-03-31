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

```
./network 1337
```

Receiver
--------

Start this client first; otherwise, the server will think you are the sender and all heck will break loose!

```
./receiver localhost 1337
```

Sender
--------

Start this client second; otherwise, the apocalyptic scenario described above applies.
```
./sender localhost 1337
```
