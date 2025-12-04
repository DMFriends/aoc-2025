package _template;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Template
{
	public static void main(String[] args)
	{
		try (Scanner input = new Scanner(new File("./src/day3/input.txt")))
		//try (Scanner input = new Scanner(new File("./src/day3/input_test.txt")))
		{
			
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error! File not found!");
		}
	}
}
