'use strict';

// This file defines request handling behaviors for the /users route

const bcrypt = require('bcrypt');
const request = require('request');
const usersModel = require('../models/usersModel');

// Handle GET /users
exports.get = async (req, res) => {
  const results = await usersModel.search();
  res.send(results);
};

// Handle POST /users/signup
exports.signup = (req, res) => {
  let user = {
    name: req.body.name.split(' '),
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
      res.status(201).send('User Added Successfully');
      console.log('User Added Successfully');
    } catch (err) {
      res.status(401).send('Unable to Add User');
    }
  });
};

// Handle POST /users/login
exports.authenticate = (req, res) => {
  let user = {
    email: req.body.email,
    password: req.body.password
  };

  // Get user's hashed password from database
  // Compare given password to hash
  // If passwords match -- Send 200 OK
  // Else -- Send 401 Unauthorized
  usersModel.getPassword(user.email).then(data => {
    bcrypt.compare(user.password, data).then(status => {
      if (status) {
        res.status(200).send('Login Successful');
        console.log('Login Successful');
      } else {
        res.status(401).send('Unauthorized');
        console.log('Unathorized');
      }
    });
  });
};
