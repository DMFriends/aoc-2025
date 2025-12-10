package day07;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day7Part2
{
	static ArrayList<ArrayList<String>> manifold = new ArrayList<ArrayList<String>>();
	static HashMap<String, Long> visited = new HashMap<>();
	static long totalPaths = 0;
	
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
			
			System.out.println("The answer is " + totalPaths);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static void traverseManifold()
	{
		visited.clear();
		
	    for(int i = 0; i < manifold.size() - 1; i++)
	    {
	        for(int j = 0; j < manifold.get(i).size(); j++)
	        {
	            if(manifold.get(i).get(j).equals("S"))
	            {
	                totalPaths += countPaths(i + 1, j);
	            }
	        }
	    }
	}

	public static long countPaths(int row, int col)
	{
	    if(col < 0 || col >= manifold.get(0).size()) return 0;
	    
	    if(row >= manifold.size())
	    {
	        return 1;
	    }
	    
	    String key = row + "," + col;
	    if(visited.containsKey(key))
	    {
	        return visited.get(key);
	    }
	    
	    String currentCell = manifold.get(row).get(col);
	    long pathCount = 0;
	    
	    if(currentCell.equals("^"))
	    {
	        pathCount = countPaths(row + 1, col - 1) + countPaths(row + 1, col + 1);
	    }
	    else if(currentCell.equals(".") || currentCell.equals("|"))
	    {
	        pathCount = countPaths(row + 1, col);
	    }

	    visited.put(key, pathCount);
	    return pathCount;
	}
}
