package lotto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class LottoSpielSimulation {
	public static void main(String[] args) {
		int anzahlKugel = 7;
		int anzahlKugelGesamt = 49;
		int anzahlSpiele = 1000;
		int kostenProSpiel = 1;

		int gewinn = ziehung(anzahlKugel, anzahlKugelGesamt, anzahlSpiele);
		int kostenGesamt = kostenProSpiel * anzahlSpiele;
		gewinn = gewinn - kostenGesamt;
		float gewProSpiel = gewinn / anzahlSpiele;

		System.out.printf("Kosten pro Spiel betragen: %d Euro%n",
				kostenProSpiel);
		System.out
				.printf("Der gesamte Gewinn (abzüglich Kosten) nach %d Spielen(%d aus %d) beträgt %d Euro.%n",
						anzahlSpiele, anzahlKugel, anzahlKugelGesamt, gewinn);
		System.out.printf(
				"Das entspricht einem Gewinn von %.2f Euro pro Spiel.",
				gewProSpiel);

	}// end of main

	public static int ziehung(int anzahlKugel, int anzahlKugelGesamt,
			int anzahlSpiele) {
		int gewinn = 0;
		Scanner in = null;
		if (anzahlSpiele < 3) {
			in = new Scanner(System.in);
		}

		for (int i = 0; i < anzahlSpiele; i++) {
			if (anzahlSpiele < 3) {
				System.out.println("\n++++++++++++ Spiel Nr." + (i + 1)
						+ "+++++++++++");
			}
			LottoSpiel lotto = new LottoSpiel(anzahlKugel, anzahlKugelGesamt);
			lotto.ziehen();
			if (anzahlSpiele < 3) {
				System.out.println(lotto);
			}

			LottoTipp tipp = new LottoTipp(anzahlKugel, anzahlKugelGesamt);

			if (anzahlSpiele < 3) {
				tipp.userInput(in);
			} else {
				tipp.abgeben();
			}

			if (anzahlSpiele < 3) {
				System.out.println(tipp);
			}
			gewinn += lotto.vergleichen(tipp);
			if (anzahlSpiele < 3) {
				System.out
						.println("___ Der Gewinn, bishergespielter Spiele beträgt: ___\n"
								+ "______________________ "
								+ gewinn
								+ " ________________________\n");
			}

		}
		if (in != null)
			in.close();
		return gewinn;
	}
}// end of class LottoSpielSimulation

class LottoSpiel {
	// ++++++++++++++++++++++++ Variablen: ++++++++++++++++++++++++++++++++++
	int anzahlKugel, anzahlKugelGesamt;
	ArrayList<Integer> spielErgebnis = new ArrayList<Integer>();

	// kugeln fehlen
	// ++++++++++++++++++++++++ Konstruktor: ++++++++++++++++++++++++++++++++
	public LottoSpiel(int anzahlKugel, int anzahlKugelGesamt) {
		this.anzahlKugel = anzahlKugel;
		this.anzahlKugelGesamt = anzahlKugelGesamt;
	}

	public int vergleichen(LottoTipp tipp) {
		// 0 richtige: 0 Euro
		// 1 richtige: 1 Euro
		// 2 richtige: 10 Euro
		// 3 richtige: 100 Euro
		// 4 richtige: 1000 Euro
		int gewinn = 0;
		for (Integer spielZahl : spielErgebnis) {
			if (tipp.tipp.contains(spielZahl)) {
				gewinn++;
			}
		}
		if (gewinn == 0)
			return 0;

		gewinn = (int) Math.pow(10, gewinn - 1);
		return gewinn;
	}

	// ++++++++++++++++++++++++ Methoden: +++++++++++++++++++++++++++++++++++
	public void ziehen() {
		Integer rand;

		for (; spielErgebnis.size() < anzahlKugel;) {
			rand = new Random().nextInt(anzahlKugelGesamt) + 1;
			if (!spielErgebnis.contains(rand))
				spielErgebnis.add(rand);
		}

		// sortiert die liste
		Comparator<Integer> cmp = new Comparator<Integer>() {
			@Override
			public int compare(Integer i1, Integer i2) {
				return i1 - i2;
			}
		};

		spielErgebnis.sort(cmp);

	}// end of ziehen

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Spiel " + anzahlKugel + " aus " + anzahlKugelGesamt + ". [");
		for (int i : spielErgebnis) {
			sb.append(i + ", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append(']');
		return sb.toString();
	}// end of @Override toString()

}// end of class LottoSpiel

class LottoTipp {
	// ++++++++++++++++++++++++ Variablen: ++++++++++++++++++++++++++++++++++
	int anzahlKugel, anzahlKugelGesamt;
	ArrayList<Integer> tipp = new ArrayList<Integer>();

	// ++++++++++++++++++++++++ Constructor: ++++++++++++++++++++++++++++++++
	public LottoTipp(int anzahlKugel, int anzahlKugelGesamt) {
		this.anzahlKugel = anzahlKugel;
		this.anzahlKugelGesamt = anzahlKugelGesamt;
	}

	public void userInput(Scanner in) {
		System.out.printf("Geben Sie %d Zahlen zwischen 1 und %d", anzahlKugel,
				anzahlKugelGesamt);
		int userInp;
		// scanner

		try {
			while (tipp.size() < anzahlKugel) {
				userInp = in.nextInt();
				if ((userInp > 0) && (userInp <= anzahlKugelGesamt)
						&& (!tipp.contains(userInp))) {
					tipp.add(userInp);
				}
			}
		} catch (InputMismatchException e) {
			System.out
					.printf("Nur Zahlen zwischen 1 und %d", anzahlKugelGesamt);
		}

		Comparator<Integer> cmp = new Comparator<Integer>() {
			@Override
			public int compare(Integer i1, Integer i2) {
				return i1 - i2;
			}
		};
		tipp.sort(cmp);
	}

	// ++++++++++++++++++++++++ Methoden: +++++++++++++++++++++++++++++++++++
	public void abgeben() {
		Integer rand;

		for (; tipp.size() < anzahlKugel;) {
			rand = new Random().nextInt(anzahlKugelGesamt) + 1;
			if (!tipp.contains(rand))
				tipp.add(rand);
		}

		// sortiert die liste
		Comparator<Integer> cmp = new Comparator<Integer>() {
			@Override
			public int compare(Integer i1, Integer i2) {
				return i1 - i2;
			}
		};

		tipp.sort(cmp);
	}// end of abgeben()

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Tipp " + anzahlKugel + " aus " + anzahlKugelGesamt + ". [");
		for (int i : tipp) {
			sb.append(i + ", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append(']');
		return sb.toString();
	}

}// end of class LottoTipp