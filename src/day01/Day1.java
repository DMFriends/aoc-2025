package day01;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Day1
{
	static int countZeros = 0;
	static int currentNumber = 50;
	
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/day1/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/day1/input_test.txt")))
		{
			while(input.hasNextLine())
			{
				String combo = input.nextLine();
				int index = Integer.parseInt(combo.substring(1, combo.length()));
				
				if(combo.substring(0, 1).equals("L"))
				{
					updateComboLeft(combo, index);
				}
				else if(combo.substring(0, 1).equals("R"))
				{
					updateComboRight(combo, index);
				}
			}
			
			System.out.println("The password is " + countZeros);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
	
	public static void updateComboLeft(String combo, int index)
	{
		if(currentNumber < index && index - currentNumber <= 100)
		{
			currentNumber -= index - 100;
		}
		else if(currentNumber < index && index - currentNumber >= 100)
		{
			while(index > 100)
			{
				index -= 100;
			}
			
			if(currentNumber > index)
			{
				currentNumber -= index;
			}
			else if(currentNumber < index)
			{
				currentNumber -= index - 100;
			}
			else if(currentNumber == index)
			{
				currentNumber = 0;
			}
		}
		else
		{
			currentNumber -= index;
		}
		
		if(currentNumber == 0)
		{
			countZeros++;
		}
		
		System.out.format("%s - %d%n", combo, currentNumber);
	}
	
	public static void updateComboRight(String combo, int index)
	{
		if(currentNumber + index >= 100 && index <= 100)
		{
			currentNumber += index - 100;
		}
		else if(currentNumber + index >= 100 && index >= 100)
		{
			while(index >= 100)
			{
				index -= 100;
			}

			if(currentNumber + index <= 100)
			{
				currentNumber += index;
			}
			else
			{
				currentNumber += index - 100;
			}
		}
		else if(currentNumber + index <= 100)
		{
			currentNumber += index;
		}
		else if(currentNumber == index)
		{
			currentNumber = 0;
		}
		
		if(currentNumber == 100)
		{
			currentNumber = 0;
			countZeros++;
		}
		else if(currentNumber == 0)
		{
			countZeros++;
		}
		
		System.out.format("%s - %d%n", combo, currentNumber);
	}
}
