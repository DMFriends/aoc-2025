package day06;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day6
{
	static ArrayList<ArrayList<Integer>> worksheet = new ArrayList<ArrayList<Integer>>();
	static ArrayList<String> operators = new ArrayList<String>();
	static long grandTotal = 0;
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/main/java/day06/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/main/java/day06/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String[] line = input.nextLine().trim().split("\\s+");
				
				ArrayList<Integer> row = new ArrayList<>();
			    for (int i = 0; i < line.length; i++)
			    {
			    	if(line[i].equals("+") || line[i].equals("*"))
			    	{
			    		if (operators.size() <= i)
			    		{
			                operators.add(line[i]);
			            }
			    		else
			    		{
			                operators.set(i, line[i]);
			            }
			    	}
			    	else
			    	{
			    		row.add(Integer.parseInt(line[i]));
			    	}
			    }
			    
			    if(!row.isEmpty())
			    {
			    	worksheet.add(row);
			    }
			}
			
			grandTotal = calculateTotal();
			
			System.out.println("The answer is " + grandTotal);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static long calculateTotal()
	{
	    int rows = worksheet.size();
	    int cols = worksheet.get(0).size();
	    long total = 0;

	    for (int j = 0; j < cols; j++)
	    {
	        String op = operators.get(j);
	        if (op.equals("+"))
	        {
	            int colSum = 0;
	            for (int i = 0; i < rows; i++)
	            {
	                colSum += worksheet.get(i).get(j);
	            }
	            total += colSum;
	        }
	        else if (op.equals("*"))
	        {
	            long colProd = 1;
	            for (int i = 0; i < rows; i++)
	            {
	                colProd *= worksheet.get(i).get(j);
	            }
	            total += colProd;
	        }
	    }
	    
	    return total;
	}
}
