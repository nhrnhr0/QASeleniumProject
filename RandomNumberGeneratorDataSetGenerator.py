import itertools

# every list represents all the options in a field of date difference page   
bounds = ['-200,-100','-100,100','0,100'] # lower and upper limits
genNums = [1,3,20] # amount of numbers to generate
allowDup = [True,False] # if to allow duplicats or not
sortType = ['Ascend','Decend', 'None'] # in None the order counld be Ascend Decend or nither
useIntType = [True, False] # True will indicate to use Ingeter as the result type and False for using Decimal
precision = [1,22,99] # if the result is in decimal, precision will indicate the desierd number of degits after the dot
# Node: this program will allso create a combinations such as useIntType: Integer and Prcision: 99
#       off couse no need to test for the precision in an Ingeter result

combinations = [bounds,genNums,allowDup, sortType, useIntType,precision] # create a list of lists from all the fields
combinations = list(itertools.product(*combinations)) # create the product from the list (get all the possible combinations)
f = open('RandomNumberGeneratorDataSet.txt', 'w+') # open the output file
for option in combinations: # iterate on every posible combination
    line = str(option) # get result as string
    # data cleaning for easy use in java:
    line = line.replace('\'', '')
    line = line.replace('(','')
    line = line.replace(')','')
    line = line.replace(' ','')
    print(line) # debug
    f.write(line + '\n')
f.close()

