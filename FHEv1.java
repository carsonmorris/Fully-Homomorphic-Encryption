
/**
 *  Author: Carson Morris
 *  CS331 - P1
 *  Version 1 of the Fully Homomorphic Encryption Project 
 */
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class FHEv1 {
	/**
	 * Driver for version 1 that takes in all the possible command line arguments
	 * and executes their commands
	 * 
	 * @param args - command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		try {
			// Declare initial variables
			String option = args[0];
			File keys = null;
			BigInteger plaintext, plaintext1, plaintext2, cipher, cipher1, cipher2;

			// Parse the command line arguments
			switch (option) {
				// Generate encrypt and decrypt keys
				case "-k": {
					keys = new File(args[2]);
					if (!keys.exists()) {
						keys.createNewFile();
					}
					generateKeys(Integer.parseInt(args[1]), keys);
					break;
				}

				// Encrypt the plaintext
				case "-e": {
					plaintext = new BigInteger(args[1]);
					keys = new File(args[2]);
					System.out.println(encrypt(plaintext, keys));
					break;
				}

				// Decrypt the cipher
				case "-d": {
					cipher = new BigInteger(args[1]);
					keys = new File(args[2]);
					System.out.println(decrypt(cipher, keys));
					break;
				}

				// Read key and then encrypt and decrypt an integer
				case "-b": {
					plaintext = new BigInteger(args[1]);
					keys = new File(args[2]);
					cipher = encrypt(plaintext, keys);
					System.out.println(plaintext);
					System.out.println(cipher);
					System.out.println(decrypt(cipher, keys));
					break;
				}

				// Perform homo-morphic addition
				case "-a": {
					if (args.length == 6) {
						plaintext1 = new BigInteger(args[2]);
						plaintext2 = new BigInteger(args[4]);
						keys = new File(args[5]);
						cipher1 = encrypt(plaintext1, keys);
						cipher2 = encrypt(plaintext2, keys);
						System.out.println(FHEOperations.hmAdd(cipher1, cipher2, keys));
					} else if (args.length == 5) {
						if (args[1].equals("-e")) {
							plaintext1 = new BigInteger(args[2]);
							plaintext2 = new BigInteger(args[3]);
							keys = new File(args[4]);
							cipher1 = encrypt(plaintext1, keys);
							cipher2 = plaintext2;
							System.out.println(FHEOperations.hmAdd(cipher1, cipher2, keys));
						} else {
							plaintext1 = new BigInteger(args[1]);
							plaintext2 = new BigInteger(args[3]);
							keys = new File(args[4]);
							cipher1 = plaintext1;
							cipher2 = encrypt(plaintext2, keys);
							System.out.println(FHEOperations.hmAdd(cipher1, cipher2, keys));
						}
					} else {
						plaintext1 = new BigInteger(args[1]);
						plaintext2 = new BigInteger(args[2]);
						keys = new File(args[3]);
						System.out.println(FHEOperations.hmAdd(plaintext1, plaintext2, keys));
					}

					break;
				}

				// Perform homo-morphic multiplication
				case "-m": {
					if (args.length == 6) {
						plaintext1 = new BigInteger(args[2]);
						plaintext2 = new BigInteger(args[4]);
						keys = new File(args[5]);
						cipher1 = encrypt(plaintext1, keys);
						cipher2 = encrypt(plaintext2, keys);
						System.out.println(FHEOperations.hmMult(cipher1, cipher2, keys));
					} else if (args.length == 5) {
						if (args[1].equals("-e")) {
							plaintext1 = new BigInteger(args[2]);
							plaintext2 = new BigInteger(args[3]);
							keys = new File(args[4]);
							cipher1 = encrypt(plaintext1, keys);
							cipher2 = plaintext2;
							System.out.println(FHEOperations.hmMult(cipher1, cipher2, keys));
						} else {
							plaintext1 = new BigInteger(args[1]);
							plaintext2 = new BigInteger(args[3]);
							keys = new File(args[4]);
							cipher1 = plaintext1;
							cipher2 = encrypt(plaintext2, keys);
							System.out.println(FHEOperations.hmMult(cipher1, cipher2, keys));
						}
					} else {
						plaintext1 = new BigInteger(args[1]);
						plaintext2 = new BigInteger(args[2]);
						keys = new File(args[3]);
						System.out.println(FHEOperations.hmMult(plaintext1, plaintext2, keys));
					}

					break;
				}

				// Check equality
				case "-t": {
					if (args.length == 6) {
						plaintext1 = new BigInteger(args[2]);
						plaintext2 = new BigInteger(args[4]);
						keys = new File(args[5]);
						cipher1 = encrypt(plaintext1, keys);
						cipher2 = encrypt(plaintext2, keys);
						if (FHEOperations.hmEqual(cipher1, cipher2, keys)) {
							System.out.println("The ciphers " + cipher1 + " and " + cipher2 + " are equal!");
						} else {
							System.out.println("The ciphers " + cipher1 + " and " + cipher2 + " are not equal!");
						}
					} else if (args.length == 5) {
						if (args[1].equals("-e")) {
							plaintext1 = new BigInteger(args[2]);
							plaintext2 = new BigInteger(args[3]);
							keys = new File(args[4]);
							cipher1 = encrypt(plaintext1, keys);
							cipher2 = plaintext2;
							if (FHEOperations.hmEqual(cipher1, cipher2, keys)) {
								System.out.println("The ciphers " + cipher1 + " and " + cipher2 + " are equal!");
							} else {
								System.out.println("The ciphers " + cipher1 + " and " + cipher2 + " are not equal!");
							}
						} else {
							plaintext1 = new BigInteger(args[1]);
							plaintext2 = new BigInteger(args[3]);
							keys = new File(args[4]);
							cipher1 = plaintext1;
							cipher2 = encrypt(plaintext2, keys);
							if (FHEOperations.hmEqual(cipher1, cipher2, keys)) {
								System.out.println("The ciphers " + cipher1 + " and " + cipher2 + " are equal!");
							} else {
								System.out.println("The ciphers " + cipher1 + " and " + cipher2 + " are not equal!");
							}
						}
					} else {
						plaintext1 = new BigInteger(args[1]);
						plaintext2 = new BigInteger(args[2]);
						keys = new File(args[3]);
						if (FHEOperations.hmEqual(plaintext1, plaintext2, keys)) {
							System.out.println("The ciphers " + plaintext1 + " and " + plaintext2 + " are equal!");
						} else {
							System.out.println("The ciphers " + plaintext1 + " and " + plaintext2 + " are not equal!");
						}
					}
					break;
				}
				default: {
					System.out.println("Incorrect command line arguments.");
				}
			}
		} catch (Exception e) {
			System.out.println(
					"An error occured while attempting to parse your command line arguments.\nPlease see the README for formatting.");
		}
	}

	/**
	 * Create all the keys and writes them to a file.
	 * 
	 * @param keySize - the size of the key
	 * @param keyFile - the file we are writing to
	 */
	public static void generateKeys(int keySize, File keyFile) {
		// Initial variables
		BigInteger prime1, prime2, prime3, probPrime, randBigInt, N, T;
		Random rand1 = new Random();

		// Generate a prime that satisfies the condition where 2 * <the prime> + 1 is
		// also prime
		while (true) {
			prime1 = BigInteger.probablePrime(keySize, new Random());
			probPrime = (BigInteger.valueOf(2).multiply(prime1)).add(BigInteger.ONE);
			if (probPrime.isProbablePrime(1)) {
				break;
			}
		}
		// Get the values for prime2, prime3, N and T
		prime2 = BigInteger.probablePrime(keySize, new Random());
		N = prime1.multiply(prime2);
		prime3 = BigInteger.probablePrime(keySize, new Random());
		T = probPrime.multiply(prime3);
		// Generates two gi's
		BigInteger expo = BigInteger.valueOf(2).multiply(prime3.subtract(BigInteger.ONE));
		ArrayList<BigInteger> arrList = new ArrayList<BigInteger>();
		for (int i = 0; i < 2; i++) {
			randBigInt = new BigInteger(keySize, rand1);
			arrList.add(randBigInt.modPow(expo, T));
		}

		// Store all the generated keys in the file
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(keyFile));
			buffer.write(prime1.toString() + "\t" + N.toString() + "\n");
			for (BigInteger num : arrList) {
				buffer.write(num.toString() + "\t");
			}
			buffer.write("\n" + T.toString());
			buffer.close();
		} catch (IOException e) {
			System.out.println("Exception while copying keys to file.");
			e.printStackTrace();
		}
	}

	/**
	 * Grabbing the keys from the file, converting them to BigInteger and storing
	 * them
	 * 
	 * @param keyFile - the file we are retrieving the keys from
	 * @return - the array of keys
	 */
	public static ArrayList<BigInteger> retrieveKeys(File keyFile) {
		ArrayList<BigInteger> retArr = new ArrayList<BigInteger>();
		// Attempt to grab the keys
		try {
			BufferedReader reader = new BufferedReader(new FileReader(keyFile));
			String line = reader.readLine();
			String[] key;
			// Traverse the file and grab all the keys
			while (line != null) {
				key = line.split("\t");
				for (String keyToAdd : key) {
					retArr.add(new BigInteger(keyToAdd));
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retArr;
	}

	/**
	 * Encrypt the message using the keys from the keyfile
	 * 
	 * @param plaintext - message we are encrypting
	 * @param keyFile   - the file with they keys
	 * @return - the encrypted message
	 */
	public static BigInteger encrypt(BigInteger plaintext, File keyFile) {
		// Get a random big int
		Random rand = new Random();
		BigInteger randBigInt = new BigInteger(32, rand);
		// Get the keys we need for the encryption
		ArrayList<BigInteger> keys = new ArrayList<BigInteger>();
		keys = retrieveKeys(keyFile);
		BigInteger prime = keys.get(0);
		BigInteger N = keys.get(1);

		// Return the encryption
		return (randBigInt.multiply(prime)).add(plaintext.mod(N));
	}

	/**
	 * Decrypt a cipher back into the plaintext
	 * 
	 * @param cipher  - the encrypted message we are decrypting
	 * @param keyFile - the file with the keys
	 * @return - the newly decrypted message
	 */
	public static BigInteger decrypt(BigInteger cipher, File keyFile) {
		// Get the required keys
		ArrayList<BigInteger> keys = new ArrayList<BigInteger>();
		keys = retrieveKeys(keyFile);
		BigInteger prime = keys.get(0);
		// Return the decrypted message
		return cipher.mod(prime);
	}
}
