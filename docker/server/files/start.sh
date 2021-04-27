#!/usr/bin/env ash

# Because the config file is on this folder, we need to cd to there first
cd /home/pudding/
java  -XX:MinRAMPercentage=60.0 -XX:MaxRAMPercentage=90.0 -XX:+HeapDumpOnOutOfMemoryError -jar /home/pudding/server.jar