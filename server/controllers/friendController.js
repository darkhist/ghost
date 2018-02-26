'use strict';

// This file defines request handling behaviors for the /friend route

const request = require('request');
const friendModel = require('../models/friendModel');

// Handle GET /friend
exports.get = async (req, res) => {
  const results = await friendModel.main();
  res.send(results);
}