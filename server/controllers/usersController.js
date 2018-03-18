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
    name: req.body.name.split(" "),
    username: req.body.username,
    email: req.body.email,
    password: req.body.password
  };

  let firstName = user.name[0];
  let lastName = user.name[1];

  // Hash user.password and add the user to the USERS table
  bcrypt.hash(user.password, 10, (err, hash) => {
    try {
      usersModel.add(firstName, lastName, user.username, hash, user.email);
      console.log("User Added Successfully");
      res.status(201).send("User Added Successfully");
    } catch (err) {
      res.status(401).send("Unable to Add User");
    }
  });
};

// Handle POST /users/login
exports.authenticate = (req, res) => {
  let user = {
    email: req.body.email,
    password: req.body.password
  };

  // Check to see if provided user info matches
  // existing user info in the USERS table
  if (usersModel.auth(user.email, user.password)) {
    console.log("Login Successful");
    res.status(200).send("Login Successful");
  } else {
    res.status(401).send("Login Failed");
  }
};
