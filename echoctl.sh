#!/bin/bash
scriptDir=$(dirname -- "$(readlink -f -- "$BASH_SOURCE")")

RESOURCE_CLUSTER='cluster'
RESOURCE_CLUSTER_SHORT='clu'

RESOURCE_SERVICE='service'
RESOURCE_SERVICE_SHORT='svc'

RESOURCE_CODE='code'
RESOURCE_CODE_SHORT='cod'

helpFunction()
{
    echo -e "Available Commands:
--------------------
    $RESOURCE_CLUSTER_SHORT | $RESOURCE_CLUSTER   Tasks related to $RESOURCE_CLUSTER 
    $RESOURCE_SERVICE_SHORT | $RESOURCE_SERVICE   Tasks related to $RESOURCE_SERVICE 
    $RESOURCE_CODE_SHORT | $RESOURCE_CODE      Tasks related to $RESOURCE_CODE 

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

      # resources
      "$RESOURCE_CLUSTER_SHORT" | "$RESOURCE_CLUSTER")
        resource=$RESOURCE_CLUSTER ;;
      "$RESOURCE_SERVICE_SHORT" | "$RESOURCE_SERVICE")
        resource=$RESOURCE_SERVICE ;;
      "$RESOURCE_CODE_SHORT" | "$RESOURCE_CODE")
        resource=$RESOURCE_CODE ;;

      # general
      '-h' | '--help')    
        if [[ -n $resource ]]; then # forward -h to the script of the resource
          arguments+=("$arg") 
        else # handle help through getopts
          set -- "$@" "$arg"
        fi    ;;

      # '-P' | '--provider')  set -- "$@" '-P'; next_is_opt=1  ;;
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

# printf '%s ' "arguments--- ${arguments[@]}"

# Parse short options
while getopts "h" opt
do
  case "$opt" in
    * )       
      helpFunction "$command" "$OPTARG"
    ;; 
  esac
done

if [[ -z $resource ]]; then
    echo "No resource found."
    helpFunction
fi

if [[ "${resource}" == "$RESOURCE_CODE" ]]; then
  sh ./code/setup.sh "${arguments[@]}"
elif [[ "${resource}" == "$RESOURCE_SERVICE" ]]; then
  sh ./services/setup.sh "${arguments[@]}" -P "gke"
elif [[ "${resource}" == "${RESOURCE_CLUSTER}" ]]; then
  sh ./cluster/lattice/install.sh -c ./cluster/constants.properties -s ./cluster/stages.properties -l ./cluster/constants.local "${arguments[@]}"
fi
