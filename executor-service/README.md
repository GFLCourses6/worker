# Executor service

## Architecture: QA Automation Cluster

### Commands:

Compile the code and package in file, skip the tests

`mvn clean package -DskipTests`

Build the project with Maven Tool

`mvn -B package --file pom.xml`

Build the project with Maven Tool without Tests

`mvn clean install -e -DskipTests`

Build the project with Maven Tool with Tests

`mvn clean install -X`

Maven will perform the necessary build tasks, such as compiling the code,
creating the project's artifacts, and verifying their correctness.

`mvn verify -e`

[![Build and Test Report](https://github.com/GFLCourses6/worker/actions/workflows/maven.yml/badge.svg)](https://github.com/GFLCourses6/worker/actions/workflows/maven.yml)
