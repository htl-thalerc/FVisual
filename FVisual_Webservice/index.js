const HOSTNAME = 'localhost';
const PORT = 3030;

var express = require('express');

/* node modules */
var bodyParser = require('body-parser');
var oracledb = require('oracledb');

/* own modules */
const centralErrorHandler = require('./public/central_error_handler');
const securityModule = require('./modules/security-module');

const baseRouters = require('./routers/base-routers');
const adminRouter = require('./routers/admin-router');
const einsatzRouter = require('./routers/einsatz-router');
const stuetzpunktRouter = require('./routers/stuetzpunkt-router');

var app = express();

app.use(bodyParser.json());
app.use(centralErrorHandler.errorHandler);

//public
app.use('/login', securityModule.login);
app.use('/logout', securityModule.logout);
app.get('/', function (req, res) {
    res.status(200).send('API up and running!');
});

app.use(securityModule.authenticate);

//private
app.use(baseRouters);
app.use('/admins', adminRouter);
app.use('/einsaetze', einsatzRouter);
app.use('/stuetzpunkte', stuetzpunktRouter);

app.listen(PORT, function () {
    console.log(`API running on ${HOSTNAME}:${PORT}!`);
});