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

minikube-build-push-image: 
	@eval $$(minikube docker-env) ;\
	docker build . -f Dockerfile -t fabiosoaza/spring-rest-base-app:latest

k8s-create-configmaps:
	kubectl create configmap properties --from-literal=db_host=10.0.2.2 --from-literal=db_name=test --from-literal=db_port=5432 --from-literal=db_user=test --from-literal=db_pass=test --from-literal=ssl_mode=disable --from-literal=management_user=test --from-literal=management_pass=test

k8s-create-services:	
	kubectl create -f app.k8s.yaml	

k8s-apply-services:	
	kubectl apply -f app.k8s.yaml	

k8s-delete-services:
	kubectl delete endpoints,services,pods,deployments,daemonsets,configmaps --all	

k8s-deploy-services: k8s-delete-services k8s-create-configmaps k8s-create-services

k8s-run-service:
	minikube service spring-rest-base-app 	

minikube-deploy-run-services: k8s-deploy-services k8s-run-service	