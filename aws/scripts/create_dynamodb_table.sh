#!/bin/bash

#dynamo db table

aws dynamodb create-table     --table-name csye6225     --attribute-definitions         AttributeName=id,AttributeType=S AttributeName=description,AttributeType=S   --key-schema AttributeName=id,KeyType=HASH AttributeName=description,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1

echo "table created"
