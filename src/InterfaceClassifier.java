import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface InterfaceClassifier {
	
	public int getTotalNumOfSpam();
	public int getTotalNumOfHam();
	public int getTotalNumMessages();
	public int getNumOccInSpam(String word);
	public int getNumOccInHam(String word);
	public void setSpamCutoff(double cut);
	public double getSpamCutoff();
	public double probSpamGivenWord(String word);
	public double probSpamGivenEmail(InputStream in);
	public boolean isSpam(InputStream in);
	public void addSpamSet(InputStream in) throws IOException;
	public void addHamSet(InputStream in) throws IOException;
	public void addAllSpamEmails(File files) throws IOException;
	public void addAllHamEmails(File files) throws IOException;

}