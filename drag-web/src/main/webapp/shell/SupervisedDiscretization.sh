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
  elif [ $key = "discretionType" ]
  then
	discretionType=$value
  elif [ $key = "labelCol" ]
  then
	labelCol=$value
  elif [ $key = "significanceLevel" ]
  then
	significanceLevel=$value
  elif [ $key = "purity" ]
  then
	purity=$value
  elif [ $key = "reserveOldColumn" ]
  then
  reserveOldColumn=$value
  fi
done
if [ "$featrueColnames" = "" ]
then
    featrueColnames="ALL_COLUMN"
fi

localScript=/usr/local/script/feature_discrete.r
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

#run local python Script
#arg1-input data;arg2-input model
if [ -e $localInputData ]
then
	R -f $localScript --args $localInputData $localoutputData $discretionType $labelCol $featrueColnames $reserveOldColumn $significanceLevel $purity
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
