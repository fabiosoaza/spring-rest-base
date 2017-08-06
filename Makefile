docker-build: build-app
	docker build . -f Dockerfile-postgres  -t fabiosoaza/spring-rest-base-database:latest
	docker build . -f Dockerfile-liquibase  -t fabiosoaza/spring-rest-base-liquibase:latest
	docker build . -f Dockerfile-app  -t fabiosoaza/spring-rest-base-app:latest

build-app:
		mvn clean install

docker-stop:
		docker-compose -f docker-compose.yml kill

docker-up:	docker-stop	
		docker-compose -f docker-compose.yml up -d		

docker: docker-build docker-up


