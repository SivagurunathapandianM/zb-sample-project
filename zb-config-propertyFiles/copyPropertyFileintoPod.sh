#!/usr/bin/env bash

set -x
CWD="$(pwd)"
echo $CWD

cd PropertyFiles/PropertyFiles
tar zxvf properties-artifacts.tar.gz

export pod=$(kubectl -n zb-config get po -o json| jq .items[0].metadata.name|sed 's/"//g')

echo Running kubectl cp on all files
for file in *properties;do
  echo $file
  kubectl -n zb-config cp $file $pod:configfiles/systemConfiguration
done
