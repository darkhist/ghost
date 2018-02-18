'use strict';

// This file contains info about different /users routes

const express = require('express');
const router = express.Router();
const userController = require("./../controllers/userController");

// GET /users
router.get('/', userController.get);

module.exports = router;