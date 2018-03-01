"use strict";

// This file defines request handling behaviors for the /users route

const bcrypt = require("bcrypt");
const request = require("request");
const usersModel = require("../models/usersModel");

// Handle GET /users
exports.get = async (req, res) => {
  const results = await usersModel.search();
  res.send(results);
};

// Handle GET /users/signup
exports.signup = (req, res) => {
  // TODO - Add functionality
  res.send("This is the /signup route!");
};

// Handle POST /users/login
exports.authenticate = (req, res) => {
  let user = {
    email: req.body.email,
    password: req.body.password
  };

  console.log(user.email);
  console.log(user.password);

  bcrypt.hash(user.password, (err, hash) => {
    // Store hash in DB
  });

  res.send("Success");
};
