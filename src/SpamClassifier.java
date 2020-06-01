import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SpamClassifier implements InterfaceClassifier {

	public int spamFiles = 0;
	public int hamFiles = 0;
	public double cutoff = 0; //threshold for spam classification
	public final double e = 2.7182818284590455; //helps with log based calculations
	
	public HashMap<String, Integer> spamStore = new HashMap<String, Integer>();
	public HashMap<String, Integer> hamStore= new HashMap<String, Integer>();

	public int getTotalNumOfSpam() {
		return spamFiles;
	}

	@Override
	public int getTotalNumOfHam() {
		return hamFiles;
	}
	
	public int getTotalNumMessages() {
		return (hamFiles+spamFiles);
	}

	@Override
	public int getNumOccInSpam(String word) {
		if (!spamStore.containsKey(word))
			return 0;
		return spamStore.get(word);
	}

	@Override
	public int getNumOccInHam(String word) {
		if (!hamStore.containsKey(word))
			return 0;
		return hamStore.get(word);
	}

	@Override
	public void setSpamCutoff(double cut) {
		cutoff = cut;
	}

	@Override
	public double getSpamCutoff() {
		return cutoff;
	}

	@Override
	public double probSpamGivenWord(String word) {
		double spamGivenWord;
		double hamGivenWord;
		spamGivenWord = ((double)getNumOccInSpam(word));
		hamGivenWord = (double)getNumOccInHam(word);
		return ((double)spamGivenWord/(spamGivenWord+hamGivenWord));
	}
	
	public double probHamGivenWord(String word) { 
		double spamGivenWord;
		double hamGivenWord;
		spamGivenWord = ((double)getNumOccInSpam(word));
		hamGivenWord = (double)getNumOccInHam(word);
		return ((double)hamGivenWord/(spamGivenWord+hamGivenWord));
	}
	

	@SuppressWarnings("resource")
	public double probSpamGivenEmail(InputStream in) { //takes in the textfiles 
		Scanner c = new Scanner(in); //if checking with string aka words and not txt files, use 'convert' method 
		double p = 0.0;
		double sum = 0.0;
		while(c.hasNext()) {				
			String s = c.next();
			p = probSpamGivenWord(s);
			if ((!spamStore.containsKey(s)) ||(!hamStore.containsKey(s))) //ignoring unseen words altogether 
				continue;
			/*if (probSpamGivenWord(s) > 0.995) //not sure about the extremities so didn't run these through
				p = 0.99;
			if (probSpamGivenWord(s) < 0.01)
				p = 0.
			    //continue; */
			sum += ((double)Math.log(1-p) - (double)Math.log(p)); //the bayes formula in log form
		}
		System.out.println("log sum: " + sum);
		System.out.println("returned: " + (1/(Math.pow(e, sum) +1)));
		return (1/(Math.pow(e, sum) +1)); //the formula that return the probability

	}
	
	public void addSpamSet(InputStream in) throws IOException{ //detects and stores spam words
		Set <String> list = new HashSet<String>();
		Scanner sc = new Scanner(in);
		while(sc.hasNext()) {
			String s = sc.next();
			if(!list.contains(s)) {
				spamStore.put(s, 1);
				list.add(s);	
			}
			else {
		        spamStore.put(s, spamStore.get(s)+1);
		        list.add(s);
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void addHamSet(InputStream in) throws IOException{ //detects and stores ham words
		Scanner sc = new Scanner(in);
		Set <String> list = new HashSet<String>();
		while(sc.hasNext()) {
			String s = sc.next();
			if(!list.contains(s)) {
				hamStore.put(s, 1);
				list.add(s);	
			}
			else {
		        hamStore.put(s, hamStore.get(s)+1);
		        list.add(s);
			}
		}
	}
	
	
	public void addAllSpamEmails(File files) throws IOException { //adding the whole dataset of spam emails
		for(File f: files.listFiles()) {
			FileInputStream spam = new FileInputStream(f);
			spamFiles++;
			addSpamSet(spam);
		}
	}
	
	public void addAllHamEmails(File files) throws IOException { //adding the whole dataset of ham emails
		for(File f: files.listFiles()) {
			FileInputStream ham = new FileInputStream(f);
			hamFiles++;
			addHamSet(ham);
		}
	}
	
	//helper class
	private static InputStream convert(String word) {
		return new ByteArrayInputStream(word.getBytes());
	}

	//helper method
	public boolean isSpam(InputStream in) {
		return (probSpamGivenEmail(in)>cutoff);
	}
}
  