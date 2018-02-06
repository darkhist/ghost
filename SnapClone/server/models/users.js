'use strict';

const config = require('.././config.json');
const bcrypt = require('bcrypt');
const mysql = require('mysql');
const connection = mysql.createConnection({
  host: `${config.host}`,
  user: `${config.user}`,
  password: `${config.password}`,
  database: `${config.schema}`
});

let userInsertQuery = `INSERT INTO USERS (username, password, email, creationDate)
VALUES('qmsalas', 'fakepassword', 'qmsalas@iastate.edu', CURRENT_DATE())`;

connection.connect();

connection.query(userInsertQuery, (err, results) => {
  if (err) {
    console.log(err.code);
  }
  console.log("Record Inserted Successfully");
  console.log(results);
});

connection.end();