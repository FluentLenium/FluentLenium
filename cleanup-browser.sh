#!/bin/bash -x

echo "killing orphan chrome* processes with fire"

killall -qv -s SIGKILL chromedriver
killall -qv -s SIGKILL google-chrome
killall -qv -s SIGKILL chrome
killall -qv -s SIGKILL chrome_crashpad_handler

set +e
ps aux |grep chrome
