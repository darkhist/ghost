// This file defines behavior for interation with the FRIENDS Table

const connection = require('../database');

exports.search = async data => {
  try {
    data = await connection.query('SELECT * FROM FRIENDS');
  } catch (err) {
    console.error(`Something went wrong! ${err.stack}`);
  }
  return data;
};
