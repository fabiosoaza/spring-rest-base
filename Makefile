VERSION :=$(shell grep -E -m 1 -o "<version>(.*)</version>" pom.xml | sed 's/<version>//' | sed 's/<\/version>//')

docker-build: build-app	build-image

build-app:
		mvn clean install

build-image:
	docker build . -f Dockerfile -t fabiosoaza/spring-rest-base-app:$(VERSION)
	docker tag fabiosoaza/spring-rest-base-app:$(VERSION) fabiosoaza/spring-rest-base-app:latest

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

push-image-minikube: 
	@eval $$(minikube docker-env) ;\
	docker build . -f Dockerfile -t fabiosoaza/spring-rest-base-app:latest

create-configmaps:
	kubectl create configmap properties --from-literal=db_host=10.0.2.2 --from-literal=db_name=test --from-literal=db_port=5432 --from-literal=db_user=test --from-literal=db_pass=test

create-k8s-services:	
	kubectl create -f app.k8s.yaml	

apply-k8s-services:	
	kubectl apply -f app.k8s.yaml	

delete-k8s-services:
	kubectl delete endpoints,services,pods,deployments,daemonsets,configmaps --all	

deploy-k8s-services: delete-k8s-services create-configmaps create-k8s-services

run-k8s-service:
	minikube service spring-rest-base-app 	