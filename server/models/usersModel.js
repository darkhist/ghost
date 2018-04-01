// This file defines behavior for interation with the USERS Table

const connection = require('../database');

exports.search = async data => {
  try {
    data = await connection.query('SELECT * FROM USERS');
  } catch (err) {
    console.error(`Something went wrong! ${err.stack}`);
  }
  return data;
};

exports.add = async (firstName, lastName, username, password, email) => {
  try {
    await connection.query(
      `INSERT INTO USERS 
    SET first_name = ?, last_name = ?, username = ?, password = ?, email = ?, creationDate = NOW()`,
      [firstName, lastName, username, password, email]
    );
  } catch (err) {
    console.error(`Something went wrong! ${err.stack}`);
  }
};

exports.getPassword = async (email, password = undefined) => {
  try {
    password = await connection.query('SELECT password FROM USERS WHERE email = ?', [email]);
  } catch (err) {
    console.error(`Something went wrong! ${err.stack}`);
  }
  // Grab the password from the returned row
  return password[0].password;
};
