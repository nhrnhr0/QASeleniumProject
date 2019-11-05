# QASeleniumProject
## What I test:
In the website [calculater.net!](https://www.calculator.net/) I tested for the Random Number Generator and the Date Calculator.
## How I test it:
Useing selenium and testNG in java with a page based mouble
### Random Number Generaotr:
![random number generator page](https://i.ibb.co/LpzDvkQ/random-Number-Generator-Screen.png)
The test need to check that for any combimation of parameters the website will give a valid random numbers:
 * if the genereted numbers are in the range
 * if the result size really mach the requested result size
 * if duplication is not allowed then to make sure no duplications found
 * if the result needs to be in some kind of sort
 * if the result is in Integer or Decimal
 * if the result presition is as expected
 I used a small python code to get the combimations I wanted to test:
 ```
 import itertools

 bounds = ['m-m','m-p','p-p'] # test from: minus to minus, minus to plus, and plus to plus
 genNums = [1,3,20] # the number of results
 allowDup = [True,False]
 sortType = ['Ascend','Decend', 'None']
 useIntType = [True, False]
 precision = [1,22,99]

 list_of_lists = [bounds,genNums,allowDup, sortType, useIntType,precision]
 all_combimations = list(itertools.product(*all))
 # write the data init file...
 ```
 Then I used this output file to tests all thouse test cases
 
 ### Date Difference:
 ![Date Difference page](https://i.ibb.co/wgHGF5M/date-Difference.png)
 * if the start date is a valid date
 * if the end date is a valid date
 * if the result is currect
 For this test I used sanaty tests and edge cases.
 
 
 #### Dependences:
 * selenium
 * testNG
