#!/usr/bin/env bash

set -e
set -x

# decide which commands we're testing
function aggregate() {
  local command=$1

  grep ${command} results.csv | cut -d"," -f 2 | cut -d"\"" -f 2 | awk '{ sum += $1 } END { if (NR > 0) print sum / NR }'
}

redis_benchmark_commands=(
  "SET"
  "GET"
  "INCR"
  "LPUSH"
  "RPUSH"
  "LPOP"
  "RPOP"
  "SADD"
  "SPOP"
)

SCRIPT_DIR=$(
  cd $(dirname $0)
  pwd
)
GEODE_BASE=$(
  cd $SCRIPT_DIR/../../..
  pwd
)

cd $GEODE_BASE

./gradlew devBuild installD

GFSH=$PWD/geode-assembly/build/install/apache-geode/bin/gfsh

pkill -9 -f ServerLauncher || true
rm -rf server1
rm -rf locator1

$GFSH -e "start locator --name=locator1"

$GFSH -e "start server
          --name=server1
          --locators=localhost[10334]
          --server-port=0
          --redis-port=6379
          --redis-bind-address=127.0.0.1"

cd ${SCRIPT_DIR}

rm -f results.csv

X=0
while [[ ${X} -lt 25 ]]; do
  redis-benchmark -t set,get -q -n 100000 --csv >>results.csv

  ((X = X + 1))
done

CURRENT_SHA=$(git rev-parse --short HEAD)
AGGREGATE_FILE_NAME=${CURRENT_SHA}-aggregate.csv

echo "Command", "Average Requests Per Second" >${AGGREGATE_FILE_NAME}
for command in ${redis_benchmark_commands[@]}; do
  SUM_AGGREGATE=$(aggregate ${command})
  echo ${command}, ${SUM_AGGREGATE} >>${AGGREGATE_FILE_NAME}
done

$GFSH -e "connect" -e "shutdown --include-locators=true"
