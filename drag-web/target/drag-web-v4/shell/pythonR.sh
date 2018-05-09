source /etc/profile
for arg in $@
do
  key=`echo $arg|cut -d "=" -f 1`
  value=`echo $arg|cut -d "=" -f 2`
  if [ $key = "inputPath" ]
  then
    hdfsInputFile=$value
  elif [ $key = "tmpOutputData" ]
  then
    hdfsOutputData=$value
  elif [ $key = "tmpOutputModel" ]
  then
    hdfsOutputModel=$value
  elif [ $key = "scriptPath" ]
  then
    scriptPath=$value
  elif [ $key = "scriptName" ]
  then
    scriptName=$value
  elif [ $key = "function" ]
  then
    function=$value
  elif [ $key = "tmpImgPath" ]
  then
    tmpImgPath=$value
  fi
done

#download hdfs data to local path
localScript=/tmp/workflow-cache/script/$scriptName
localData=/tmp/workflow-cache/data/`date +%N%S%N`.csv
localModel=/tmp/workflow-cache/model/`date +%N%S%N`.csv
localImg=/tmp/workflow-cache/img

hadoop fs -test -e $hdfsInputFile
if [ $? -eq 0 ]
then
    hadoop fs -get $hdfsInputFile $localData
else
    exit 2
fi

hadoop fs -test -e $scriptPath
if [ $? -eq 0 ]
then
    hadoop fs -get $scriptPath $localScript
else
    exit 3
fi

if [ $function = "r" ]
then
    #run local R Script
    #arg1-input data path;arg2-input model path;args3-img store path
    R -f $localScript --args $localData $localModel $localImg
elif [ $function = "python" ]
then
    #run local python Script
    #arg1-input data;arg2-input model
    python3 $localScript $localData $localModel $localImg
else
    exit 4
fi
rm -f $localScript

#upload local file to hdfs
if [ -e $localData ]
then
    hadoop fs -copyFromLocal $localData $hdfsOutputData
    rm -f $localData
fi
if [ -e $localModel ]
then
    hadoop fs -copyFromLocal $localModel $hdfsOutputModel
    rm -f $localModel
fi
if [ `ls $localImg | wc -l` != 0 ]
then
    hadoop fs -test -d $tmpImgPath
    if [ $? -eq 0 ]
    then
        hadoop fs -rmr $tmpImgPath
        hadoop fs -mkdir $tmpImgPath
        hadoop fs -chmod 777 $tmpImgPath
    fi
    hadoop fs -copyFromLocal $localImg $tmpImgPath
    for image in `ls $localImg`
    do
       image=${localImg}/$image
       rm -f $image
    done
fi
