#!/bin/bash
kill $(cat ./pid.file) || echo kill-failed
