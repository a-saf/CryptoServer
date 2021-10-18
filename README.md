# CryptoServer
Multi-thread socket-based client-server for encryption and decryption of text files.

Compile and run on IntelliJ and Java 11. Provide command line arguments for Server and Client (see below).

To run Server:
```
java CryptoServer port
```

To run Client
```
java Client host port
```

Provide a password of your choice following instructions, and the name of the test text file (test.txt).

The server provides the following two services:  
- Encrypt text file  
- Decrypt text file  

The server provides the following features:  

- User can choose between AES and DES algorithm for encryption/decryption  
- Password masking on user input (not available via IDE)
- Execution time for each encrypt/decrypt service

