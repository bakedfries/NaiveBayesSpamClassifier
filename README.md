# Spam Classifier 
###### 'Spam'/'Ham' detection using Naive Bayes's Classifier 
-------------------------------------------------------------------------------------------------------------------------------------------
Language used: _Java_
-------------------------------------------------------------------------------------------------------------------------------------------
I will be analysing a pre-existing dataset of emails which are classified as 'spam', aka harmful, unwanted emails which are inappropriate at times and 'ham', aka important , relevant emails. Then, I will later train the program to classify emails from a completely new dataset and test the accuracy of the spam/ham classification.

## Classification Steps:
#### 1. The dataset:

I downloaded the pre-processed enron dataset of emails (https://www.kaggle.com/wanderfj/enron-spam) from kaggle and extracted enron1 to my workspace under src/datasets. Further, I separated the spam and ham to separate folders. From each of these datasets, I chose 5 random .txt files and put them separately under separate folders called hamTest and spamTest. The reason I did this was because I didn't want to test my trained data to those files that had already been classified. Bottomline, I didn't want any biases in my model.

#### 2. The Naive Bayes Model

This spam classifier is based on the Naive Bayes's Theorem. The Bayes's theorem states the following:

      P(A|B) = P(B|A) * P(A) 
              -----------------
                    P(B)
This can be written in terms of Spam (S) and a given word (W), and ham (H) aka not spam!

      P(S|W) = P(W|S) * P(S) 
              -----------------
                    P(H)

I calculated the number of times W occured in the spam and ham datasets respectively and formulated that to find the probability of W being a spam or a ham. Similarly, I used the same principle to calculate the probablity of a whole text file (multiple Ws) being spam or ham. The formula used is as follows:

                       1 
        ------------------------------
          [e^ {ln(1-P) - ln(P)} + 1]
          
where 'P' is the probability of each word in the text file being a spam.

#### 3. Training the datasets

After testing on a simple text file, I ran a few tests on the text files from the actual spam and ham dataset folders taht contained the enron emails. Adding the datasets into their designated methods label them as spam or ham respectively. So, the words were stored in a HashMap that would also count the number of times a certain word appears on the said spam or ham HashMaps respectively. This way, counting the number of occurence as well as the probability of a word being or a spam can be calculated with ease. 


#### 4. Testing the datasets

After the training, I took the 5 text files that I had put under a spearate folder from both the spam and ham datasets and ran tests on them. This is crucial because this part highlights the accuracy and shows that the model can actually classify the emails based on a completely different dataset. These new text files helps prevent biases in the model and give accurate results.

_Lo and behold a Spam Classifier!_
