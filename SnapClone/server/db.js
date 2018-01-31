'use strict';

const db = require('./config.json');
const mysql = require('mysql');
const connection = mysql.createConnection({
  host: `${db.host}`,
  user: `${db.user}`,
  password: `${db.password}`
});

connection.connect((err) => {
  if (err) {
    console.log("Error Connecting to MySQL") + err.stack;
    return;
  }
  console.log("Successfully Connected!");
  initQuery();
  createUserTable();
  connection.end();
});

const initQuery = async () => {
  await connection.query("USE db309vc4");
}

const createUserTable = async () => {
  await connection.query(
    "CREATE TABLE `USERS` (userID INT NOT NULL AUTO_INCREMENT, username VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, creationDate DATE, PRIMARY KEY(userID));"
  );
  console.log("Table Successfully Created!");
}