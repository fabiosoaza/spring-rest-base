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
   git add pom.xml
   git commit -m '[skip ci]Generating release version '$VERSION
   git checkout master
   git merge --ff-only "$TRAVIS_COMMIT"
   git tag -a "v$VERSION" -m "Tagging version v$VERSION"
}

function set_development_and_increment_version(){
   VERSION_WITHOUT_QUALIFIER="$(get_artifact_version_without_classifier)"   
   BRANCH_NAME="$TRAVIS_BRANCH"
   a=( ${VERSION_WITHOUT_QUALIFIER//./ } )                  
   ((a[1]++))            
   VERSION="${a[0]}.${a[1]}.0-SNAPSHOT"  
   echo "Setting develop version to: $VERSION"
   echo "Current Branch:  $BRANCH_NAME" 
   mvn versions:set -DnewVersion=$VERSION
   git add pom.xml 
   git commit -m '[skip ci]Setting develop version to '$VERSION  
   git checkout master
   git merge --ff-only "$TRAVIS_COMMIT"  
}



function push(){
   last_tag=$(git describe --abbrev=0 --tags)  
   git push "https://${GITHUB_TOKEN}@github.com/fabiosoaza/spring-rest-base" master
  # git push "https://$GITHUB_TOKEN@github.com/fabiosoaza/spring-rest-base" $last_tag
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


