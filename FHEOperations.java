
/**
 *  @Author: Carson Morris
 *  CS331 - P1
 *  This class is for performing the homo-morphic operations for both version 1 and 2
 */
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;

public class FHEOperations {
	/**
	 * Checking for homomorphic equality between two ciphers
	 * 
	 * @param cipher1 - first cipher we are comparing
	 * @param cipher2 - second cipher we are comparing
	 * @param keyFile - the file of keys
	 * @return - True if the ciphers are equal, false if not.
	 */
	public static boolean hmEqual(BigInteger cipher1, BigInteger cipher2, File keyFile) {
		// Declare some initial variables
		BigInteger T, difference;
		// Retrieve keys and store them
		ArrayList<BigInteger> arrList = new ArrayList<BigInteger>();
		ArrayList<BigInteger> keys = new ArrayList<BigInteger>();
		keys = FHEv1.retrieveKeys(keyFile);
		for (int i = 2; i < keys.size() - 1; i++) {
			arrList.add(keys.get(i));
		}
		// Assign T
		T = keys.get(keys.size() - 1);
		// Get the difference using the largest cipher to ensure positive values
		difference = cipher2.subtract(cipher1);
		// If the cipher1 - cipher2 is a better option, use that
		if (cipher1.compareTo(cipher2) == 1) {
			difference = cipher1.subtract(cipher2);
		}
		// Ensure that the equality holds for all cases
		int i = 0;
		while (i < arrList.size()) {
			BigInteger g = arrList.get(i);
			if (g.modPow(difference, T).equals(BigInteger.ONE)) {
				i++;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Homomorphic addition between two ciphers using the keys in the keyFile
	 * 
	 * @param cipher1 - first cipher
	 * @param cipher2 - second cipher
	 * @param keyFile - the file with the keys
	 * @return - the sum of the ciphers
	 */
	public static BigInteger hmAdd(BigInteger cipher1, BigInteger cipher2, File keyFile) {
		BigInteger retVal, N;
		// Retrieve the key and assign it to N
		ArrayList<BigInteger> keys = new ArrayList<BigInteger>();
		keys = FHEv1.retrieveKeys(keyFile);
		N = keys.get(1);
		// Homomorphic add
		retVal = (cipher1.add(cipher2)).mod(N);
		return retVal;
	}

	/**
	 * Homomorphic multiplication between two ciphers using the keys in the keyFile
	 * 
	 * @param cipher1 - the first cipher
	 * @param cipher2 - the second cipher
	 * @param keyFile - the file with the keys
	 * @return - the product of the two ciphers
	 */
	public static BigInteger hmMult(BigInteger cipher1, BigInteger cipher2, File keyFile) {
		BigInteger retVal, N;
		// Retrieve the key and assign it to N
		ArrayList<BigInteger> keys = new ArrayList<BigInteger>();
		keys = FHEv1.retrieveKeys(keyFile);
		N = keys.get(1);
		// Homomorphic multiplication
		retVal = (cipher1.multiply(cipher2)).mod(N);
		return retVal;
	}

}
