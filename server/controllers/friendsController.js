'use strict';

// This file defines request handling behaviors for the /friends route

const request = require('request');
const friendsModel = require('../models/friendsModel');

// Handle GET /friends
exports.get = async (req, res) => {
  const results = await friendsModel.search();
  res.send(results);
};
