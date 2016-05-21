package homeWork4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.List;
import java.util.Scanner;

public class Problem3 
{
	static List<HashMap<String, Integer>> schema;
	
	public static int countRecords(File f) throws FileNotFoundException
	{
		Scanner sc = new Scanner(f);
		int count = 0;
		while(sc.hasNextLine())
		{
			++count;
			sc.nextLine();
			//System.out.println(count);
		}
		sc.close();
		
		return count;
	}

	
	public static int generateFrequent1Itemsets(Itemset itemset1, double supportThresh, boolean sparseMatrix[][])
	{
		int i, j; 
		double count, supportCurrent;
		ArrayList<TreeSet<Integer>>frequentItemset = new ArrayList<TreeSet<Integer>>();
		ArrayList<Double>support = new ArrayList<Double>();
		ArrayList<Double>supportCount = new ArrayList<Double>();
		int totalConsideredItemsets = 0;
		int records = sparseMatrix.length;//countRecords(f);
		for(i=0; i < sparseMatrix[0].length; ++i)
		{
			count = 0;
			for(j = 0; j < sparseMatrix.length; ++j)
			{
				if(sparseMatrix[j][i] == true)
					++count;
			}
			totalConsideredItemsets++;
			supportCurrent = count/records;
			if(supportCurrent >= supportThresh)
			{
				TreeSet<Integer> temp = new TreeSet<Integer>();
				temp.add(i);
				frequentItemset.add(temp);
				support.add(supportCurrent);
				supportCount.add(count);
			}
		}
		itemset1.frequentItemset = frequentItemset;
		itemset1.support = support;
		itemset1.supportCount = supportCount;
		//System.out.println();
		//System.out.println(frequentItemset + "\n" + support);
		//System.out.println(schema);
		return totalConsideredItemsets;
	}

	
	public static Itemset aprioriGenkMinus1X1(Itemset itemset1, Itemset itemsetK1)
	{
		Itemset temp = new Itemset();
		ArrayList<TreeSet<Integer>> frequentItemset1, frequentItemsetK1, frequentItemsetResult;
		frequentItemset1 = itemset1.frequentItemset;
		frequentItemsetK1 = itemsetK1.frequentItemset;
		frequentItemsetResult = new ArrayList<TreeSet<Integer>>();
		
		TreeSet<Integer> tempResult = null;
		
		for(TreeSet<Integer> tempK1: frequentItemsetK1)
		{
			for(TreeSet<Integer> temp1: frequentItemset1)
			{
				if(temp1.last() > tempK1.last())
				{
					tempResult = new TreeSet<Integer>();
					tempResult.addAll(temp1);
					tempResult.addAll(tempK1);
					if(tempResult != null)
					{
						frequentItemsetResult.add(tempResult);
					}
				}
			}
		}
		temp.frequentItemset = frequentItemsetResult;
		return temp;
	}

	
	public static Itemset aprioriGenkMinus1XkMinus1(Itemset itemset1, int count)
	{
		Itemset temp = new Itemset();
		ArrayList<TreeSet<Integer>> frequentItemset1, frequentItemsetResult;
		frequentItemset1 = itemset1.frequentItemset;
		frequentItemsetResult = new ArrayList<TreeSet<Integer>>();
		
		TreeSet<Integer> tempResult = null;
		int i, j, size = frequentItemset1.size();
		
		for(i = 0; i <size; ++i)
		{
			TreeSet<Integer> itemsetForcomparison1 = frequentItemset1.get(i);
			TreeSet<Integer> copy1;
			for(j = i+1; j<size; ++j)
			{
				copy1 = new TreeSet<Integer>();
				copy1.addAll(itemsetForcomparison1);
				
				TreeSet<Integer> itemsetForcomparison2 = frequentItemset1.get(j);
				TreeSet<Integer> copy2 = new TreeSet<Integer>();
				copy2.addAll(itemsetForcomparison2);
				int last1 = 0, last2 = 0;
				if(count !=0)
				{
					last1 = copy1.pollLast();
					last2 = copy2.pollLast();
				
					copy1.removeAll(copy2);
					if(copy1.size()!= 0)
					{
						continue;
					}
					
					if(last1 == last2)
					{
						continue;
					}
				}
				tempResult = new TreeSet<Integer>();
				tempResult.addAll(frequentItemset1.get(i));
				tempResult.addAll(frequentItemset1.get(j));
				if(tempResult != null)
				{
					frequentItemsetResult.add(tempResult);
				}
				
			}
		}
		
		temp.frequentItemset = frequentItemsetResult;
		return temp;
	}

	
	public static void pruneItemset(Itemset itemset, double supportThresh, boolean arr[][])
	{
		if(itemset.frequentItemset.size() == 0)
			return;
		
		ArrayList<TreeSet<Integer>> frequentItemset, remove;
		frequentItemset = itemset.frequentItemset;
		//int totalItemsetsConsidered = frequentItemset.size();
		
		remove = new ArrayList<TreeSet<Integer>>();
		ArrayList<Double> support = new ArrayList<Double>();
		ArrayList<Double> supportCount = new ArrayList<Double>();
		int i, totalRecords; 
		double count;
		boolean flag;
		
		totalRecords = arr.length;
		for(TreeSet<Integer> temp: frequentItemset)
		{
			count = 0; //support count
			
			for(i=0; i< arr.length; ++i)
			{
				flag = false;
				for(int valueInItemSet: temp)
				{
					if(arr[i][valueInItemSet] == true)
					{
						continue;
					}
					flag = true;
					break;
				}
				if(flag == false)
					++count;
			}
			
			if(count/totalRecords < supportThresh)
			{
				remove.add(temp);
			}
			else
			{
				support.add(count/totalRecords);
				supportCount.add(count);
			}
		}
		frequentItemset.removeAll(remove);
		
		itemset.frequentItemset = frequentItemset;
		itemset.support = support;
		itemset.supportCount = supportCount;
		//return totalItemsetsConsidered;
	}

	
	public static boolean compareLast2ItemsetsGenerated(Itemset itemset1, Itemset itemset2)
	{
		if(itemset2.frequentItemset.size() ==0)
			return false;
		if(itemset1.frequentItemset.size() != itemset2.frequentItemset.size())
			return true;
		
		ArrayList<TreeSet<Integer>> frequentItemset1, frequentItemset2;
		frequentItemset1 = itemset1.frequentItemset;
		frequentItemset2 = itemset2.frequentItemset;
		
		for(TreeSet<Integer> temp: frequentItemset1)
		{
			if(!frequentItemset2.contains(temp))
			{
				return false;
			}
		}
		
		return true;
	}

	
	public static void kMinus1X1(ArrayList<Itemset> itemset, Itemset itemset1, double supportThresh, boolean[][] sparseMatrix, int totalConsideredIn1Item) throws FileNotFoundException
	{
		
		int count = 0;
		int totalItemsetsConsidered = totalConsideredIn1Item;
		while(true)
		{
			//System.out.println("Iteration Count: " + (count+1));
			Itemset temp = aprioriGenkMinus1X1(itemset1, itemset.get(count));
			totalItemsetsConsidered += temp.frequentItemset.size(); 
			pruneItemset(temp, supportThresh, sparseMatrix);
			
			//System.out.println(itemset.get(count).frequentItemset+ " \n"+ temp.frequentItemset);
			if(compareLast2ItemsetsGenerated(itemset.get(count), temp))
			{
				itemset.add(temp);
			}
			else
				break;
			++count;
		}
		count = 0;
		int num = 1;
		System.out.println("----------Itemsets Produced Using k-1 X 1 Method------------------");
		System.out.println("\n------------------------------------------------------------------");
		
		for(Itemset temp: itemset)
		{
			System.out.println(num++ +"-Itemset:" + temp.frequentItemset);
			//System.out.println("Support:" + temp.support);
			//System.out.println("SupportCount:" + temp.supportCount);
			count += temp.frequentItemset.size();
		}
		System.out.println("Number of Itemsets considered: " + totalItemsetsConsidered);
		System.out.println("Number of Frequent Itemsets Generated: " + count);
		System.out.println("\n------------------------------------------------------------------");
	}

	
	public static void kMinus1XkMinus1(ArrayList<Itemset> itemset, Itemset itemset1, double supportThresh, boolean[][] sparseMatrix, int totalConsideredIn1Item) throws FileNotFoundException
	{
		int count = 0;
		int totalItemsetsConsidered = totalConsideredIn1Item;
		while(true)
		{
			//System.out.println("Iteration Count: " + (count+1));
			Itemset temp = aprioriGenkMinus1XkMinus1(itemset.get(count), count);
			//System.out.println("Before Pruning:\n "+ temp.frequentItemset);
			totalItemsetsConsidered += temp.frequentItemset.size();
			pruneItemset(temp, supportThresh, sparseMatrix);
			//System.out.println("After Pruning:\n "+ temp.frequentItemset);
			
			if(compareLast2ItemsetsGenerated(itemset.get(count), temp))
			{
				itemset.add(temp);
			}
			else
				break;
			++count;
			
		}
		count = 0;
		int num = 1;
		System.out.println("\n----------Itemsets Produced Using k-1 X k-1 Method----------------");
		
		for(Itemset temp: itemset)
		{
			System.out.println(num++ + "-Itemset: " + temp.frequentItemset);
			//System.out.println("Support: " + temp.support);
			//System.out.println("Support Count: " + temp.supportCount);
			count += temp.frequentItemset.size();
			if(temp.frequentItemset.isEmpty() || temp.frequentItemset.get(0).size() == 1)
				temp.rulesViaBruteForceCount = 0;
			else
			{
				temp.rulesViaBruteForceCount = ((Math.pow(2, temp.frequentItemset.get(0).size()) -2)* temp.frequentItemset.size());
			}
			//System.out.println(temp.rulesViaBruteForceCount);
		}
		
		System.out.println("Number of Itemsets considered: " + totalItemsetsConsidered);
		System.out.println("Number of Frequent Itemsets Generated: " + count);
		System.out.println("\n------------------------------------------------------------------");
	}

	
	public static int maximalFrequentItemSetCount(ArrayList<Itemset> itemset)
	{
		int count = 0;
		for(int i = 0; i < itemset.size() - 1; ++i)
		{
			Itemset compare1 = itemset.get(i);
			Itemset compare2 = itemset.get(i+1);
			count += compare1.getFrequentItemSetCount(compare2);
		}
		return count+itemset.get(itemset.size()-1).frequentItemset.size();
	}

	
	public static int closedFrequentItemSetCount(ArrayList<Itemset> itemset)
	{
		int count = 0;
		for(int i = 0; i < itemset.size() - 1; ++i)
		{
			Itemset compare1 = itemset.get(i);
			Itemset compare2 = itemset.get(i+1);
			count += compare1.getClosedItemSetCount(compare2);
		}
		return count + itemset.get(itemset.size()-1).frequentItemset.size();
	}

	
	public static ArrayList<TreeSet<Integer>> generateSubSets(TreeSet<Integer> set)
	{
		ArrayList<TreeSet<Integer>> subsets = new ArrayList<TreeSet<Integer>>();
		Integer[] arr = set.toArray(new Integer[set.size()]);
		int i, temp, j; 
		int n = set.size();
		
		int totalElements = 1<< n;
		
		for(i=1; i<totalElements; ++i)
		{
			TreeSet<Integer> temporary = new TreeSet<Integer>();
			for(j=0;j<n;j++)
			{
				temp = (i& (1<<j));
				if(temp > 0)
				{
					temporary.add(arr[j].intValue());
				}
			}
			if(temporary.size() == set.size() && temporary.containsAll(set))
				continue;
			subsets.add(temporary);
		}
		
		return subsets;
	}
	
	
	public static double getSupportCount(TreeSet<Integer> set, ArrayList<Itemset> itemset)
	{
		double supportCount = 0;
		Itemset inWhichSetISPresent = itemset.get(set.size()-1);
		ArrayList<TreeSet<Integer>> frequentItemsets = inWhichSetISPresent.frequentItemset;
		int iterator = 0;
		
		for(TreeSet<Integer> item: frequentItemsets)
		{
			if((item.size() == set.size()) && (item.containsAll(set)))
			{
				supportCount = inWhichSetISPresent.supportCount.get(iterator);
				break;
			}
			++iterator;
		}
		
		return supportCount;
	}

	
	public static double getSupport(TreeSet<Integer> set, ArrayList<Itemset> itemset)
	{
		double support = 0;
		Itemset inWhichSetISPresent = itemset.get(set.size()-1);
		ArrayList<TreeSet<Integer>> frequentItemsets = inWhichSetISPresent.frequentItemset;
		int iterator = 0;
		
		for(TreeSet<Integer> item: frequentItemsets)
		{
			if((item.size() == set.size()) && (item.containsAll(set)))
			{
				support = inWhichSetISPresent.support.get(iterator);
				break;
			}
			++iterator;
		}
		
		return support;
	}
	
	//public static TreeSet<String> generateRules(ArrayList<Itemset> itemset, double confidenceThresh, ArrayList<TreeSet<Integer>> ruleLeftPart, ArrayList<TreeSet<Integer>> ruleRightPart, ArrayList<Rules> ruleObjects, ArrayList<Double> confidence)
	public static void generateRulesUsingConfidence(ArrayList<Itemset> itemset, double confidenceThresh, ArrayList<Rules> ruleObjects)
	{
		//TreeSet<String> rules = new TreeSet<String>(); // not using this
		TreeSet<Integer> right, left;
		
		ArrayList<TreeSet<Integer>> frequentItemsets;
		int i;
		double supportAC, supportA, confid;
		//String rule= "";
		for(i = itemset.size() - 1; i > 0; --i)
		{
			frequentItemsets = itemset.get(i).frequentItemset;
			
			for(TreeSet<Integer> set: frequentItemsets)
			{
				ArrayList<TreeSet<Integer>> subsets = generateSubSets(set);
				
				for(TreeSet<Integer> subset: subsets)
				{
					TreeSet<Integer> setCopy = new TreeSet<Integer>();
					TreeSet<Integer> subsetCopy = new TreeSet<Integer>();
					setCopy.addAll(set);
					subsetCopy.addAll(subset);
					setCopy.removeAll(subsetCopy);
					
					left = new TreeSet<Integer>();
					right = new TreeSet<Integer>();
					
					supportAC = getSupportCount(set, itemset);
					supportA = getSupportCount(subset, itemset);
					confid = (supportAC/supportA);
					if(confid >= confidenceThresh)
					{
						Rules ruleGenerated = new Rules();
						//rule = subsetCopy.toString() + " -> " + setCopy.toString();
						left.addAll(subsetCopy);
						right.addAll(setCopy);
						ruleGenerated.confidence = confid;
						ruleGenerated.ruleLeftPart = left;
						ruleGenerated.ruleRightPart = right;
						ruleObjects.add(ruleGenerated);
						//rules.add(rule); // rule generated in string format
					}
				}
			}
		}
		//return rules;
	}
	
	//Remove and do this stuff in upper function itself
	public static void generateRulesUsingLift(ArrayList<Itemset> itemset, ArrayList<Rules> ruleObjects)
	{
		TreeSet<Integer> right, left;
		
		ArrayList<TreeSet<Integer>> frequentItemsets;
		int i;
		double supportI, supportX, supportIMinusX, lift;
		for(i = itemset.size() - 1; i > 0; --i)
		{
			frequentItemsets = itemset.get(i).frequentItemset;
			
			for(TreeSet<Integer> set: frequentItemsets)
			{
				ArrayList<TreeSet<Integer>> subsets = generateSubSets(set);
				
				for(TreeSet<Integer> subset: subsets)
				{
					TreeSet<Integer> setCopy = new TreeSet<Integer>();
					TreeSet<Integer> subsetCopy = new TreeSet<Integer>();
					setCopy.addAll(set);
					subsetCopy.addAll(subset);
					setCopy.removeAll(subsetCopy);
					
					left = new TreeSet<Integer>();
					right = new TreeSet<Integer>();
					
					supportI = getSupport(set, itemset);
					supportX = getSupport(subset, itemset);
					supportIMinusX = getSupport(setCopy, itemset);
					
					lift = supportI/(supportX * supportIMinusX);
					//if(lift < 1)
					//	continue;
					Rules ruleGenerated = new Rules();
					ruleGenerated.lift = lift;
					left.addAll(subsetCopy);
					right.addAll(setCopy);
					ruleGenerated.ruleLeftPart = left;
					ruleGenerated.ruleRightPart = right;
					ruleObjects.add(ruleGenerated);
					
				}
			}
		}
	}
	
	public static int calculateRulesByBruteForce(ArrayList<Itemset> itemsetFork1Xk1)
	{
		int count = 0;//returns total number of rules that can be generated by brute force
		for(Itemset temp: itemsetFork1Xk1)
		{
			count+= temp.rulesViaBruteForceCount;
		}
		
		return count;
		
	}
	
	public static String mapping(TreeSet<Integer> set, String mapping[])
	{
		String result="[";
		String temp = "";
		int count = 0;
		for(int i: set)
		{
			
			if(count != set.size()-1)
				temp += mapping[i] + ", ";
			else
				temp += mapping[i];
			++count;
		}
		
		result += temp + "]";
		
		return result;				
	}
	public static void main(String args[]) throws FileNotFoundException
	{
		/*###########################################Nursery Dataset##########################################*/
		File file = new File("D:\\Documents\\CS\\IUB Courses\\2nd Sem\\Data Mining\\Homework\\Hw4\\Q3\\nursery\\nursery.data");
		String mapping[] = {
				   "Parent's Occupation: usual", "Parent's Occupation: pretentious", "Parent's Occupation: great_pret",
				   "Child's Nursery: proper", "Child's Nursery: less_proper", "Child's Nursery: improper", "Child's Nursery: critical", "Child's Nursery: very_crit",
				   "Family Form: complete", "Family Form: completed", "Family Form: incomplete", "Family Form: foster", 
				   "Number of children: 1", "Number of children: 2", "Number of children: 3", "Number of children: more than 3", 
				   "Housing conditions: convenient", "Housing conditions: less_conv", "Housing conditions: critical", 
				   "Financial Standing of family: convenient", "Financial Standing of family: inconvenient", 
				   "Social Conditions of family: non-prob", "Social Conditions of family: slightly_prob", "Social Conditions of family: problematic", 
				   "Health Conditions of family: recommended", "Health Conditions of family: priority", "Health Conditions of family: not recommended", 
				   "Class: recommend", "Class: priority", "Class: not_recom", "Class: very_recom", "Class: spec_prior"};
		
		
		/*###########################################Car Evaluation Dataset##########################################*/
		/*File file = new File("D:\\Documents\\CS\\IUB Courses\\2nd Sem\\Data Mining\\Homework\\Hw4\\Q3\\carEvaluation\\car_decision.csv");
		String mapping[] = {"v-high buying price", "high buying price", "medium  buying price", "low  buying price", 
				"v-high maintenance cost", "high maintenance cost", "medium maintenance cost", "low maintenance cost", 
				"2 doors car", "3 doors car", "4 doors car", "more than 5 doors car", 
				"2 person car", "4 person car", "more than 4 person car", 
				"small luggage boot", "medium luggage boot", "big luggage boot", 
				"low safety", "medium safety", "high safety", 
				"class: unacceptable", "class: acceptable", "class: v-good", "class: good"};
		
		*/
		/*###########################################Contraceptive Method Choice Dataset##########################################*/
		/*
		File file = new File("D:\\Documents\\CS\\IUB Courses\\2nd Sem\\Data Mining\\Homework\\Hw4\\Q3\\contraceptiveMethod\\cmc(afterBinning).csv");
		String mapping[] = {"Wife's Age in 21-25", "Wife's Age in 41-45", "Wife's Age in 36-40", "Wife's Age in 16-20", "Wife's Age in 26-30", "Wife's Age in 46-50", "Wife's Age in 31-35",
		   "Wife's Education: 2", "Wife's Education: 1=low", "Wife's Education: 3", "Wife's Education: 4=high",
		   "Husband's Education: 3", "Husband's Education: 2", "Husband's Education: 4=high", "Husband's Education: 1=low",
		   "Number of children ever born: 3", "Number of children ever born: 10", "Number of children ever born: 7", "Number of children ever born: 9", "Number of children ever born: 8", "Number of children ever born: 0", "Number of children ever born: 6", "Number of children ever born: 1", "Number of children ever born: 2", "Number of children ever born: 4", "Number of children ever born: 5", "Number of children ever born: 12", "Number of children ever born: 11", "Number of children ever born: 13", "Number of children ever born: 14",  
		   "Wife's Religion: Islam", "Wife's Religion: Non-Islam", 
		   "Wife Not working now", "Wife working now",
		   "Husband's occupation: 2", "Husband's occupation: 3", "Husband's occupation: 1", "Husband's occupation: 4", 
		   "Standard of living index: 3", "Standard of living index: 4=high", "Standard of living index: 2", "Standard of living index: 1=low", 
		   "Media Exposure: Good", "Media Exposure: Not Good", 
		   "Contraceptive method used: 1=No-use", "Contraceptive method used: 2=Long-term", "Contraceptive method used: 3=Short Term"};
		
		*/
		
		
		schema = new ArrayList<HashMap<String, Integer>>();
		Scanner sc = new Scanner(System.in);
		HashMap<Integer, String> mapBackToAttributeName = new HashMap<Integer, String>(); 
		MakeSparseMatrix obj = new MakeSparseMatrix();
		boolean[][] sparseMatrix = obj.makeMatrix(schema, file, mapBackToAttributeName);
		//obj.displayMatrix(sparseMatrix);
		//System.out.println(mapBackToAttributeName);
		//System.out.println(schema);
		System.out.println("Enter Support Threshold Value (double): ");
		double supportThresh = sc.nextDouble();//= 0.05; //(fewer maximal and closed itemsets) //Set Support Thresh here
		System.out.println("Please wait.");
		
		ArrayList<Itemset> itemsetFork1Xk1 = new ArrayList<Itemset>();// Contains all k-Itemsets
		ArrayList<Itemset> itemsetFork1X1 = new ArrayList<Itemset>();// Contains all k-Itemsets
		Itemset itemset1 = new Itemset(); // All 1 Itemsets stored in this object
		
		//---------------------------------1-Itemset generation---------------------------------
		int totalConsideredIn1Item = generateFrequent1Itemsets(itemset1, supportThresh, sparseMatrix);
		itemsetFork1X1.add(itemset1);
		itemsetFork1Xk1.add(itemset1);
		
		//Candidate generation using k-1X1 Method-----------------------------------------------
		kMinus1X1(itemsetFork1X1, itemset1, supportThresh, sparseMatrix, totalConsideredIn1Item); //k1X1 method
		//--------------------------------------------------------------------------------------
		
		//Candidate generation using k-1X1 Method-----------------------------------------------
		kMinus1XkMinus1(itemsetFork1Xk1, itemset1, supportThresh, sparseMatrix, totalConsideredIn1Item); // k1Xk1 method

		//System.out.println("Maximal Frequent ItemSet Count: " + maximalFrequentItemSetCount(itemsetFork1Xk1));
		//System.out.println("Closed Frequent ItemSet Count: " + closedFrequentItemSetCount(itemsetFork1Xk1));
		//--------------------------------------------------------------------------------------
		
		//Maximal Frequent Itemset count and Frequent Closed Itemset Count----------------------
		System.out.println("Maximal Frequent ItemSet Count: " + maximalFrequentItemSetCount(itemsetFork1Xk1));
		System.out.println("Closed Frequent ItemSet Count: " + closedFrequentItemSetCount(itemsetFork1Xk1));
		//--------------------------------------------------------------------------------------
		
		//RulesGeneratedWithoutConfidence-------------------------------------------------------
		System.out.println("\n------------------------------------------------------------------");
		System.out.println("Total number of rules generated by bruteforce method: " + calculateRulesByBruteForce(itemsetFork1Xk1));
		System.out.println("\n------------------------------------------------------------------");
		
		int displayOnlyTop10 = 0;
		//------------------------------------------Rule Generation using Confidence-------------------------------
		System.out.println("Enter Confidence Threshold Value (double): ");
		double confidenceThresh = sc.nextDouble();//1;
		System.out.println("Please wait.");
		ArrayList<Rules> rulesGeneratedUsingConfidence = new ArrayList<Rules>();
		//rules = generateRules(itemsetFork1Xk1, confidenceThresh, ruleLeftPart, ruleRightPart, rules, confidence); 
		generateRulesUsingConfidence(itemsetFork1Xk1, confidenceThresh, rulesGeneratedUsingConfidence);
		
		System.out.println("Total number of rules generated after confidence-based pruning: " + rulesGeneratedUsingConfidence.size());
		if(rulesGeneratedUsingConfidence.size() != 0)
			System.out.println("Top 10 are:");
		
		Collections.sort(rulesGeneratedUsingConfidence, new RulesConfidenceComparator());
		for(int i=0; i < rulesGeneratedUsingConfidence.size() && displayOnlyTop10 < 10; displayOnlyTop10++, ++i)
		{
			System.out.println("\nRule " + (i+1) + ": \n" + mapping(rulesGeneratedUsingConfidence.get(i).ruleLeftPart, mapping) + " -> " + mapping(rulesGeneratedUsingConfidence.get(i).ruleRightPart, mapping));
			//System.out.println(" Confidence: " + rulesGeneratedUsingConfidence.get(i).confidence);
		}
		System.out.println("\n------------------------------------------------------------------");
		//---------------------------------------------------------------------------------------------------------
		
		displayOnlyTop10 = 0;
		//------------------------------------------Rule Generation using Lift--------------------------------------
		ArrayList<Rules> rulesGeneratedUsingLift = new ArrayList<Rules>();
		generateRulesUsingLift(itemsetFork1Xk1, rulesGeneratedUsingLift);
		//System.out.println("\nTotal number of rules generated using Lift: " + rulesGeneratedUsingLift.size());
		
		if(rulesGeneratedUsingLift.size() != 0)
			System.out.println("Top 10 rules generated using lift are:");
		
		Collections.sort(rulesGeneratedUsingLift, new RulesLiftComparator());
		for(int i=0; i < rulesGeneratedUsingLift.size() && displayOnlyTop10 < 10; displayOnlyTop10++, ++i)
		{
			System.out.println("\nRule " + (i+1) + ": \n" + mapping(rulesGeneratedUsingLift.get(i).ruleLeftPart, mapping) + " -> " + mapping(rulesGeneratedUsingLift.get(i).ruleRightPart, mapping));
			//System.out.println(" Lift: " + rulesGeneratedUsingLift.get(i).lift);
		}
		//-----------------------------------------------------------------------------------------------------------
		sc.close();
		//System.out.println(schema);
	}
}
