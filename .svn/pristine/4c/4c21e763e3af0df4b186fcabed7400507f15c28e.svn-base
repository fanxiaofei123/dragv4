source /etc/profile
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
  elif [ $key = "outlierProportion" ]
  then
    rate=$value
  elif [ $key = "DeleteOutlier" ]
  then
    mode=$value
  fi
done
if [ "$featrueColnames" = "" ]
then
    featrueColnames="ALL_COLUMN"
fi

#download hdfs data to local path
localScript=/usr/local/script/anomaly.py
localInputData=/tmp/workflow-cache/data/`date +%N%S%N`IsolationForest_in.csv
localoutputData=/tmp/workflow-cache/data/`date +%N%S%N`IsolationForest_out.csv

if [ ! `hadoop fs -test -e $hdfsInputFile` ]
then
    hadoop fs -get $hdfsInputFile $localInputData
else
	exit 1
fi

#run local python Script
#arg1-input data;arg2-input model
if [ $mode = "true" ]
then
    mode="filter"
else
    mode="mark"
fi
method="IsolationForest"
if [ -e $localInputData ]
then
	python3 $localScript $localInputData $localoutputData $featrueColnames $rate $method $mode
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
