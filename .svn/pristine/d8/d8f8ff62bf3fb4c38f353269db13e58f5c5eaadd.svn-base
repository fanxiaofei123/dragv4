util.MapInputPort <- function()
{
    args = commandArgs(TRUE)
    if(!file.exists(args[1]))
    {
        stop('Input data not found\n')
    }
	return (read.csv(args[1], encoding = 'UTF-8', check.names = F))
}
util.GetImagePath <- function()
{
    args = commandArgs(TRUE)
    return(args[3])
}

util.mapOutputPort <- function(dataSet,port)
{
    args = commandArgs(TRUE)
    port <- as.numeric(port)
    if(is.na(port) | port < 0 | port > 2)
    {
        stop('output port index is not right format\n')
    }
    write.csv(dataSet,file = args[port],row.names = F)
}

args = commandArgs(TRUE)
if(length(args) != 3)
{
   stop("Arguments Not Equal To 3\n")
}