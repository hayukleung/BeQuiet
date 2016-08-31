#!/bin/bash

min=`ls | grep time_min`
sec=`ls | grep time_sec`

for j in $sec
do
    echo $j

    sed 's/sec/min/' $j > time_min_${j##*time_sec_}
done
