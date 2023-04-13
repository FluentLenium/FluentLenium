#!/bin/bash -e

echo "XXXYXXXXXXXXXXXXXXXXXXXXXXXXX"

set +e
ps aux |grep chrome
set -e

lsof -a -p $$