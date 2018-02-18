'use strict';

const config = require('.././config.json');
const mysql = require('mysql');

const connection = mysql.createConnection({
  host: `${config.host}`,
  user: `${config.user}`,
  password: `${config.password}`,
  database: `${config.schema}`
});

const createFriendsTable = `CREATE TABLE FRIENDSHIPS (
  userID VARCHAR(255) AUTO_INCREMENT NOT NULL,
  // TODO
)`;