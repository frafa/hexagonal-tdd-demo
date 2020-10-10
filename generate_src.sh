#!/bin/sh

declare GENPATH="./"
declare WORKSPACE="./"
declare YAMLFILE="exchange-rate-service.yaml"

java -jar ${GENPATH}openapi-generator-cli.jar generate -i ${WORKSPACE}${YAMLFILE} -g spring -o ${WORKSPACE} -c ${WORKSPACE}spring-config.yaml
