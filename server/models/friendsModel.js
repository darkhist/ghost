'use strict';

// This file defines behavior for interation with the FRIENDSHIPS Table

const connection = require('../database');

exports.search = async data => {
  try {
    data = await connection.query('SELECT * FROM FRIENDSHIPS');
  } catch (err) {
    console.error('Something went wrong!' + err.stack);
  }
  return data;
};
