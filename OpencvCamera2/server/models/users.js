const mysql      = require('mysql');

//This is written to test the connection to my local server
const connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'pwd',
  database : 'university'
});

//starts the connection
connection.connect((err) => {
  if(err){
    console.log('Error connecting to Db');
    return;
  }
  console.log('Connection established');
  initQuery();
  createUserTable();
  connection.end();
});

const initQuery = async () => {
  await connection.query("USE university");
}

const createUserTable = async () => {
  await connection.query(
    "CREATE TABLE `USERS` (userID INT NOT NULL AUTO_INCREMENT, username VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, creationDate DATE, PRIMARY KEY(userID));"
  );
  console.log("Table Successfully Created!");
}
