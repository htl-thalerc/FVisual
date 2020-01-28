/* node modules */
var express = require('express');
var bodyParser = require('body-parser');
var ip = require("ip");

/* own modules */
const centralErrorHandler = require('./public/central_error_handler');
const securityModule = require('./modules/security-module');
const loggerModule = require('./modules/logger-module');

const baseRouters = require('./routers/base-router');
const stuetzpunktRouter = require('./routers/stuetzpunkt-router');

/* logger settings */
loggerModule.settings.Development();

/* local variables */
const HOSTNAME = ip.address();
const PORT = 3030;
var app = express();
var logger = loggerModule.loggers['Application'];

/* middlewares */
app.use(bodyParser.json());

/* public sector */
app.get('/', function(req, res) {
    res.status(200).send('API up and running!');
});

app.use('/login', securityModule.login);

/* security module */
app.use(securityModule.authenticate);

/*private sector */
app.use(baseRouters);
app.use('/stuetzpunkte', stuetzpunktRouter);

/* central error handler */
app.use(centralErrorHandler);

/* run app */
app.listen(PORT, function() {
    logger.info(`API running on ${HOSTNAME}:${PORT}!`);
    loggerModule.lineFeed();
});