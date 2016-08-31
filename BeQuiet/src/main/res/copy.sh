#!/bin/bash

i=`ls | grep time_sec`

for j in $i
do
    echo 'copy '$j' to time_min_'${j##*time_sec_}''
    cp $j time_min_${j##*time_sec_}
done
