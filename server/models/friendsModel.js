'use strict';

// This file defines behavior for interation with the FRIENDSHIPS Table

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
  const results = await conn.execute('SELECT * FROM FRIENDSHIPS');

  // Return Rows from Friendship Table
  const rows = results[0];

  // TODO: Parse Results

  return rows;
}