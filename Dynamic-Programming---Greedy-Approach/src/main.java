import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.Random;
import java.util.Scanner;

public class Ali_Siyar_Arslan_2019510017 {

	static int GOLD_AMOUNT;
	static int MAX_LEVEL_ALLOWED;// 1-9 Pawn Rook Archer ...

	static int NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL;// 1-10

	static int max(int a, int b) {
		return (a > b) ? a : b;
	}

	// Returns the maximum value that can
	// be put in a knapsack of capacity W
	static ArrayList<String> dynamicProgrammingApproach(int W, int wt[], int val[], int n, String[] heros,
			String[] pieceTypes) {// from lab document (knapsack)
		int i, w;
		int K[][] = new int[n + 1][W + 1];
		ArrayList<String> selectedTypes = new ArrayList<String>();
		// Build table K[][] in bottom up manner
		for (i = 0; i <= n; i++) {
			for (w = 0; w <= W; w++) {
				if (i == 0 || w == 0)
					K[i][w] = 0;
				else if (wt[i - 1] <= w)
					K[i][w] = max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
				else
					K[i][w] = K[i - 1][w];
			}
		}

		int res = K[n][W];
		w = W;
		for (i = n; i > 0 && res > 0; i--) {

			// either the result comes from the top (K[i-1][w]) or from (val[i-1] + K[i-1]
			// [w-wt[i-1]]) as in Knapsack table. If it comes from the latter one/ it means
			// the item is included.

			if (res == K[i - 1][w])
				continue;
			else {

				// This item is included. System.out.print(wt[i - 1] + " ");
				
				selectedTypes.add(heros[i - 1] + " " + pieceTypes[i - 1] + " " + wt[i - 1] + " " + val[i - 1]);

				// Since this weight is included its value is deducted
				
				res = res - val[i - 1];
				w = w - wt[i - 1];
			}
		}
		selectedTypes.add("total attack points " + K[n][W]);

		return selectedTypes;
		
	}

	public static void greddyApproach(int[] v, int[] w, int W, String[] heros, String[] pieceTypes) {// fractional
																										// knapsack
		double load = 0;
		int i = v.length - 1;//
		int total_attack_points = 0;
		ArrayList<String> selectedTypes = new ArrayList<String>();
		while (load < W && i >= 0) {// loop works in reverse because array is sent in ascending order

			boolean flag = true;
			for (int j = 0; j < selectedTypes.size(); j++) {// only one element can be taken from each level control
				if (selectedTypes.get(j).equals(pieceTypes[i])) {
					flag = false;
				}
			}

			if (flag && w[i] <= W - load) {// include if there is enough gold
				load += w[i];
				System.out.println(heros[i] + " " + pieceTypes[i] + " " + w[i] + " " + v[i]);
				total_attack_points += v[i];
				selectedTypes.add(pieceTypes[i]);
			}
			i--;

		}

		System.out.println("total attack points " + total_attack_points);//
	}

	public static ArrayList<String> randomApproach(int[] v, int[] w, int W, String[] heros, String[] pieceTypes) {
		Random rnd = new Random();
		int load = 0;
		int total_attack_points = 0;
		ArrayList<String> selected = new ArrayList<String>();
		for (int i = 0; i < MAX_LEVEL_ALLOWED; i++) {
			int select = rnd.nextInt(2);// take or not take soldier from that level //random.nextInt(max - min) + min;

			if (select == 1) {

				int max = (i + 1) * NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL;
				int min = (i + 1) * NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL - NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL;

				int selectHero = rnd.nextInt(max - min) + min;// random soldier from this level

				if (w[selectHero] + load < W) {
					total_attack_points += v[selectHero];
					load += w[selectHero];
					selected.add(heros[selectHero] + " " + pieceTypes[selectHero] + " " + v[selectHero] + " "
							+ w[selectHero]);

				}
			}

		}

		selected.add("total attack points : " + total_attack_points);
		return selected;

	}

	public static void main(String[] args) {

		File file = new File("input_1.csv");
		String[] allHeros = new String[90];
		String[] allPieceTypes = new String[90];
		int[] allGolds = new int[90];
		int[] allAttackPoints = new int[90];
		double[] allPrices = new double[90];

		try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
			int i = 0;
			while (sc.hasNextLine()) {

				String[] arrOfStr = sc.nextLine().split(",");
				if (i != 0) {
					allHeros[i - 1] = arrOfStr[0];
					allPieceTypes[i - 1] = arrOfStr[1];
					allGolds[i - 1] = Integer.parseInt(arrOfStr[2]);
					allAttackPoints[i - 1] = Integer.parseInt(arrOfStr[3]);
					allPrices[i - 1] = (double) allAttackPoints[i - 1] / allGolds[i - 1];
				}
				i++;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scanner scn =new Scanner(System.in);
		System.out.print("enter gold amount :");
		while (true) {
			GOLD_AMOUNT=scn.nextInt();
			if (GOLD_AMOUNT>=5&&GOLD_AMOUNT<=1200) {
				break;
			}
			System.out.print("gold amount can be between 5 and 1200 enter new value:");
		}
		
		System.out.print("enter max level allowed :");
		while (true) {
			MAX_LEVEL_ALLOWED=scn.nextInt();
			if (MAX_LEVEL_ALLOWED>=1&&MAX_LEVEL_ALLOWED<=9) {
				break;
			}
			System.out.print("max level allowed can be between 1 and 9 enter new value:");
		}
		
		System.out.print("enter number of available pieces per level :");
		
		while (true) {
			NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL=scn.nextInt();
			if (NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL>=1&&NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL<=25) {
				break;
			}
			System.out.print("number of available pieces per level can be between 1 and 25 enter new value:");
		}



		// create new array according to NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL and
		// MAX_LEVEL_ALLOWED
		String[] lastHeros = new String[NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL * MAX_LEVEL_ALLOWED];
		String[] lastPieceTypes = new String[NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL * MAX_LEVEL_ALLOWED];
		int[] lastGolds = new int[NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL * MAX_LEVEL_ALLOWED];
		int[] lastAttackPoints = new int[NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL * MAX_LEVEL_ALLOWED];
		double[] lastPrices = new double[NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL * MAX_LEVEL_ALLOWED];

		int index = 0;
		for (int j = 0; j < MAX_LEVEL_ALLOWED; j++) {

			for (int i = 0; i < NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL; i++) {
				lastHeros[index] = allHeros[(j * 10) + i];
				lastPieceTypes[index] = allPieceTypes[(j * 10) + i];
				lastGolds[index] = allGolds[(j * 10) + i];
				lastAttackPoints[index] = allAttackPoints[(j * 10) + i];
				lastPrices[index] = allPrices[(j * 10) + i];
				index++;
			}

		}

		long beginTime3 = 0, endTime3 = 0;

		beginTime3 = System.nanoTime();
		ArrayList<String> randomApproachResults = randomApproach(lastAttackPoints, lastGolds, GOLD_AMOUNT, lastHeros,
				lastPieceTypes);// knapsack

		endTime3 = System.nanoTime();
		long randomApproachEstimatedTime = endTime3 - beginTime3;

		System.out.println("=============== TRIAL #1 ===============");

		long beginTime = 0, endTime = 0;

		beginTime = System.nanoTime();
		//
		ArrayList<String> dynamicProgramingResults = dynamicProgrammingApproach(GOLD_AMOUNT, lastGolds,
				lastAttackPoints, lastAttackPoints.length, lastHeros, lastPieceTypes);// knapsack

		endTime = System.nanoTime();
		long dynamicProgramingEstimatedTime = endTime - beginTime;

		System.out.println("\nUser's Dynamic Programing results");

		for (int i = 0; i < dynamicProgramingResults.size(); i++) {
			System.out.println(dynamicProgramingResults.get(i));
		}

		System.out.println("\nComputer's Greedy Approach results");
		long beginTime2 = 0, endTime2 = 0;

		beginTime2 = System.nanoTime();
		int n = lastPrices.length;
		for (int i = 0; i < n - 1; i++) {// Sort items in the increasing order of AttackPoint/gold (bubble sort)
			for (int j = 0; j < n - i - 1; j++) {
				if (lastPrices[j] > lastPrices[j + 1]) {
					// swap arr[j+1] and arr[j]
					double temp_lastPrices = lastPrices[j];
					lastPrices[j] = lastPrices[j + 1];
					lastPrices[j + 1] = temp_lastPrices;

					String temp_lastHeros = lastHeros[j];
					lastHeros[j] = lastHeros[j + 1];
					lastHeros[j + 1] = temp_lastHeros;

					String temp_lastPieceTypes = lastPieceTypes[j];
					lastPieceTypes[j] = lastPieceTypes[j + 1];
					lastPieceTypes[j + 1] = temp_lastPieceTypes;

					int temp_lastGolds = lastGolds[j];
					lastGolds[j] = lastGolds[j + 1];
					lastGolds[j + 1] = temp_lastGolds;

					int temp_lastAttackPoints = lastAttackPoints[j];
					lastAttackPoints[j] = lastAttackPoints[j + 1];
					lastAttackPoints[j + 1] = temp_lastAttackPoints;
				}
			}
		}

		greddyApproach(lastAttackPoints, lastGolds, GOLD_AMOUNT, lastHeros, lastPieceTypes);// fractionalKnapsack

		endTime2 = System.nanoTime();
		long greddyApproachEstimatedTime = endTime2 - beginTime2;

		System.out.println("\n=============== TRIAL #2 ===============");

		System.out.println("\nUser's Dynamic Programing results");
		for (int i = 0; i < dynamicProgramingResults.size(); i++) {
			System.out.println(dynamicProgramingResults.get(i));
		}

		System.out.println("\nComputer's Random Approach results");


		for (int i = 0; i < randomApproachResults.size(); i++) {
			System.out.println(randomApproachResults.get(i));
		}

		System.out.println("\nRun time for User's Dynamic Programing results  : " + dynamicProgramingEstimatedTime);
		System.out.println("Run time for Computer's Greedy Approach results  : " + greddyApproachEstimatedTime);
		System.out.println("Run time for Computer's Random Approach results  : " + randomApproachEstimatedTime);

	}

}
