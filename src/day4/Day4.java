package day4;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Day4
{
	static int numRolls = 0;
	
	static ArrayList<ArrayList<String>> puzzleDiagram = new ArrayList<ArrayList<String>>();
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/day4/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/day4/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String line = input.nextLine();
				
			    ArrayList<String> row = new ArrayList<String>();
			    for (int i = 0; i < line.length(); i++)
			    {
			        row.add(String.valueOf(line.charAt(i)));
			    }
			    
			    puzzleDiagram.add(row);
			}

			int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
			int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
			
			boolean[][] removed = new boolean[puzzleDiagram.size()][puzzleDiagram.get(0).size()];

			for (int r = 0; r < puzzleDiagram.size(); r++)
			{
			    for (int c = 0; c < puzzleDiagram.get(r).size(); c++)
			    {
			    	String current = puzzleDiagram.get(r).get(c);
			        
			        if (!current.equals("@")) continue;
			    	
			    	int numNeighbors = 0;
			    	
			        for (int k = 0; k < 8; k++)
			        {
			            int nr = r + dr[k];
			            int nc = c + dc[k];

			            if (nr < 0 || nr >= puzzleDiagram.size() || nc < 0 || nc >= puzzleDiagram.get(r).size()) continue;

			            String neighbor = puzzleDiagram.get(nr).get(nc);
			            
			            if(neighbor.equals("@"))
			    		{
			    			numNeighbors++;
			    		}
			        }
			        
			        if (numNeighbors < 4)
			        {
			            numRolls++;
			            removed[r][c] = true;
			        }
			    }
			}
			
			for(int i = 0; i < removed.length; i++)
			{
				for(int j = 0; j < removed[i].length; j++)
				{
					if(removed[i][j])
					{
			            puzzleDiagram.get(i).set(j, "x");
					}
				}
			}
			
			System.out.println("The answer is " + numRolls);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
}
