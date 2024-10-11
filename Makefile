show-java-version:
	echo "Checking Java version"
	java -version
	echo "Check Java version is 21 or not, if not then install it"

install-dependencies:
	mvn clean install -DskipTests

start-app:
	docker-compose up --build

delete-app:
	docker-compose down -v