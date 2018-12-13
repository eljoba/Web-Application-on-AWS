#CLOUDFORMATIONSTACK -- stack name
export VPC_ID=$(aws ec2 describe-vpcs --query "Vpcs[0].VpcId" --output text)
export SUBNET_ID=$(aws ec2 describe-subnets --filters "Name=vpc-id, Values=$VPC_ID" --query "Subnets[0].SubnetId" --output text)
export SUBNET_ID_2=$(aws ec2 describe-subnets --filters "Name=vpc-id, Values=$VPC_ID" --query "Subnets[1].SubnetId" --output text)
export z_id=$(aws route53 list-hosted-zones --query 'HostedZones[0].Id' --output text)
z_id=${z_id#*e/}
export NAME=$(aws route53 list-hosted-zones --query "HostedZones[0].Name" --output text)
export GROUP_NAME=csye6225-webapp
export RDS_GROUP_NAME=csye6225-rds
export ALLOCATED_STORAGE=10
export DB_INSTANCE_CLASS=db.t2.medium
export ENGINE=MySQL
export ENGINE_VERSION=5.6.35
export DB_INSTANCE_IDENTIFIER=csye6225-fall2017
export DB_USER=csye6225master
export DB_PASSWORD=csye6225password
export DB_NAME=csye6225
export PRIMARY_KEYNAME=id
export TABLE_NAME=csye6225
export S3_BUCKET_NAME=$NAME"csye6225.com"
export CODE_DEPLOY_EC2_SERVICE_ROLE=CodeDeployEC2ServiceRole
export CODE_DEPLOY_ROLE=CodeDeployServiceRole
export TRAVIS_UPLOAD_S3=Travis-Upload-To-S3
export CODE_DEPLOY_EC2_S3=CodeDeploy-EC2-S3
export TRAVIS_CODE_DEPLOY=Travis-Code-Deploy
export SNS_TOPIC=csye6225-Topic
export LAMBDA_EXECUTION_ROLE=Lambda-sns-Exec-Role
export CLOUD_WATCH_POLICY=cloud-watch-policy
aws cloudformation create-stack --stack-name $1 --template-body file://ec2.json --capabilities CAPABILITY_NAMED_IAM --parameters ParameterKey=ImageId,ParameterValue=ami-cd0f5cb6 ParameterKey=VpcId,ParameterValue=$VPC_ID ParameterKey=SubnetId,ParameterValue=$SUBNET_ID ParameterKey=HostedZoneId,ParameterValue=$z_id ParameterKey=InstanceType,ParameterValue=t2.micro ParameterKey=KeyName,ParameterValue=csye6225-aws ParameterKey=GroupName,ParameterValue=$GROUP_NAME ParameterKey=Name,ParameterValue=$NAME ParameterKey=RDSGroupName,ParameterValue=$RDS_GROUP_NAME ParameterKey=AllocatedStorage,ParameterValue=$ALLOCATED_STORAGE ParameterKey=DBInstanceClass,ParameterValue=$DB_INSTANCE_CLASS ParameterKey=Engine,ParameterValue=$ENGINE ParameterKey=EngineVersion,ParameterValue=$ENGINE_VERSION ParameterKey=DBInstanceIdentifier,ParameterValue=$DB_INSTANCE_IDENTIFIER ParameterKey=DBUser,ParameterValue=$DB_USER ParameterKey=DBPassword,ParameterValue=$DB_PASSWORD ParameterKey=DBName,ParameterValue=$DB_NAME ParameterKey=SubnetId2,ParameterValue=$SUBNET_ID_2 ParameterKey=PrimaryKeyName,ParameterValue=$PRIMARY_KEYNAME ParameterKey=TableName,ParameterValue=$TABLE_NAME ParameterKey=S3BucketName,ParameterValue=$S3_BUCKET_NAME ParameterKey=CodeDepEc2ServiceRole,ParameterValue=$CODE_DEPLOY_EC2_SERVICE_ROLE ParameterKey=CodeDepRole,ParameterValue=$CODE_DEPLOY_ROLE ParameterKey=TravisS3,ParameterValue=$TRAVIS_UPLOAD_S3 ParameterKey=CodeDepEC2S3,ParameterValue=$CODE_DEPLOY_EC2_S3 ParameterKey=TravisCodeDep,ParameterValue=$TRAVIS_CODE_DEPLOY ParameterKey=SnsTopic,ParameterValue=$SNS_TOPIC ParameterKey=LamdaSnsRole,ParameterValue=$LAMBDA_EXECUTION_ROLE ParameterKey=CloudwatchPolicy,ParameterValue=$CLOUD_WATCH_POLICY
