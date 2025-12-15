package day10;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day10
{
	static ArrayList<ArrayList<String>> indicators = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<ArrayList<Integer>>> buttons = new ArrayList<ArrayList<ArrayList<Integer>>>();
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/main/java/day10/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/main/java/day10/input_test.txt")))
		{
			while(input.hasNextLine())
			{
			    String[] line = input.nextLine().split(" ");
			    ArrayList<ArrayList<Integer>> buttonSet = new ArrayList<>();
			    
			    for(int i = 0; i < line.length; i++)
			    {
			        if(line[i].startsWith("["))
			        {
			            ArrayList<String> indicator = new ArrayList<>();
			            for(int j = 0; j < line[i].length() - 2; j++)
			            {
			                indicator.add(Character.toString(line[i].charAt(j + 1)));
			            }
			            indicators.add(indicator);
			        }
			        
			        if(line[i].startsWith("("))
			        {
			            ArrayList<Integer> button = new ArrayList<>();
			            String content = line[i].substring(1, line[i].length() - 1);
			            String[] numbers = content.split(",");
			            
			            for(String num : numbers)
			            {
			                button.add(Integer.parseInt(num));
			            }
			            buttonSet.add(button);
			        }
			    }
			    
			    buttons.add(buttonSet);
			}
			
			System.out.println("The answer is " + countPresses());
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static int countPresses()
	{
	    int totalPresses = 0;
	    
	    for(int i = 0; i < indicators.size(); i++)
	    {
	        boolean[] target = new boolean[indicators.get(i).size()];
	        
	        for(int j = 0; j < indicators.get(i).size(); j++)
	        {
	            target[j] = indicators.get(i).get(j).equals("#");
	        }
	        
	        ArrayList<ArrayList<Integer>> currentButtonSet = buttons.get(i);
	        
	        totalPresses += findMinPresses(target, currentButtonSet);
	    }
	    
	    return totalPresses;
	}

	private static int findMinPresses(boolean[] target, ArrayList<ArrayList<Integer>> buttonSet)
	{
	    int n = buttonSet.size();
	    int minPresses = Integer.MAX_VALUE;
	    
	    for(int mask = 1; mask < (1 << n); mask++)
	    {
	        boolean[] state = new boolean[target.length];
	        int pressCount = 0;
	        
	        for(int i = 0; i < n; i++)
	        {
	            if((mask & (1 << i)) != 0)
	            {
	                pressCount++;
	                for(int pos : buttonSet.get(i))
	                {
	                    state[pos] = !state[pos];
	                }
	            }
	        }
	        
	        if(matches(target, state))
	        {
	            minPresses = Math.min(minPresses, pressCount);
	        }
	    }
	    
	    return minPresses == Integer.MAX_VALUE ? 0 : minPresses;
	}
	
	private static boolean matches(boolean[] target, boolean[] test)
	{
	    if(target.length != test.length) return false;
	    
	    for(int i = 0; i < target.length; i++)
	    {
	        if(target[i] != test[i]) return false;
	    }
	    return true;
	}
}
