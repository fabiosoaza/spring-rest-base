#!/bin/bash

ARTIFACT_VERSION=$(grep -E -m 1 -o "<version>(.*)</version>" pom.xml | sed 's/<version>//' | sed 's/<\/version>//')
VERSION_WITHOUT_QUALIFIER="${ARTIFACT_VERSION%%-*}"



function set_release_version(){
   VERSION=$VERSION_WITHOUT_QUALIFIER-RELEASE
   mvn versions:set -DnewVersion=$VERSION
   git add pom.xml
   git commit -m 'Generating release version '$VERSION
   git tag -a "v$VERSION" -m "Tagging version v$VERSION"
   git push --atomic "https://$GITHUB_TOKEN@github.com/fabiosoaza/spring-rest-base" master "$VERSION" > /dev/null 2>&1
   git pull
  
}

function set_development_and_increment_version(){   
   a=( ${VERSION_WITHOUT_QUALIFIER//./ } )                  
   ((a[1]++))            
   NEW_VERSION="${a[0]}.${a[1]}.${a[2]}"   
   mvn versions:set -DnewVersion=$NEW_VERSION-SNAPSHOT
   git add pom.xml
   git commit -m 'Setting release version to '$NEW_VERSION-SNAPSHOT
   git push "https://$GITHUB_TOKEN@github.com/fabiosoaza/spring-rest-base" master > /dev/null 2>&1
   git pull
  
}


case $1 in
"develop")
    set_development_and_increment_version
    ;;
"release")
    set_release_version
    ;;
esac
