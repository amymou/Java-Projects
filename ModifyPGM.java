import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


public class ModifyPGM {

	/**
	 * Each gray value in the returned array is equal to the corresponding value
	 * in the pgm array plus n, but is not less than 0 nor greater than the
	 * maxGrayValue. Throw an IllegalArgumentException if the pgm array is
	 * "ragged", that is one (or more) row(s) contain(s) a different number of
	 * columns than the first row
	 * 
	 * @param pgm
	 *            the pgm array
	 * @param maxGrayValue
	 *            max gray value in the pgm array
	 * @param n
	 *            value to plus
	 * @return a new 2D array with the same number of rows and columns as the
	 *         pgm array.
	 */
	public static int[][] modifyPGM(int[][] pgm, int maxGrayValue, int n) {
		int[][] newPgm = new int[pgm.length][pgm[0].length];
		int col = pgm[0].length;
		for (int k = 0; k < pgm.length; ++k)
			for (int j = 0; j < pgm[k].length; ++j) {
				if (pgm[k].length != col)
					throw new IllegalArgumentException(
							"the pgm array is 'ragged' at row" + k);
				int val = pgm[k][j] + n;
				if (val < 0)
					val = 0;
				else if (val > maxGrayValue)
					val = maxGrayValue;
				newPgm[k][j] = val;
			}
		return newPgm;
	}

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java ModifyPGM [-]n infile outfile");
			return;
		}
		int n = 0;
		try {
			n = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("n must be an integer value");
			return;
		}
		if (!args[1].endsWith(".pgm") || !args[2].endsWith(".pgm")) {
			System.out.println("Filename(s) must have .pgm extension");
			return;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		String inFile = args[1], outFile = args[2];
		// open file streams
		try {
			fis = new FileInputStream(inFile);
		} catch (FileNotFoundException e) {
			System.out.println(inFile + " (No such file or directory)");
			return;
		}
		try {
			fos = new FileOutputStream(outFile);
		} catch (FileNotFoundException e) {
			System.out.println(outFile + " (No such file or directory)");
			try {
				fis.close();
			} catch (IOException e1) {
			}
			return;
		}
		//
		Scanner scanner = new Scanner(fis);
		scanner.nextLine();// fist line is :P2
		int row, col;
		col = scanner.nextInt();// columns of the array
		row = scanner.nextInt();// rows of the array
		String max = scanner.nextLine();
		if (max.length() == 0)// skip empty line
			max = scanner.nextLine();
		int maxGrayValue = Integer.parseInt(max);// max gray value
		int[][] pgm = new int[row][col];
		// read array
		for (int k = 0; k < row; ++k) {
			for (int j = 0; j < col; ++j) {
				pgm[k][j] = scanner.nextInt();
			}
		}
		pgm = modifyPGM(pgm, maxGrayValue, n);
		// save to out file
		PrintStream ps = new PrintStream(fos);
		ps.println("P2");// first line
		ps.println(col + " " + row);// second line
		ps.println(maxGrayValue);// third line
		for (int k = 0; k < row; ++k) {
			for (int j = 0; j < col; ++j) {// save array
				ps.print(" " + pgm[k][j]);
			}
			ps.println();
		}
		// close file streams
		try {
			fis.close();
		} catch (IOException e) {
		}
		try {
			fos.close();
		} catch (IOException e) {
		}
	}

}
