package homeWork4;

import java.util.ArrayList;
import java.util.TreeSet;

public class Itemset {

	ArrayList<TreeSet<Integer>> frequentItemset;
	ArrayList<Double> support;
	ArrayList<Double> supportCount;
	double rulesViaBruteForceCount;
	
	public int getFrequentItemSetCount(Itemset itemset2)
	{
		int count = 0;
		ArrayList<TreeSet<Integer>> frequentItemset, frequentItemsetSuperSet;
		frequentItemset = this.frequentItemset;
		frequentItemsetSuperSet = itemset2.frequentItemset;
		boolean flag;
		for(TreeSet<Integer> item: frequentItemset)
		{
			flag = false;
			for(TreeSet<Integer> itemSuper: frequentItemsetSuperSet)
			{
				if(itemSuper.containsAll(item))
				{
					flag = true;
					break;
				}
			}
			if(flag == false)
			{
				++count;
			}
		}
		
		return count;
	}
	
	public int getClosedItemSetCount(Itemset itemset2)
	{
		int count = 0;
		ArrayList<TreeSet<Integer>> frequentItemset, frequentItemsetSuperSet;
		frequentItemset = this.frequentItemset;
		frequentItemsetSuperSet = itemset2.frequentItemset;
		boolean flag;
		int iteratorX = 0, iteratorY;
		double supportofItem, supportofItemSuper;
		for(TreeSet<Integer> item: frequentItemset)
		{
			flag = false;
			supportofItem = this.supportCount.get(iteratorX);
			iteratorY = 0;
			for(TreeSet<Integer> itemSuper: frequentItemsetSuperSet)
			{
				if(itemSuper.containsAll(item))
				{
					supportofItemSuper = itemset2.supportCount.get(iteratorY);
					if(supportofItem == supportofItemSuper)
					{
						flag = true;
						break;
					}
				}
				++iteratorY;
			}
			if(flag == false)
				++count;
			++iteratorX;
		}
		return count;
	}
	
}
