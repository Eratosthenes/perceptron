import java.util.*;
import java.io.*;
public class Perceptron
{    
    public static void main(String args[]) throws FileNotFoundException
    {   String training_file=args[0];
        String testing_file=args[1];
        double alpha=Double.parseDouble(args[2]);
        int epochs=Integer.parseInt(args[3]);

        System.out.println("Training file: "+training_file);
        System.out.println("Testing file: "+testing_file);
        System.out.println("Alpha: "+alpha);
        System.out.println("Epochs: "+epochs);
        
        double[] w=new double[65]; //weights array 0-64, bias is first
        double[] x=new double[65]; //x array 
        double input=0; //calculate the input
        int I=0;
        int T=0;
        double num_examples=0;

        //training set
        for (int i=0;i<epochs;i++) 
        {
            Scanner fscan=new Scanner(new File(training_file)); 
            num_examples=Double.parseDouble(fscan.nextLine());

            while (fscan.hasNextLine())
            {   String line=fscan.nextLine();
                String[] numstr = line.split(",\\s*"); //get a string of the numbers
    
                //double[] x=new double[65]; //x array 
                x[0]=1; //first one is for the bias
    
                for (int j = 0; j < 64; j++) //doesn't include the target 
                {   x[j+1]=Double.parseDouble(numstr[j]); //create the x array
                }
                T=Integer.parseInt(numstr[64]); //last integer on the line
                //System.out.println(Arrays.toString(x));

                input=0;
                for (int j=0;j<x.length;j++)
                {   input=input+x[j]*w[j];
                }
                I=(input>0) ? 1 : 0; //determine whether the neuron fires

                //update the weights
                for (int j=0;j<w.length;j++)
                {   w[j]=w[j]+alpha*(T-I)*x[j];
                }
            }
            fscan.close();
        }       
        System.out.println("Number of examples: "+num_examples);

        //print the weights
        for (int j=0;j<w.length;j++)
        {   //if (j%16==0) System.out.println();
            System.out.format("%1.1f ",w[j]);
        }
        System.out.println();
        
        //testing set
        double num_correct=0;
        double pct_correct=0;

        Scanner fscan=new Scanner(new File(testing_file)); 
        num_examples=Double.parseDouble(fscan.nextLine());
        System.out.println("Number of examples: "+num_examples);

        while (fscan.hasNextLine())
        {   String line=fscan.nextLine();
            String[] numstr = line.split(",\\s*"); //get a string of the numbers
    
            //double[] x=new double[65]; //x array 
            x[0]=1; //first one is for the bias
    
            for (int j = 0; j < 64; j++) //doesn't include the target 
            {   x[j+1]=Double.parseDouble(numstr[j]); //create the x array
            }
            T=Integer.parseInt(numstr[64]); //last integer on the line
            //System.out.print("T= "+T);

            input=0; 
            for (int j=0;j<x.length;j++)
            {   input=input+x[j]*w[j];
            }
            I=(input>0) ? 1 : 0; //determine whether the neuron fires
            //System.out.println(" input= "+input);
            
            //calculate the percentage classified correctly
            num_correct=(T==I) ? num_correct+1 : num_correct; 
        }
        pct_correct=100*num_correct/num_examples;    
        System.out.println("Number classified correctly: "+num_correct);
        System.out.println("Percentage classified correctly: "+pct_correct);
        fscan.close();
    }
}

