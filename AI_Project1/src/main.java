import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.math3.distribution.NormalDistribution;

public class main {
	
		private static NormalDistribution femaleHeight;
		private static NormalDistribution maleHeight;
		private static NormalDistribution femaleWeight;
		private static NormalDistribution maleWeight;
		public static int iteration = 25; 
		public static int numberPatterns = 2; 
		public static int numberOfAugInputs = 3; 
		public static double learningConstant = 0.5;
		public static double[] weights = {1,1,70};
		public static int[][] pattern = {{1,2,1},{2,1,1}};
		public static int[] dOutput = {-1,1};
		public static int[] output = new int[numberPatterns]; 
		public static ArrayList<Human> humans = new ArrayList<Human>();

		public static int[] dOutputHuman;
		public static int[] outputHuman; 
		
		public static void main(String args[]){
			
			ArrayList<Human> men = new ArrayList<Human>();
			ArrayList<Human> women = new ArrayList<Human>();
				
			femaleHeight = new NormalDistribution(50, 2);
			maleHeight = new NormalDistribution(80, 2);
			
			femaleWeight = new NormalDistribution(150, 3);
			maleWeight = new NormalDistribution(190, 4);
			
			
			System.out.println("Deviation for male" + maleHeight.getStandardDeviation());
			
			for(int i = 0; i < 10; i++){
				men.add(new Human((int) maleHeight.sample(), (int) maleWeight.sample(), "male"));
				women.add(new Human((int) femaleHeight.sample(), (int) femaleWeight.sample(), "female"));	
			}	
			System.out.println("MEN:");
			for(int i = 0; i < men.size(); i++){
				System.out.println(men.get(i).toString());
			}
			System.out.println("WOMEN:");
			for(int i = 0; i < women.size(); i++){
				System.out.println(women.get(i).toString());
		       
				
			}
			//create desired and actual output for the two arrays of output.
			dOutputHuman = new int[men.size()+women.size()];
			outputHuman = new int[men.size()+women.size()];
			
			//get both arrays into one.. 
			createInputArray(men, women);
			
			
			System.out.println("Size of men: " + men.size() + " with item at index 0: " + men.get(0).toString());
			System.out.println("Size of women: " + women.size() + " with item at index 0: " + women.get(0).toString());
			//System.out.println("Size of humans: " + humans.size() + " with item at index 0: " + humans.get(0).toString() + " and women " + humans.get(10).toString());
			
			
			
			decideOnArrayList();
			
			
			
			
			//neuron();
		}
		
		public static void createInputArray(ArrayList<Human> men, ArrayList<Human> women){
			for(int i =0; i < (men.size()); i++){
				humans.add(men.get(i));
				dOutputHuman[i] = -1;
			}
			for(int i =0; i < (women.size()); i++){
				humans.add(women.get(i));
				dOutputHuman[i+men.size()] = 1;
			}
		}
		
		
		public static void decideOnArrayList(){
			System.out.println(" weights:" + "[" + weights[0] + "," + weights[1] + " " + weights[2] + "]");
			for(int i = 0; i < iteration; i++){
				for(int j = 0; j < humans.size(); j++){
					double net = 0.0;
					for(int k = 0; k < 3; k++){
						//System.out.println("net" + net);
						//System.out.println("humans.get(j).getArray()[k]" + humans.get(j).getArray()[k]);
						net = RoundTo2Decimals(net + weights[k]*humans.get(j).getArray()[k]);
					}
					outputHuman[j] = sign(net);
					double error = dOutputHuman[j] - outputHuman[j];
					double learn = learningConstant*error;
					printStuff(i, j, net, error, learn, weights);
					for(int l = 0; l < humans.get(j).getArray().length; l ++){
						weights[l] = RoundTo2Decimals(weights[l] + learn*humans.get(j).getArray()[l]);	
					}	
				}
			}			
		}
		

		
		public static void neuron(){
			for(int i = 0; i < iteration; i++){
				for(int j = 0; j < numberPatterns; j++){
					double net = 0.0;
					for(int k = 0; k < numberOfAugInputs; k++){
						net = RoundTo2Decimals(net + weights[k]*pattern[j][k]);
					}
					output[j] = sign(net);
					double error = dOutput[j] - output[j];
					double learn = learningConstant*error;
					printStuff(i, j, net, error, learn, weights);
					for(int l = 0; l < weights.length; l ++){
						weights[l] = RoundTo2Decimals(weights[l] + learn*pattern[j][l]);	
					}	
				}
			}			
		}
		
		public static int sign(double net){
			int y = 0;
			//System.out.println("Activation Function: " + net);
			if(net > 0) y = 1;
			if(net < 0) y = -1;
			return y;
		}
		
		static double RoundTo2Decimals(double val) {
            DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
		}
		
		public static void printStuff(int currIt, int patternNum, double net, double err, double learn, double[] weights){
			//System.out.println("Pattern:" + " ["+pattern[patternNum][0] +","+ pattern[patternNum][1]+ "," + pattern[patternNum][2] + "]");
			System.out.println("iteration=" + currIt + " p= " + patternNum + " net=" + net + " err= " + err + " lrn= "+ learn + " weights:" + "[" + weights[0] + "," + weights[1] + " " + weights[2] + "]") ;
			
		}
}
