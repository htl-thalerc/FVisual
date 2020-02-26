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

var isValidSubQuery = function (query, pattern, emptyAllowed) {
    if (Object.keys(query).length === 0 && emptyAllowed) {
        return true;
    } else if (Object.keys(query).length !== pattern.length) {
        logger.warn('invalid subquery size');
        return false;
    } else {
        try {
            Object.keys(pattern.data).forEach((element) => {
                Object.keys(pattern.data[element]).forEach((constraint) => {
                    if (constraint == 'isMandatory' && pattern.data[element][constraint] == true && typeof query[element] === 'undefined') {
                        logger.warn('subquery property not found {' + element + '}');
                        throw "";
                    } else if (constraint == 'type') {
                        if (pattern.data[element][constraint] == 'number') {
                            query[element] = parseInt(query[element]);
                            if (Number.isNaN(query[element])) {
                                logger.warn('invalid subquery type {' + element + '}');
                                throw "";
                            }
                        } else if (typeof query[element] !== pattern.data[element][constraint]) {
                            logger.warn('invalid subquery type {' + element + '}');
                            throw "";
                        }
                    } else if (constraint == 'min-length' && typeof query[element] === 'string') {
                        if (query[element].length < pattern.data[element][constraint]) {
                            logger.warn('invalid subquery string length {' + element + '}');
                            throw "";
                        }
                    } else if (constraint == 'min-length' && typeof query[element] === 'number') {

                        if (query[element] < pattern.data[element][constraint]) {
                            logger.warn('invalid subquery number size {' + element + '}');
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
                        logger.warn('body property not found {' + element + '}');
                        throw "";
                    }
                    if (constraint == 'type') {
                        if (pattern.data[element][constraint] == 'number') {
                            body[element] = parseInt(body[element]);
                            if (Number.isNaN(body[element])) {
                                logger.warn('invalid body type {' + element + '}');
                                throw "";
                            }
                        } else if (pattern.data[element][constraint] == 'boolean') {
                            switch (body[element]) {
                                case 'true':
                                    body[element] = 'true';
                                    break;
                                case 'false':
                                    body[element] = 'false';
                                    break;
                                default:
                                    logger.warn('invalid body type {' + element + '}');
                                    throw "";
                            }
                        } else if (typeof body[element] !== pattern.data[element][constraint]) {
                            logger.warn('invalid body type {' + element + '}');
                            throw "";
                        }
                    } else if (constraint == 'min-length' && typeof body[element] === 'string') {
                        if (body[element].length < pattern.data[element][constraint]) {
                            logger.warn('invalid body string length {' + element + '}');
                            throw "";
                        }
                    } else if (constraint == 'min-length' && typeof body[element] === 'number') {
                        if (body[element] < pattern.data[element][constraint]) {
                            logger.warn('invalid body number size {' + element + '}');
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
            "id": { "isMandatory": false },
            "NAME": {
                "isMandatory": true, "type": "string", "min-length": "3"
            }
        }
    };
}

var getMitgliedPattern = function () {
    return {
        "length": 7, "data": {
            "id": {
                "isMandatory": false
            },
            "ID_DIENSTGRAD": {
                "isMandatory": true, "type": "number", "min-length": "0"
            },
            "ID_STUETZPUNKT": {
                "isMandatory": true, "type": "number"
            },
            "VORNAME": {
                "isMandatory": true, "type": "string", "min-length": "2"
            },
            "NACHNAME": {
                "isMandatory": true, "type": "string", "min-length": "3"
            },
            "USERNAME": {
                "isMandatory": true, "type": "string", "min-length": "3"
            },
            "PASSWORD": {
                "isMandatory": true, "type": "string", "min-length": "3"
            },
            "ISADMIN": {
                "isMandatory": true, "type": "boolean"
            }
        }
    };
}

var getStuetzpunktPattern = function () {
    return {
        "length": 5, "data": {
            "id": {
                "isMandatory": false
            },
            "NAME": {
                "isMandatory": true, "type": "string", "min-length": "3"
            },
            "ORT": {
                "isMandatory": true, "type": "string", "min-length": "3"
            },
            "PLZ": {
                "isMandatory": true, "type": "number", "min-length": "3"
            },
            "STRASSE": {
                "isMandatory": true, "type": "string", "min-length": "3"
            },
            "HAUSNR": {
                "isMandatory": true, "type": "string", "min-length": "1"
            }
        }
    };
}

var getFahrzeugPattern = function(){
    return {
        "length": 1, "data": {
            "id": {
                "isMandatory": false
            },
            "BEZEICHNUNG": {
                "isMandatory": true, "type": "string", "min-length": "3"
            },
            "ID_STUETZPUNKT": {
                "isMandatory": false
            }
        }
    };
}

var getAdminsPattern = function () {
    return {
        "length": 1, "data": {
            "id": {
                "isMandatory": false
            },
            "name": {
                "isMandatory": true, "type": "string", "min-length": "3"
            }
        }
    };
}

var getSubQueryNamePattern = function () {
    return {
        "length": 1, "data": {
            "name": {
                "isMandatory": true, "type": "string", "min-length": "3"
            }
        }
    };
}

var getSubQueryEinsatzPattern = function(){
    return{
        "length":2, "data":{
            "name": {
                "isMandatory": true, "type": "string", "min-length": "3"    
            },
            "zeit": {
                "isMandatory": true
            }
        }
    }
}

var getSubQueryIdPattern = function () {
    return {
        "length": 1, "data": {
            "id": {
                "isMandatory": true, "type": "number", "min-length": "0"
            }
        }
    };
}

module.exports = {
    'isValidParamId': paramIsInteger,
    'isValidSubQuery': isValidSubQuery,
    'isValidBody': isValidBody,
    'patterns': {
        'getAOrgsPattern': getAOrgsPattern,
        'getMitgliedPattern': getMitgliedPattern,
        'getStuetzpunktPattern': getStuetzpunktPattern,
        'getFahrzeugPattern': getFahrzeugPattern,
        'getAdminsPattern': getAdminsPattern,
        'getSubQueryNamePattern': getSubQueryNamePattern,
        'getSubQueryEinsatzPattern': getSubQueryEinsatzPattern,
        'getSubQueryIdPattern': getSubQueryIdPattern
    }
}