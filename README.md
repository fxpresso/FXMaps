# ![FXMaps Logo](http://metaware.us/images/fx-maps.png) FXMaps
**NOTE: Minimum JavaSE version is 1.8**

A new JavaFX Mapping API which allows you to embed Google Maps in your JavaFX application and combines two API's (GMapsFX and google-maps-services-java) to allow one to easily use functionality missing in both, such as:

* Draw and plot routes and objects on your google map using JavaFx
* Use Google APIs such as Directions API and GeoCoding API - which the GMapsFX API doesn't offer! 
* The above allows you to add routes to a map either by hand or by file, and automatically SNAP routes to the nearest road or highway - when desired.
* Open source so you can add features too!

***

# Usage

Right now it is a project meant for import into an IDE. There is a reference implementation that I will be working on to show the potential of this library. The class is: 

ai.cogmission.fxmaps.RefImpl

I will keep its status up to date until I reach the point where I can start work on the project that uses this library (see [Nostromo](https://github.com/cogmission/Nostromo))

***
**(2015-09-23)**
* Running the above class loads a map, an empty directions pane (to the right) and will place a marker any place the user clicks. It will automatically locate the user and print out their location to standard out (it doesn't yet center the map there, which will be one of the next things I do).
* Prints out lat/lon of click to standard out
* Prints out lat/lon to/from pixel conversion

**(2015-09-25)**
* Implemented route persistence, added RouteStore
* Added RouteStoreTest

### Stay Tuned For More as this gets flushed out! :-)

