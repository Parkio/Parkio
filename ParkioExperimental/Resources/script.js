var map;
var markers = [];
var parkingSpaces;

function getParkingSpots(theUrl, callback){
  // Try HTML5 geolocation.
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };

      var xmlHttp = new XMLHttpRequest();
      xmlHttp.onreadystatechange = function() { 
          if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
              callback(xmlHttp.responseText);
      }
      xmlHttp.open("GET", theUrl, true); // true for asynchronous 
      xmlHttp.setRequestHeader("Location", pos.lat+","+pos.lng);
      xmlHttp.send(null);

    });
  }
}

function initMap() {
  getParkingSpots("",function(data){ //Get parking data
    parkingSpaces=JSON.parse(data)["spots"];//Parse data

    map = new google.maps.Map(document.getElementById('map'), { //Make a new map
      center: {lat: -34.397, lng: 150.644},
      zoom: 6,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });
    var infoWindow = new google.maps.InfoWindow({map: map}); //Make a new infor window

    // Try HTML5 geolocation.
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position) {
        var pos = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };

        infoWindow.setPosition(pos);
        infoWindow.setContent('Location found.');
        map.setCenter(pos);
      }, function() {
        handleLocationError(true, infoWindow, map.getCenter());
      });
    } else {
      // Browser doesn't support Geolocation
      handleLocationError(false, infoWindow, map.getCenter());
    }

    var image = 'http://s22.postimg.org/9ta46k47h/icon.png';

    //Now display all parking spaces
    for (var i=0; i<parkingSpaces.length; i++){
      console.log(parkingSpaces[i]["lat"]+","+parkingSpaces[i]["lng"])
      var Latlng_0 = new google.maps.LatLng(parseFloat(parkingSpaces[i]["lat"]),parseFloat(parkingSpaces[i]["lng"]));
      var marker_0 = new google.maps.Marker( //Make a new marker
        {
            position: Latlng_0,
            title:"Parking Space",
            icon: image
        }
      );

      marker_0.setMap(map); //Add it to the map
    }
  });
}

//Handles Google Maps errors
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
  infoWindow.setPosition(pos);
  infoWindow.setContent(browserHasGeolocation ?
                        'Error: The Geolocation service failed.' :
                        'Error: Your browser doesn\'t support geolocation.');
}
