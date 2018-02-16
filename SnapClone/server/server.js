'use strict';

const bodyParser = require('body-parser');
const express = require('express');
const passport = require('passport');
const routes = require("./routes/routes.js");
const port = 3000;
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
  console.log(`Server started on port ${port}`);
});