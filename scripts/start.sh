#!/bin/bash
java -jar payments-0.0.1-SNAPSHOT.jar & echo $! > ./pid.file &
