'use strict';

//module we will use to read incoming requests' HTTP action
//Will be one of the following: GET, POST, PUT or DELETE, and Headers
//will also create server for us
//#var http = require('http');
//a callback function that takes two arguments
//(req for request and res for response)
//Note that response is an object that can be used
//to reply back to the request
//#createServer()
//


const bodyParser = require('body-parser');
const express = require('express');
const passport = require('passport');
const routes = require("./routes/routes.js")
const port = 3001;
const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));
app.use('/users', routes);

app.get('/', (req, res) => {
  res.send("Hi Quinn!");
});

app.listen(port, () => {
  console.log(`Server started on port http://localhost:3306/`);
});