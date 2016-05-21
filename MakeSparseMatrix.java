package homeWork4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MakeSparseMatrix 
{
	public int countAttributes(File f) throws FileNotFoundException
	{
		Scanner sc = new Scanner(f);
		StringBuilder str = new StringBuilder(sc.nextLine());
		String[] split = str.toString().split(",");
		
		sc.close();
		return split.length;
	}
	
	public void createSchema(File file, List<HashMap<String, Integer>> schema, int numOfAttributes) throws FileNotFoundException
	{
		Scanner sc = new Scanner(file);
		StringBuilder str;
		String split[];
		int i, co;
		ArrayList<Integer> count = new ArrayList<Integer>();
		
		while(sc.hasNextLine())
		{
			str = new StringBuilder(sc.nextLine());
			split = str.toString().split(",");
			
			for(i=0; i<split.length; ++i)
			{
				HashMap<String, Integer> temp;
				if(schema.size()!= numOfAttributes || schema.get(i) == null)
				{
					temp = new HashMap<String, Integer>();
				}
				else
				{
					temp = schema.get(i);
				}
				if(temp.get(split[i]) == null)
				{
					if(count.size() != numOfAttributes)
						co = 0;
					else
						co = count.get(i);
					temp.put(split[i], co);
					if(count.size() != numOfAttributes)
						count.add(i, 0);
					count.set(i, co + 1);
					if(schema.size() != numOfAttributes)
						schema.add(i, null);
					schema.set(i, temp);
				}
			}
		}
		
		sc.close();
	}
	
	public int countTotalUniqueValues(List<HashMap<String, Integer>> schema, HashMap<Integer, String> mapBackToAttributeName)
	{
		int count = 0, index = 0;
		for(HashMap<String, Integer> temp: schema)
		{
			count += temp.size();
			for(Map.Entry<String, Integer> ele: temp.entrySet())
				mapBackToAttributeName.put(index++, ele.getKey());
		}
		return count;
	}
	
	public int countRecords(File f) throws FileNotFoundException
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
	
	public void populateMatrix(List<HashMap<String, Integer>> schema, boolean[][] arr, File f) throws FileNotFoundException
	{
		Scanner sc = new Scanner(f);
		StringBuilder str;
		String split[];
		int i, count = 0, recordNumber = 0, bigCount;
		
		while(sc.hasNextLine())
		{
			str = new StringBuilder(sc.nextLine());
			split = str.toString().split(",");
			bigCount = 0; count = 0;
			for(i=0; i<split.length; ++i)
			{
				count += schema.get(i).get(split[i]);
				arr[recordNumber][count] = true;
				bigCount += schema.get(i).size();
				count = bigCount;
			}
			recordNumber++;
		}
		sc.close();
	}
	
	public void displayMatrix(boolean[][] arr)
	{
		for(int i=0;i<arr.length; ++i)
		{
			for(int j=0; j<arr[0].length; ++j)
			{
				System.out.print(arr[i][j] + "\t");
				
			}
			System.out.println();
		}
	}
	
	public boolean[][] makeMatrix(List<HashMap<String, Integer>> schema, File file, HashMap<Integer, String> mapBackToAttributeName) throws FileNotFoundException
	{
		int numOfAttributes = countAttributes(file);
		createSchema(file, schema, numOfAttributes);
		int totalUniqueValues = countTotalUniqueValues(schema, mapBackToAttributeName);
		int totalRecords = countRecords(file);
		boolean[][] sparseMatrix = new boolean[totalRecords][totalUniqueValues];
		populateMatrix(schema, sparseMatrix, file);
		//displayMatrix(sparseMatrix);
		return sparseMatrix;
	}
}
