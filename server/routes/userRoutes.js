'use strict';

// This file contains info about different /user routes

const express = require('express');
const router = express.Router();
const userController = require("../controllers/userController");

// GET /user
router.get('/', userController.get);

module.exports = router;