# 🌊 Flood Evacuation Routing System

Welcome to this project! This repository contains a simulation and routing system designed to manage and navigate through flood scenarios. It models how a flood spreads over a geographical area and calculates the safest and fastest evacuation routes, actively avoiding inundated zones.

## 🎯 What does this project do?
In the event of a flood, time and safety are critical. This system helps by:
- **Modeling the environment:** It recreates a real-world map with locations, elevations, and roads.
- **Simulating water spread:** It predicts how a flood propagates over time, taking altitude slopes and distances into account.
- **Finding the best routes:** It calculates the shortest paths to safety while dynamically avoiding areas that are (or will be) flooded.

## 🧠 How it works: Dijkstra's Algorithm
To find the best evacuation routes, this project relies on a famous computer science concept called **Dijkstra's Algorithm**. 

Imagine you are in a maze of streets, and you want to reach a safe zone. Instead of guessing, Dijkstra's algorithm systematically explores all possible paths starting from your current position. It keeps track of the shortest time or distance to reach every intersection. 

When it encounters a roadblock (like a flooded street), it ignores that path and focuses on the next fastest option. By continually expanding outwards along the quickest routes, it guarantees finding the absolute fastest path to your destination!

Here is how this concept is brought to life using three simple building blocks (Java classes) in the code:

### 📍 `Localisation` (The Intersections)
This represents a specific point on the map, such as a crossroads or a building. Each point holds vital information:
- A unique identifier and a name.
- Geographic coordinates (latitude and longitude).
- **Altitude**, which is crucial to calculate if the point is at risk of being underwater!

### 🛣️ `Arc` (The Roads)
An `Arc` acts as a connection between two `Localisation` points. It represents a physical road or street. It holds information about:
- Where the road starts and where it ends.
- The **distance** to travel between the two points.
- The name of the street.

### 🗺️ `Graph` (The Map & The Brain)
This is the core of the project. The `Graph` links all the `Localisation` points together using `Arc` roads to build a complete virtual map. 
It also acts as the "brain" that runs the algorithms. It reads map data from files and performs complex tasks like:
- **Determining flooded zones:** Finding which areas are underwater based on elevation.
- **Calculating flood timelines:** Predicting *when* a specific street will be flooded.
- **Finding evacuation routes:** Running Dijkstra's algorithm to guide you to safety as quickly as possible, ensuring your route doesn't cross paths with the incoming water.

---
*This project is a practical application of graph theory to solve real-world emergency management situations.*
