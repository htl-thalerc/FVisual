var express = require('express');
var app = express();

app.get('/', function (req, res) {
    res.send('API up and running!');
});

app.listen(3030, function () {
    console.log('API running on port 3030!');
});