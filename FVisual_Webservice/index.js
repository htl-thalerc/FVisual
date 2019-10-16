var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var oracledb = require('oracledb');

var host = 'localhost';
var port = 3030;

app.use(bodyParser.json());

app.get('/', function (req, res) {
    res.send('API up and running!');
});

app.listen(port, function () {
    console.log(`API running on ${host}:${port}!`);
});