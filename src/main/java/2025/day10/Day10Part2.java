package day10;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;


public class Day10Part2
{
	static ArrayList<ArrayList<Integer>> joltageReqs = new ArrayList<ArrayList<Integer>>();
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
			    	if(line[i].startsWith("{"))
			    	{
			    	    ArrayList<Integer> joltageReq = new ArrayList<>();
			    	    String content = line[i].substring(1, line[i].length() - 1);
			    	    String[] numbers = content.split(",");
			    	    
			    	    for(String num : numbers)
			    	    {
			    	        joltageReq.add(Integer.parseInt(num));
			    	    }
			    	    
			    	    joltageReqs.add(joltageReq);
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
	
	public static long countPresses()
	{
	    long totalPresses = 0;
	    
	    for(int i = 0; i < joltageReqs.size(); i++)
	    {
	        int[] target = new int[joltageReqs.get(i).size()];
	        for(int j = 0; j < joltageReqs.get(i).size(); j++)
	        {
	            target[j] = joltageReqs.get(i).get(j);
	        }
	        
	        ArrayList<ArrayList<Integer>> currentButtonSet = buttons.get(i);
	        int result = findMinPresses(target, currentButtonSet);
	        
	        if(result == -1)
	        {
	            System.out.println("ERROR: No solution found!");
	            return -1;
	        }
	        
	        totalPresses += result;
	    }
	    
	    return totalPresses;
	}

	private static int findMinPresses(int[] target, ArrayList<ArrayList<Integer>> buttonSet)
	{
	    Loader.loadNativeLibraries();
	    
	    // Create the solver using SCIP backend
	    MPSolver solver = MPSolver.createSolver("SCIP");
	    if(solver == null)
	    {
	        System.out.println("Could not create solver");
	        return -1;
	    }
	    
	    int numButtons = buttonSet.size();
	    int numPositions = target.length;
	    
	    // Create variables: how many times to press each button
	    MPVariable[] buttonPresses = new MPVariable[numButtons];
	    for(int i = 0; i < numButtons; i++)
	    {
	        buttonPresses[i] = solver.makeIntVar(0, 1000, "button_" + i);
	    }
	    
	    // Add constraints: for each position, sum of button effects must equal target
	    for(int pos = 0; pos < numPositions; pos++)
	    {
	        MPConstraint constraint = solver.makeConstraint(target[pos], target[pos], "pos_" + pos);
	        
	        for(int btn = 0; btn < numButtons; btn++)
	        {
	            if(buttonSet.get(btn).contains(pos))
	            {
	                constraint.setCoefficient(buttonPresses[btn], 1);
	            }
	        }
	    }
	    
	    // Objective: minimize total button presses
	    MPObjective objective = solver.objective();
	    for(int i = 0; i < numButtons; i++)
	    {
	        objective.setCoefficient(buttonPresses[i], 1);
	    }
	    objective.setMinimization();
	    
	    // Solve
	    MPSolver.ResultStatus resultStatus = solver.solve();
	    
	    if(resultStatus == MPSolver.ResultStatus.OPTIMAL)
	    {
	        int totalPresses = 0;
	        for(int i = 0; i < numButtons; i++)
	        {
	            totalPresses += (int)buttonPresses[i].solutionValue();
	        }
	        return totalPresses;
	    }
	    else
	    {
	        System.out.println("No optimal solution found");
	        return -1;
	    }
	}
}
