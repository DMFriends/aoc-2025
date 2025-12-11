package day11;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Day11
{
	static ArrayList<ArrayList<String>> devices = new ArrayList<ArrayList<String>>();
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/main/java/day11/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/main/java/day11/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String[] line = input.nextLine().split(" ");
				
				ArrayList<String> row = new ArrayList<String>();
			    for (int i = 0; i < line.length; i++)
			    {
			    	row.add(line[i]);
			    }
			    
			    devices.add(row);
			}
			
			System.out.println("The answer is " + countPaths());
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static int countPaths()
	{
	    HashMap<String, ArrayList<String>> graph = new HashMap<>();
	    
	    for (int i = 0; i < devices.size(); i++)
	    {
	        String device = devices.get(i).get(0).replace(":", "");
	        ArrayList<String> outputs = new ArrayList<>();
	        
	        for (int j = 1; j < devices.get(i).size(); j++)
	        {
	            outputs.add(devices.get(i).get(j));
	        }
	        
	        graph.put(device, outputs);
	    }
	    
	    HashSet<String> visited = new HashSet<>();
	    ArrayList<String> currentPath = new ArrayList<>();
	    int[] pathCount = {0};
	    
	    dfs(graph, "you", "out", visited, currentPath, pathCount);
	    
	    return pathCount[0];
	}

	private static void dfs(HashMap<String, ArrayList<String>> graph, 
	                       String current, String destination,
	                       HashSet<String> visited, ArrayList<String> currentPath,
	                       int[] pathCount)
	{
	    currentPath.add(current);
	    visited.add(current);
	    
	    if (current.equals(destination))
	    {
	        pathCount[0]++;
	    }
	    else if (graph.containsKey(current))
	    {
	        for (String neighbor : graph.get(current))
	        {
	            if (!visited.contains(neighbor))
	            {
	                dfs(graph, neighbor, destination, visited, currentPath, pathCount);
	            }
	        }
	    }
	    
	    visited.remove(current);
	    currentPath.remove(currentPath.size() - 1);
	}
}
