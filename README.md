# Pathfinding Project with Dijkstra and Greedy Algorithms

This project is a Java-based web application developed using the Spring Boot framework and utilizes MySQL as the underlying database. The primary goal of this project is to find the optimal path among a list of drop points for a vehicle traveling at an assumed average speed of 35 km/hr. The project accomplishes this task by implementing two different algorithms: Dijkstra's algorithm and a Greedy algorithm. It then compares the results obtained from both algorithms based on the distance traveled and the time taken to cover that distance.

## Project Overview

### Features
- **Optimal Path Finding:** The project is capable of finding the optimal path for a vehicle to visit a list of drop points in the most efficient manner based on distance and time considerations.
- **Dijkstra's Algorithm:** It employs Dijkstra's algorithm, a well-known graph-based algorithm, to find the shortest path between points.
- **Greedy Algorithm:** The project also utilizes a Greedy algorithm to provide an alternative solution, allowing for a comparison between the two methods.
- **Average Speed Assumption:** The average speed of the vehicle is assumed to be 35 km/hr, which is used to calculate the time taken for each path.

### Technologies Used
- **Java:** The primary programming language for the project.
- **Spring Boot:** The framework used to develop the web application.
- **MySQL:** The database system for storing and managing data related to drop points and paths.
- **GitHub:** The version control platform for this project.

### Prerequisites
- To run the project, you will need Java Development Kit (JDK) installed on your system.
- MySQL should be installed and configured with the necessary database settings.
- An integrated development environment (IDE) like IntelliJ IDEA or Eclipse for Java development.
- Git should be installed to clone and work with the project repository.

## Getting Started

1. Clone the project repository to your local machine:
   ```
   git clone https://github.com/yourusername/findOptimalPath.git
   ```

2. Open the project in your preferred Java IDE.

3. Configure the MySQL database settings in the project's configuration files.

4. Run the Spring Boot application.

5. Access the web application through your browser at `http://localhost:8080` (or the specified port if you have configured it differently).

## Usage

1. Add drop points to the system, specifying their coordinates and other relevant information.

2. Initiate the pathfinding process using either the Dijkstra's algorithm or the Greedy algorithm.

3. View the results, including the optimal path, distance traveled, and time taken to cover the distance, for both algorithms.

4. Compare the results and make informed decisions based on your preferences or requirements.

## Data Model

The project's database is structured to store every location of small portion of kathmadu 

- the location table has the column as id,name,latitude and longitude

- the location is from the openstreetmap.org (which is open source location provieder platform)

## Contribute

If you would like to contribute to this project, please fork the repository and create a pull request with your changes. We welcome improvements, bug fixes, and feature enhancements.


## Contact

If you have any questions or need further information, feel free to contact me at `https://www.linkedin.com/in/ravi-yadav-404a63212/`.

Thank you for your interest in our Pathfinding Project! We hope this tool proves useful in optimizing routes for your needs.
