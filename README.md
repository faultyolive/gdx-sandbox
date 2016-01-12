# README #

### Universal Tween Engine Setup ###

* Download [Universal Tween Engine](https://code.google.com/p/java-universal-tween-engine/downloads/list).
* Extract the jar files, open a terminal and run the following commands to add the universal tween engine jars to your local maven repo (version number may differ!):
`mvn install:install-file -Dfile=tween-engine-api.jar -DgroupId=aurelienribon -DartifactId=tweenengine -Dversion=6.3.3 -Dpackaging=jar`
`mvn install:install-file -Dfile=tween-engine-api-sources.jar -DgroupId=aurelienribon -DartifactId=tweenengine -Dversion=6.3.3 -Dpackaging=jar -Dclassifier=sources`
* Navigate to the project directory and refresh dependencies:
`gradle --refresh-dependencies`