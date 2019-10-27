/* node modules */
var express = require('express');
var bodyParser = require('body-parser');

/* own modules */
const centralErrorHandler = require('./public/central_error_handler');
const securityModule = require('./modules/security-module');

const baseRouters = require('./routers/base-routers');
const adminRouter = require('./routers/admin-router');
const einsatzRouter = require('./routers/einsatz-router');
const stuetzpunktRouter = require('./routers/stuetzpunkt-router');

/* local variables */
const HOSTNAME = 'localhost';
const PORT = 3030;
var app = express();

/* central handlers for express */
app.use(bodyParser.json());
app.use(centralErrorHandler.errorHandler);

/* public sector */
app.use('/login', securityModule.login);
app.get('/', function(req, res) {
    res.status(200).send('API up and running!');
});

/* security module */
app.use(securityModule.authenticate);

/*private sector */
app.use(baseRouters);
app.use('/admins', adminRouter);
app.use('/einsaetze', einsatzRouter);
app.use('/stuetzpunkte', stuetzpunktRouter);

/* run app */
app.listen(PORT, function() {
    console.log(`API running on ${HOSTNAME}:${PORT}!`);
});