'use strict';

// This file defines request handling behaviors for the /users route

const request = require('request');
const usersModel = require('../models/usersModel');

// Handle GET /user
exports.get = async (req, res) => {
  const results = await usersModel.main();
  res.send(results);
}

// Handle POST /user/login
exports.authenticate = (req, res) => {
  let user = {
    email: req.body.email,
    password: req.body.password
  };

  console.log(user.email);
  console.log(user.password);

  // res.send("Success");
  // TODO: Hash Password
  // TODO: Call function to check if email and password match existing info in USERS table

}