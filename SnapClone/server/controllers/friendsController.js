'use strict';

// This file defines request handling behaviors for the /friends route

const request = require('request');
const friendsModel = require('../models/friendsModel');

exports.get = (req, res) => {
  // TODO: Make a database query instead
  res.send("Hello! Welcome to the /friends route!");
}