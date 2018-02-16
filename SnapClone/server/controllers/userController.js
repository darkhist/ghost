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

exports.create = (req, res) => {
  // TODO
}

exports.login = (req, res) => {
  // TODO
}

exports.get = (req, res) => {
  res.send(json);
}