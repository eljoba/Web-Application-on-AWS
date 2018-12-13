# Project Title

The main goal of this application is to enable users to log into the application and authenticate users by checking if they are already logged in or already registered users. <br/>
The user password is stored using BCrypt password hashing scheme with salt and stored in MySQL database. <br/>
Junit and JMeter is used to run test cases against the code.
<<<<<<< HEAD
Create a security group and launch EC2 instance.

Create a security group and launch EC2-instance.

# Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

# Prerequisites.

## Install the following:

### Intellij

Download Intellij idea
tar xfz ideaIC.tar.gz or ideaIU.tar.gz. <new_archive_folder>
sudo tar xf -*.tar.gz -C /opt/
####testin
### Gradle

wget https://services.gradle.org/distributions/gradle-3.4.1-bin.zip <br/>
sudo unzip -d /opt/gradle gradle-3.4.1-bin.zip

### MySQL-

sudo apt-get install mysql-server <br/>
sudo mysql_secure_installation

### JMeter

sudo apt-get install mysql-server <br/>
sudo mysql_secure_installation

# Running the tests

Performed unit test in code and load testing using JMeter. <br/>

## Load testing

1.	100 concurrent users creating new accounts in your application. See JMeter documentation for details on auto-generating values for form.
2.	100 concurrent authenticated user request home page of your application. You may use the users created in first step.

## And coding style tests

- Test if attributes of user bean is properly getting set.
- Test the Rest Api

## Running .sh files (script files) 
#ttt
- Test the creation of security group.
- Test if EC2 instance was launched and terminated.
- Test the creation and deletion of CloudFormation stack.

# Deployment

Merge code from assignment3 branch to master after completion of development.
GitHub release must be created from the master branch (Scrum master repository).
#tetetettyughjkmlh
## Steps to create a GitHub release:

- On GitHub, navigate to the main page of the repository.
- Under your repository name, click Releases.
- Click Draft a new release.
- Type a version number for your release. Versions are based on Git tags. We recommend naming tags that fit within semantic versioning.
- Select a branch that contains the project you want to release. Usually, you'll want to release against your master branch, unless       you're releasing beta software.
- Type a title and description that describes your release.
- If you'd like to include binary files along with your release, such as compiled programs, drag and drop or select files manually in     the binaries box.
- If the release is unstable, select This is a pre-release to notify users that it's not ready for production.
- If you're ready to publicize your release, click Publish release. Otherwise, click Save draft to work on it later.

# Built With

-	Spring Boot - The web framework used <br/>
-	Gradle - Dependency Management
- Script file
#testing app
# Contributing

-	Nisarg Patel  -  [https://github.com/eljoba/csye6225-fall2017-1]
- Payal Dodeja  -  [https://github.com/payaldodeja15/csye6225-fall2017]
- Ishita Babel  -  [https://github.com/ishita12/csye6225-fall2017]
- Elton Barbosa -  [https://github.com/eljoba/csye6225-fall2017-1]

# License

This project is licensed under the MIT License - see the LICENSE.md file for details.


