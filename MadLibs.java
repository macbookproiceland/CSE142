
// Zerong Yu, CSE 142, Winter 2017, Section BP
// Programming Assignment #6, 02/21/17
//
// When creating a new mad lib, the program prompts
// the user for input and output file names. Then the
// program reads the input file, prompting the user to
// fill in any placeholders that are found without showing
// the user the rest of the story. As the user fills in each
// placeholder, the program is writing the resulting text to
// the output file. The user can later view the mad lib that
// was created or quit the program.

import java.util.*; // so that I can use scanner
import java.io.*; // so that I can input and output file

public class MadLibs {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		String resp;
		info();

		do {
			System.out.print("(C)reate mad-lib, (V)iew mad-lib, (Q)uit? ");
			resp = console.nextLine();
			if (resp.equalsIgnoreCase("C")) {
				System.out.print("Input file name: ");
				String fileName = console.nextLine();
				File file = new File(fileName);
				file = ifExist(fileName, file, console);
				madLib(file, console);
			} else if (resp.equalsIgnoreCase("V")) {
				viewFile(console);
			}
		} while (!resp.equalsIgnoreCase("Q"));

	}

	// play a round of mad lib
	// has 1 file parameter and 1 Scanner
	public static void madLib(File file, Scanner console) throws FileNotFoundException {
		System.out.print("Output file name: ");
		String fileOut = console.nextLine();
		System.out.println();
		String temp = "";
		Scanner lineScan = new Scanner(file);
		PrintStream output = new PrintStream(fileOut);
		// read each line
		while (lineScan.hasNextLine()) {
			String line = lineScan.nextLine();
			Scanner token = new Scanner(line);
			String vowels = "aeiou";
			boolean isVowel = false;
			// read each token
			while (token.hasNext()) {
				String word = token.next();
				if (word.startsWith("<") && word.length() >= 3) {
					word = word.replace('-', ' ');
					word = word.substring(1, word.length() - 1);
					for (int i = 0; i < 5; i++) {
						if (word.substring(0, 1).equalsIgnoreCase(vowels.substring(i, i + 1))) {
							isVowel = true;
						}
					}
					if (isVowel) {
						System.out.print("Please type an " + word + ": ");
					} else {
						System.out.print("Please type a " + word + ": ");
					}
					word = console.nextLine();
				}
				temp += word + " ";
				isVowel = false;
			}
			output.println(temp);
			temp = "";
		}
		System.out.println("Your mad-lib has been created!");
		System.out.println();
	}

	// has 1 Scanner parameter to read the file
	// reads an input file and prints it out
	public static void viewFile(Scanner console) throws FileNotFoundException {
		System.out.print("Input file name: ");
		String fileName = console.nextLine();
		File file = new File(fileName);
		file = ifExist(fileName, file, console);
		System.out.println();
		Scanner lineScan = new Scanner(file);
		while (lineScan.hasNextLine()) {
			System.out.println(lineScan.nextLine());
		}
		System.out.println();
	}

	// has 3 parameters- String, File, and Scanner types
	// checks if a file exists and promts user to give a new
	// name if not. Returns a file that exists.
	public static File ifExist(String fileName, File file, Scanner console) {
		while (!file.exists()) {
			System.out.print("File not found. Try again: ");
			fileName = console.nextLine();
			file = new File(fileName);
		}
		return file;
	}

	// prints simple instructions at the beginning
	public static void info() {
		System.out.println("Welcome to the game of Mad Libs.");
		System.out.println("I will ask you to provide various words");
		System.out.println("and phrases to fill in a story.");
		System.out.println("The result will be written to an output file.");
		System.out.println();
	}
}
