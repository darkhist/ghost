'use strict';

// This file defines request handling behaviors for the /user route

const request = require('request');
const userModel = require('../models/userModel');

// Handle GET /user
exports.get = async (req, res) => {
  const results = await userModel.main();
  res.send(results);
}

// Handle GET /users/create
exports.create = (req, res) => {
  // Insert a user into the database
  // TODO
}

// Handle GET /users/search
exports.search = (req, res) => {
  // Search for a specific user in the database
}

// Handle GET /users/login
exports.login = (req, res) => {
  // TODO
}