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

// Handle user create on GET
exports.create = (req, res) => {
  // TODO
}

// Handle user login on GET
exports.login = (req, res) => {
  // TODO
}

// Display user information on GET
exports.get = (req, res) => {
  res.send(json);
}