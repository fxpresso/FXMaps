<p align="center">
<img src="http://metaware.us/images/fx-maps.png" alt="FXMaps">
</p>

[![htm.java awesomeness](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](http://cogmission.ai)

**NOTE: Minimum JavaSE version is 1.8**

***
#### Originally Developed for Use with [Nostromo](https://github.com/cogmission/Nostromo), a [Numenta HTM Challenge entry.](http://numenta.com/blog/introducing-the-numenta-htm-challenge.html)
***

A new JavaFX Mapping API which allows you to embed Google Maps in your JavaFX application and combines two API's (GMapsFX and google-maps-services-java) to allow one to easily use functionality missing in both, such as:

* Draw and plot routes and objects on your google map using JavaFx
* Use Google APIs such as Directions API and GeoCoding API - which the GMapsFX API doesn't offer! 
* The above allows you to add routes to a map either by hand or by file, and automatically SNAP routes to the nearest road or highway - when desired.
* Open source so you can add features too!

***

# Usage

Right now it is a project meant for import into an IDE. There is a reference implementation that I will be working on to show the potential of this library. The class is: 

**[ai.cogmission.fxmaps.RefImpl](https://github.com/fxpresso/FXMaps/blob/master/src/main/java/ai/cogmission/fxmaps/demo/RefImpl.java)**

I will keep its status up to date until I reach the point where I can start work on the project that uses this library (see [Nostromo](https://github.com/cogmission/Nostromo))

***
**(2015-10-18)**
* Added ability to set the style on the flyout background
* Added ability to right click on route lines and delete them
* Added ability to right click on waypoints and delete them and have
  the adjoining waypoints and lines adjust correctly
* Added the ability for the Markers to be reset to the last letter in 
  the route so that continuations of a route by adding waypoints will 
  result in markers with succeeding letters (correct letters)
* Removed the "Add" button, routes now can be displayed or hidden by 
  selecting or deselecting the route CheckComboBox items

**(2015-10-15)**
* Added ability to display all or subset of routes
* Added CheckedComboBox in route chooser
* Added ability to add new Routes to selection 
![FXMaps Demo](http://metaware.us/images/screen.png)

**(2015-10-12)**
* Includes use of new Flyout 
* ...above used for new Map & Route editors
* Border decoration for Route editor mode
* New Workflows added for new Maps; and saved map loading...
![FXMaps Demo](http://metaware.us/images/refimpl.png)

**(2015-10-4)**
* Now load GPX Files!
* New GPX object model for loading GPX files
![FXMaps Demo](http://metaware.us/FXMap2.png)

**(2015-10-2)**
* Multiple routes now save and load correctly
* Can save a load multiple maps from the combo list

**(2015-09-26)**
* Added route lines to waypoints! (still needs work)
* Added MapOptions
* Added basic MapShape capability (getting there...)

**(2015-09-25)**
* Implemented route persistence, added RouteStore
* Added RouteStoreTest

**(2015-09-23)**
* Running the above class loads a map, an empty directions pane (to the right) and will place a marker any place the user clicks. It will automatically locate the user and print out their location to standard out (it doesn't yet center the map there, which will be one of the next things I do).
* Prints out lat/lon of click to standard out
* Prints out lat/lon to/from pixel conversion

### Stay Tuned For More as this gets flushed out! :-)

