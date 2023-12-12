
/**
 *  Author: Carson Morris
 *  CS331 - P1
 *  Version 2 of the Fully Homomorphic Encryption Project
 */
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class FHEv2 {
	/**
	 * Driver for version 2 that takes in all the possible command line arguments
	 * and executes their commands
	 * 
	 * @param args - command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		try {
			// Initial variables
			String option = args[0];
			File keys = null;
			BigInteger plaintext, plaintext1, plaintext2, cipher, cipher1, cipher2;

			// Parse the command line arguments
			switch (option) {
				// Generate encrypt and decrypt keys
				case "-k": {
					keys = new File(args[4]);
					int w = Integer.parseInt(args[2]);
					int z = Integer.parseInt(args[3]);
					if (!keys.exists()) {
						keys.createNewFile();
					}
					generateKeys(Integer.parseInt(args[1]), w, z, keys);
					break;
				}
				// Perform random padding
				case "-p": {
					keys = new File(args[2]);
					plaintext = new BigInteger(args[1]);
					System.out.println(messagePad(plaintext, keys));
					break;
				}
				// Encrypt the plaintext
				case "-e": {
					keys = new File(args[2]);
					plaintext = new BigInteger(args[1]);
					System.out.println(encrypt(plaintext, keys));
					break;
				}
				// Decrypt the cipher
				case "-d": {
					keys = new File(args[2]);
					cipher = new BigInteger(args[1]);
					System.out.println(decrypt(cipher, keys));
					break;
				}

				// Encrypt and decrypt the message and cipher
				case "-b": {
					plaintext = new BigInteger(args[1]);
					keys = new File(args[2]);
					cipher = encrypt(plaintext, keys);
					System.out.println(plaintext);
					System.out.println(cipher);
					System.out.println(decrypt(cipher, keys));
					break;
				}
				// Perform Homo-morphic addition
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
				// Perform Homo-morphic multiplication
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
	 * @param keySize - size of key
	 * @param w       - user defined constant
	 * @param z       - user defined constant
	 * @param keyFile - file we are writing to
	 */
	public static void generateKeys(int keySize, int w, int z, File keyFile) {
		BigInteger prime1, prime2, prime3, probPrime;
		/**
		 * Generate a prime that satisfies the condition where 2 * <the prime> + 1 is
		 * also prime. In addition, ensure that prime1's size is at least (k+1)(z+w)
		 * bits,
		 * where k is the number of consecutive homomorphic multiplications (which was
		 * specified
		 * to be 5 in our instruction set)
		 */
		while (true) {
			while (true) {
				long bitLength = (5 + 1) * (z + w);
				prime1 = BigInteger.probablePrime(keySize, new Random());
				if (BigInteger.valueOf(bitLength).compareTo(prime1) == -1) {
					break;
				}
			}
			probPrime = (BigInteger.valueOf(2).multiply(prime1)).add(BigInteger.ONE);
			if (probPrime.isProbablePrime(1)) {
				break;
			}
		}
		// Get the values for prime2 and N
		prime2 = BigInteger.probablePrime(keySize, new Random());
		prime3 = prime1.multiply(prime2);
		// Store all the generated keys in the file
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(keyFile));
			buffer.write(prime1.toString() + "\t" + prime3.toString() + "\n" + Integer.toString(w) + "\t"
					+ Integer.toString(z));
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
	 * @param keyFile - file we are reading the keys from
	 * @return - the array of keys
	 */
	public static ArrayList<BigInteger> retrieveKeys(File keyFile) {
		ArrayList<BigInteger> retArr = new ArrayList<BigInteger>();
		// Attempt to grab the keys
		try {
			// Read through the file and store the keys in array to return
			BufferedReader reader = new BufferedReader(new FileReader(keyFile));
			String line = reader.readLine();
			String[] key;
			while (line != null) {
				key = line.split("\t");
				for (String k : key) {
					retArr.add(new BigInteger(k));
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Exception while attempting to read keys from file.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (retArr);
	}

	/**
	 * Adds random padding to a message
	 * 
	 * @param plaintext - the message we are adding the padding to
	 * @param keyFile   - file with the keys
	 * @return - the padded message
	 */
	public static BigInteger messagePad(BigInteger plaintext, File keyFile) {
		// Retrieve the keys we need
		ArrayList<BigInteger> keyArr = new ArrayList<BigInteger>();
		keyArr = retrieveKeys(keyFile);
		// Create random padding
		Random rand = new Random();
		int w = keyArr.get(2).intValue();
		int z = keyArr.get(3).intValue();
		BigInteger R = new BigInteger(z, rand);
		return R.multiply((BigInteger.valueOf(2).pow(w))).add(plaintext);
	}

	/**
	 * Encrypt the message using the keys from the keyfile
	 * 
	 * @param plaintext - the message we are encrypting
	 * @param keyFile   - the file with the keys
	 * @return - the encrypted message
	 */
	public static BigInteger encrypt(BigInteger plaintext, File keyFile) {
		// Initial variables
		BigInteger prime, randBigInt, messagePad, N;
		// Get random big integer
		Random rand = new Random();
		randBigInt = new BigInteger(32, rand);
		// Get the keys we need
		ArrayList<BigInteger> keyArr = new ArrayList<BigInteger>();
		keyArr = retrieveKeys(keyFile);
		prime = keyArr.get(0);
		N = keyArr.get(1);

		// Pad our message before encrypting
		messagePad = messagePad(plaintext, keyFile);

		// Do the encrypt and return it
		return (randBigInt.multiply(prime)).add(messagePad.mod(N));
	}

	/**
	 * Decrypt a cipher back into the plaintext
	 * 
	 * @param cipher  - the encrypted message we are going to decrypt
	 * @param keyFile - the file of keys
	 * @return - the newly decrypted message
	 */
	public static BigInteger decrypt(BigInteger cipher, File keyFile) {
		// Get the keys we need
		ArrayList<BigInteger> keyArr = new ArrayList<BigInteger>();
		keyArr = retrieveKeys(keyFile);
		BigInteger prime = keyArr.get(0);
		int w = keyArr.get(2).intValue();

		// Do the decrypt and return it
		return (cipher.mod(prime)).mod(BigInteger.valueOf(2).pow(w));
	}
}
