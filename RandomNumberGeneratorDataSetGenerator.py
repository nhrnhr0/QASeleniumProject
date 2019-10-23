import itertools

bounds = ['m-m','m-p','p-p']
genNums = [1,3,20]
allowDup = [True,False]
sortType = ['Ascend','Decend', 'None']
useIntType = [True, False]
precision = [22]

all = [bounds,genNums,allowDup, sortType, useIntType,precision]
allList = list(itertools.product(*all))
f = open('RandomNumberGeneratorDataSet.txt', 'w+')
for option in allList:
    line = str(option)
    line = line.replace('m-m', '-200,-100')
    line = line.replace('m-p', '-100,100')
    line = line.replace('p-p', '0,100')
    line = line.replace('\'', '')
    line = line.replace('(','')
    line = line.replace(')','')
    line = line.replace(' ','')
    print(line)
    f.write(line + '\n')
f.close()

