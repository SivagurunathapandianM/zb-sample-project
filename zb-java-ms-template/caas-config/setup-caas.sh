#!/bin/bash
set -x

# Detect if running in an ADO release pipeline or not
if [[ ! -z $SYSTEM_ARTIFACTSDIRECTORY  ]];then cd $SYSTEM_ARTIFACTSDIRECTORY/caas-setup/caas-setup;fi

kubectl -n zb-jms-template delete  deployment.apps/zb-java-ms-template

kubectl -n zb-jms-template apply -f microservice-deploy.yaml
kubectl -n zb-jms-template apply -f microservice-ingress.yaml
kubectl -n zb-jms-template apply -f microservice-service.yaml