'use strict';

const bodyParser = require('body-parser');
const express = require('express');
const passport = require('passport');
const users = require("./routes/usersRoutes.js");
const friends = require("./routes/friendsRoutes.js");
const port = 3000;
const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));
app.use('/users', users);
app.use('/friends', friends)

app.get('/', (req, res) => {
  res.send("✨ Hello! ✨");
});

app.listen(port, () => {
  console.log(`Server started on port ${port}`);
});