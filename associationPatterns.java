//My code solves for finding association pattern for n order and find out if they
//are statistically significant with given threshold.

//My code can work with any n order.

//Before my code was not checking the states of variables, I fixed that. Now code asks how many different states are in each variable 
// then asks which states are they and then takes values for each row.

//The limitation of my code is that I'm taking user input for every entry of data set manually, therefore it'll consume a lot of time 
//to enter if the data set is huge. 

//One more limitation is that I'm not using the information that if a pattern is not statistically significant than if that pattern is present 
//for higher order will also not be statistically significant.

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class associationPatterns {
	public int rows;
	public int columns;
	public int n;
	public double threshold;
	int[][] y;
	int[][] nOrderPatterns;
	ArrayList<ArrayList<Integer>> uniquePatterns;
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public void createDataSet(int rows, int columns) {
		y = new int[columns][rows];
		Scanner a = new Scanner( System.in );

		for(int i =0; i<getColumns(); i++ )
		{
			
			if(i== getColumns()-1) {
				System.out.println("Enter number of states for G and press enter ");
				int states = a.nextInt();
				ArrayList<Integer> statesValue = new ArrayList<Integer>(states);
				System.out.println("Enter each state one at a time and press enter ");
				while(states>0) {
					statesValue.add(a.nextInt());
					states--;
				}
				
				System.out.println("Enter values for g column, Press enter after each entry");
				for(int j =0; j<getRows(); j++ )
				{
					int value =a.nextInt();
					y[i][j]=value;
				}
			}
			else 
			{
				System.out.println("Enter number of states for Y" +(i+1)+ " and press enter ");
				int states = a.nextInt();
				ArrayList<Integer> statesValue = new ArrayList<Integer>(states);
				System.out.println("Enter each state one at a time and press enter ");
				while(states>0) {
					statesValue.add(a.nextInt());
					states--;
				}
				
				System.out.println("Enter values for Y" +(i+1)+ ", Press enter after each entry");
				int j=0;
				while(j<getRows()) 
				{
					int value =a.nextInt();
					if(statesValue.contains(value)) {
						y[i][j] = value;
						j++;
					}
					else System.out.println("The value you entered doesn't belong to Y" +(i+1)+ " Please enter another value.");
					
					
				}
			}
		}
		

		System.out.println("The data set you entered is:");
		for(int j=0; j<columns; j++ ) {
			if(j==columns-1) {
				System.out.println("G");
			}
			else {	
			System.out.print("Y"+(j+1));
			System.out.print("\t");
			}
		}
		
		for(int i =0; i<rows; i++) {
			for(int j=0; j<columns; j++ ) {
				System.out.print(y[j][i]);
				System.out.print("\t");
			}
			System.out.println();
	}
		
	}
	
	public boolean findDuplicates(ArrayList<ArrayList<Integer>> uniquePatterns , int checkDuplicate[]) {
		for(int i=0; i<uniquePatterns.size(); i++) {
			for(int j=0; j<n; j++ ) {		
				 if(!(uniquePatterns.get(i).get(j)==checkDuplicate[j])) 
					 break;
	
				 if((uniquePatterns.get(i).get(j)==checkDuplicate[j])&&(j==n-1)) 
					 return true;		
			}
		}
		return false;
	
	}
	
	public int frequencyCounter(int [][] nOrderPatterns , ArrayList<Integer> arrayList) {
		int frequency =0;
		
		for(int i =0; i<nOrderPatterns.length; i++) {
			for(int j=0; j<n; j++ ) {				
				if(nOrderPatterns[i][j]!=arrayList.get(j))
					break;
				
				 if(nOrderPatterns[i][j]==arrayList.get(j)&&(j==n-1)) {
					frequency++;
				 }
				 }
			}
		
		return frequency;
	
	}

	public int valueCounterforEntropy(int[][] y, int i) {
		int count=0;
		ArrayList<Integer> uniqueValues = new ArrayList<Integer>();
		for(int j=0; j< getRows(); j++) {
			if(!uniqueValues.contains(y[i][j])) {
				uniqueValues.add(y[i][j]);
				count++;
			}
		}
		return count;	
	}
	
	public void nOrderPatterns() {
		Scanner a = new Scanner( System.in );
		int[] toStoreNColumns = new int[getN()];
		System.out.println("Enter the "+ n +" variables whose "+ n +" order association pattern you like to find that are statistically significant."
				+ "For Y columns enter the column number only and for G enter the last column number. Enter number then hit enter and then second number then enter .");
		int val;
		int b=0;
		while(b<n) {
			val= a.nextInt();
			toStoreNColumns[b]=(val-1);
			b++;
			
		}		
		
		
		nOrderPatterns = new int[rows][getN()];
		uniquePatterns = new ArrayList<ArrayList<Integer> >();
	
		for(int i =0; i<rows; i++)
		{
			for(int j=0; j<n; j++ )
			nOrderPatterns[i][j]= y[toStoreNColumns[j]][i];
		}
				
		for(int i =0; i<getRows(); i++) {
			if(i==0) {
			uniquePatterns.add(new ArrayList<Integer>());
			for(int j=0; j<n; j++ ) {
				uniquePatterns.get(uniquePatterns.size()-1).add(j,nOrderPatterns[i][j]);
			}
			}
			if(i>0) {
			if(!findDuplicates(uniquePatterns,nOrderPatterns[i]))
			{uniquePatterns.add(new ArrayList<Integer>());
				for(int j=0; j<n; j++ ) {
					uniquePatterns.get(uniquePatterns.size()-1).add(j,nOrderPatterns[i][j]);
				}
			}
			}
		}
		//System.out.println(Arrays.toString(uniquePatterns.toArray()));
		for(int i =0; i < uniquePatterns.size(); i++ ) {
			double frequency = frequencyCounter(nOrderPatterns,uniquePatterns.get(i));
			double thresholdTest = (frequency/nOrderPatterns.length);
			
		if(thresholdTest>getThreshold()) {
			System.out.println("The frequency of "+ uniquePatterns.get(i)+" is " + frequency +" and it passed threshold test with value "+ thresholdTest);
			System.out.println("Second Test for "+uniquePatterns.get(i));
			mutualInfoMeasureTest(uniquePatterns.get(i), toStoreNColumns, frequency);
			

		}
		else {
			System.out.println("The frequency of "+ uniquePatterns.get(i)+" is " + frequency +" and it did not passed threshold test with value "+ thresholdTest);
			System.out.println("=====================================");
		}
		}
	}	
	
	public boolean mutualInfoMeasureTest(ArrayList<Integer> patternToCheck, int[] columnNumber, double frequencyOfCurrentPattern) {
		double N = getRows();
		double maxEntropyOfSystem=1;
		double sumOfIndividualEntropy=0;
		for(int i =0; i< columnNumber.length; i++ )
			maxEntropyOfSystem*=valueCounterforEntropy(y,columnNumber[i]);
		maxEntropyOfSystem= Math.log(maxEntropyOfSystem)/Math.log(2);
		System.out.println("E'= "+ maxEntropyOfSystem);
		
		for(int i =0; i<uniquePatterns.size(); i++ ) {
			double frequency=frequencyCounter(nOrderPatterns,uniquePatterns.get(i));
			frequency= (frequency/N)*Math.log(N/frequency)/Math.log(2);
			sumOfIndividualEntropy+=frequency; 
		}
		System.out.println("E^= "+ sumOfIndividualEntropy);
		
		double productOfIndividualProbabilityCount=1;
		for(int i = 0; i<n; i++ ) {
			double probCount=0;
			for(int j =0; j<getRows(); j++) {
				if(patternToCheck.get(i)==y[columnNumber[i]][j]){
				probCount++;
				}
			}

			probCount= probCount/N;
			productOfIndividualProbabilityCount*=probCount;
		}
		double LHS= ((frequencyOfCurrentPattern/N)/productOfIndividualProbabilityCount);
		LHS = Math.log(LHS)/Math.log(2);
		System.out.println("LHS "+ LHS);
		
		double O_i = N* (frequencyOfCurrentPattern/N);
		double e_i= N*productOfIndividualProbabilityCount;
		
		double chi_square = Math.pow((O_i-e_i),2);
		chi_square= chi_square/e_i;
		
		System.out.println("chi-square "+ chi_square);
		chi_square=chi_square/(2*N);
		System.out.println("chi-square/2N "+chi_square);
		
		double valueOfPower= sumOfIndividualEntropy/maxEntropyOfSystem;
		valueOfPower= Math.pow(valueOfPower, n/2);
		
		double RHS= (N/frequencyOfCurrentPattern)*Math.pow(chi_square,valueOfPower);
		
		System.out.println("value of LHS " +LHS + " value of RHS " +RHS);
		if (LHS>RHS) {
			System.out.println(patternToCheck.toString() + " is statistically significant.");
			System.out.println("=====================================");
			return true;

		}
		System.out.println(patternToCheck.toString() + " does not pass the second test and is not statistically significant with given threshold.");
		System.out.println("=====================================");
		return false;
	}
	
	public static void main(String[] args) {
		Scanner a = new Scanner( System.in );
		associationPatterns aP = new associationPatterns();

		int rows;
		System.out.println("How many rows of data you have?");
		aP.setRows(rows=a.nextInt());
		
		int columns;
		System.out.println("How many columns of data you have (including the final g column)? Example Y1 Y2 Y3 G In that case type 4");
		aP.setColumns(columns=a.nextInt());
		
		aP.createDataSet(aP.getRows(),aP.getColumns());
		
		
		int n;
		System.out.println("Enter the value for n order association pattern you want to find.");
		aP.setN(n= a.nextInt());
		
		double threshold;
		System.out.println("Enter the threshold value: ");
		aP.setThreshold(threshold=a.nextDouble());
		
		aP.nOrderPatterns();
	
	    }
}
