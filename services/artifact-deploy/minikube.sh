IMAGE="demoappjava"

echo "🔨  Building Docker Image"
minikube image build -t $IMAGE .

echo "🖼️  Pushing image to minikube. This will take time..."
minikube image rm $IMAGE || echo "Image not present"
minikube image load $IMAGE
