#!/bin/bash


function get_artifact_version_without_classifier(){
    ARTIFACT_VERSION=$(grep -E -m 1 -o "<version>(.*)</version>" pom.xml | sed 's/<version>//' | sed 's/<\/version>//')
    VERSION_WITHOUT_QUALIFIER="${ARTIFACT_VERSION%%-*}"
    echo $VERSION_WITHOUT_QUALIFIER
}


function set_release_version(){
   VERSION="$(get_artifact_version_without_classifier)-RELEASE"   
   BRANCH_NAME="$TRAVIS_COMMIT"
   echo "Generating release version : $VERSION"
   echo "Current Branch: $BRANCH_NAME"
   mvn versions:set -DnewVersion=$VERSION    
}

function set_snapshot_version(){
    VERSION_WITHOUT_QUALIFIER="$(get_artifact_version_without_classifier)" 
    BRANCH_NAME="$TRAVIS_COMMIT"
    
    a=( ${VERSION_WITHOUT_QUALIFIER//./ } )                  
    ((a[1]++))            
    SNAPSHOT_VERSION="${a[0]}.${a[1]}.0-SNAPSHOT"
    
    echo "Setting develop version to: $SNAPSHOT_VERSION"
    echo "Current Branch:  $BRANCH_NAME" 
    mvn versions:set -DnewVersion=$SNAPSHOT_VERSION 
}

function tag_release(){
    RELEASE_VERSION="$(get_artifact_version_without_classifier)-RELEASE"
    BRANCH_NAME="$TRAVIS_COMMIT"
    echo "Current Branch: $BRANCH_NAME"
    git add pom.xml
    git commit -m '[skip ci] - Generating release version '$RELEASE_VERSION
    git tag -a "v$RELEASE_VERSION" -m "Tagging version v$RELEASE_VERSION"
    git checkout master
    git merge --ff-only "$BRANCH_NAME" 
}

function tag_snapshot(){    
    SNAPSHOT_VERSION="$(get_artifact_version_without_classifier)-SNAPSHOT"
    BRANCH_NAME="$TRAVIS_COMMIT"
    echo "Current Branch: $BRANCH_NAME"

    git add pom.xml 
    git commit -m '[skip ci] - Setting develop version to '$SNAPSHOT_VERSION 

    git checkout master
    git merge --ff-only "$BRANCH_NAME" 

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
        "set_release_version")
            set_release_version
            ;;
        "set_snapshot_version")
            set_snapshot_version
            ;;
        "tag_release")
            tag_release
            ;; 
        "tag_snapshot")
            tag_snapshot
            ;;                         
        "push")
            push
            ;;   
  esac


