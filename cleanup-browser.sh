#!/bin/bash -e

echo "killing orphan chrome* processes"

killall -qv chromedriver
killall -qv google-chrome
killall -qv chrome_crashpad_handler

set +e
ps aux |grep chrome
set -e
