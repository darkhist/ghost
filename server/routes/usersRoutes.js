'use strict';

// This file contains info about different /users routes

const express = require('express');
const router = express.Router();
const usersController = require('../controllers/usersController');

// GET /users
router.get('/', usersController.get);

// POST /users/login
router.post('/login', usersController.authenticate);

// POST /users/signup
router.post('/signup', usersController.signup);

module.exports = router;
