import os
import pandas as pd
import pickle
import sys

def GetInputData_port1(argv):
    filePath = argv[1]
    if os.path.isfile(filePath):
        print("Warning: input data not find!\n")
        return None
    inputData = pd.read_csv(filePath, encoding="utf8")
    if inputData.shape[0] == 0:
        sys.exit("Input data is empty!\n")
    return(inputData)

def GetInputModel_port2(argv):
    filePath = argv[2]
    if os.path.isfile(filePath):
        print("Warning: input model not find!\n")
        return None
    try:
        with open(filePath, "rb") as f:
            inputModel = pickle.load(f)
    except:
        sys.exit("Input model format error!\n")
    return(inputModel)

def Python_Manipulation(data_port1, model_port2, img_path):
#compile your own Python script here with dataSource from data_port1 and/or model from model_port2
#data_port1 or model_port2 may be None as your link state
111111111111333333333333333
#transfer your output data to data_port3 and/or your output model to model_port4
result = {"data_port3": None, "data_port4": None}
return result
                                if __name__ == "__main__":
    if len(sys.argv) != 4:
        sys.exit("The length of rguments not equal to 4!\n")
    data_port1 = GetInputData_port1(sys.argv)
    model_port2 = GetInputModel_port2(sys.argv)
    result = Python_Manipulation(data_port1, model_port2, sys.argv[3])
    if result["data_port3"] != None:
    	result["data_port3"].to_csv(sys.argv[1], encoding="utf8", index=False) 
    if result["data_port4"] != None:
        with open(sys.argv[2], "rb") as f:
            result["data_port4"] = pickle.load(f)

