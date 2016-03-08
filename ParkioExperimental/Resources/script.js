//Parkio 2016

var defaultPos = { //Default position (in New York test area)
      lat: 40.7127,
      lng: -74.0059
    };

//Initializes the map
function initMap() {
  getParkingSpots("",function(data, auxData){       //Get parking data
    var parkingSpaces=JSON.parse(data)["spots"];//Parse JSON data

    pos = auxData[0];
    infoMessage = auxData[1];

    var map = new google.maps.Map(document.getElementById('map'), { //Make a new map
      zoom: 15,                                 //Set zoom level
      mapTypeId: google.maps.MapTypeId.ROADMAP, //Set type to roadmap
      fullscreenControl: false                  //Disable full screen
    });

    
    var infoWindow = new google.maps.InfoWindow({map: map}); //Make a new info window
    infoWindow.setPosition(pos);        //Set position of info window
    infoWindow.setContent(infoMessage); //Set the text content of the info window

    map.setCenter(pos);   //Center the map on the user (or default position)

    //Now display all parking spaces
    displayParkingSpaces(map, parkingSpaces);
  });
}

//Gets parking spots near the user
function getParkingSpots(url, callback){ //URL is the server's url, dallback is the function the data will be passed into
  // Try HTML5 geolocation.
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) { //Ask for user's location
      var pos = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };

      sendToServer("GET", url, [["Location",pos.lat+","+pos.lng]], callback, [pos,"You are here."]); //Send position to the server
    },
    function(error){ //If permission is denied...
      //Send default position.
      sendToServer("GET", url, [["Location",pos.lat+","+pos.lng]], callback, [defaultPos,"Enable geolocation to see your location."]);
    });
  }else{ //If geolocation is not supported...
    //Send default position.
    sendToServer("GET", url, [["Location",pos.lat+","+pos.lng]], callback, [defaultPos,"Browser does not support geolocation."]);
  }
}

//Sends a HTTP request to the server
function sendToServer(method, url, headers, callback, auxData){ //auxData is not used, simply passed to callback
      var xmlHttp = new XMLHttpRequest();  //New request object

      xmlHttp.onreadystatechange = function() {  //When server responds...
          if (xmlHttp.readyState == 4 && xmlHttp.status == 200) //If response is ready and OK
              callback(xmlHttp.responseText, auxData); //Pass the response to the callback, along with the auxillary data
      }

      xmlHttp.open(method, url, true); // true for asynchronous calls
      for (var i=0; i<headers.length; i++){ //Iterate across headers
        xmlHttp.setRequestHeader(headers[i][0], headers[i][1]); //Add the header
      }
      xmlHttp.send(); //Send the request
}

//Display coordinates on the map with custom markers
function displayParkingSpaces(map, parkingSpaces){
  var imageURL = 'http://s22.postimg.org/9ta46k47h/icon.png'; //Marker icon URL

  for (var i=0; i<parkingSpaces.length; i++){ //Iterate across parking spaces
    var Latlng_0 = new google.maps.LatLng(parseFloat(parkingSpaces[i]["lat"]),parseFloat(parkingSpaces[i]["lng"])); //Parse Lat and Lng
    var marker_0 = new google.maps.Marker( //Make a new marker
      {
          position: Latlng_0,       //Set position
          title:"Parking Space",    //Set title (not used)
          icon: imageURL           //Set the custom appearance 
      }
    );

    marker_0.setMap(map); //Add it to the map
  }
}


//Handles Google Maps errors
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
  infoWindow.setPosition(pos);
  infoWindow.setContent(browserHasGeolocation ?
                        'Error: The Geolocation service failed.' :
                        'Error: Your browser doesn\'t support geolocation.');
}
