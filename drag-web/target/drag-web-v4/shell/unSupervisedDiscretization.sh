for arg in $@
do
  key=`echo $arg|cut -d "=" -f 1`
  value=`echo $arg|cut -d "=" -f 2`
  if [ $key = "inputPath" ]
  then
	hdfsInputFile=$value
  elif [ $key = "outputPath" ]
  then
	hdfsOutputData=$value
  elif [ $key = "usedFields" ]
  then
    featrueColnames=$value
  elif [ $key = "discretionType" ]
  then
	discretionType=$value
  elif [ $key = "equalWidth" ]
  then
	widthVal=$value
  elif [ $key = "equalFrequency" ]
  then
	frequencyVal=$value
  elif [ $key = "userDefined" ]
  then
    definedVal=$value
  elif [ $key = "reserveOldColumn" ]
  then
    reserveOldColumn=$value
  fi
done
if [ "$featrueColnames" = "" ]
then
    featrueColnames="ALL_COLUMN"
fi

localScript=/usr/local/script/feature_discrete.py
localInputData=/tmp/workflow-cache/data/`date +%N%S%N`Discretization_in.csv
localoutputData=/tmp/workflow-cache/data/`date +%N%S%N`Discretization_out.csv

#download hdfs data to local path
hadoop fs -test -e $hdfsInputFile
if [ $? -eq 0 ]
then
    hadoop fs -get $hdfsInputFile $localInputData
else
	exit 1
fi

#parse the discretionType and discretion value
if [ $discretionType = "equalWidth" ]
then
	bins=$widthVal
elif [ $discretionType = "equalFrequency" ]
then
	bins=$frequencyVal
elif [ $discretionType = "userDefined" ]
then
	bins=$definedVal
else
	#no use
	bins=100
fi

#run local python Script
#arg1-input data;arg2-input model
if [ $reserveOldColumn = "true" ]
then
    reserveOldColumn="append"
else
    reserveOldColumn="replace"
fi

if [ -e $localInputData ]
then
	python3 $localScript $localInputData $localoutputData $featrueColnames $bins $discretionType $reserveOldColumn
else
	exit 2
fi
#upload local file to hdfs
if [ -e $localoutputData ]
then
    hadoop fs -copyFromLocal $localoutputData $hdfsOutputData
    rm -f $localoutputData
	rm -f $localInputData
else
	exit 3
fi
