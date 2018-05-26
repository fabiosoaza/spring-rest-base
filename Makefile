docker-build: build-app	
	docker build . -f Dockerfile -t fabiosoaza/spring-rest-base-app:latest

build-app:
		mvn clean install

docker-stop:
		docker-compose kill
		docker-compose rm -fv 

docker-build-up:	docker-stop			
		docker-compose up --build -d		

docker-up:	docker-stop			
		docker-compose up -d

docker: docker-build docker-build-up

docker-run-liquibase-update: 
	docker-compose run liquibase run-liquibase update 



