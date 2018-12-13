#create rds instance

aws rds create-db-instance --db-instance-identifier csye6225-fall2017 \
--allocated-storage 20 --db-instance-class db.t2.medium --engine mysql \
--master-username csye6225master --master-user-password csye6225password


#delete db instance
# aws rds delete-db-instance --db-instance-identifier csye6225-fall2017 --skip-final-snapshot 
