#!/bin/bash


function get_artifact_version_without_classifier(){
    ARTIFACT_VERSION=$(grep -E -m 1 -o "<version>(.*)</version>" pom.xml | sed 's/<version>//' | sed 's/<\/version>//')
    VERSION_WITHOUT_QUALIFIER="${ARTIFACT_VERSION%%-*}"
    echo $VERSION_WITHOUT_QUALIFIER
}


function set_release_version(){
   VERSION="$(get_artifact_version_without_classifier)-RELEASE"
   echo "Generating release version : $VERSION"
   mvn versions:set -DnewVersion=$VERSION  
   git add pom.xml
   git commit -m '[skip ci]Generating release version '$VERSION
   git tag -a "v$VERSION" -m "Tagging version v$VERSION"
}

function set_development_and_increment_version(){
   VERSION_WITHOUT_QUALIFIER="$(get_artifact_version_without_classifier)"   
   a=( ${VERSION_WITHOUT_QUALIFIER//./ } )                  
   ((a[1]++))            
   VERSION="${a[0]}.${a[1]}.0-SNAPSHOT"  
   echo "Setting develop version to: $VERSION" 
   mvn versions:set -DnewVersion=$VERSION
   git add pom.xml 
   git commit -m '[skip ci]Setting develop version to '$VERSION   
}



function push(){
   last_tag=$(git describe --abbrev=0 --tags)
   git push "https://${GITHUB_TOKEN}@github.com/fabiosoaza/spring-rest-base" master
  # git push "https://$GITHUB_TOKEN@github.com/fabiosoaza/spring-rest-base" $last_tag
   git pull

}

 case $1 in
        "develop")
            set_development_and_increment_version
            ;;
        "release")
            set_release_version
            ;;
        "push")
            push
            ;;   
  esac


