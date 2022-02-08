# Onye backend test
This is a test for new candidates joining Onye. It is meant for developers that will be working on the backend of the Onye platform.

Read this entire readme before you start coding.  

The repository contains a small Java API using the Jooby web framework. The code in the application is functional but the structure is...ehhhm...not the best. It will be up to you to complete a few tasks testing your general Java skills (ability to write logic and write scalable code) and then to fix the problems in the application that is already present.
All the basic structure is setup for you already, such as the exact method where you should return your results.

The application will be running on port 8080 hence you can access it on http://localhost:8080/ when running it on your machine.

The test should not take you more than 2-3 hours to complete.

### How to perform the test
Checkout this repository to your local machine and complete the tasks described below. When you are done create a pull request for us to review.

### How you will be judged
We will be primarily looking at code quality and structure. Do not focus solely on providing solutions, focus more on scalability. Will your code be extendable and scalable?

It is better to not complete all scenarios but have good code structure and quality than to complete all the scenarios and have a "bad" solution.

# Test tasks
The test consists of two tasks. The first task consists of one base scenario and a few extensions. Complete as many as you can.

### Task 1
The parts must be solved in order - one at a time

The solutions for the scenarios should be returned in the FruitController class.

#### Background scenario information
John and Charlene drive from Las Vegas to Los Angeles. Along the way they pass several fruit stands.
Each fruit stand sells baskets of cherries at a price, and baskets with peaches at a different price.
John wants to buy a basket of cherries, and Charlene wants to buy a basket of peaches. They want to stop at only one fruit stand.
What fruit stand should they stop at to minimize the total cost?
The program should return the number of the first fruit stand that has the lowest possible total price for a basket of cherries and a basket of peaches.

You yourself decide the test data such as the number and order of the stands and set the price of the fruits in the stands.

#### Scenario 1 - modification:
There has been a new fruit - pear - among the fruit stands. John and Charlene love pears. The fruit basket they are after must consist of a pear and one (1) other fruit (cherry or peach). Return the number of the first stand and what the price was.
#### Scenario 2 - modification:
But what fruits did we buy?
Return stands, prices and what fruits were purchased.
#### Scenario 3 - modification:
Now it turns out that not all fruit stands have all fruits.
Take that into account.
Return stands, price, what fruits were purchased and by how many stands the selection was made.
#### Scenario 4 - modification:
Now their friend is going the same way. Find out which stand the friend should buy from.
Note. After John and Charlene bought their fruit, that fruit is not available in that stand (it is sold out).
Return the stand, price and what fruits the friend buys and how large the selection was.

### Task 2
Try to improve the structure of the code already present in the application before you started working on it.

# Prerequisites
- Java 11
- Docker (optional)

# Running the application

### building

    ./gradlew build

### running

    ./gradlew joobyRun

### docker

    ./gradlew build
    docker build . -t onye-backend-test
    docker run -p 8080:8080 -it onye-backend-test

### docker-compose

    ./gradlew build
    docker build . -t onye-backend-test
    docker-compose up

# Resources
- https://jooby.io // Jooby documentation
- https://jooby.io/modules/hibernate/ // Jooby hibernate module documentation
