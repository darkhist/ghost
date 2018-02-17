const mysql      = require('mysql');

//This is written to test the connection to my local server
const connection = mysql.createConnection({
  //Note host for team database = mysql.cs.iastate.edu
  //host    : 'mysql.cs.iastate.edu'
  host     : 'localhost',
  //Note user for team database = dbu309vc4
  //user    : 'root'
  user     : 'dbu309vc4',
  //Note password for team database = A#R4aSs!
  //password    : 'A#R4aSs!'
  password : 'xxxxxx',
  //Note password for team database = db309vc4
  //password    : 'db309vc4'
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

//sets user to
const initQuery = async () => {
  await connection.query("USE university");
}

const createUserTable = async () => {
  await connection.query(
    "CREATE TABLE `USERS` (userID INT NOT NULL AUTO_INCREMENT, username VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, creationDate DATE, PRIMARY KEY(userID));"
  );
  console.log("Table Successfully Created!");
}
