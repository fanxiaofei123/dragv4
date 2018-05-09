GetInputData_port1 <- function(args)
{
    filePath <- args[1]
    if(!file.exists(filePath))
    {
	   cat("warning:input data not find!\n")
	   return(NULL)
	}
	inputData <- read.csv(filePath,stringsAsFactors = F,encoding = 'UTF-8',check.names = F)
	if(nrow(inputData) == 0)
	{
	   stop('input data is empty\n')
	}
	return(inputData)
}
GetInputModel_port2 <- function(args)
{
    filePath <- args[2]
    if(!file.exists(filePath))
    {
	   cat("warning:input model not find!\n")
	   return(NULL)
	}
	inputModel<- readRDS(filePath)
	if(is.empty.model(inputModel))
	{
	   stop('input model is invalid\n')
	}
	return(inputModel)
}
R_Manipulation <- function(data_port1,model_port2,img_path)
{
#compile your own R script here with dataSource from data_port1 and/or model from model_port2
#data_port1 or model_port2 may be NULL as your link state, use is.null() to examine
22222222222222222222
#transfer your output data to data_port3 and/or your output model to model_port4
return(list(data_port3 = NULL,model_port4 = NULL))
}
                                    args <- commandArgs(TRUE)
if(length(args) != 3)
{
  stop("Arguments Not Equal To 3\n")
}

data_port1 <- GetInputData_port1(args)
model_port2 <- GetInputModel_port2(args)
result <- R_Manipulation(data_port1,model_port2,args[3])
if(!is.null(result$data_port3))
{
	write.csv(result$data_port3,file = args[1],row.names = F)
}
if(!is.null(result$data_port4))
{
	saveRDS(result$data_port4,file = args[2])
}
