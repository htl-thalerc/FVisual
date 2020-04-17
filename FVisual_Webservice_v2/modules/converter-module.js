'use strict';

/* own modules */
const loggerModule = require('../modules/logger-module');

/* local variables */
const logger = loggerModule.loggers['Converter'];

var convertResult = function(metaData, result) {
    if (typeof metaData !== 'object' && typeof metaData !== 'string') {
        logger.warn('invalid metadata supplied');
        return null;
    }

    try {
        if (typeof metaData === 'string') {
            metaData = JSON.parse(metaData);
        }

        var resultArray = [];
        var current;
        for (let idx1 in result.rows) {
            current = JSON.parse(JSON.stringify(metaData));
            resultArray.push(generateFilledRow(current, result.rows[idx1]));
        }
        var toReturn = resultArray.map((val) => {
            return val.substring(1, val.length - 1);
        });

    } catch (ex) {
        logger.warn(ex.stack);
        return null;
    }

    return '[' + toReturn.toString() + ']';
}

function generateFilledRow(curMetaData, curRow, isNested = false) {
    for (let idx2 in curMetaData[0]) {
        let val = curRow[curMetaData[0][idx2]];
        if (typeof curMetaData[0][idx2] === 'object') {
            let arr = [];
            arr.push(curMetaData[0][idx2]);
            curMetaData[0][idx2] = generateFilledRow(arr, curRow, true);
        } else if (typeof val === 'undefined') {
            throw { message: "invalid metadata supplied", stack: "typeof metadata === undefined" };
        } else {
            curMetaData[0][idx2] = val;
        }
    }
    if (isNested)
        return curMetaData[0];
    else
        return JSON.stringify(curMetaData);
}

var convertSimpleInput = function(metaData, curBody) {
    try {
        var resultObject = {};
        resultObject = generateInputRow(metaData, curBody);
        return resultObject;
    } catch (ex) {
        logger.warn("invalid metadata supplied");
        return null;
    }
}

function generateInputRow(metaData, curBody) {
    var result = {};
    Object.keys(metaData).forEach((element) => {
        if (typeof curBody[element] === 'object') {
            result[metaData[element]] = generateInputRow(metaData[element], curBody[element]);
        } else if (typeof curBody[element] !== 'undefined') {
            result[metaData[element]] = curBody[element];
        }
    });
    return result;
}

var convertValuesToUpper = function(metadata) {
    for (var key in metadata) {
        metadata[key] = metadata[key].toUpperCase();
    }
    return '[' + JSON.stringify(metadata) + ']';
}

module.exports.convertResult = convertResult;
module.exports.convertSimpleInput = convertSimpleInput;
module.exports.convertValuesToUpper = convertValuesToUpper;