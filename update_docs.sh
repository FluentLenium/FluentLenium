#!/usr/bin/env bash

mvn javadoc:aggregate
rsync -av target/site/apidocs/ docs/javadoc/
rm -rf target