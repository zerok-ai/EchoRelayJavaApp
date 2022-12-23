#!/bin/bash
scriptDir=$(dirname -- "$(readlink -f -- "$BASH_SOURCE")")

RESOURCE_CLUSTER='cluster'
RESOURCE_SERVICE='service'
RESOURCE_CODE='code'

helpFunction()
{
    echo -e "Available Commands:
--------------------
    $RESOURCE_CLUSTER   Tasks related to $RESOURCE_CLUSTER 
    $RESOURCE_SERVICE   Tasks related to $RESOURCE_CLUSTER 
    $RESOURCE_CODE      Tasks related to $RESOURCE_CLUSTER 

    -h --help           To see this help text
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
      'clu' | $RESOURCE_CLUSTER)  
        resource=$RESOURCE_CLUSTER ;;
      'svc' | $RESOURCE_SERVICE)  
        resource=$RESOURCE_SERVICE ;;
      'cod' | $RESOURCE_CODE)     
        resource=$RESOURCE_CODE ;;

      # general
      '-h' | '--help')      set -- "$@" '-h'   ;;
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

# printf '%s\n' "args ${arguments[@]}"

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

if [[ "$resource" == "$RESOURCE_CODE" ]]; then
    sh ../jar/setup.sh $arguments
elif [[ "$resource" == "$RESOURCE_SERVICE" ]]; then
  # if [[ "$command" == "$COMMAND_INSTALL" ]]; then
    sh ./services/setup.sh $arguments
  # fi
elif [[ "$resource" == "$RESOURCE_CLUSTER" ]]; then
  ./lattice/install.sh -c constants.properties -l constants.local $arguments
fi
