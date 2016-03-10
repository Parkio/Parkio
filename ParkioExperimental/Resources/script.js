//Parkio 2016
var markers = [];
var userPosition;

var defaultPos = { //Default position (in New York test area)
      lat: 40.7211671197085,
      lng: -73.98785478029151
    };

//Initializes the map
function initMap() {
  getLocation(function(infoMessage){ //Get user's location
    var map = new google.maps.Map(document.getElementById('map'), { //Make a new map
      zoom: 15,                                 //Set zoom level
      mapTypeId: google.maps.MapTypeId.ROADMAP, //Set type to roadmap
      fullscreenControl: false                  //Disable full screen
    });

    var infoWindow = new google.maps.InfoWindow({map: map}); //Make a new info window
    infoWindow.setPosition(pos);        //Set position of info window
    infoWindow.setContent(infoMessage); //Set the text content of the info window

    map.setCenter(userPosition);   //Center the map on the user (or default position)

    getParkingSpots(userPosition, function(data){       //Get parking data
      var parkingSpaces=JSON.parse(data)["spots"];//Parse JSON data

      //Now display all parking spaces
      clearMarkers();
      buildMarkers(parkingSpaces);
      displayMarkers(map);

      //Now add drag listeners.
      map.addListener('dragend', function() { //When the center of the map is changed...
        var center = map.getCenter(); //Get the center of the map
        var pos = {
          lat: center.lat(), 
          lng: center.lng()
        };

        getParkingSpots(pos, function(data){       //Get parking data
          var parkingSpaces = JSON.parse(data)["spots"];  //Parse JSON data
          clearMarkers();
          buildMarkers(parkingSpaces); //Now display all parking spaces
          displayMarkers(map);
        });

      });
   });
  });
}

//Gets parking spots near the user
function getParkingSpots(pos, callback){ //pos is the center position, callback is the function the data will be passed into
  sendToServer("GET", "", [["Location",pos.lat+","+pos.lng]], callback); //Send position to the server
}

function getLocation(callback){
  // Try HTML5 geolocation.
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) { //Ask for user's location
      pos = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };
      userPosition = pos;
      callback("You are here.");
    },
      function(error){ //If permission is denied...
      userPosition = defaultPos;
      callback("Enable geolocation for a better experience.");
    }
    );
  }else{ //If geolocation is not supported...
    userPosition = defaultPos;
    callback(defaultPos,"Geolocation is not supported on your browser.");
  }
}

//Sends a HTTP request to the server
function sendToServer(method, url, headers, callback){
      var xmlHttp = new XMLHttpRequest();  //New request object

      xmlHttp.onreadystatechange = function() {  //When server responds...
          if (xmlHttp.readyState == 4 && xmlHttp.status == 200){ //If response is ready and OK
              callback(xmlHttp.responseText); //Pass the response to the callback, along with the auxillary data
          }
      }

      xmlHttp.open(method, url, true); // true for asynchronous calls
      for (var i=0; i<headers.length; i++){ //Iterate across headers
        xmlHttp.setRequestHeader(headers[i][0], headers[i][1]); //Add the header
      }
      xmlHttp.send(); //Send the request
}

//Convert parkingSpaces to markers (doesn't display)
function buildMarkers(parkingSpaces){
  var imageURL = 'http://s22.postimg.org/9ta46k47h/icon.png'; //Marker icon URL

  var image = {
    url : imageURL,
    scaledSize: new google.maps.Size(20, 20)
  }

  //Build markers
  for (var i=0; i<parkingSpaces.length; i++){ //Iterate across parking spaces
    var markerLatLng = new google.maps.LatLng(parseFloat(parkingSpaces[i]["lat"]),parseFloat(parkingSpaces[i]["lng"])); //Parse Lat and Lng
    var newMarker= new google.maps.Marker( //Make a new marker
      {
          position: markerLatLng,       //Set position
          title:"Parking Space",    //Set title (not used)
          icon: image           //Set the custom appearance 
      }
    );
    markers.push(newMarker);
  }
}

//Displays markers
function displayMarkers(map){
  for (var i=0; i<markers.length; i++){ //Iterate across parking spaces
    markers[i].setMap(map); //Add it to the map
  }
}

//Deletes all markers
function clearMarkers(){
  displayMarkers(null); //Move markers to 'null map'
  markers = []; //Clear their references
}


//Handles Google Maps errors
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
  infoWindow.setPosition(pos);
  infoWindow.setContent(browserHasGeolocation ?
                        'Error: The Geolocation service failed.' :
                        'Error: Your browser doesn\'t support geolocation.');
}
