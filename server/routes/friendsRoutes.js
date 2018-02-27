'use strict';

// This file contains info about different /friends routes

const express = require('express');
const router = express.Router();
const friendsController = require("../controllers/friendsController");

// GET /friends
router.get('/', friendsController.get);

module.exports = router;