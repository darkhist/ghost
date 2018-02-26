'use strict';

const bodyParser = require('body-parser');
const express = require('express');
const passport = require('passport');
const users = require("./routes/userRoutes.js");
const friends = require("./routes/friendRoutes.js");
const port = 3000;
const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));
app.use('/user', users);
app.use('/friend', friends);

app.get('/', (req, res) => {
  res.send("Hello! Welcome to the API!");
});

app.listen(port, () => {
  console.log(`Server started on port ${port}`);
});

module.exports = app;