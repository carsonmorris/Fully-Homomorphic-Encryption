****************
* Project 1 - Fully Homomorphic Encryption
* Class: CS331
* Date: March 30, 2023
* Carson Morris
**************** 

OVERVIEW:

 This is a program that implemented Fully Homomorphic Encryption (FHE) in two different versions. It takes in specific command line inputs to perform various tasks such as generating a file of keys, performing random padding, encryption and decryption, homomorphic operations such as addition, multiplication, and equality, etc. The program only performs the operations on integers.


INCLUDED FILES:

 * FHEv1.java - the main program for version one of the FHE
 * FHEv2.java - the main program for version two of the FHE
 * FHEOperations.java - file for storing the homomorphic operations used by both versions
 * run_test_java - a provided file for testing the program for correctness (has not been modified)


COMPILING AND RUNNING:

To compile our project from the main directory containing these files:

```$ javac *.java```

To run the provided test file, simply use:

```$ ./run_test_java```

To run either version of the project, there are a number of possible command line inputs:
----------------------------------------------------------------------------
For FHEv1: 
Generate encryption/decryption keys and operational keys, and write them to a file:

``` $ java FHEv1 -k <key size> <KeyFileName>```

Read keys from the key file and then encrypt a plain integer m, output the cipher

```$ java FHEv1 -e <m> <KeyFileName>```

Read keys from the key file and then decrypt a cipher Cm, output the plain integer

```$ java FHEv1 -d <Cm > <KeyFileName>```

Read keys from the key file and then encrypt and decrypt an integer m. Outputs the original message, the cipher, and the decrypted message.

```$ java FHEv1 -b <m > <KeyFileName>```

Read keys from the key file and then add two ciphers Cm1 and Cm2. Outputs the result. There is an optional -e flag to encrypt the messages before addition.

```$ java FHEv1 -a <-e <m1> | Cm1> <-e <m2> | Cm2> <KeyFileName>```

Example uses of the optional flag:
```
$ java FHEv1 -a -e 10 -e 20 <KeyFileName>
$ java FHEv1 -a 10 -e 20 <KeyFileName>
$ java FHEv1 -a -e 10 20 <KeyFileName>
$ java FHEv1 -a 10 20 <KeyFileName>
```
 
Read keys from the key file and then multiply two ciphers Cm1 and Cm2. Outputs the result. There is an optional -e flag to encrypt the messages before multiplication.

```$ java FHEv1 -m <-e <m1> | Cm1> <-e <m2> | Cm2> <KeyFileName>```

Example uses of the optional flag:
```
* $ java FHEv1 -m -e 10 -e 20 <KeyFileName>
* $ java FHEv1 -m 10 -e 20 <KeyFileName>
* $ java FHEv1 -m -e 10 20 <KeyFileName>
* $ java FHEv1 -m 10 20 <KeyFileName>
```

Read keys from the key file and then test the equality of two ciphers Cm1 and Cm2. Outputs whether the ciphers are equal. There is an optional -e flag to encrypt the messages before equality checking.

```$ java FHEv1 -t <-e <m1> | Cm1 > <-e <m2> | Cm2 > <KeyFileName>```

Example uses of the optional flag:
```
* $ java FHEv1 -t -e 10 -e 20 <KeyFileName>
* $ java FHEv1 -t 10 -e 20 <KeyFileName>
* $ java FHEv1 -t -e 10 20 <KeyFileName>
* $ java FHEv1 -t 10 20 <KeyFileName>
```

----------------------------------------------------------------------------
For FHEv2:

Generating the encryption/decryption keys, the operational key, the user-defined constants (w, z), and write them to a key file.

```$ java FHEv2 -k <key size> <w> <z> <KeyFileName>```

Given a plain integer, this option reads the constants from the key file and then perform a random padding to m. It then outputs the padded message.

```$ java FHEv2 -p <m> <KeyFileName>```

Read keys from the key file and then encrypt a plain integer m, output the cipher

```$ java FHEv2 -e <m> <KeyFileName>```

Read keys from the key file and then decrypt a cipher Cm, output the plain integer

```$ java FHEv2 -d <Cm > <KeyFileName>```

Read keys from the key file and then encrypt and decrypt an integer m. Outputs the original message, the cipher, and the decrypted message.

```$ java FHEv2 -b <m > <KeyFileName>```

Read keys from the key file and then add two ciphers Cm1 and Cm2. Outputs the result. There is an optional -e flag to encrypt the messages before addition.

```$ java FHEv2 -a <-e <m1> | Cm1> <-e <m2> | Cm2> <KeyFileName>```

Example uses of the optional flag:
```
* $ java FHEv2 -a -e 10 -e 20 <KeyFileName>
* $ java FHEv2 -a 10 -e 20 <KeyFileName>
* $ java FHEv2 -a -e 10 20 <KeyFileName>
* $ java FHEv2 -a 10 20 <KeyFileName>
```
 
Read keys from the key file and then multiply two ciphers Cm1 and Cm2. Outputs the result. There is an optional -e flag to encrypt the messages before multiplication.

```$ java FHEv2 -m <-e <m1> | Cm1> <-e <m2> | Cm2> <KeyFileName>```

Example uses of the optional flag:
```
* $ java FHEv2 -m -e 10 -e 20 <KeyFileName>
* $ java FHEv2 -m 10 -e 20 <KeyFileName>
* $ java FHEv2 -m -e 10 20 <KeyFileName>
* $ java FHEv2 -m 10 20 <KeyFileName>
```

----------------------------------------------------------------------------
