package day07;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day7
{
	static ArrayList<ArrayList<String>> manifold = new ArrayList<ArrayList<String>>();
	static HashSet<String> visitedPositions = new HashSet<>();
	static HashSet<String> visitedSplitters = new HashSet<>();
	static long countSplits = 0;
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/day7/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/day7/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String[] line = input.nextLine().split("");
				
				ArrayList<String> row = new ArrayList<>();
			    for (int i = 0; i < line.length; i++)
			    {
			    	row.add(line[i]);
			    }
			    
			    manifold.add(row);
			}
			
			traverseManifold();
			
			System.out.println("The answer is " + countSplits);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static void traverseManifold()
	{
	    visitedPositions.clear();
		visitedSplitters.clear();
	    
	    for(int i = 0; i < manifold.size() - 1; i++)
	    {
	        for(int j = 0; j < manifold.get(i).size(); j++)
	        {
	            if(manifold.get(i).get(j).equals("S"))
	            {
	                followBeam(i + 1, j);
	            }
	        }
	    }
	}

	public static void followBeam(int row, int col)
	{
	    if(row >= manifold.size() || col < 0 || col >= manifold.get(row).size())
	    {
	        return;
	    }

	    String position = row + "," + col;
	    if(visitedPositions.contains(position))
	    {
	        return;
	    }
	    
	    visitedPositions.add(position);
	    
	    String currentCell = manifold.get(row).get(col);
	    
	    if(currentCell.equals("^"))
	    {
	        if(!visitedSplitters.contains(position))
	        {
	            visitedSplitters.add(position);
	            countSplits++;
	        }
	        
	        if(col - 1 >= 0)
	        {
	            manifold.get(row).set(col - 1, "|");
	            followBeam(row + 1, col - 1);
	        }
	        
	        if(col + 1 < manifold.get(row).size())
	        {
	            manifold.get(row).set(col + 1, "|");
	            followBeam(row + 1, col + 1);
	        }
	    }
	    else if(currentCell.equals(".") || currentCell.equals("|"))
	    {
	        manifold.get(row).set(col, "|");
	        followBeam(row + 1, col);
	    }
	    else
	    {
	        followBeam(row + 1, col);
	    }
	}
}
