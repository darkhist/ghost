'use strict';

// This file defines request handling behaviors for the /user route

const request = require('request');
const userModel = require('../models/userModel');

// Handle GET /user
exports.get = async (req, res) => {
  const results = await userModel.main();
  res.send(results);
}

// Handle POST /user/login
exports.authenticate = (req, res) => {
  let user = {
    email: req.email,
    passowrd: req.passowrd
  };
  console.log(user.username);
  console.log(user.email);

  // TODO: Call function to check if email and password match existing info in USERS table
}