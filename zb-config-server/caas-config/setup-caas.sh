  #!/bin/bash
  set -x

  # Detect if running in an ADO release pipeline or not
  if [[ ! -z $SYSTEM_ARTIFACTSDIRECTORY  ]];then cd $SYSTEM_ARTIFACTSDIRECTORY/caas-setup/caas-setup;fi
  kubectl -n zb-config delete  deployment.apps/zb-config
  kubectl -n zb-config apply -f zb-config-deploy.yaml
