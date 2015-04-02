package integermatrix;

import java.util.Random;

public class IntMatrixTest {
	public static void main(String[] args) {

		IntMatrix m1 = new IntMatrix(2, 3);
		System.out.println(m1);

		IntMatrix m2 = new IntMatrix(5, 3, 100);
		System.out.println(m2);

		int i = m1.get(1, 2);
		System.out.println("m1.get(1, 2) => " + i);

		IntMatrix m3 = IntMatrix.getRandomMatrix(4, 6, 200);
		System.out.println(m3);

		m1 = new IntMatrix(5, 3, 100);
		m2 = new IntMatrix(5, 3, 100);

		System.out.println(m1.equals(m2));

		System.out.println("now 2 rand IntMatrix");
		m1 = IntMatrix.getRandomMatrix(5, 3, 100);
		m2 = IntMatrix.getRandomMatrix(5, 3, 100);

		System.out.println(m1.equals(m2));
	}
}

class IntMatrix {
	// ++++++++++++++++++++++++ Variablen: ++++++++++++++++++++++++++++++++++
	int[] matrix[];
	int maxRows, maxCols;
	int capacity;

	// ++++++++++++++++++++++++ Konstruktor: ++++++++++++++++++++++++++++++++
	public IntMatrix() {
		this(2, 2);
	}

	public IntMatrix(int rows, int cols) {
		this(rows, cols, 0);
	}

	public IntMatrix(int rows, int cols, int defVal) {
		this.matrix = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = defVal;
			}
		}
		this.maxRows = rows;
		this.maxCols = cols;
		this.capacity = rows * cols * 4; // TODO: rückrechen um max dim zu
											// bekommen...
	}

	// hilfs Konstruktor für die getRandomMatrix Methode
	private IntMatrix(int rows, int cols, int minVal, int maxVal) {
		this.matrix = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = new Random().nextInt(maxVal) + minVal;
			}
		}
		this.maxRows = rows;
		this.maxCols = cols;
		this.capacity = rows * cols * 4; // TODO: rückrechen um max dim zu
											// bekommen...
	}

	// ++++++++++++++++++++++++ Methoden: +++++++++++++++++++++++++++++++++++
	public Integer get(int rowPos, int colPos) throws RowLengthException,
			ColLengthException {
		if ((rowPos < 0) || (rowPos > maxRows))
			throw new RowLengthException();
		if ((colPos < 0) || (colPos > maxCols))
			throw new ColLengthException();

		return matrix[rowPos - 1][colPos - 1];
	}// end of get()

	static IntMatrix getRandomMatrix(int rows, int cols, int maxVal) {
		return new IntMatrix(rows, cols, 0, maxVal);
	}// end of getRandomMatrix()

	@Override
	public boolean equals(Object obj) {
		boolean ret = false;
		if (obj instanceof IntMatrix) {
			IntMatrix tmpIM = (IntMatrix) obj;
			if ((tmpIM.maxRows == this.maxRows)
					&& (tmpIM.maxCols == this.maxCols)) {
				for (int i = 0; i < tmpIM.matrix.length; i++) {
					for (int j = 0; j < tmpIM.matrix[i].length; j++) {
						if (!(tmpIM.matrix[i][j] == this.matrix[i][j])) {
							return false;
						}
					}
				}// wenn ein Wert nicht gleich ist geht es raus
				ret = true;
			}// maxRows & maxCols Prüfung
		}// instanceof Prüfung
		return ret;
	}// end of equals()

	@Override
	public String toString() {
		String sF = "";

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				sF += String.format("%3d, ", matrix[i][j]);
			}
			sF = sF.substring(0, sF.lastIndexOf(","));
			sF += "\n";
		}
		return sF;
	}// end of toString()

	// ++++++++++++++++++++++++ Exceptions: +++++++++++++++++++++++++++++++++
	class RowLengthException extends IllegalArgumentException {
		@Override
		public String getMessage() {
			String msg = "Wertebereiche beachten: für rowPos [0 -" + maxRows
					+ "]";
			return msg;
		}
	}

	class ColLengthException extends IllegalArgumentException {
		@Override
		public String getMessage() {
			String msg = "Wertebereiche beachten: für colPos[0 - " + maxCols
					+ "]";
			return msg;
		}
	}
}
