#!/bin/bash




function get_artifact_version_without_classifier(){
    ARTIFACT_VERSION=$(grep -E -m 1 -o "<version>(.*)</version>" pom.xml | sed 's/<version>//' | sed 's/<\/version>//')
    VERSION_WITHOUT_QUALIFIER="${ARTIFACT_VERSION%%-*}"
    echo $VERSION_WITHOUT_QUALIFIER
}


function set_release_version(){
   VERSION="$(get_artifact_version_without_classifier)-RELEASE"   
   BRANCH_NAME="$(git rev-parse --abbrev-ref HEAD)"
   echo "Generating release version : $VERSION"
   echo "Current Branch: $BRANCH_NAME"
   mvn versions:set -DnewVersion=$VERSION    
}

function set_snapshot_version(){
    VERSION_WITHOUT_QUALIFIER="$(get_artifact_version_without_classifier)" 
    BRANCH_NAME="$(git rev-parse --abbrev-ref HEAD)"
    
    a=( ${VERSION_WITHOUT_QUALIFIER//./ } )                  
    ((a[1]++))            
    SNAPSHOT_VERSION="${a[0]}.${a[1]}.0-SNAPSHOT"
    
    echo "Setting develop version to: $SNAPSHOT_VERSION"
    echo "Current Branch:  $BRANCH_NAME" 
    mvn versions:set -DnewVersion=$SNAPSHOT_VERSION 
}

function tag_release(){
    RELEASE_VERSION="$(get_artifact_version_without_classifier)-RELEASE"
    BRANCH_NAME="$(git rev-parse --abbrev-ref HEAD)"
    echo "Current Branch: $BRANCH_NAME"
    git add pom.xml
    git commit -m '[skip ci] - Generating release version '$RELEASE_VERSION
    git tag -a "v$RELEASE_VERSION" -m "Tagging version v$RELEASE_VERSION"
    git checkout master
    git merge --ff-only "$BRANCH_NAME" 
}

function tag_snapshot(){    
    SNAPSHOT_VERSION="$(get_artifact_version_without_classifier)-SNAPSHOT"
    BRANCH_NAME="$(git rev-parse --abbrev-ref HEAD)"
    echo "Current Branch: $BRANCH_NAME"

    git add pom.xml 
    git commit -m '[skip ci] - Setting develop version to '$SNAPSHOT_VERSION 

    git checkout master
    git merge --ff-only "$BRANCH_NAME" 

}


function release(){
   echo "[RELEASE] - Creating temporary branch $BRANCH_NAME" 
   git checkout -b $INTEGRATION_BRANCH 
   set_release_version
   tag_release
   echo "[RELEASE] - Removing temporary branch $BRANCH_NAME"
   git branch -D $INTEGRATION_BRANCH 
}

function start(){
   echo "[SNAPSHOT] - Creating temporary branch $BRANCH_NAME" 
   git checkout -b $INTEGRATION_BRANCH 
   set_snapshot_version
   tag_snapshot
   git branch -D $INTEGRATION_BRANCH
   echo "[SNAPSHOT] - Removing temporary branch $BRANCH_NAME"

}

function push(){
   last_tag=$(git describe --abbrev=0 --tags)  
   git push "https://${GITHUB_TOKEN}@github.com/fabiosoaza/spring-rest-base" master
   git push "https://${GITHUB_TOKEN}@github.com/fabiosoaza/spring-rest-base" $last_tag
   git pull

}

function install(){
    echo "Running install task"
    if [ "$TRAVIS_BRANCH" == "master" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
        git fetch --prune
        set_release_version
    fi
    mvn install -B -V  
}

function after_success(){
    echo "Running after sucess task"
    bash <(curl -s https://codecov.io/bash)
    echo "Publishing codecoverage report"
    mvn clean test jacoco:report
    if [ "$TRAVIS_BRANCH" == "master" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
        echo "Releasing version"
        release
        echo "Sending artifacts to repository"
        mvn deploy --settings maven_settings.xml
        echo "Changing pom to next snapshot versions"
        start 
        echo "Merging to branch master e sending to scm"
        push  
    fi

}

INTEGRATION_BRANCH="build_$TRAVIS_JOB_NUMBER"

git config --global user.email 'travis@travis-ci.org'
git config --global user.name 'Travis'
git remote set-branches --add origin master
git fetch
git reset --hard

INTEGRATION_BRANCH="build_$TRAVIS_JOB_NUMBER"

 case $1 in
        "set_release_version")
            set_release_version
            ;;
        "set_snapshot_version")
            set_snapshot_version
            ;;
        "start")
            start
            ;; 
        "release")
            release
            ;;      
        "install")
            install
            ;;     
        "after_success")
            after_success
            ;;                                    
        "push")
            push
            ;;   
  esac


