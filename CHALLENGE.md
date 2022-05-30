# Challenge

Given a dataset with the following columns (provided in the .zip we sent)

* device_id
* lat
* lng
* timestamp (unix timestamp)

that records the advertisement views (in the advertisement slang, they are called "impressions") coming from mobile devices, build a project, consisting of an API service, that answers the following questions:

* How many impressions are coming from each device?
* How many impressions for each hour of the day?
* How many impressions for each day of the week?
* How many impressions for each day of the month?

We expect the candidate to complete the challenge in 3 hours.

# Requirements

You should send your project as a .zip file containing everything needed to run your code, including a `README.md` file and a script that runs the application. 
The project should also contain a git repo, remember to run `git init` at the beginning and progressively commit your changes.

Remember: 

* In this case you don't need to use a DB for queries
* You don't need to use third party APIs to complete the assignment, even the bonus points you see below


## API

Use a language of your choice. We would gladly prefer: Java, Kotlin.
Use a framework of you choice. We would gladly prefer: Spring boot, Quarkus.

# Evaluation insights

We value a lot the following things:

* Asynchronous patterns
* Error handling
* Code design
* Testing

# Bonus points

* Using docker


