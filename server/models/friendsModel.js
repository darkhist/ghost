'use strict';

// This file defines behavior for interation with the Friendship Table

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

  // Query Friendship Table
  const results = await conn.execute('SELECT * FROM Friendship');

  // Return Rows from Friendship Table
  return results[0];
}