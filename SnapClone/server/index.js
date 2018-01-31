'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const port = 8080;
const app = express();
app.use(bodyParser.json());

app.get('/', (req, res) => {
  res.send("Hi Quinn!");
});

app.listen(port, () => {
  console.log("Success!");
});