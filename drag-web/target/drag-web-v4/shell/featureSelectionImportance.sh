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
  elif [ $key = "labelCol" ]
  then
    labelColname=$value
  elif [ $key = "featrueSelectionMethod" ]
  then
    method=$value
  fi
done
if [ "$featrueColnames" = "" ]
then
    featrueColnames="ALL_COLUMN"
fi

#download hdfs data to local path
localScript=/usr/local/script/feature_selection.py
localInputData=/tmp/workflow-cache/data/`date +%N%S%N`featureSel_importance_in.csv
localoutputData=/tmp/workflow-cache/data/`date +%N%S%N`featureSel_importance_out.csv

hadoop fs -test -e $hdfsInputFile
if [ $? -eq 0 ]
then
    hadoop fs -get $hdfsInputFile $localInputData
else
	exit 1
fi

#run local python Script
#arg1-input data  arg2-input model
mode="featureImportance"
featureSelectedNum=0
if [ -e $localInputData ]
then
	python3 $localScript $localInputData $localoutputData $featrueColnames $labelColname $method $mode $featureSelectedNum
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
