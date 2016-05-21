package homeWork4;

import java.util.Comparator;
import java.util.TreeSet;

public class Rules 
{
	TreeSet<String> rules;
	TreeSet<Integer> ruleLeftPart, ruleRightPart;
	double confidence;
	double lift;
	
	Rules()
	{
		ruleLeftPart = new TreeSet<Integer>();
		ruleRightPart = new TreeSet<Integer>();
	}
	
}

//Comparator for sorting (ascending) according to Confidence values
class RulesConfidenceComparator implements Comparator<Rules>
{
	@Override
	public int compare(Rules arg0, Rules arg1) {
		// TODO Auto-generated method stub
		double diff = arg0.confidence - arg1.confidence;
		if(diff < 0)
			return 1;
		else if(diff > 0)
			return -1;
		return 0;
	}
	
}

//Comparator for sorting (ascending) according to Lift values
class RulesLiftComparator implements Comparator<Rules>
{
	@Override
	public int compare(Rules arg0, Rules arg1) {
		// TODO Auto-generated method stub
		double diff = arg0.lift - arg1.lift;
		if(diff < 0)
			return 1;
		else if(diff > 0)
			return -1;
		return 0;
	}
	
}
