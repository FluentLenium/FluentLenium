#!/bin/bash

set +e
echo "killing orphan chrome* processes with fire"
killall -v -s SIGKILL chromedriver
killall -v -s SIGKILL google-chrome
killall -v -s SIGKILL chrome
killall -v -s SIGKILL chrome_crashpad_handler

echo "chrome processes"
ps aux |grep chrome

echo "open file handle count"
set -e
lsof|wc -l

