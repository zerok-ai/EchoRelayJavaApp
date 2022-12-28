#!/bin/bash
scriptDir=$(dirname -- "$(readlink -f -- "$BASH_SOURCE")")
PROJECT_DIR=$(dirname "$SCRIPT_DIR")

COMMAND_BUILD='build'
COMMAND_RUN='run'

helpFunction()
{
    if [[ -z $1 ]]; then

echo -e "Available Commands:
--------------------
    $COMMAND_BUILD  $COMMAND_BUILD a supported resource
    $COMMAND_RUN    $COMMAND_RUN a supported resource

    -h --help       To see this help text
    "
    fi
    echo ""
    exit 1 # Exit script after printing help
}

arguments=()
# Transform long options to short ones
next_is_opt=0
for arg in "$@"; do
  shift
  
    case "$arg" in
      # general
      '-h' | '--help')  
        set -- "$@" '-h'   ;;
      
      # command
      $COMMAND_BUILD)   
        command="$arg"   ;;
      $COMMAND_RUN)     
        command="$arg"   ;;

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
while getopts "h" opt
do
  case "$opt" in
    ? )       
      helpFunction "$command" "$OPTARG" 
    ;; 
  esac
done

if [[ -z $command ]]; then
    echo "No command found."
    helpFunction
fi

dir=${PWD}
cd "$scriptDir/../"
if [ "$command" = "$COMMAND_BUILD" ] ; then
    echo "🔨  Building the project"
    rm -rf ./build/libs
    ./gradlew build -p $PROJECT_DIR -x test
elif [ "$command" = "$COMMAND_RUN" ]; then
    echo "🏃  Running the project locally"
    java -jar ./build/libs/echorelayapp-0.0.1-SNAPSHOT.jar
fi
cd $dir