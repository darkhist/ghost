// This file defines request handling behaviors for users
const express = require('request');

let json = `{
            "userID": "1",
            "username": "qmsalas",
            "password": "fakepswd",
            "email": "qmsalas@iastate.edu",
            "creationDate": "2018-02-05"
            }`;

const create = (req, res) => {
  // TODO
}

const login = (req, res) => {
  // TODO
}

const get = (req, res) => {
  res.send(json);
}

module.exports.create = create;
module.exports.login = login;
module.exports.get = get;