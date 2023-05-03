#!/usr/bin/env bash

mvn -Pjava11 javadoc:aggregate
rsync -av target/site/apidocs/ docs/javadoc/
rm -rf target