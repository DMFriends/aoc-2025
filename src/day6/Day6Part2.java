package day6;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day6Part2
{
	static ArrayList<ArrayList<Integer>> worksheet = new ArrayList<ArrayList<Integer>>();
	static ArrayList<String> operators = new ArrayList<String>();
	static ArrayList<String> rawLines = new ArrayList<>();
	static long grandTotal = 0;
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/day6/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/day6/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String raw = input.nextLine();
				rawLines.add(raw);
				
				String[] line = raw.trim().split("\\s+");
				
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
	    int lineCount = rawLines.size();
	    int opRowIndex = lineCount - 1;

	    int width = 0;
	    for (String s : rawLines) {
	        if (s.length() > width) width = s.length();
	    }
	    char[][] grid = new char[lineCount][width];
	    for (int r = 0; r < lineCount; r++)
	    {
	        String s = rawLines.get(r);
	        for (int c = 0; c < width; c++)
	        {
	            grid[r][c] = (c < s.length()) ? s.charAt(c) : ' ';
	        }
	    }

	    long grandTotal = 0L;
	    ArrayList<Long> currentNumbers = new ArrayList<>();
	    char currentOp = '+';
	    boolean inGroup = false;

	    for (int col = width - 1; col >= -1; col--)
	    {
	        boolean separator = (col < 0);
	        if (!separator)
	        {
	            separator = true;
	            for (int row = 0; row < opRowIndex; row++)
	            {
	                if (grid[row][col] != ' ') {
	                    separator = false;
	                    break;
	                }
	            }
	        }

	        if (separator)
	        {
	            if (inGroup)
	            {
	                long acc = (currentOp == '+') ? 0L : 1L;
	                for (long n : currentNumbers)
	                {
	                    acc = (currentOp == '+') ? acc + n : acc * n;
	                }
	                grandTotal += acc;

	                currentNumbers.clear();
	                inGroup = false;
	            }
	            continue;
	        }

	        // build a number from this digit column (top to bottom)
	        StringBuilder sb = new StringBuilder();
	        for (int row = 0; row < opRowIndex; row++)
	        {
	            char ch = grid[row][col];
	            if (Character.isDigit(ch)) sb.append(ch);
	        }
	        
	        long value = Long.parseLong(sb.toString());
	        currentNumbers.add(value);
	        inGroup = true;

	        // operator for this column comes from bottom row
	        char opChar = grid[opRowIndex][col];
	        if (opChar == '+' || opChar == '*')
	        {
	            currentOp = opChar;
	        }
	    }

	    return grandTotal;
	}
}
