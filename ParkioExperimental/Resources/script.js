//Parkio 2016
var markers = [];
var userPosition;

var defaultPos = { //Default position (in New York test area)
      lat: 40.7211671197085,
      lng: -73.98785478029151
    };

//Initializes the map
function initMap() {
  getLocation(function(){ //Get user's location
    var map = new google.maps.Map(document.getElementById('map'), { //Make a new map
      zoom: 17,                                 //Set zoom level
      mapTypeId: google.maps.MapTypeId.ROADMAP, //Set type to roadmap
      fullscreenControl: false,                  //Disable full screen
      center: defaultPos
    });

    getParkingSpots(userPosition, function(data){       //Get parking data
      var parkingSpaces=JSON.parse(data)["spots"];//Parse JSON data
      map.panTo(new google.maps.LatLng(parkingSpaces[0]["lat"],parkingSpaces[0]["lng"])); //Move to nearest parking spot

      //Now display all parking spaces
      clearMarkers();
      buildMarkers(parkingSpaces);
      displayMarkers(map);


      //Function to update markers
      function updateMarkers(){
        if (rateFreeze){
          return;
        }else{
          rateFreeze=true;
        }
        var center = map.getCenter(); //Get the center of the map
        var pos = {
          lat: center.lat(), 
          lng: center.lng()
        };
        var zoom = map.getZoom();
        if (zoom >= 17) { //No markers until zoom 17
          getParkingSpots(pos, function(data){       //Get parking data
            var parkingSpaces = JSON.parse(data)["spots"];  //Parse JSON data
            clearMarkers();
            buildMarkers(parkingSpaces); //Now display all parking spaces
            displayMarkers(map);
          });
        }else{
          clearMarkers();
        }
      }

      //Now add movement listeners.
      map.addListener('center_changed', function() { //When the center of the map is changed...
        updateMarkers();
      });
      map.addListener('dragend', function(){
        updateMarkers();
      });
      map.addListener('zoom_changed', function() { //When the center of the map is changed...
        updateMarkers();
      });

      $(document).mouseup(function(){
        updateMarkers();
      })

      //Initial update
      updateMarkers();
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
      userPosition = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };
      callback();
    },
      function(error){ //If permission is denied...
      userPosition = defaultPos;
      callback();
    }
    );
  }else{ //If geolocation is not supported...
    userPosition = defaultPos;
    callback();
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


//Lookup table for image urls
var imageLookup = {
  "basic" : function(){return ;},
}

function getParkingSpaceImage(parkingSpace){
  return {
    url : "http://s22.postimg.org/9ta46k47h/icon.png",
    scaledSize: new google.maps.Size(20, 20)
  }
}

//Convert parkingSpaces to markers (doesn't display)
function buildMarkers(parkingSpaces){
  //Build markers
  for (var i=0; i<parkingSpaces.length; i++){ //Iterate across parking spaces
    var markerLatLng = new google.maps.LatLng(parseFloat(parkingSpaces[i]["lat"]),parseFloat(parkingSpaces[i]["lng"])); //Parse Lat and Lng
    var image = getParkingSpaceImage(parkingSpaces[i]);

    var newMarker= new google.maps.Marker( //Make a new marker
      {
          position: markerLatLng,       //Set position
          title:"Parking Space",    //Set title (not used)
          icon: image           //Set the custom appearance 
      }
    );
    markers.push(newMarker);

    markers.push(new google.maps.Marker({
        position: userPosition,
        title: "You are here"
      }));
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
function handleLocationError(browserHasGeolocation, pos) {
  alert("Geolocation failed.")
}

var rateFreeze;
window.setInterval(function(){rateFreeze = false;}, 300); //Freeze rate at 300ms

