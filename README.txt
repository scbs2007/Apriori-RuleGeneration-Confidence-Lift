This was a homework assignment for the Data Mining course.

Apriori algorithm.

Implement the Apriori algorithm by first determining frequent
itemsets and then proceeding to identify association rules. 
a) Implement both Fk-1 x F1 and Fk-1 X Fk-1 methods. Allow in your code to track the
number of generated candidate itemsets as well as the total number of frequent itemsets.
b) Use three data sets from the UCI Machine learning repository to test your algorithms.
The data sets should contain at least 1000 examples, and at least one data set should contain 10,000
examples or more. You can convert any classification or regression data set into a set of transactions
and you are allowed to discretize all numerical features into two or more categorical features. Compare
these two candidate generation methods on each of the three data sets for three different meaningful
levels of the minimum support threshold (the thresholds should allow you to properly compare different
methods and make useful conclusions). Provide the numbers of candidate itemsets considered in a table
and discuss the observed savings that one of these methods achieves.
c) Enumerate the number of frequent closed itemsets as well as maximal frequent itemsets
on each of your data sets for each of the minimum support thresholds from the previous question.
Compare those numbers with the numbers of frequent itemsets.
d) Implement confidence-based pruning to enumerate all association rules for a given set of
frequent itemsets. Use the previous data sets, with three levels of support and three levels of confidence
to quantify the savings in the number of generated confident rules compared to the brute-force method.
e) For each data set and each minimum support threshold, select three confidence levels for
which you will generate association rules. Identify top 10 association rules for each combination of
support and confidence thresholds and discuss them (i.e. comment on their quality or peculiarity).
Select data sets where you can more easily provide meaningful comments regarding the validity of
rules.
f) Instead of confidence, use lift as your measure of rule interestingness. Identify top 10 rules
for each of the previous situations and discuss the relationship between confidence and lift.
No specialized libraries are allowed for this task. Make sure that you code runs and submit all the code you
used in this task, including the code that converts data sets from UCI Machine Learning repository into a
transaction data set. Do not include raw data sets in your supplement; however, do provide links to the data
sets you used such that your code can be run independently and its performance can be verified.


Executing:
You will first have to change the path of the dataset file at line 548 in Problem3.java
Also uncomment the respective mapping[] after the line where you enter the file path.
You will be asked to enter the support threshold at the command line.

After that the following will be displayed:
1. All the frequent itemsets generated, along with the total number of 
considered itemsets (in both the k-1X1 and k-1Xk-1 methods).
2. Maximal Frequent ItemSet Count
3. Closed Frequent ItemSet Count
4. Total number of rules generated by bruteforce method

Now, you will be asked to enter the confidence threshold at the command line.
Then the top 10 rules will be displayed using confidence followed by lift as a measure of interestingness.
[The rules have been sorted in decreasing order of confidence and lift values respectively.]

Also the total number of rules generated using both list and confidence will be displayed.


For Rules:
If you use the data set files that have been enclosed for all the 3 data sets (Car Evaluation, Contraceptive Method Choices and
Nursery) then the mapping for Sparse Matrix coulmn indexes to actual attributes will be as follows:

Name of dataset:
   Name of attribute
   (sparse matrix column index)actual value,...
   .
   .
   .

Car Evaluation dataset:
   buying
   (0)v-high, (1)high, (2)med, (3)low
   maint        
   (4)v-high, (5)high, (6)med, (7)low
   doors
   (8)2, (9)3, (10)4, (11)5-more
   persons
   (12)2, (13)4, (14)more
   lug_boot
   (15)small, (16)med, (17)big
   safety
   (18)low, (19)med, (20)high
   class
   (21)unacc, (22)acc, (23)v-good, (24)good 

Contraceptive Method Choices dataset (after binning the Wife's age attribute):
   Wife's age
   (0)21-25, (1)41-45, (2)36-40 , (3)16-20, (4)26-30, (5)46-50, (6)31-35
   Wife's education
   (7)2, (8)1=low, (9)3, (10)4=high
   Husband's education
   (11)3, (12)2, (13)4=high, (14)1=low
   Number of children ever born
   (15)3, (16)10, (17)7, (18)9, (19)8, (20)0, (21)6, (22)1, (23)2, (24)4, (25)5, (26)12, (27)11, (28)13, (29)14
   Wife's religion
   (30)1=Islam, (31)0=Non-Islam
   Wife's now working?
   (32)1=No, (33)0=Yes
   Husband's occupation
   (34)2, (35)3, (36)1, (37)4
   Standard-of-living index
   (38)3, (39)4=high, (40)2, (41)1=low
   Media exposure
   (42)0=Good, (43)1=Not good
   Contraceptive method used
   (44)1=No-use, (45)2=Long-term, (46)3=Short-term

Nursery dataset:
   parents
   (0)usual, (1)pretentious, (2)great_pret
   has_nurs       
   (3)proper, (4)less_proper, (5)improper, (6)critical, (7)very_crit
   form
   (8)complete, (9)completed, (10)incomplete, (11)foster
   children
   (12)1, (13)2, (14)3, (15)more
   housing
   (16)convenient, (17)less_conv, (18)critical
   finance
   (19)convenient, (20)inconv
   social
   (21)non-prob, (22)slightly_prob, (23)problematic
   health
   (24)recommended, (25)priority, (26)not_recom
   class
   (27)recommend, (28)priority, (29)not_recom, (30)very_recom, (31)spec_prior