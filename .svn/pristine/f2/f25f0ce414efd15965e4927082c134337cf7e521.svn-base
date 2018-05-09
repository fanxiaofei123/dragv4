if __name__ == "__main__":
    if len(sys.argv) != 4:
        sys.exit("The length of rguments not equal to 4!\n")
    data_port1 = GetInputData_port1(sys.argv)
    model_port2 = GetInputModel_port2(sys.argv)
    result = Python_Manipulation(data_port1, model_port2, sys.argv[3])
    if type(result["data_port3"]) != type(None):
    	result["data_port3"].astype(str).to_csv(sys.argv[1], encoding="utf8", index=False)
    if type(result["data_port4"]) != type(None):
        with open(sys.argv[2], "wb") as f:
            pickle.dump(result["model_port4"], f)

