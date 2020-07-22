#!/bin/bash
set -x

# Detect if running in an ADO release pipeline or not
if [[ ! -z $SYSTEM_ARTIFACTSDIRECTORY  ]];then cd $SYSTEM_ARTIFACTSDIRECTORY/caas-setup/caas-setup;fi

sed -i "s/@ingress_env@/$1/g" microservice-ingress.yaml
sed -i "s/@ingress_env@/$1/g" microservice-deploy.yaml
sed -i "s/@currentenvironment@/$2/g" microservice-deploy.yaml

kubectl -n zb-spring-template delete  deployment.apps/zb-spring-template

kubectl -n zb-spring-template apply -f microservice-deploy.yaml
kubectl -n zb-spring-template apply -f microservice-service.yaml
kubectl -n zb-spring-template apply -f microservice-ingress.yaml
