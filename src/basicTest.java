import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class basicTest {
	InputStream in;
	SpamClassifier sc;
	public static final double threshold = 0.001;

	@Before
	public void setUp() throws Exception {
		sc = new SpamClassifier();
		in = new FileInputStream("basicTest/ham/1.txt");

		sc.addAllSpamEmails(new File("basicTest/spam"));
		sc.addAllHamEmails(new File("basicTest/ham"));
	}
	
	private static InputStream convert(String word) {
		return new ByteArrayInputStream(word.getBytes());
	}

	@Test
	public void testCutoff() throws Exception {
		sc.setSpamCutoff(0.1);
		assertEquals(0.1,sc.getSpamCutoff(),threshold);
	}
	
	@Test
	public void testAddingDatasets() throws Exception {
		sc.addHamSet(in);
		assertEquals(true, sc.hamStore.containsKey("oh"));
		assertEquals(true, sc.hamStore.containsKey("hi"));
		assertEquals(true, sc.hamStore.containsKey("mark"));

	}

	@Test
	public void testAddAllSpam() throws Exception {
		assertEquals(1, sc.getTotalNumOfHam());
		assertEquals(1, sc.getTotalNumOfSpam());
		assertEquals(2, sc.getTotalNumMessages());
	}
	
	@Test
	public void testprob() throws Exception {
		double word = sc.probSpamGivenWord("money");
		assertEquals(1.0, word, threshold);
		
	}
	
	
	@Test
	public void testPleaseSend() throws Exception {
		double word = sc.probSpamGivenEmail(new FileInputStream ("basicTest/test1"));
		assertEquals(0.5, word, threshold);
	}
	
    public void testcheck() throws Exception{
        for (String s : new String[] {"please","hot","mark","in","idk"}) {
            double p1=sc.probSpamGivenWord(s);
            System.out.println("P(S|"+s+")="+p1);
        }
    }
}
