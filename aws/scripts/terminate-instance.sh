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


export SECU=$(aws ec2 describe-security-groups --group-name csye6225-fall2017-webapp --query "SecurityGroups[0].GroupId" --output text)
echo $SECU


export SE=$(aws ec2 describe-instances --query "Reservations[0].Instances[0].InstanceId" --output text)


#terminate instance
aws ec2 terminate-instances --instance-ids $SE








