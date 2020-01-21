'use strict';

/* node modules */
const CryptoJS = require('crypto-js');

/* own modules */
const loggerModule = require('../modules/logger-module');

/* local variables */
const logger = loggerModule.loggers['Security-Module'];
const SECRET = 'jdlsjflksajflkjflksjeflaskjflkdflkasjflks';

/* login */
function login(req, res) {
    if (req.headers.flow == "mobile") {
        res.status(200).send(generateToken(req.body.username, "mobile"));
        logger.debug('mobile login successful / ' + req.body.username);
    } else if (req.headers.flow == "management") {
        res.status(200).send(generateToken(req.body.username, "management"));
        logger.debug('management login successful / ' + req.body.username);
    } else {
        logger.warn("invalid flow: " + req.headers.flow);
        res.status(400).send("invalid flow");
    }
}

/* authenticate */
function authenticate(req, res, next) {
    /*var token = req.headers['authorization'];

    if (token == undefined || token == '') {
        logger.warn('Unauthorized login');
        logger
        res.status(401).send('Unauthorized');
        return;
    }
    try {
        var userObject = decryptToken(token);
    } catch (ex) {
        logger.error(ex.message);
        loggerModule.lineFeed();
        res.status(401).send('Unauthorized');
        return;
    }
    req.login = userObject.username;
    req.flow = userObject.flow;*/

    next();
}

function generateToken(username, flow) {
    let userObject = {
        'username': username,
        'flow': flow
    }
    return encrypt(JSON.stringify(userObject));
}

function decryptToken(token) {
    let userString = decrypt(token);
    return JSON.parse(userString);
}

function encrypt(plainText) {
    var b64 = CryptoJS.AES.encrypt(plainText, SECRET).toString();
    var e64 = CryptoJS.enc.Base64.parse(b64);
    var eHex = e64.toString(CryptoJS.enc.Hex);
    return eHex;
}

function decrypt(cipherText) {
    var reb64 = CryptoJS.enc.Hex.parse(cipherText);
    var bytes = reb64.toString(CryptoJS.enc.Base64);
    var decrypt = CryptoJS.AES.decrypt(bytes, SECRET);
    var plain = decrypt.toString(CryptoJS.enc.Utf8);
    return plain;
}

/* exports */
module.exports.login = login;
module.exports.authenticate = authenticate;