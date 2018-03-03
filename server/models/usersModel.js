'use strict';

// This file defines behavior for interation with the USERS Table

const config = require('.././config.json');

exports.main = async () => {
  const mysql = require('mysql2/promise');

  // Establish Database Connection
  const conn = await mysql.createConnection({
    host: `${config.host}`,
    user: `${config.user}`,
    password: `${config.password}`,
    database: `${config.schema}`
  });

  // Query USERS Table
  const results = await conn.execute('SELECT * FROM USERS');

  // Return Rows from USERS Table
  return results[0];
}