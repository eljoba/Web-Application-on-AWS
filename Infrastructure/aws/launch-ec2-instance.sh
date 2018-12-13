#!/bin/bash
#
#security group creation and launch ec2 instance script
#
clear

export VPC_ID=$(aws ec2 describe-vpcs --query "Vpcs[0].VpcId" --output text)
#echo $VPC_ID

#volume id
export VOLUME_ID=$(aws ec2 describe-volumes --query "Volumes[0].VolumeId" --output text)
#subnet id
export SUBNET_ID=$(aws ec2 describe-subnets --filters "Name=vpc-id, Values=$VPC_ID" --query "Subnets[0].SubnetId" --output text)

HOSTED_ZONE_ID=$(aws route53 list-hosted-zones --query HostedZones[0].Id --output text)
ZONE_ID=${HOSTED_ZONE_ID:12}
echo $ZONE_ID

export SECU=$(aws ec2 describe-security-groups --group-name csye6225-fall2017-webapp --query "SecurityGroups[0].GroupId" --output text)

export SE=$(aws ec2 describe-instances --query "Reservations[0].Instances[0].InstanceId" --output text)


#for launch insatnce

aws_variable=ami-cd0f5cb6
aws ec2 run-instances --image-id $aws_variable --count 1 --instance-type t2.micro --key-name csye6225-aws --security-group-ids $SECU --subnet-id $SUBNET_ID

#modify volume size

aws ec2 modify-volume --region us-east-1 --volume-id $VOLUME_ID --size 16

#route53 domain change

aws route53 change-resource-record-sets --hosted-zone-id $ZONE_ID --change-batch file://record.json


