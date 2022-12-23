#!/bin/bash
scriptDir=$(dirname -- "$(readlink -f -- "$BASH_SOURCE")")
PROJECT_DIR=$(dirname "$SCRIPT_DIR")

# COMMAND_CREATE='create'
# COMMAND_UPDATE='update'
COMMAND_INSTALL='install'
COMMAND_UPLOAD='upload'
# COMMAND_RUN='run'

CLUSTER_PROVIDER_GKE='gke'
CLUSTER_PROVIDER_MINIKUBE='minikube'

helpFunction()
{
    echo "Available Commands:
--------------------
    $COMMAND_INSTALL         $COMMAND_INSTALL the service
    $COMMAND_UPLOAD         $COMMAND_UPLOAD and upload the image

    -P --provider   To set the cluster provide. Allowed values - $CLUSTER_PROVIDER_GKE|$CLUSTER_PROVIDER_MINIKUBE
    -h --help       To see this help text
    "
    echo ""
    exit 1 # Exit script after printing help
}

arguments=()
# Transform long options to short ones
next_is_opt=0
for arg in "$@"; do
  shift
  
    case "$arg" in
      # # command
      $COMMAND_INSTALL)     
        command="$arg"   ;;

      # general
      '-P' | '--provider')  
        set -- "$@" '-P'
        next_is_opt=1  
        ;;
      '-h' | '--help')      
        set -- "$@" '-h'   
        ;;
      *) 
        # capture this as argument iff it's not a value for opt
        if [[ next_is_opt -eq 0 ]]; then
          arguments+=("$arg") 
        else
          next_is_opt=0
          set -- "$@" "$arg"
        fi    ;;
    esac
done

# Parse short options
while getopts "P:h" opt
do
  case "$opt" in
    P ) 
      CLUSTER_PROVIDER="$OPTARG" 
    ;;
    ? )       
      helpFunction "$command" "$OPTARG" 
    ;; 
  esac
done

if [[ -z $CLUSTER_PROVIDER ]]; then
    echo "No cluster provider found."
    helpFunction
fi

if [[ -z $command ]]; then
    echo ""
    echo "✗ No valid command found."
    echo ""
    helpFunction;
fi

if [[ "$command" == "$COMMAND_INSTALL" ]]; then
  sh $scriptDir/install/$CLUSTER_PROVIDER.sh 
elif [[ "$command" == "$COMMAND_UPLOAD" ]]; then
  sh $scriptDir/artifact-deploy/$CLUSTER_PROVIDER.sh 
fi
