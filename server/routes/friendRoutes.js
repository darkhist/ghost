'use strict';

// This file contains info about different /friend routes

const express = require('express');
const router = express.Router();
const friendController = require("../controllers/friendController");

// GET /friends
router.get('/', friendController.get);

module.exports = router;