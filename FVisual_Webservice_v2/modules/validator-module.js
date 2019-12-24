'use strict';

/* own modules */
const loggerModule = require('../modules/logger-module');

/* local variables */
const logger = loggerModule.loggers['Validator'];

var paramIsInteger = function (id) {
    if (!Number.isNaN(id) && id > 0) {
        return true;
    }
    else {
        logger.warn('invalid param {id}');
        return false;
    }
}

var isValidBody = function (body, pattern) {

    if (typeof Object.keys(body).length === 'undefined') {
        logger.warn('empty body supplied');
        return false;
    } else if (Object.keys(body).length !== pattern.length) {
        logger.warn('invalid body size');
        return false;
    } else {
        try {
            Object.keys(pattern.data).forEach((element) => {
                
                Object.keys(pattern.data[element]).forEach((constraint) => {
                    if (constraint == 'isMandatory' && pattern.data[element][constraint] == true && typeof body[element] === 'undefined') {
                        logger.warn('property not found {' + element + '}');
                        throw "";
                    }
                    if (constraint == 'type') {
                        if (typeof body[element] !== pattern.data[element][constraint]) {
                            logger.warn('invalid type {' + element + '}');
                            throw "";
                        }
                    } else if (constraint == 'min-length' && typeof body[element] === 'string') {
                        if (body[element].length < pattern.data[element][constraint]) {
                            logger.warn('invalid string length {' + element + '}');
                            throw "";
                        }
                    } else if (constraint == 'min-length' && typeof body[element] === 'number') {
                        if (body[element] < pattern.data[element][constraint]) {
                            logger.warn('invalid number size {' + element + '}');
                            throw "";
                        }
                    }
                });
            });
        } catch (ex) {
            return false;
        }
        return true;
    }
}

var getAOrgsPattern = function () {
    return {
        "length": 1, "data": {
            "id": {"isMandatory": false},
            "name": {
                "isMandatory":true, "type": "string", "min-length": "3"
            }
        }
    };
}

var getAdminsPattern = function () {
    return {
        "length": 1, "data": {
            "id": {"isMandatory": false},
            "name": {
                "isMandatory":true, "type": "string", "min-length": "3"
            }
        }
    };
}

module.exports = {
    'isValidParamId': paramIsInteger,
    'isValidBody': isValidBody,
    'patterns': {
        'getAOrgsPattern': getAOrgsPattern,
        'getAdminsPattern': getAdminsPattern
    }
}