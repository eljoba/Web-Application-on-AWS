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

#security group creation

aws ec2 create-security-group --group-name csye6225-fall2017-webapp --description "csye assignment 4" --vpc-id $VPC_ID

export SECU=$(aws ec2 describe-security-groups --group-name csye6225-fall2017-webapp --query "SecurityGroups[0].GroupId" --output text)
echo $SECU


#for port ssh 22
aws ec2 authorize-security-group-ingress --group-id $SECU --protocol tcp --port 22 --cidr 0.0.0.0/0

#for port http 80
aws ec2 authorize-security-group-ingress --group-id $SECU --protocol tcp --port 80 --source-group sg-529cfa21

#for https 443
aws ec2 authorize-security-group-ingress --group-id $SECU --protocol tcp --port 443 --source-group sg-529cfa21

