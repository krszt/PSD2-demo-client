mvnw clean install package
docker build -f src/main/docker/Dockerfile --pull -t krszt/psd2-client .
docker push krszt/psd2-client