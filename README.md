# AWS Explorer

Copyright (C) 2013 Bruno França dos Reis

Licensed under the Apache License, version 2.0 - http://www.apache.org/licenses/LICENSE-2.0

## Instructions

### Clone the repository

    git clone https://github.com/bfreis/awsexplorer.git

### Run locally

Replace the question marks `?` with your AWS credentials.

    cd awsexplorer
    mvn -Daws.accessKeyId=? -Daws.secretKey=? tomcat7:run

Then open http://localhost:8080 and have fun.

### Run on Elastic Beanstalk

First, build the `war` package:

    cd awsexplorer
    mvn clean package

It will be created inside the `awsexplorer/target` folder. Next, sign into the AWS
Management Console, create an Elastic Beanstalk application and upload the
`awsexplorer-x.y.war` file you've just built.

*Note*: to properly run the demos on Elastic Beanstalk, the *role* of the
instances running the demo must be allowed to run the commands. Be sure to
give the proper permissions to the role on IAM!
