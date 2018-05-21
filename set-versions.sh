#!/bin/bash


function get_artifact_version_without_classifier(){
    ARTIFACT_VERSION=$(grep -E -m 1 -o "<version>(.*)</version>" pom.xml | sed 's/<version>//' | sed 's/<\/version>//')
    VERSION_WITHOUT_QUALIFIER="${ARTIFACT_VERSION%%-*}"
    echo $VERSION_WITHOUT_QUALIFIER
}


function set_release_version(){
   VERSION="$(get_artifact_version_without_classifier)-RELEASE"   
   BRANCH_NAME="$TRAVIS_BRANCH"
   echo "Generating release version : $VERSION"
   echo "Current Branch: $BRANCH_NAME"
   mvn versions:set -DnewVersion=$VERSION    
}

function set_development_and_increment_version(){
   RELEASE_VERSION="$(get_artifact_version_without_classifier)-RELEASE"  
   VERSION_WITHOUT_QUALIFIER="$(get_artifact_version_without_classifier)"   
   BRANCH_NAME="$TRAVIS_BRANCH"

   git add pom.xml
   git commit -m '[skip ci] - Generating release version '$RELEASE_VERSION
   git tag -a "v$RELEASE_VERSION" -m "Tagging version v$RELEASE_VERSION"

   a=( ${VERSION_WITHOUT_QUALIFIER//./ } )                  
   ((a[1]++))            
   SNAPSHOT_VERSION="${a[0]}.${a[1]}.0-SNAPSHOT"  

   echo "Setting develop version to: $SNAPSHOT_VERSION"
   echo "Current Branch:  $BRANCH_NAME" 
   mvn versions:set -DnewVersion=$SNAPSHOT_VERSION

   git add pom.xml 
   git commit -m '[skip ci] - Setting develop version to '$SNAPSHOT_VERSION  

   git checkout master
   git merge --ff-only "$TRAVIS_COMMIT"  
}


function push(){
   last_tag=$(git describe --abbrev=0 --tags)  
   git push "https://${GITHUB_TOKEN}@github.com/fabiosoaza/spring-rest-base" master
   git push "https://${GITHUB_TOKEN}@github.com/fabiosoaza/spring-rest-base" $last_tag
   git pull

}

git config --global user.email 'travis@travis-ci.org'
git config --global user.name 'Travis'
git remote set-branches --add origin master
git fetch
git reset --hard

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


