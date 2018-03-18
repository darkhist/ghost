"use strict";

// This file defines behavior for interation with the USERS Table

const connection = require("../database");

exports.search = async data => {
  try {
    data = await connection.query("SELECT * FROM USERS");
  } catch (err) {
    console.error("Something went wrong!" + err.stack);
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
    console.error("Something went wrong!" + err.stack);
  }
};

exports.auth = async (email, password) => {
  try {
    await connection.query(`SELECT * FROM USERS WHERE email = ?`, [email], (err, results) => {
      if (err) {
        console.error("Error Authenticating User" + err.stack);
        return false;
      } else {
        if (results.length != 0 && results[0].password == password) {
          return true;
        }
      }
    });
  } catch (err) {
    console.error("Something went wrong!" + err.stack);
  }
};
