LOCATION="us-west1"
PROJECT_ID="zerok-dev"
REPOSITORY="multi-tier-app"
IMAGE="echo-relay-java"
TAG="latest"
ART_Repo_URI="$LOCATION-docker.pkg.dev/$PROJECT_ID/$REPOSITORY/$IMAGE:$TAG"

docker tag $IMAGE $ART_Repo_URI

gcloud auth configure-docker \
    $LOCATION-docker.pkg.dev

docker push $ART_Repo_URI