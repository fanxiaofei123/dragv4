import os
import pandas as pd
import pickle
import sys

def GetInputData_port1(argv):
    filePath = argv[1]
    if not os.path.isfile(filePath):
        print("Warning: input data not find!\n")
        return None
    inputData = pd.read_csv(filePath, encoding="utf8")
    if inputData.shape[0] == 0:
        sys.exit("Input data is empty!\n")
    return(inputData)

def GetInputModel_port2(argv):
    #filePath = argv[2]
    #if not os.path.isfile(filePath):
        #print("Warning: input model not find!\n")
        #return None
    #try:
        #with open(filePath, "rb") as f:
            #inputModel = pickle.load(f)
    #except:
        #sys.exit("Input model format error!\n")
    #return(inputModel)
    return None

