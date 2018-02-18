'use strict';

const config = require('.././config.json');
const mysql = require('mysql');

const connection = mysql.createConnection({
  host: `${config.host}`,
  user: `${config.user}`,
  password: `${config.password}`,
  database: `${config.schema}`
});

const userInsertQuery = `INSERT INTO USERS (username, password, email, creationDate)
VALUES('qmsalas', 'fakepassword', 'qmsalas@iastate.edu', CURRENT_DATE())`;

connection.connect();

connection.query(userInsertQuery, (err, results) => {
  if (err) {
    console.log(err.stack);
  }
  console.log("Record Inserted Successfully");
  console.log(results);
});

connection.end();