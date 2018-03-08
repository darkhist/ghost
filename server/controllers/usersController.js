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

// Handle POST /users/signup
exports.signup = (req, res) => {
  let user = {
    name: req.body.name,
    username: req.body.username,
    password: req.body.password,
    email: req.body.email
  };
  console.log(user);
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
