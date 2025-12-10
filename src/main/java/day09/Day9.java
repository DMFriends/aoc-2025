package day09;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day9
{
	static ArrayList<ArrayList<Integer>> coords = new ArrayList<ArrayList<Integer>>();
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/day9/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/day9/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String[] line = input.nextLine().split(",");
				
				ArrayList<Integer> row = new ArrayList<Integer>();
			    for (int i = 0; i < line.length; i++)
			    {
			    	row.add(Integer.parseInt(line[i]));
			    }
			    
			    coords.add(row);
			}
			
			System.out.println("The answer is " + findLargestRect());
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static double calculateDistance(ArrayList<Integer> p1, ArrayList<Integer> p2)
	{
		double dx = p1.get(0) - p2.get(0);
		double dy = p1.get(1) - p2.get(1);
	    
	    return Math.sqrt(dx*dx + dy*dy);
	}
	
	public static long findLargestRect()
	{
	    long maxArea = 0;
	    
	    for(int i = 0; i < coords.size(); i++)
	    {
	        for(int j = i + 1; j < coords.size(); j++)
	        {
	            ArrayList<Integer> p1 = coords.get(i);
	            ArrayList<Integer> p2 = coords.get(j);
	            
	            long width = Math.abs(p1.get(0) - p2.get(0)) + 1;
	            long height = Math.abs(p1.get(1) - p2.get(1)) + 1;
	            
	            long area = width * height;
	            
	            if (area > maxArea)
	            {
	                maxArea = area;
	            }
	        }
	    }
	    
	    return maxArea;
	}
}
