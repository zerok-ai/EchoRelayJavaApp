LOCATION="us-west1"
PROJECT_ID="zerok-dev"
REPOSITORY="zkproxy-demo"
IMAGE="demoappjava"
TAG="latest"
ART_Repo_URI="$LOCATION-docker.pkg.dev/$PROJECT_ID/$REPOSITORY/$IMAGE:$TAG"

echo "🔨  Building and tagging Docker Image"
docker build -t $IMAGE .
docker tag $IMAGE $ART_Repo_URI

gcloud auth configure-docker $LOCATION-docker.pkg.dev

echo "🖼️  Pushing image to gke. This will take time..."
docker push $ART_Repo_URI

