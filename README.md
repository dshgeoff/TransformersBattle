# Transformers Battle
Aequilibrium - Technical Assignment

The Transformers are at war and you are in charge of settling the score! The application evaluates who wins a fight between the Autobots and the Decepticons.

# Overview
The application is designed based on MVP architecture, using a Singleton background networking thread with a Repository and Remote Datasource pattern. To support updates to the UI, the application implements Callback observer pattern.

**Assumptions:**
* Update Transformer API **PUT** request is currently returning status code 400 for all valid requests, for updating Transformers we will temporarily use **DELETE** and **CREATE** to mimic functionality, **note that the Transformer ID will be changed** 
* Delete Transformer API **DELETE** request does not return a full list of remaining Transformers as described in the API documentation, but this will not affect how we handle our logic
* API calls in quick succession is limited by the cloud service, therefore the application will need to add a short delay between HTTP requests in queue when necessary
* The application requires a connection to the internet in order to retrieve the list of transformers you create. **You will not be able to retrieve the same list again after deleting application data and cache**
* Battle rule applied between Transformers named Optimus Prime and Predaking must have names exactly as provided including spacing and capitalization
* Unit testing files are located in the **app/src/androidTest** directory
# Getting Started
1. Download the Git repository to your local machine
2. Sync Gradle, build project, and run
3. Thats it!

# Quick Usage Guide
* **SWIPE DOWN** in order to refresh the list of created transformers
* **PRESS** the **CREATE TRANSFORMER** button to start creating your transformer
* **FILL** in all properties on the create transformer page and **PRESS** the **CONFIRM CREATION** button in order to create your transformer
* **PRESS** the **START SIMULATION** button to apply the rules of battle to your list of transformers
* **SWIPE** the list item that you wish to remove to **DELETE** the transformer

# Battle Rules

* The teams should be sorted by rank and faced off one on one against each other in order to determine a victor, the loser is eliminated
* A battle between opponents uses the following rules:
  * If any fighter is down 4 or more points of courage and 3 or more points of strength compared to their opponent, the opponent automatically wins the face-off regardless of overall rating (opponent has ran away)
  * Otherwise, if one of the fighters is 3 or more points of skill above their opponent, they win the fight regardless of overall rating
  * The winner is the Transformer with the highest overall rating
* In the event of a tie, both Transformers are considered destroyed
* Any Transformers who don’t have a fight are skipped (i.e. if it’s a team of 2 vs. a team of 1, there’s only going to be one battle)
* The team who eliminated the largest number of the opposing team is the winner

**Special rules:**
* Any Transformer named Optimus Prime or Predaking wins his fight automatically regardless of any other criteria
* In the event either of the above face each other (or a duplicate of each other), the game immediately ends with all competitors destroyed
