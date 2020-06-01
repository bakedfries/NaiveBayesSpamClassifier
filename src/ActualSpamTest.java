import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class ActualSpamTest {
	InputStream in;
	SpamClassifier sc;
	public static final double threshold = 0.0001;

	@Before
	public void setUp() throws Exception {
		sc = new SpamClassifier();
		sc.setSpamCutoff(0.4);
		
		//in = new FileInputStream("basicTest/ham/1.txt");

		sc.addAllSpamEmails(new File("datasets/spam"));
		sc.addAllHamEmails(new File("datasets/ham"));
	}
	

	@Test
	public void testHam1() throws Exception {
		double prob = sc.probSpamGivenEmail(new FileInputStream("datasets/hamtest/0004.1999-12-14.farmer.ham.txt"));
        assertTrue(prob < 0.10);
	}
	
	@Test
	public void testHam2() throws Exception {
		double prob = sc.probSpamGivenEmail(new FileInputStream("datasets/hamtest/0004.1999-12-14.farmer.ham.txt"));
        assertTrue(prob <0.01);
	}
	
	@Test
    public void testSpam1() throws Exception {
        double prob = sc.probSpamGivenEmail(new FileInputStream("datasets/spamtest/0006.2003-12-18.GP.spam.txt"));
        assertTrue(prob > 0.90);
    }
    
    
    public void testSpam2() throws Exception {
        double prob = sc.probSpamGivenEmail(new FileInputStream("datasets/spamtest/0017.2003-12-18.GP.spam.txt"));
        assertTrue(prob > 0.90);
    }
    
	@Test
    public void testFinalSpam() throws Exception {
        assertFalse(sc.isSpam(new FileInputStream("datasets/hamtest/0004.1999-12-14.farmer.ham.txt")));
    }

    @Test
    public void testFinalHam() throws Exception {
        assertFalse(sc.isSpam(new FileInputStream("datasets/hamtest/0005.1999-12-14.farmer.ham.txt")));
    }
	
    

}
