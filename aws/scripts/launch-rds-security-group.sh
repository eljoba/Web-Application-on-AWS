#create security group rds

SECURITY_GROUP_NAME="csye6225-rds"

aws rds create-db-security-group --db-security-group-name $SECURITY_GROUP_NAME --db-security-group-description "rds security group":wq

