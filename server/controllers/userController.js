'use strict';

// This file defines request handling behaviors for the /users route

const request = require('request');

let json = `{
            "userID": "1",
            "username": "qmsalas",
            "password": "fakepswd",
            "email": "qmsalas@iastate.edu",
            "creationDate": "2018-02-05"
            }`;

// Handle GET /users
exports.get = (req, res) => {
  res.send(json);
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