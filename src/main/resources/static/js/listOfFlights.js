let formList = document.getElementById("values").getAttribute("current");


fetch("http://localhost:8000/flights/flight-list")

.then((response)=> response.json())
.then((body)=> console.log(body) )