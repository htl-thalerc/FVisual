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
        res.status(200).send(generateToken("grafiboyjesussexyboy", "69696931", "mobile"));
        logger.debug('mobile login successful / ' + req.body.username);
    } else if (req.headers.flow == "management") {
        res.status(200).send(generateToken("grafiboyjesussexyboy", "69696931", "management"));
        logger.debug('management login successful / ' + req.body.username);
    } else {
        logger.warn("invalid flow: " + req.headers.flow);
        res.status(400).send("invalid flow");
    }
}

/* authenticate */
function authenticate(req, res, next) {
    var token = req.headers['authorization'];

    if (token == undefined || token == '') {
        res.status(401).send('Unauthorized');
        return;
    }
    var userObject = decryptToken(token);
    req.login = userObject.username;
    req.flow = userObject.flow;

    next();
}

function generateToken(username, password, flow) {
    let userObject = {
        'username': username,
        'password': password,
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